import com.github.fommil.netlib.F2jBLAS
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.ml.recommendation.ALSModel
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import scala.collection.mutable

class Item2ItemRecall extends Serializable {

  def getCosSim(model:ALSModel, spark:SparkSession): DataFrame = {
    val _itemFactors = model.itemFactors

    /**
      *  item_id   factors
      *  3        [0.42, 0.23, 0.42]
      */
    import spark.implicits._
    val itemFactors = _itemFactors.as[(Int, Array[Float])]
      .mapPartitions(_.grouped(4096))

    val itemSim = itemFactors.crossJoin(itemFactors)
      .as[(Seq[(Int, Array[Float])], Seq[(Int, Array[Float])])]
      .flatMap{x => x match {
        case (itF_1, itF_2) =>
          val size_1 = itF_1.size
          val size_2 = itF_2.size
          // 物品相似度矩阵
          val outPut = new Array[(Int, Int, Float)](size_1 * size_2)
          val pq = mutable.PriorityQueue[(Int, Float)]()
          val operator = new F2jBLAS
//          val rank = model.rank
          val rank = size_2
          var i = 0
          itF_1.foreach {
            case (item1, item1F) =>
              itF_2.foreach{
                case (item2, item2F) =>
                  // 计算余弦相似度
                  val sim = cosinSim(operator, item1F, item2F, rank)
                  pq.enqueue((item2, sim))
              }
              pq.foreach {
                case (item2, cosSim) =>
                  outPut(i) = (item1, item2, cosSim)
                  i += 1
              }
              pq.clear()
          }

          outPut.toSeq
      }}.toDF("item_id_1", "item_id_2", "sim")

    itemSim
  }

  // 获取基于物品协同过滤的召回
  def getItem2ItemRecall(data:DataFrame, itemCosSim:Broadcast[DataFrame], spark:SparkSession):DataFrame = {
    val itemSim = itemCosSim.value

    /**
      * uid, item_id rating
      *
      * item_id1, item_id_2 sim
      */
    val interest = data.join(itemSim, data("item_id") === itemSim("item_id_2"))
      .select(
        data("uid"),
        data("item_id"),
        itemSim("item_id_1"),
        itemSim("item_id_2"),
        (data("rating") * itemSim("sim")).alias("interest")
      )

    // group by
    val recallData = interest.groupBy(col("uid"), col("item_id_1"))
      .agg(sum(col("interest")))
      .withColumnRenamed("sum(interest)", "recom")
      .withColumnRenamed("item_id_1", "item_id")
      .select(col("uid"), col("item_id"))
      .orderBy(desc("recom"))

    recallData
  }


  def cosinSim(operator:F2jBLAS, itemF1:Array[Float], itemF2:Array[Float],rank:Int):Float = {
    // 内积
    val i1 = operator.sdot(rank, itemF1,1, itemF2, 1)
    // itemF1模
    val i2 = operator.snrm2(rank, itemF1, 1)
    // itemF2模
    val i3 = operator.snrm2(rank, itemF2, 1)

    i1 / i2 * i3
  }

}

object Item2ItemRecall {
  def apply: Item2ItemRecall = new Item2ItemRecall()
}
