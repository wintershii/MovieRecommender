package tool

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DoubleType

class ModelUtil(spark:SparkSession) extends Serializable {

  def getFeature() : DataFrame = {
    /**
      * 用户特征
      * user_feature
      *
      * 用户行为分值表
      * ods.ods_user_behavior
      */

    import spark.implicits._
    val hbaseUtil = new HBaseUtil(spark)
    val userTable = "user_feature"
    val itemTable = "item_feature"
    val cf = "profile"
    val column = "feature"

    val actions = spark.sql(
      "select uid, mid as item_id, sum(click) + sum(score) + sum(collect) as rating " +
        " from ods.ods_user_behavior " +
        " group by uid, mid"
    )

    //val list = actions.collectAsList()
    val userFeature = hbaseUtil.getData(userTable, cf, column)
      .withColumnRenamed("key", "uid")
      .withColumnRenamed("value", "userFeature")
//        userFeature.show(false)
    val itemFeature = hbaseUtil.getData(itemTable, cf, column)
      .withColumnRenamed("key", "item_id")
      .withColumnRenamed("value", "itemFeature")
    //    itemFeature.show()

    val rs = actions.join(userFeature, "uid")
      .join(itemFeature, "item_id")
      .select("uid", "item_id", "rating", "userFeature", "itemFeature")
      .withColumn("userFeature", col("userFeature").cast(DoubleType))
      .withColumn("itemFeature", col("itemFeature").cast(DoubleType))


//    rs.show(false)
    rs

  }
}
