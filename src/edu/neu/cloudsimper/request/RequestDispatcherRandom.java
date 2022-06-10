package edu.neu.cloudsimper.request;

import java.util.List;
import java.util.Random;

import edu.neu.cloudsimper.EnergyMixDatacenterBroker;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherRandom extends RequestDispatcherAbstract implements RequestDispatcher {
	
	public  RequestDispatcherRandom() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public boolean allocateBroker4Request(RequestBatch requestBatch) {
		// TODO Auto-generated method stub
		List<Request> requests = requestBatch.getRequests();
		for (Request request : requests) {
			Random randomBroker = new Random();
			int randCount = randomBroker.nextInt(brokers.size());
			EnergyMixDatacenterBroker broker = this.brokers.get(randCount);
			List<Request> disList = rTable.get(broker);
			disList.add(request);
		}
		return true;
	}

}
