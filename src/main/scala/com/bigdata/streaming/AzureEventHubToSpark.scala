package com.bigdata.streaming

import com.bigdata.streaming.utils.Util
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.eventhubs.{ConnectionStringBuilder, EventHubsConf, EventPosition}
import org.apache.spark.sql._

object AzureEventHubToSpark {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)


    val sparkSession =
      SparkSession.builder
        .master("local[*]")
        .appName("Evenhub2Spark")
        .getOrCreate()

    //  -----------------

    val connectionString = ConnectionStringBuilder(
      Util.AZURE_EVENTHUB_ENDPOINT
    ).setEventHubName(Util.AZURE_EVENTHUB_NAME).build


    val eventHubsConf = EventHubsConf(connectionString)
      .setStartingPosition(EventPosition.fromEndOfStream)


    val eventhubs: DataFrame = sparkSession.readStream
      .format("eventhubs")
      .options(eventHubsConf.toMap)
      .load()

    val df = eventhubs.selectExpr("cast (body as string) AS messageContent")


    val consoleOutput1 = df.writeStream
      .outputMode("append")
      .format("console")
      .option("truncate", false)
      .start()


    consoleOutput1.awaitTermination()


  }

}
