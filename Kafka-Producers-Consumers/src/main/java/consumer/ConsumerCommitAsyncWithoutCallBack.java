package consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

public class ConsumerCommitAsyncWithoutCallBack {
	private static Logger logger = Logger.getLogger(ConsumerCommitAsyncWithoutCallBack.class);

	public static void main(String[] args) {
		logger.debug("ConsumerCommitAsyncWithoutCallBack.main(): Starts");
		
		//Properties
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092, localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "Test");
		
		//CommitSync Property
		props.put("enable.auto.commit", "false");
	
		//Topics
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("TestTopic");
		topics.add("TestTopic2");
		
		//Consumer
		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);
		
		//Subscribing
		myConsumer.subscribe(topics);
		
		//Poll Loop
		try{
			while(true){
				int i=0;
				ConsumerRecords<String, String> records = myConsumer.poll(100);
				for (ConsumerRecord<String, String> oneRecord : records) {
					i++;
					logger.debug(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, "
							+ "value: %s, MessageNumber: %d", oneRecord.topic(), oneRecord.partition(), oneRecord.offset(), 
							oneRecord.key(), oneRecord.value(), i));
				}
				try {
					myConsumer.commitAsync();
				} catch (CommitFailedException e) {
					logger.error(e.getMessage());
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			logger.debug("ConsumerCommitAsyncWithoutCallBack.main(): Ends");
			myConsumer.close();
		}
	}

}
