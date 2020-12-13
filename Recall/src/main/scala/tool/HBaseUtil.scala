package tool

import java.security.MessageDigest

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf

class HBaseUtil(spark:SparkSession) extends Serializable {

  // 读取数据
  def getData(tableName:String, cf:String, column:String) : DataFrame = {
    val hbaseConfig = HBaseConfiguration.create()
    val sc = spark.sparkContext
    hbaseConfig.set(TableInputFormat.INPUT_TABLE, tableName)
    val hbaseRDD:RDD[(ImmutableBytesWritable, Result)]
    = sc.newAPIHadoopRDD(hbaseConfig, classOf[TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])

    import  spark.implicits._
    val rs = hbaseRDD.map(_._2)
      .map(r => {
        (r.getValue(Bytes.toBytes(cf), Bytes.toBytes(column)))
      }).toDF("value")

    rs
  }

  // 写入数据 cf列族， column列名
  def putData(tableName:String, data:DataFrame, cf:String, column:String) : Unit = {
    val hbaseConfig = HBaseConfiguration.create()
    val sc = spark.sparkContext
    // 初始化Job，设置输出格式TableOutputFormat，hbase.mapred.jar
    val jobConf = new JobConf(hbaseConfig, this.getClass)
    jobConf.setOutputFormat(classOf[TableOutputFormat])
    jobConf.set(TableOutputFormat.OUTPUT_TABLE, tableName)

    val _data = data.rdd.map( x => {
      val uid = x.get(0)
      val itemList = x.get(1)
      val _rowKey = rowKeyHash(uid.toString, "uid")
      val put = new Put(Bytes.toBytes(_rowKey))
      put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(itemList.toString))
      (new ImmutableBytesWritable, put)
    })
    _data.saveAsHadoopDataset(jobConf)
  }


  def rowKeyHash(key:String, tp:String) : String = {

    var md5:MessageDigest = null

    md5 = MessageDigest.getInstance("MD5")

    // rowKey组成是 时间戳 + uid
    val rowKey = tp + ":" + key
    val encode = md5.digest(rowKey.getBytes())

    encode.map("%02x".format(_)).mkString
  }
}

object HBaseUtil{
  def apply(spark:SparkSession): HBaseUtil = new HBaseUtil(spark)
}