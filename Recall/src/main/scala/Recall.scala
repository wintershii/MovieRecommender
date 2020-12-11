import org.apache.spark.broadcast.Broadcast
import org.apache.spark.ml.recommendation.ALSModel
import org.apache.spark.sql.{DataFrame, SparkSession}
import tool.ModelUtil

object Recall {

  val colALS = "als"
  val colItem2Item = "item2item"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Recall")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    // 数据处理
    val modelUtil = ModelUtil(spark)
    val data = modelUtil.getUserItemRating

    // 生成候选集
    // 召回1：ALS
    val als = ALSRecall(data)
    //迭代次数 10次左右
    val maxIter = 10
    // 正则 防止过拟合， 取数据不需要太大
    val reg = Array(0.1, 0.0001)
    // 特征矩阵维度 10 - 200， 维度越大，计算量越大
    val rank = Array(10, 30)
    // 学习率
    val alpha = Array(2.0, 3.0)
    val model:ALSModel = als.getModel(maxIter, rank, reg, alpha)

    val alsRecallData = als.getALSRecall(model, spark)

    // 存储候选集
    modelUtil.saveRecall(alsRecallData, colALS)

    // 召回2：基于物品协同过滤 ItemCF
    val item2Item = new Item2ItemRecall()
    // 获取物品相似度矩阵 余弦相似度
    val itemSim = item2Item.getCosSim(model, spark)
    // 广播相似度矩阵
    val itemCosSimBd:Broadcast[DataFrame] = spark.sparkContext.broadcast(itemSim)

    /**
      * 耗时
      */
    val item2ItemRecallData = item2Item.getItem2ItemRecall(data, itemCosSimBd, spark)

    // 存储候选集
    modelUtil.saveRecall(item2ItemRecallData, colItem2Item)
//    alsRecallData.show()
//    item2ItemRecallData.show()
  }
}
