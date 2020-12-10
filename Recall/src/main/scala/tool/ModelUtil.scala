package tool

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class ModelUtil(spark:SparkSession) {

  /**
    * 获取用户物品打分表
    */
  def getUserItemRating: DataFrame = {

    val data = spark.sql(
      "select * from dws.dws_user_item_rating"
    )
    data
  }

  // 把召回策略生成的候选集存储奥HBase
  def saveRecall(recommend:DataFrame, cell:String):Unit = {
    // 将同一个uid的推荐item_id放到一个数组里
    val recomList = recommend.groupBy(col("uid"))
      .agg(collect_list(col("item_id")))
      .withColumnRenamed("collect_list(item_id)", "item_id")
      .select(col("uid"), col("item_id"))

    // HBase

  }

}

object ModelUtil {

  def apply(spark: SparkSession): ModelUtil = new ModelUtil(spark)
}
