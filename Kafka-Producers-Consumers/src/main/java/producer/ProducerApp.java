package producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

public class ProducerApp {

	final static Logger logger = Logger.getLogger(ProducerApp.class);

	public static void main(String[] args) {
		long start;
		long end;

		// Properties
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// Kafka Producer
		KafkaProducer<String, String> myProducer = new KafkaProducer<String, String>(props);

		logger.debug("################ Producer App Stats Starts #############");
		logger.debug("Starting Loop");
		start = System.currentTimeMillis();
		logger.debug("System Time in Milliseconds: " + start);

		// Message Sending loop
		try {
			for (int i = 0; i < 10000; i++) {
				myProducer.send(new ProducerRecord<String, String>("TestTopic", "Message No. " + i));
			}

			logger.debug("Loop Ended");
			end = System.currentTimeMillis();
			logger.debug("System Time in Milliseconds: " + end);
			logger.debug("Time required for publishing was " + Stats.getstats(start, end) + " milliseconds");
			logger.debug("############### Producer App Stats Ends ###################");

		} catch (Exception e) {
			logger.error("Here is the error: " + e);

		} finally {
			myProducer.close();
		}

	}
}