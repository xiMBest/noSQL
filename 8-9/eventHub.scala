import org.apache.spark.eventhubs.{ ConnectionStringBuilder, EventHubsConf, EventPosition }
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

// To connect to an Event Hub, EntityPath is required as part of the connection string.
// Here, we assume that the connection string from the Azure portal does not have the EntityPath part.
val appID = "ea07640e-c333-4be8-9bc3-d817f30d5fcb"
val password = "r01iYou6G_O8GiU7II-E-4BOqp2-ei6VeX"
val tenantID = "7631cd62-5187-4e15-8b8e-ef653e366e7a"
val fileSystemName = "naidacontainer"
val storageAccountName = "naidaaccount"
val connectionString = ConnectionStringBuilder("Endpoint=sb://naidaspace.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=CLG2Pg719qKRTuFohSZF+kHagFUnxJeafNujjdWOj0k=")
  .setEventHubName("naidavitalii")
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
  .option("checkpointLocation", "/mnt/naidacontainer/labnaida8")
  .start("/mnt/naidacontainer/labnaida8")