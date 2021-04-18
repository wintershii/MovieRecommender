package tool

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class ModelUtil(spark:SparkSession) {

  val rsRecall = "history_rs_recall"

  val cf = "recall"

  /**
    * 获取用户物品打分表
    */
  def getUserItemRating: DataFrame = {

    val data = spark.sql(
      "select cast(uid as int), cast(item_id as int), cast(rating as double) from dws.dws_user_item_rating"
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
//    val hbase = new HBaseUtil(spark)
    putData(rsRecall, recomList, cell)

  }

  def putData(tableName:String, data:DataFrame, column:String) : Unit = {
    val sc = spark.sparkContext
    val key_predix = "recall_%s_%d"
    val list : List[String] = List()
    data.rdd.map( x => {
      val uid = x.get(0)
      val itemList = x.getList(1)
      val redis = GetJedisConn.getJedis()
//        redis.lpush(uid.toString, itemList.)\

      import scala.collection.JavaConverters._
      for (data <- itemList.toArray) {
        redis.lpush(key_predix.format(column, uid), data.toString)
      }
//      redis.set(key_predix.format(column, uid), itemList.toString)
      redis.close()
    }).count()

  }

}

object ModelUtil {

  def apply(spark: SparkSession): ModelUtil = new ModelUtil(spark)

  def main(args: Array[String]): Unit = {
//    val jedis = GetJedisConn.getJedis()
//    jedis.set("jedix", "123")
    val a = "als"
    val b = 12345
    println("recall_%s_%d".format(a, b))
  }
}
