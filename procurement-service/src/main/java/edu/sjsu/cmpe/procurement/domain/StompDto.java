/**
 * 
 */
package edu.sjsu.cmpe.procurement.domain;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.jms.Session;















//import org.apache.activemq.broker.Connection;
/*import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import com.google.common.eventbus.Subscribe;
*/
import edu.sjsu.cmpe.procurement.config.ConfigElements;
















import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;
import org.fusesource.stomp.jms.message.StompJmsMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * @author Amit
 *
 */
public class StompDto {
	
	private JSONObject jsonObject = new JSONObject();
	private String id = "69169";
	
	/**
	 * @return true: if messages arrived, False: if messages didn't arrive
	 * pull all the messages from the Apollo Queue
	 * @throws JMSException
	 */
	public boolean getMessages(){
		System.out.println("inside get Message method");
		StompConnection connection = new StompConnection();
		List<String> messageList = new ArrayList<String>();
		long waitUntil = 5000;
		try {
			connection.open(ConfigElements.getApolloHost(), Integer.parseInt(ConfigElements.getApolloPort()));
			connection.connect(ConfigElements.getApolloUser(), ConfigElements.getApolloPassword());
			connection.subscribe(ConfigElements.getStompQueueName());
			StompFrame message;
			while(true)
			{	
				message = connection.receive(waitUntil);
				messageList.add(message.getBody());
				System.out.println("Message body is:"+message.getBody());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("all messages received");
		}
		
		if(!messageList.isEmpty()){
			List<Integer> isbns = getIsbns(messageList);
			
			//Json Array is used to get the isbns in the JsonArray format i.e [1,2,3] format
			JSONArray array = new JSONArray();
			array.addAll(isbns);		
			System.out.println("string:"+array);
			
			//Created json object to send it to publisher
			this.jsonObject.put("id", id);
			this.jsonObject.put("order_book_isbns", array);
			System.out.println("final object"+this.jsonObject);
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else{
			System.out.println("there are no meesages in the queue");
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
    }
	
	/**
	 * @param messages
	 * @return
	 * This method will take out just Isbn from the messages that are pulled from the queue 
	 */
	private List<Integer> getIsbns(List<String> messages){
		List<Integer> isbns = new ArrayList<Integer>();
		Iterator<String> itr = messages.iterator();
		while(itr.hasNext()){
			String str = itr.next();
			String stringIsbn = str.substring(10);
			Integer isbn = Integer.parseInt(stringIsbn);
			isbns.add(isbn);
		}
		return isbns;
	}
/*	
	private String getStringIsbns(List<Integer> isbns){
		String stringIsbns="";
		stringIsbns = stringIsbns+"[";
		Iterator<Integer> itr = isbns.iterator();
		while(itr.hasNext())
		{
			stringIsbns = stringIsbns + itr.next().toString();
			if(itr.hasNext())
			{
				stringIsbns = stringIsbns + ",";
			}
			System.out.println("stringIsbn: "+stringIsbns);
		}
		stringIsbns = stringIsbns + "]";
		System.out.println("Final String:"+stringIsbns);
		return stringIsbns;
	}
*/

	/**
	 * @return the jsonObject
	 */
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	/**
	 * @param jsonObject the jsonObject to set
	 */
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * @param books
	 * this method creates topics
	 */
	public void sendTopics(ArrayList<Book> books){
		System.out.println("??????????INSIDE THE SEND TOPIC??????????");
		//StompConnection connection = new StompConnection();
		try{
		/*connection.open(ConfigElements.getApolloHost(), Integer.parseInt(ConfigElements.getApolloPort()));
		connection.connect(ConfigElements.getApolloUser(), ConfigElements.getApolloPassword());*/
		//setupConnection
			for (Book book : books){
				System.out.println("This is book:"+book.getIsbn());
				if(book.getCategory().equalsIgnoreCase("computer")){
					//connection.send(ConfigElements.getStompTopicComputer(), getMessageForTopic(book));
					sendToTopic(ConfigElements.getStompTopicComputer(), getMessageForTopic(book));
					System.out.println("?????????Message Sent To Computer Topic??????????????");
				}
				else{
					//connection.send("/topic/69169.book."+book.getCategory(), getMessageForTopic(book));
					sendToTopic("/topic/69169.book."+book.getCategory(), getMessageForTopic(book));
					System.out.println("?????????Message Sent To Other topic??????????????");
				}
			}
			//connection.close();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	private  String getMessageForTopic(Book book){
		String topicMessage = String.valueOf(book.getIsbn())+":";
		topicMessage = topicMessage + "\"" +book.getTitle()+"\"" + ":" + "\"" + book.getCategory()+"\"" + ":"+"\""+book.getCoverimage()+"\"";
		//convert string to TextMessage
		/*TextMessage msg = null; 
		try {
			msg.setText(topicMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return topicMessage;
	}
	
	private void sendToTopic(String topicName, String data) throws JMSException{
		/*String topicName = configuration.getStompTopicName()+category; 
		String user = env("APOLLO_USER", "admin");
		String password = env("APOLLO_PASSWORD", "password");
		String host = env("APOLLO_HOST", "54.215.210.214");
		int port = Integer.parseInt(env("APOLLO_PORT", "61613"));*/
		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI("tcp://" + ConfigElements.getApolloHost() + ":" + ConfigElements.getApolloPort());
		Connection jmconnection = factory.createConnection(ConfigElements.getApolloUser(), ConfigElements.getApolloPassword());
		
		jmconnection.start();
		Session session = jmconnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = new StompJmsDestination(topicName);
		MessageProducer producer = session.createProducer(dest);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		if(data=="SHUTDOWN")
		{ 
			System.out.println("sending shutdown msg"); 
			producer.send(session.createTextMessage("SHUTDOWN")); 
		}
		else { 
			 TextMessage msg = session.createTextMessage(data); 
			 msg.setLongProperty("id", System.currentTimeMillis()); 
			 producer.send(msg); System.out.println("successfully published"); 
			 }
		jmconnection.close(); 
	}
}