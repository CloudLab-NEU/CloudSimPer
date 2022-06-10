package edu.neu.cloudsimper.request;

import java.util.Collection;
import java.util.List;

import edu.neu.cloudsimper.EnergyMixDatacenterBroker;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherGreedy extends RequestDispatcherAbstract implements RequestDispatcher {
	
	public  RequestDispatcherGreedy() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public boolean allocateBroker4Request(RequestBatch requestBatch) {
		// TODO Auto-generated method stub
		List<Request> requests = requestBatch.getRequests();
		int maxEnergyIndex = 0;
		double lastRequestEnergy = 0;
		long lastRequestLen = 0;
		for (Request request : requests) {
			double max = Double.MIN_VALUE;
			for (int i = 0; i < brokers.size(); i++) {
				Collection<Double> consumptions = this.brokers.get(i).getDatacenter().getHistoryEnergyConsumption().values();
				double consumptionAll = 0;
				for (Double consumption: consumptions) {
					consumptionAll+=consumption;
				}
				if (maxEnergyIndex == i) {
					consumptionAll+=lastRequestLen;
				}
				double supplyAll = 0;
				Collection<Double> supplys = this.brokers.get(i).getDatacenter().getHistoryEnergySupplyment().values();
				for (Double supply: supplys) {
					supplyAll+=supply;
				}
				double rest = supplyAll - consumptionAll - request.getLength();
				if (max < rest) {
					maxEnergyIndex = i;
					lastRequestLen = request.getLength();
				}
			}
			EnergyMixDatacenterBroker broker = this.brokers.get(maxEnergyIndex);
			List<Request> disList = rTable.get(broker);
			disList.add(request);
		}
		return false;
	}

}
