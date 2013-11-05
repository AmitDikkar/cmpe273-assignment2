package edu.sjsu.cmpe.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import edu.sjsu.cmpe.library.api.resources.BookResource;
import edu.sjsu.cmpe.library.api.resources.RootResource;
import edu.sjsu.cmpe.library.config.ConfigElements;
import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.dto.StompAsyncListener;
import edu.sjsu.cmpe.library.repository.BookRepository;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.ui.resources.HomeResource;

public class LibraryService extends Service<LibraryServiceConfiguration> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
	new LibraryService().run(args);
    }

    @Override
    public void initialize(Bootstrap<LibraryServiceConfiguration> bootstrap) {
	bootstrap.setName("library-service");
	bootstrap.addBundle(new ViewBundle());
	bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(LibraryServiceConfiguration configuration,
	    Environment environment) throws Exception {
	// This is how you pull the configurations from library_x_config.yml
	String queueName = configuration.getStompQueueName();
	String topicName = configuration.getStompTopicName();
	System.out.println("Queue Name: "+queueName+" topic name: "+topicName);
	log.debug("Queue name is {}. Topic name is {}", queueName,
		topicName);
	ConfigElements.setApolloHost(configuration.getApolloHost());
	ConfigElements.setApolloPassword(configuration.getApolloPassword());
	ConfigElements.setApolloPort(configuration.getApolloPort());
	ConfigElements.setApolloUser(configuration.getApolloUser());
	ConfigElements.setStompQueueName(configuration.getStompQueueName());
	ConfigElements.setStompTopicName(configuration.getStompTopicName());
	ConfigElements.setLibraryName(configuration.getLibraryName());
	// TODO: Apollo STOMP Broker URL and login
	StompAsyncListener listener = new StompAsyncListener();
	listener.setupListner();

	/*StompAsyncListener listener = new StompAsyncListener();
	listener.setupListner();*/
	/** Root API */
	environment.addResource(RootResource.class);
	/** Books APIs */
	BookRepository bookRepository = new BookRepository();
	environment.addResource(new BookResource(bookRepository,listener));

	/** UI Resources */
	environment.addResource(new HomeResource(bookRepository));
    }
}
