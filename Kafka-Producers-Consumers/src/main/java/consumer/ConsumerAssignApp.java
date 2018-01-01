package consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

public class ConsumerAssignApp {
	final static Logger logger = Logger.getLogger(ConsumerAssignApp.class);

	public static void main(String[] args) {
		logger.debug("ConsumerAssignApp.main(): Starts");
		
		//Properties
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "Test");
		
		//Kafka Consumer
		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);
		
		//Partitions
		TopicPartition part1 = new TopicPartition("TestTopic",0);
		TopicPartition part2 = new TopicPartition("TestTopic2",1);
		ArrayList<TopicPartition> partitions = new ArrayList<TopicPartition>();
		partitions.add(part1);
		partitions.add(part2);
		
		//Assign
		myConsumer.assign(partitions);
		
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
			logger.debug("ConsumerAssignApp.main(): Ends");
			myConsumer.close();
		}

	}

}