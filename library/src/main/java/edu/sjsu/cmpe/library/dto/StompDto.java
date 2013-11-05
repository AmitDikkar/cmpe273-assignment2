/**
 * 
 */
package edu.sjsu.cmpe.library.dto;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

import com.yammer.dropwizard.jersey.params.LongParam;

import edu.sjsu.cmpe.library.config.ConfigElements;
/**
 * @author Amit
 *
 */
public class StompDto {

	StompConnection connection = new StompConnection();
	public StompDto() {
		// TODO Auto-generated constructor stub
		try {
			connection.open(ConfigElements.getApolloHost(), Integer.parseInt(ConfigElements.getApolloPort()));
			connection.connect(ConfigElements.getApolloUser(), ConfigElements.getApolloPassword());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMessage(LongParam isbn) throws Exception{
		System.out.println("Sending message....");
		System.out.println("this is library: "+ConfigElements.getLibraryName());
		String message;
		if(ConfigElements.getLibraryName().equalsIgnoreCase("library-a")){
			message = "library-a:"+isbn.toString();
		}
		else{
			message = "library-b:"+isbn.toString();
		}
		connection.send(ConfigElements.getStompQueueName(), message);
		System.out.println("message Sent........");
	}
	
	public ArrayList<String> receiveBooks(){
		long waitUntil = 5000;
		ArrayList<String> messages = new ArrayList<String>(); //to store messages coming from the topics
		try{
			connection.subscribe(ConfigElements.getStompTopicName());
			StompFrame message;
			while(true)
			{	
				message = connection.receive(waitUntil);
				messages.add(message.getBody());
				System.out.println("Message body is:"+message.getBody());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return messages;
	}
}