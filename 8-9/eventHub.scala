import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

val appID = "be4c1d34-ac5f-45bf-bd26-216f8a4273f8"
val password = "FFo3uHZx5QxOFF4~~JJ9I4_zO5j.NM.2If"
val tenantID = "7631cd62-5187-4e15-8b8e-ef653e366e7a"
val fileSystemName = "naida9"
val storageAccountName = "naida9"
val connectionString = ConnectionStringBuilder("Endpoint=sb://naida9.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=XfUW/+PEO2s+RFOa4Dqg7rsktAQ8DTnsFTWeBd6WFb4=")
  .setEventHubName("naida9")
  .build
val eventHubsConf = EventHubsConf(connectionString)
  .setStartingPosition(EventPosition.fromEndOfStream)

var streamingInputDF =
  spark.readStream
    .format("eventhubs")
    .options(eventHubsConf.toMap)
    .load()

val filtered = streamingInputDF.select (
  from_unixtime(col("enqueuedTime").cast(LongType)).alias("enqueuedTime")
     , get_json_object(col("body").cast(StringType), "$.state_code").alias("state_code")
     , get_json_object(col("body").cast(StringType), "$.county_code").alias("county_code")
     , get_json_object(col("body").cast(StringType), "$.site_num").alias("site_num")
     , get_json_object(col("body").cast(StringType), "$.parameter_code").alias("parameter_code")
)


filtered.writeStream
  .format("com.databricks.spark.csv")
  .outputMode("append")
  .option("checkpointLocation", "/mnt/naida9/lab9dir")
  .start("/mnt/naida9/lab9dir")