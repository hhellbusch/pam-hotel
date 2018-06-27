package org.redhat.hhellbus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class HotelPreBookingWih implements WorkItemHandler {

	private String baseApiServiceAddr = "http://pam-hotel-service-pam7-dallas-take3.apps.na37.openshift.opentlc.com/api";
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// getting params from the process
		//	MyObject obj = (MyObject) workItem.getProcess("parameter_name");

		// TODO fetch endpoint of service
		//TODO from proc info retrive room info so it can be eval'd by service

		// build response obj
		Map<String, Object> results = new HashMap<String, Object>();
		// results.put("output_mapping_f1", "value 1");
		// results.put("output_mapping_f2", 2);

		// TODO set value based on response from webservice
		results.put("vacancy", this.checkForVacancy());

		// with out manager.completeWorkItem - the transaction will be put 
		// into a wait state (saves to db)
		// also required for passing forward results
		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}
	
	//TODO better error handling
	private Boolean checkForVacancy()
	{
		HttpClient client = HttpClientBuilder.create().build();
		Boolean vacancy = null;
		try {
			HttpGet request = new HttpGet(this.baseApiServiceAddr + "/vacancy");
			HttpResponse response;
			response = client.execute(request);
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			
			JsonParser parser = new JsonParser();
			JsonElement jsonTree = parser.parse(rd);
			JsonObject jsonObject = jsonTree.getAsJsonObject();
			JsonObject data = jsonObject.get("data").getAsJsonObject();
			vacancy = data.get("vacancy").getAsBoolean();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vacancy;
	}
	

	public static void main(String[] args) throws Exception {
		HotelPreBookingWih hotelPreBooking = new HotelPreBookingWih();
		hotelPreBooking.checkForVacancy();
	}

}
