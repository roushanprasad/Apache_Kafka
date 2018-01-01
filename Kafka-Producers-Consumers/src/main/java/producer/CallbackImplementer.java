package producer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

public class CallbackImplementer implements Callback {
	final static Logger logger = Logger.getLogger(CallbackImplementer.class);

	public void onCompletion(RecordMetadata rm, Exception ex) {
		if(ex != null) logger.error(ex);
	}

}