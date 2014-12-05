package com.boxupp.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class MailManager {
	private static String apiKey = "key-c6ff792c4302a87dde14906beea6d8cb";
	private static String webResourceURL = "https://api.mailgun.net/v2/support.boxupp.com/messages";
	
	public ClientResponse sendRegistrationMail(String emailID, String name, String password){
		Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter(
	        "api","key-c6ff792c4302a87dde14906beea6d8cb"));
	    WebResource webResource = client.resource("https://api.mailgun.net/v2/support.boxupp.com/messages");
	    MultivaluedMapImpl formData = new MultivaluedMapImpl();
	    formData.add("from", "Boxupp Support <info@boxupp.com>");
	    formData.add("to", emailID);
	    formData.add("subject", "Welcome to Boxupp");
	    BufferedReader fileReader = null;

		InputStream iStream = getClass().getResourceAsStream("/data.html");
		InputStreamReader reader = new InputStreamReader(iStream);
		fileReader = new BufferedReader(reader);
		
	    StringBuffer buffer = new StringBuffer();
	    String data;
	    try {
			while((data = fileReader.readLine())!=null){
				buffer.append(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    data = buffer.toString();
	    data = data.replaceAll("<USERNAME>", emailID);
	    data = data.replaceAll("<PASSWORD>", password);
	    data = data.replaceAll("<NAME>", name);
	    formData.add("html", data);
//	    formData.add("text","Welcome to Boxupp");
//	    formData.add("o:campaign", "welcome");
	    
	    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
	        post(ClientResponse.class, formData);
	}
	
	public static void main(String args[]) {
		MailManager manager = new MailManager();
		manager.sendRegistrationMail("akshay.kapoor@paxcel.net","Akshay","abc");
	}

}
