import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{DataFrame, SparkSession}
import tool.HBaseUtil

class LRModel {

  def getModel(training:DataFrame, spark:SparkSession) : LogisticRegressionModel = {

    val vector = new VectorAssembler()
      .setInputCols(Array("user_feature", "item_feature"))
      .setOutputCol("features")

    val trainFeature = vector.transform(training)

    val lr = new LogisticRegression()
      .setLabelCol("click")
      .setFeaturesCol("features")
      .setMaxIter(500)


    val lrModel = lr.fit(trainFeature)
    //存储lr模型到hdfs
    lrModel.save("/models/lr.obj")

    lrModel
  }


  def getRank(lr:LogisticRegressionModel, spark:SparkSession) : DataFrame = {

    val hbaseUtil = new HBaseUtil(spark)
    val table = "history_rs_recall"
    val cf = "recall"
    val colum = "item_id"
    val recall = hbaseUtil.getData(table, cf, colum)
    val _recall = lr.transform(recall)

    _recall
  }
}
