import UserFeature.{userFeatureCf, userFeatureCol, userFeatureTableName}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.avg
import tool.{HBaseUtil, TimeUtil}

object ItemFeature {

  private val itemFeatureTableName = "item_feature"

  private val itemFeatureCf = "profile"

  private val itemFeatureCol = "feature"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ItemFeature")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    val actions = spark.sql(
      "select * from ods.ods_user_behavior"
    )

    import spark.implicits._
    val itemFeatureWeight = actions.rdd.map(row => {
//      val uid = Integer.parseInt(row.getString(0))
      val item_id = Integer.parseInt(row.getString(1))
      val year = row.getString(6)
      val month = row.getString(7)
      val day = row.getString(8)
      val hour = 24 - row.getInt(5)

      val click = row.getInt(2)
      val score = row.getInt(3)
      val collect = row.getInt(4)
      val date = year + "-" + month + "-" + day + " " + hour + ":00:00"
      val timeUtil = new TimeUtil
      val timestamp : Long = timeUtil.DateConvertTimestamp(date)

      val alpha = timeUtil.getTimeAlpha(timestamp)
      val weight = (click + score + collect) * alpha
      (item_id, weight)
    }).toDF("item_id", "weight")

    val itemFeatureAvgWeight = itemFeatureWeight.groupBy("item_id").agg(avg("weight"))
      .withColumnRenamed("avg(weight)", "weight")

//    itemFeatureAvgWeight.show(false)

    val hbaseUtil = new HBaseUtil(spark)
    hbaseUtil.putData(itemFeatureTableName, itemFeatureAvgWeight, itemFeatureCf, itemFeatureCol, "item_id")

  }
}
