package tool

import java.text.SimpleDateFormat
import java.util.Date

class TimeUtil {

  def DateConvertTimestamp(date:String) : Long = {
    val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val dt = fm.parse(date)
    val timestamp : Long = dt.getTime
    timestamp
  }

  // 计算时间衰减系数
  def getTimeAlpha(time: Long) : Double = {
    val now : Date = new Date()
    val old : Date = new Date(time)
    val interval = (now.getTime - old.getTime) / (60*60*24*1000)
    val timeAlpha = 1 / (Math.log(interval) + 1)
    timeAlpha
  }
}

object TimeUtil {


  def main(args: Array[String]): Unit = {
    def apply: TimeUtil = new TimeUtil()
    val tu = new TimeUtil
    println(tu.DateConvertTimestamp("2020-12-11 20:00:00"))
  }
}
