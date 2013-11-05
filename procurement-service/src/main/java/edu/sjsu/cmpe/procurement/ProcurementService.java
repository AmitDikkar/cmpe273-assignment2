package edu.sjsu.cmpe.procurement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import de.spinscale.dropwizard.jobs.JobsBundle;
import edu.sjsu.cmpe.procurement.config.ConfigElements;
import edu.sjsu.cmpe.procurement.config.ProcurementServiceConfiguration;

public class ProcurementService extends Service<ProcurementServiceConfiguration> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
	new ProcurementService().run(args);
	
    }

    @Override
    public void initialize(Bootstrap<ProcurementServiceConfiguration> bootstrap) {
	bootstrap.setName("procurement-service");
	//pass the package location of the code where you will write the job which runs after 5 mins
	bootstrap.addBundle(new JobsBundle("edu.sjsu.cmpe.procurement"));
    }

    @Override
    public void run(ProcurementServiceConfiguration configuration,
	    Environment environment) throws Exception {
	String queueName = configuration.getStompQueueName();
//	String topicName = configuration.getStompTopicName();
	String topicName = configuration.getStompTopicBook();
	ConfigElements.setAll(configuration);
	
	/*StompDto stompDto = new StompDto();
	boolean result = stompDto.getMessages(); //take messages from the queue
	
	if(result==true){
		JSONObject message = stompDto.getJsonObject(); //get message in the required format to post to publisher 
		System.out.println("Json object is:"+message);
		/*HttpDto httpDto = new HttpDto();
		httpDto.sendRequest(jsonObject);*/
		/*JerseyClient myClient = new JerseyClient();
		myClient.postMessage(message);    // Pos
		myClient.getMessage();
	}*/
	log.debug("Queue name is {}. Topic is {}", queueName, topicName);
	// TODO: Apollo STOMP Broker URL and login
    }
}
