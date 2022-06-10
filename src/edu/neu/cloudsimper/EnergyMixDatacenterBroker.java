package edu.neu.cloudsimper;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEvent;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyMixDatacenterBroker extends DatacenterBroker {
	private EnergyMixDatacenter datacenter;
	private boolean resubmit = false;

	public EnergyMixDatacenterBroker(EnergyMixDatacenter datacenter) throws Exception {
		super(datacenter.getName() + "Broker");
		this.datacenter = datacenter;
		this.datacenter.setBroker(this);
		this.datacenterIdsList = new ArrayList<Integer>();
		datacenterIdsList.add(datacenter.getId());
	}

	public EnergyMixDatacenter getDatacenter() {
		return datacenter;
	}

	public void setDatacenterIdsList(List<Integer> datacenterIdsList) {
		Log.printConcatLine(CloudSim.clock(), ": ", getName(), ": has been binded to the exclusive Datacenter");
	}

	public void submitCloudletList(List<? extends Cloudlet> list) {
		super.submitCloudletList(list);
		super.submitCloudlets();
	}

	public void disband() {
		this.clearDatacenters();
		this.finishExecution();
	}

	protected void processCloudletReturn(SimEvent ev) {
		Cloudlet cloudlet = (Cloudlet) ev.getData();
		getCloudletReceivedList().add(cloudlet);
		Log.printConcatLine(CloudSim.clock(), ": ", getName(), ": Cloudlet ", cloudlet.getCloudletId(), " received");
		cloudletsSubmitted--;
	}
}
