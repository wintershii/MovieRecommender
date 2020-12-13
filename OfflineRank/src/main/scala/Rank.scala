import org.apache.spark.sql.SparkSession
import tool.ModelUtil

object Rank {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("OfflineRank")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()



    val modelUtil = new ModelUtil(spark)
    val training = modelUtil.getFeature

//    training.show(false)

//    val lr = new LRModel
//    val lrModel = lr.getModel(training, spark)
//
//    val rank = lr.getRank(lrModel, spark)

  }
}
