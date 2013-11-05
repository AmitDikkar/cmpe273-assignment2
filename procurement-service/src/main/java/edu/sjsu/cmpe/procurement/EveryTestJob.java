/**
 * 
 */
package edu.sjsu.cmpe.procurement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.procurement.domain.Book;
import edu.sjsu.cmpe.procurement.domain.JerseyClient;
import edu.sjsu.cmpe.procurement.domain.StompDto;

/**
 * @author Amit
 *
 */

@Every("40s")
public class EveryTestJob extends Job{

	@Override
	public void doJob(){
	System.out.println("!!!!!!!!!!!!!!STARTED PROCUREMENT SERVICE!!!!!!!!!");
		StompDto stompDto = new StompDto();
		boolean result = stompDto.getMessages(); //take messages from the queue
		
		if(result==true){
			JSONObject message = stompDto.getJsonObject(); //get message in the required JSON format to post to publisher 
			System.out.println("Json object is:"+message);
			/*HttpDto httpDto = new HttpDto();
			httpDto.sendRequest(jsonObject);*/
			JerseyClient myClient = new JerseyClient();
			myClient.postMessage(message);    // POST Request to publisher
			
			//pull all the books from publisher and put them in the ArrayList
			ArrayList<Book> books = myClient.getMessage();	//GET request From publisher. This will have all books in the BooksArrived class
			stompDto.sendTopics(books); //Send message to the respective topics
		}
	}
}
