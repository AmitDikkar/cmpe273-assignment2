/**
 * 
 */
package edu.sjsu.cmpe.library.dto;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.broker.Connection;
import org.apache.activemq.transport.stomp.StompConnection;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.library.api.resources.BookResource;
import edu.sjsu.cmpe.library.config.ConfigElements;
import edu.sjsu.cmpe.library.repository.BookRepository;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

/**
 * @author Amit
 *
 */
public class StompAsyncListener implements MessageListener {
	
	
	public void setupListner(){
		try {
			System.out.println("---------------------------------------------------------------------setting listener--------------");
			StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
	        factory.setBrokerURI("tcp://" + ConfigElements.getApolloHost() + ":" + ConfigElements.getApolloPort());

	        javax.jms.Connection connection = factory.createConnection(ConfigElements.getApolloUser(), ConfigElements.getApolloPassword());
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Destination dest = new StompJmsDestination(ConfigElements.getStompTopicName());

	        MessageConsumer consumer = session.createConsumer(dest);
	        StompAsyncListener stopmAsyncListener = new StompAsyncListener();
			consumer.setMessageListener(stopmAsyncListener);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---------------------------------------------------------------------setup done--------------");
	}
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("**************************************************************************************I am Listening--------------");
		
		TextMessage txtmsg = (TextMessage) message;
		String msg="";
		try {
			msg = txtmsg.getText();
			System.out.println("Received book is: "+msg);
			//BookRepository bookRepo = new BookRepository();
			//--------bookRepository.receiveBooks(msg);
			BookResource.bookRepository.receiveBooks(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Message is not in correct format");
		}
	}
}