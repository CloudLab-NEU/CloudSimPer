package edu.neu.cloudsimper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.container.core.containerCloudSimTags;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.VmList;

import edu.neu.cloudsimper.energy.Energy;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.region.RegionManger;
import edu.neu.cloudsimper.request.Request;
import edu.neu.cloudsimper.request.RequestBatch;
import edu.neu.cloudsimper.request.RequestDispatcher;
import edu.neu.cloudsimper.util.WriteCSV;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class CloudSimPer {

	private static List<EnergyMixDatacenterBroker> brokers;
	private static List<EnergyMixDatacenter> datacenters;
	private static List<Request> requests;
	private static RequestDispatcher dispatcher;
	private static List<RequestBatch> requestBatchs;
	private static boolean running;
	private static WriteCSV writeDatacenter;
	private static WriteCSV writeEnergy = new WriteCSV();
	
	

	public static void main(String[] args) {
		CloudSimPer.init();
		CloudSimPer.execute(3600);//604800*5
		CloudSimPer.log();
	}

	public static void init() {
		initMeta();
		initPlugins();
		initRegions();
		initCloudSim();
		initDatacenters();
		initBrokers();
		initVms();
		initRequests();
	}
	
	
	public static void execute(long duration) {
		running = true;
		startCloudSim();
		startRequests();
		startCloudSimper(duration);
		stopRequests();
		stopCloudSim();
		stopCloudSimper();
	}

	public static void log() {
		writeDatacenter = new WriteCSV();
		writeEnergy = new WriteCSV();
		for (EnergyMixDatacenterBroker broker : brokers) {
			List<Cloudlet> list = broker.getCloudletReceivedList();
			List<EnergyMixHost> hosts = broker.getDatacenter().getHosts();
			//List<Energy> energys = broker.getDatacenter().getEnergys();
			EnergyMixDatacenter datacenter = broker.getDatacenter();
			List<Vm> vms = broker.getVmList();	
			datacenterLog(list,vms,hosts,datacenter);
			printCloudletList(list);
			energyLog(datacenter.getName(),datacenter.getELog());
		}
	}

	public static List<EnergyMixDatacenterBroker> getBrokers() {
		return brokers;
	}

	public static List<EnergyMixDatacenter> getDatacenters() {
		return datacenters;
	}

	public static List<Request> getRequests() {
		return requests;
	}

	private static void initMeta() {
		MetaManager.init();
	}

	private static void initPlugins() {
	}

	private static void initRegions() {
		RegionManger.init();
	}

	private static void initDatacenters() {
		datacenters = EnergyMixDatacenter.createAllDatacenters();
	}

	private static void initBrokers() {
		brokers = new ArrayList<EnergyMixDatacenterBroker>(datacenters.size());
		for (EnergyMixDatacenter datacenter : datacenters) {
			try {
				brokers.add(new EnergyMixDatacenterBroker(datacenter));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void initVms() {
		for (EnergyMixDatacenterBroker broker : brokers) {
			EnergyMixVm.createVms4Broker(broker);
		}
	}

	private static void initRequests() {
		dispatcher = PluginFactory.cloudsimper.createRequestDispatcher();
		requestBatchs = new ArrayList<RequestBatch>();
		requests = new ArrayList<Request>();
		List<MetaContainer> requestMeta = MetaManager.getRequests();
		int idPrefix = Const.ID_PREFIX;
		for (MetaContainer metaContainer : requestMeta) {
			Request request = new Request(metaContainer, idPrefix);
			requests.add(request);
			idPrefix += Const.ID_PREFIX;
		}
	}
	
	

	private static void initCloudSim() {
		CloudSim.init(MetaManager.getDatacenters().size(), Calendar.getInstance(), false);
	}

	private static void startCloudSim() {
		CloudSim.runStart();
	}

	private static void startRequests() {
		for (Request request : requests) {
			request.start();
		}
	}

	private static void startCloudSimper(long duration) {
		boolean flag = true;
		while (true) {
			if (CloudSim.clock() >= duration) {
				break;
			}
			if (CloudSim.runClockTick()) {
				generateRequests();
				dispatchRequests();
				executeRequests();
			}
		}
	}

	private static void generateRequests() {
		requestBatchs.clear();
		for (Request request : requests) {
			requestBatchs.add(request.nextBatch(CloudSim.clock()));
		}
	}

	private static void dispatchRequests() {
		dispatcher.allocateBroker4Request(requestBatchs);
	}

	private static void executeRequests() {
		dispatcher.doDispatch();
	}

	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;
		
		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + "Time"
				+ indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);
			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");
				
				Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + cloudlet.getVmId()+indent
						+ indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent
						+ dft.format(cloudlet.getExecStartTime()) + indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}
	}
	
	public static void datacenterLog(List<Cloudlet> list,List<Vm> vms,List<EnergyMixHost> hosts,EnergyMixDatacenter datacenter) {	
		String[] header = {"Cloudlet ID     STATUS     Data center ID       VMID         HostId          Time       Start Time       Finish Time      ConsumptionEnergy"};
		String path = Const.CONIF_FILE_DATACENTERRECORD;
		writeDatacenter.writeCSV(path, list,header,vms,hosts, datacenter);
	}
	
	public static void energyLog(String datacenterName,List<String> eLog) {
		String[] header = {"Datacenter     Time     Duration     ConsumptionEnergy     SupplyEnergy"};
		String path = Const.CONIF_FILE_ENERGYRECORD;
		writeEnergy.writeCSV(path, datacenterName,eLog, header);
	}

	private static void stopCloudSim() {
		for (EnergyMixDatacenterBroker broker : brokers) {
			broker.disband();
		}
		CloudSim.runStop();
	}

	private static void stopRequests() {
		for (Request request : requests) {
			request.stop(CloudSim.clock());
		}
	}

	private static void stopCloudSimper() {
	}
}
