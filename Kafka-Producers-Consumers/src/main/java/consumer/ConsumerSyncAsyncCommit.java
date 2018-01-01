package consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

public class ConsumerSyncAsyncCommit {
	private static Logger logger = Logger
			.getLogger(ConsumerSyncAsyncCommit.class);

	public static void main(String[] args) {
		logger.debug("ConsumerSyncAsyncCommit.main(): Starts");

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "Test");

		// CommitSync Property
		props.put("enable.auto.commit", "false");

		// Consumer
		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(
				props);

		// Topics List
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("TestTopic");
		topics.add("TestTopic2");

		// Subscribing Topics
		myConsumer.subscribe(topics);

		// Poll Loop
		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(100);
				for (ConsumerRecord<String, String> oneRecord : records) {
					logger.debug(String.format(
							"Topic: %s, Partition: %d, Offset: %d, Key: %s, "
									+ "value: %s", oneRecord.topic(),
							oneRecord.partition(), oneRecord.offset(),
							oneRecord.key(), oneRecord.value()));
				}
				// Commit Async
				myConsumer.commitAsync();
			}
		} catch (Exception e) {
			logger.error("Unexpected Error: " + e);
		} finally {
			try {
				myConsumer.commitSync();
			} finally {
				logger.debug("ConsumerSyncAsyncCommit.main(): Ends");
				myConsumer.close();
			}
		}
	}
}