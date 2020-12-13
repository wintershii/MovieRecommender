import java.util.Date

import org.apache.spark.sql.SparkSession
import tool.{HBaseUtil, TimeUtil}
import org.apache.spark.sql.functions._

object UserFeature {

  private val userFeatureTableName = "user_feature"

  private val userFeatureCf = "profile"

  private val userFeatureCol = "feature"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("UserFeature")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    val actions = spark.sql(
      "select * from ods.ods_user_behavior"
    )

    import spark.implicits._
    val userFeatureWeight = actions.rdd.map(row => {
      val uid = Integer.parseInt(row.getString(0))
//      val item_id = Integer.parseInt(row.getString(1))
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
      (uid, weight)
    }).toDF("uid", "weight")

    val userFeatureAvgWeight = userFeatureWeight.groupBy("uid").agg(avg("weight"))
      .withColumnRenamed("avg(weight)", "weight")

//    println(userFeatureAvgWeight.collect())
//    userFeatureAvgWeight.show(false)
    val hbaseUtil = new HBaseUtil(spark)
    hbaseUtil.putData(userFeatureTableName, userFeatureAvgWeight, userFeatureCf, userFeatureCol, "uid")
  }



}
