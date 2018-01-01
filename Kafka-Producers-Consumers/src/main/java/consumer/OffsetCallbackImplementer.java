package consumer;

import java.util.Map;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

public class OffsetCallbackImplementer implements OffsetCommitCallback{
	private static Logger logger = Logger.getLogger(OffsetCallbackImplementer.class);

	public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets,
			Exception ex) {
		if(ex != null) {
			logger.error("Offset Commit failed for offsets {}", (Throwable) offsets);
			logger.error(ex.getMessage());
		}
	}
}