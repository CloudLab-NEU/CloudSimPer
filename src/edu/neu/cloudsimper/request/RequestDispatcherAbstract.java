package edu.neu.cloudsimper.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;


import edu.neu.cloudsimper.CloudSimPer;
import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixDatacenterBroker;
import edu.neu.cloudsimper.EnergyMixHost;
import edu.neu.cloudsimper.util.WriteCSV;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class RequestDispatcherAbstract implements RequestDispatcher {

	protected List<EnergyMixDatacenterBroker> brokers;
	protected Map<EnergyMixDatacenterBroker, List<Request>> rTable;
	protected WriteCSV requestCSV;
	
	public RequestDispatcherAbstract() {
		this.brokers = CloudSimPer.getBrokers();
		initRTable();
		requestCSV = new WriteCSV();
	}

	public boolean allocateBroker4Request(List<RequestBatch> requestBatches) {
		for (RequestBatch requestBatch : requestBatches) {
			allocateBroker4Request(requestBatch);
		}
		return true;
	}

	public abstract boolean allocateBroker4Request(RequestBatch requestBatch);

	public boolean doDispatch() {
		Set<EnergyMixDatacenterBroker> brokerSet = rTable.keySet();
		for (EnergyMixDatacenterBroker broker : brokerSet) {
			List<Request> requests = rTable.get(broker);
			List<Cloudlet> cloudlets = new ArrayList<Cloudlet>();
			for (Request request : requests) {
				Cloudlet cloudlet= request.asCloudLet();
				cloudlet.setUserId(broker.getId());
				cloudlets.add(cloudlet);
			}
			String path = Const.CONIF_FILE_REQUESTRECORD;
			String[] header = {"RequestLen        InputSize         OutputSize      Region      CloudletID"};
			//requestCSV.writeCSV(path,requests,cloudlets,header);
			if (!cloudlets.isEmpty()) {
				broker.submitCloudletList(cloudlets);
			}
		}
		initRTable();
		return true;
	}

	private void initRTable() {
		this.rTable = new HashMap<EnergyMixDatacenterBroker, List<Request>>();
		for (EnergyMixDatacenterBroker broker : brokers) {
			this.rTable.put(broker, new ArrayList<Request>());
		}
	}
}