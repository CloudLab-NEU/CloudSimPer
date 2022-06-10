package edu.neu.cloudsimper.request;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.cloudbus.cloudsim.core.CloudSim;

import java.util.Random;
import java.util.Set;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixDatacenterBroker;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherMinCost extends RequestDispatcherAbstract implements RequestDispatcher {

	@Override
	public boolean allocateBroker4Request(RequestBatch requestBatch) {
		// TODO Auto-generated method stub
		double min = Double.MAX_VALUE;
		int index = 0;
		List<Request> requests = requestBatch.getRequests();
		for (Request request : requests) {
			double costTemp = 0;
			label:for (int i = 0; i < brokers.size(); i++) {
				if (!this.brokers.get(i).getDatacenter().getHistoryEnergyConsumption().isEmpty() ) {
					double lower = this.brokers.get(i).getDatacenter().getRecentEnergyConsumption();
					for (Double cost: this.brokers.get(i).getDatacenter().geteCost().keySet()) {
						if (this.brokers.get(i).getDatacenter().geteCost().get(cost) < lower) {
							costTemp += this.brokers.get(i).getDatacenter().geteCost().get(cost)*cost;
							lower -= this.brokers.get(i).getDatacenter().geteCost().get(cost);
						}else {
							costTemp += this.brokers.get(i).getDatacenter().geteCost().get(cost)*cost;
							break;
						}
					}
					if (min > costTemp) {
						min = costTemp;
						for (int j = 0; j < this.brokers.get(i).getDatacenter().getHosts().size(); j++)
							if (this.brokers.get(i).getDatacenter().getHosts().get(j).getCpuUtilization() < 1) {
								index = i;
								break label;
							}else {
								min = Double.MAX_VALUE;
							}
						}
					}else {
						Random randomBroker = new Random();
						index = randomBroker.nextInt(brokers.size());
					}
				}	
			EnergyMixDatacenterBroker broker = this.brokers.get(index);
			List<Request> disList = rTable.get(broker);
			disList.add(request);
		}
		return true;
	}

}
