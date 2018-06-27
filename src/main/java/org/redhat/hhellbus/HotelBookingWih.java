package org.redhat.hhellbus;

import java.util.Map;
import java.util.HashMap;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class HotelBookingWih implements WorkItemHandler {

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
		// results.put("vacancy", true);

		// with out manager.completeWorkItem - the transaction will be put 
		// into a wait state (saves to db)
		// also required for passing forward results
		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		System.out.println("test");
	}

}
