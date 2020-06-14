name := "SparkStreaming-GettingStarted"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.5"


libraryDependencies ++= {
  val sparkV = "2.4.6"

  Seq(
    "org.apache.spark" %% "spark-core" % sparkV,
    "org.apache.spark" %% "spark-streaming" % sparkV,
    "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkV,
    "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkV,
    "org.apache.spark" %% "spark-sql" % sparkV,
    "com.microsoft.azure" %% "azure-eventhubs-spark" % "2.3.15"
  )
}

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"

mainClass in assembly := some("com.demo.streaming.KafkaToSpark")
assemblyJarName := "StructuredStreamingDemo.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


