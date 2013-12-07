/**
 * 
 */
package edu.sjsu.cmpe.procurement.domain;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONObject;



/**
 * @author Amit
 *
 */
public class HttpDto {
	CloseableHttpClient httpclient = HttpClients.createDefault();
	String target = "http://54.219.156.168:9000/orders";
	
	public void sendRequest(JSONObject message){
		HttpPost httpPost = new HttpPost(target);
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
		httpPost.setHeader("Accept", "application/json");
		StringEntity body = null;
		try {
			body = new StringEntity(message.toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			httpPost.setEntity(body);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			System.out.println("Response is:" +response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}