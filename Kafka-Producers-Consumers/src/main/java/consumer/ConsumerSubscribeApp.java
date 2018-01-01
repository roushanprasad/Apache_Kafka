package consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

public class ConsumerSubscribeApp {
	final static Logger logger = Logger.getLogger(ConsumerSubscribeApp.class);

	public static void main(String[] args) {
		logger.debug("ConsumerSubscribeApp.main(): Starts");
		
		//Properties
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "Test");
		
		//Kafka Consumer
		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);
		
		//Topic List
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("TestTopic");
		topics.add("TestTopic2");
		
		//Subscribe topic list
		myConsumer.subscribe(topics);
		
		//Poll Loop
		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
							record.topic(), record.partition(), record.offset(), record.key(), record.value()));
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			logger.debug("ConsumerSubscribeApp.main(): Ends");
			myConsumer.close();
		}

	}

}