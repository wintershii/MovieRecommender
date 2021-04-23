import org.apache.spark.sql.SparkSession

object all_rating {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    //val df = spark.sql("select uid, item_id, avg(rating) from dws.dws_user_item_rating group by uid, item_id")
    val df = spark.sql(" select uid, mid, (case when score > 3 or collect > 1 or click > 5 then 1 else 0 end) as label " +
                        "from " +
                        "(select uid, mid, sum(click) as click, sum(score) as score, sum(collect) as collect from ods.ods_user_behavior group by uid, mid)")
    //df.write.format("com.databricks.spark.csv").save("file:///Users/mirac/Desktop/SparrowRecSys/TFRecModel/src/com/sparrowrecsys/offline/tensorflow/real_train")
    df.coalesce(1).write.option("header", true).csv("file:///Users/mirac/Desktop/MovieRecommender/ranker/real_train")
    df.show()
  }
}
