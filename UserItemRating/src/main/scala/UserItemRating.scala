import java.util.Calendar

import org.apache.spark.sql.SparkSession

object UserItemRating {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DATE)

    // 注册sigmoid函数为udf
    spark.udf.register("scaler", (score:Double) => 2*1/(1+math.exp(-score)))

    val scaler = spark.sql("select uid, " +
      "item_id, " +
      "round(scaler(sum(click_score) + sum(grade_score) + sum(collect_score)), 3) as rating " +
      "from dws.dws_behavior_score " +
      "where year='" + year + "' " +
      "and month='" + month + "' " +
      "and day='" + day + "' " +
      "group by uid, item_id")

//    scaler.show()
    scaler.createOrReplaceTempView("scaler")

    spark.sql("insert overwrite table " +
      "dws.dws_user_item_rating partition(year='" + year +"', month='" + month + "', day='"+ day +"') " +
      "select * from scaler")

  }
}
