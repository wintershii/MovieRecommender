import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.{ALS, ALSModel}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, explode}
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.conf.Configuration

import scala.collection.mutable.ArrayBuffer

class ALSRecall(data: DataFrame) {

  // 计算不同参数的模型 1.最大迭代次数 2.特征矩阵维度 3.正则因子 4.隐式反馈参数
  def getModel(maxIter:Int,
               rankArray:Array[Int],
               regArray:Array[Double],
               alphaArray:Array[Double]):ALSModel = {

    val format = new SimpleDateFormat("yyyy-MM-dd")
    val date = format.format(new Date())
    val savePath = "/model/als_model/" + date
    val hadoopConf = new Configuration()
    hadoopConf.addResource("hdfs-site.xml")
    hadoopConf.addResource("core-site.xml")
    val hdfs = FileSystem.get(hadoopConf)
//    if (hdfs.exists(new Path(savePath))) {
//      val model = ALSModel.read.load(savePath)
//      return model
//    }

    val Array(training, test) = data.randomSplit(Array(0.8, 0.2))
    var map = Map[Double, ALSModel]()
    // 均方根误差
    val listMSE = ArrayBuffer[Double]()

    for (rank <- rankArray;
         reg <- regArray;
         alpha <- alphaArray){
      val als = new ALS()
        .setMaxIter(maxIter)
        .setUserCol("uid")
        .setItemCol("item_id")
        .setRatingCol("rating")
        .setRank(rank)
        .setRegParam(reg)
        .setImplicitPrefs(true)
        .setAlpha(alpha)

      val model: ALSModel = als.fit(training)
      // 冷启动处理
      model.setColdStartStrategy("drop")
      val predict = model.transform(test)

      /**
        * |uid |item_id |rating |prediction|
        *
        */
      val rmse = getEvaluate(predict)
      listMSE += rmse
      map += (rmse -> model)
    }

    val minMSE = listMSE.min
    val bestModel = map(minMSE)

    // 存放模型到HDFS
    // 按日期存放
    bestModel.write.overwrite().save(savePath)

    bestModel

  }

  // 获取模型评估值
  // 均方根误差
  def getEvaluate(predict: DataFrame):Double = {

    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")

    val rmse = evaluator.evaluate(predict)
    rmse

  }

  // 获取召回候选集结果
  def getALSRecall(model:ALSModel, spark:SparkSession):DataFrame ={
    val list = model.recommendForAllUsers(20)

    /**
      * uid recommendations
      * 2    [[34, 0.33],[233. 0.422]]
      *
      * uid item_id
      * 2   34
      * 2   233
      *
      * uid recommend
      * 2   [34, 0.33]
      * 2   [233. 0.422]
      */
    import spark.implicits._
    val recallData = list.withColumn("recommend",
      explode(col("recommendations")))
      .drop("recommendations ")
      .select("uid", "recommend")
      .rdd.map(row=>{
        val uid = row.getInt(0)
        val recommend = row.getStruct(1)
        val item_id = recommend.getAs[Int]("item_id")
        (uid, item_id)
    }).toDF("uid", "item_id")

    recallData
  }




}

object ALSRecall {
  def apply(data: DataFrame): ALSRecall = new ALSRecall(data)
}
