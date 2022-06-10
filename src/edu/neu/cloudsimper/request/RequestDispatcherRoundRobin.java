package edu.neu.cloudsimper.request;

import java.util.List;

import edu.neu.cloudsimper.EnergyMixDatacenterBroker;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherRoundRobin extends RequestDispatcherAbstract implements RequestDispatcher {
	private int current;

	public RequestDispatcherRoundRobin() {
		super();
		this.current = 0;
	}

	@Override//��ѯ
	public boolean allocateBroker4Request(RequestBatch requestBatch) {
		int max = brokers.size() - 1;
		List<Request> requests = requestBatch.getRequests();
		for (Request request : requests) {
			if (++current > max) {
				current = 0;
			}
			EnergyMixDatacenterBroker broker = this.brokers.get(current);
			List<Request> disList = rTable.get(broker);
			disList.add(request);
		}
		return true;
	}

}
