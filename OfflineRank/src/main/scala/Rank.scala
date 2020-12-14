import org.apache.spark.sql.SparkSession
import tool.{HBaseUtil, ModelUtil}

object Rank {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("OfflineRank")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()



//    val modelUtil = new ModelUtil(spark)
//    val training = modelUtil.getFeature

//    training.show(false)

    val hbaseUtil = new HBaseUtil(spark)
    val recall = hbaseUtil.getRecallData("history_rs_recall", "recall", "item2item")
        .withColumnRenamed("value", "features")
    recall.show()

//    val lr = new LRModel
//    val lrModel = lr.getModel(training, spark)
////
//    val rank = lr.getRank(lrModel, spark)
//    rank.show(false)
  }
}
