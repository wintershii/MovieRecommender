import java.util.Calendar

import org.apache.spark.sql.SparkSession

object BehaviorScore {
  def main(args: Array[String]): Unit = {

    /**
      * 用户行为数值*时间权重
      */
    val clickWeight = 0.33
    val scoreWeight = 0.33
    val collectWeight = 0.33

    val spark = SparkSession.builder()
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DATE)
    var dayStr = String.valueOf(day)
    if (dayStr.length == 1) {
        dayStr = "0" + dayStr
    }
//    println("alter table ods.ods_user_behavior add partition(year='" + year +"', month='" + month +"', day='" + dayStr +"') " +
//      "location '/hive/external/ods/behavior/year=" + year +
//      "/month=" + month +
//      "/day=" + dayStr + "'")

    // 在ods中创建当天新的分区
    spark.sql("alter table ods.ods_user_behavior add partition(year='" + year +"', month='" + month +"', day='" + dayStr +"') " +
      "location '/hive/external/ods/behavior/year=" + year +
      "/month=" + month +
        "/day=" + dayStr + "'")


    val behaviorScore = spark.sql(
      "select uid, " +
        "mid as item_id," +
        "round(sum(click) * " + clickWeight + " * (1-avg(interva)/24),3) " +
        "as click_score, " +
        "round(sum(score) * " + scoreWeight + " * (1-avg(interva)/24),3) " +
        "as grade_score, " +
        "round(sum(collect) * " + collectWeight + " * (1-avg(interva)/24),3) " +
        "as collect_score " +
        "from ods.ods_user_behavior " +
        " where year = " + year +
        " and month = " + month +
        " and day = " + dayStr +
        " group by uid, mid "
    )

//    behaviorScore.show()
    behaviorScore.createOrReplaceTempView("behavior_score")

    spark.sql("insert overwrite table " +
      "dws.dws_behavior_score partition(year='" + year +"', month='" + month + "', day='"+ day +"') " +
      "select * from behavior_score")
  }
}
