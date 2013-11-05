/**
 * 
 */
package edu.sjsu.cmpe.procurement.domain;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


/**
 * @author Amit
 *
 */
public class JerseyClient {
	private Client jerseyClient = Client.create();
	String targetURI = "http://54.215.210.214:9000/orders";
	String getURI = "http://54.215.210.214:9000/orders/69169";
	String contentType = "application/json";
	ClientResponse response;
	
	
	/**
	 * @param messageBody
	 * This method will send the POST request to publisher. This needs the Message(in Json format) that needs to be sent. 
	 */
	public void postMessage(JSONObject messageBody){
		System.out.println("Sending order.....");
		WebResource webResource = jerseyClient.resource(this.targetURI);
		this.response = webResource.type(contentType).post(ClientResponse.class, messageBody);
		System.out.println("response Status is:"+response.getStatus());
		System.out.println("response is "+response.getEntity(String.class));
		System.out.println("Order Sent.....");
	}
	
	/**
	 * This method will send "GET" message to publisher and will receive all the books from publisher
	 */
	public ArrayList<Book> getMessage(){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{receiving order.....");
		
		WebResource webResource = jerseyClient.resource(this.getURI);
		BooksArrived booksArrived = webResource.accept(contentType).get(BooksArrived.class);
		
		//booksArrived will have all the books sent by publisher.
		ArrayList<Book> books = booksArrived.getShipped_books();
		System.out.println("Order Received.....");
		System.out.println("Number of BooksReceived: "+books.size());
		for(Book book : books){
		System.out.println("Book is: Title:"+book.getTitle()+"  category:"+book.getCategory()+"  coverimage:"+book.getCoverimage());
		}
		return books;
	}
}
