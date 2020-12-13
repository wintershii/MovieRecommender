package tool

import org.apache.spark.sql.{DataFrame, SparkSession}

class ModelUtil(spark:SparkSession) extends Serializable {

  def getFeature() : Unit = {
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
      "select uid, mid, sum(click), sum(score), sum(collect) " +
        " from ods.ods_user_behavior " +
        " group by uid, mid"
    )
//    val userStart = hbaseUtil.rowKeyHash("4", "uid")
//    val userEnd = hbaseUtil.rowKeyHash("4", "uid")
//    val userFeature = hbaseUtil.scanData(userTable, cf, column, userStart, userEnd)
//    userFeature.show()
//
//    val itemStart = hbaseUtil.rowKeyHash("2", "item_id")
//    val itemEnd = hbaseUtil.rowKeyHash("2", "item_id")
//    val itemFeature = hbaseUtil.scanData(itemTable, cf, column, itemStart, itemEnd)
//    itemFeature.show()

//    actions.show(false)

    if (spark == null) {
      println("空 sparkcontext22")
      return
    }

    val rs = actions.rdd.map(row => {

      val uid = row.getString(0)
      val item_id = row.getString(1)
      val click = row.getLong(2)
      val score = row.getLong(3)
      val collect = row.getLong(4)
      val userStart = hbaseUtil.rowKeyHash(uid, "uid")
      val userEnd = hbaseUtil.rowKeyHash(uid, "uid")
      val itemStart = hbaseUtil.rowKeyHash(item_id, "item_id")
      val itemEnd = hbaseUtil.rowKeyHash(item_id, "item_id")
      println(userStart + ":" + itemStart)
      val userFeature = hbaseUtil.scanData(userTable, cf, column, userStart, userEnd)
      val itemFeature = hbaseUtil.scanData(itemTable, cf, column, itemStart, itemEnd)
      var userValue = 0.0
      var itemValue = 0.0
      userFeature.foreach(v => {
        if (v.getString(0) != null) {
          userValue = v.getString(0).toDouble
        }
      })
      itemFeature.foreach(v => {
        if (v.getString(0) != null) {
          itemValue = v.getString(0).toDouble
        }
      })
      (click, score, collect, userValue, itemValue)
   }).toDF("click", "score", "collect", "userFeature", "itemFeature")
    rs.show(false)
    rs
  }
}
