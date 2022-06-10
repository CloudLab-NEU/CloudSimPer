package edu.neu.cloudsimper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.HarddriveStorage;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.ParameterException;
import org.cloudbus.cloudsim.SanStorage;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEvent;

import com.sun.javafx.collections.MappingChange.Map;

import edu.neu.cloudsimper.energy.Energy;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaDatacenter;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.plugin.PluginFactoryDatacenter;
import edu.neu.cloudsimper.region.Location;
import edu.neu.cloudsimper.region.RegionManger;
import edu.neu.cloudsimper.util.WriteCSV;
import sun.management.ManagementFactoryHelper;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyMixDatacenter extends Datacenter {

	private EnergyMixDatacenterBroker broker;

	private Location location;
	private List<Energy> energies;

	private double eLogInterval;
	private double eConsumption;
	private double eSupplyment;
	private double eLastTime;
	private SortedMap<Double, Double> eCost = new TreeMap<Double, Double>();
	private List<String> elog = new ArrayList<String>();
	

	/**
	 * Map<startTime, energy_from_startTime_to_next_"startTime">
	 */
	private SortedMap<Double, Double> eSupplyHistory = new TreeMap<Double, Double>();
	private SortedMap<Double, Double> eConsumptionHistory = new TreeMap<Double, Double>();

	private EnergyMixDatacenter(String name, DatacenterCharacteristics characteristics,
			VmAllocationPolicy vmAllocationPolicy, List<Storage> storageList, double schedulingInterval,
			String locationName, List<Energy> energies) throws Exception {
		super(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval);
		this.location = RegionManger.getLocation(locationName);
		this.energies = energies;
		for (Energy energy : energies) {
			energy.setDatacenter(this);
		}
	}

	public static List<EnergyMixDatacenter> createAllDatacenters() {
		return DatacenterBuilder.buildAll();
	}

	public static EnergyMixDatacenter createDatacenter(MetaDatacenter metaDatacenter) throws Exception {
		return DatacenterBuilder.buildOne(metaDatacenter);
	}

	@Override
	public void startEntity() {
		super.startEntity();
		for (Energy energy : energies) {
			energy.start();
		}
	}

	public Location getLocation() {
		return location;
	}

	public List<EnergyMixHost> getHosts() {
		return this.getCharacteristics().getHostList();
	}
	
	public List<Energy> getEnergys(){
		return energies;
	}

	public double getRecentEnergySupplyment() {
		if (eSupplyment == 0) {
			eSupplyHistory.get(eSupplyHistory.lastKey());
		}
		return eSupplyment;
	}

	public double getRecentEnergyConsumption() {
		if (eConsumption == 0) {
			eConsumptionHistory.get(eConsumptionHistory.lastKey());
		}
		return eConsumption;
	}

	public EnergyMixDatacenterBroker getBroker() {
		return broker;
	}

	public void setBroker(EnergyMixDatacenterBroker broker) {
		this.broker = broker;
	}

	public SortedMap<Double, Double> getHistoryEnergySupplyment() {
		return eSupplyHistory;
	}

	public SortedMap<Double, Double> getHistoryEnergyConsumption() {
		return eConsumptionHistory;
	}

	public void processEvent(SimEvent ev) {
		super.processEvent(ev);
		processEnergy();
	}

	private void processEnergy() {
		double now = CloudSim.clock();
		if (now > eLastTime + CloudSim.getMinTimeBetweenEvents()) {
			eLogInterval += (now - eLastTime);
			if (eLogInterval > Const.ENERGY_LOG_INTERVAL) {
				eSupplyment += processESupplyment(eLastTime, now);
				eConsumption += processEConsumption(eLastTime, now);
				logEnergyData(now);
				addEHistory();
				eLogInterval = 0;
				eConsumption = 0;
				eSupplyment = 0;
				eLastTime = now;
			}
		}
	}

	private double processESupplyment(double last, double now) {
		double result = 0;
		for (Energy energy : energies) {
			result += energy.getEnergy(last, now);
			eCost.put(energy.getCost(), energy.getEnergy(last, now));
		}
		return result;
	}

	private double processEConsumption(double last, double now) {
		double result = 0;
		List<EnergyMixHost> hosts = this.getHosts();
		for (EnergyMixHost host : hosts) {
			result += host.getEnergyConsumption(last, now);
		}
		return result;
	}

	private void logEnergyData(double now) {
		// TODO log energy
		// log csv files to somewhere, this data is critical
		System.err.println("DataCenter:" + this.getName() + " Time:" + eLogInterval + " EC:" + eConsumption+ " ES:"
				+ eSupplyment);	
	//	elog.add(this.getName()+"             "+now+"             "+eLogInterval+"             "+eConsumption+"             "+30*Math.random());
	}

	private void addEHistory() {
		eSupplyHistory.put(eLastTime, eSupplyment);
		eConsumptionHistory.put(eLastTime, eConsumption);
	}

	public SortedMap<Double, Double> geteCost() {
		return eCost;
	}

	private static class DatacenterBuilder {
		private static List<EnergyMixDatacenter> buildAll() {
			List<EnergyMixDatacenter> datacenters = new ArrayList<EnergyMixDatacenter>();
			List<MetaDatacenter> metaDcs = MetaManager.getDatacenters();
			try {
				for (MetaDatacenter metaDc : metaDcs) {
					datacenters.add(buildOne(metaDc));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return datacenters;
		}

		private static EnergyMixDatacenter buildOne(MetaDatacenter metaDc) throws Exception {
			PluginFactoryDatacenter facorty = PluginFactory.datacenter(metaDc);
			String name = metaDc.getName();
			DatacenterCharacteristics characteristics = buildCharacteristics(metaDc);
			VmAllocationPolicy vmAllocationPolicy = facorty.createVmAllocationPolicy(characteristics.getHostList());
			List<Storage> storageList = buildStorage(metaDc);
			double schedulingInterval = Double.parseDouble(metaDc.getAttribute(Const.V_SCHEDULING_INTERVAL));
			String locationName = metaDc.getAttribute(Const.V_LOCATION);
			List<Energy> energies = buildEnergies(metaDc);
			return new EnergyMixDatacenter(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval,
					locationName, energies);
		}

		private static DatacenterCharacteristics buildCharacteristics(MetaDatacenter metaDc) {
			List<EnergyMixHost> hostList = EnergyMixHost.createHosts4Datacenter(metaDc);
			double costPerSec = Double.parseDouble(metaDc.getAttribute(Const.V_COST_PER_SECOND));
			double costPerMem = Double.parseDouble(metaDc.getAttribute(Const.V_COST_PER_MEM));
			double costPerStorage = Double.parseDouble(metaDc.getAttribute(Const.V_COST_PER_STORAGE));
			double costPerBw = Double.parseDouble(metaDc.getAttribute(Const.V_COST_PER_BW));
			DatacenterCharacteristics characteristics = new DatacenterCharacteristics(Const.ARCHITECTURE, Const.OS,
					Const.VMM, hostList, 0, costPerSec, costPerMem, costPerStorage, costPerBw);
			return characteristics;
		}

		private static List<Storage> buildStorage(MetaDatacenter metaDc) throws ParameterException {
			List<MetaContainer> metaDcStorages = metaDc.getStorages();
			List<Storage> storageList = new ArrayList<Storage>();
			for (MetaContainer metaDcStorage : metaDcStorages) {
				String name = metaDcStorage.getName();
				int size = metaDcStorage.getSize();
				MetaContainer metaStorage = MetaManager.getStorage(name);
				double capacity = Double.parseDouble(metaStorage.getAttribute(Const.V_CAPACITY));
				String tmpBand = metaStorage.getAttribute(Const.V_BANDWIDTH);
				String tmpNet = metaStorage.getAttribute(Const.V_NETWORK_LATENCY);
				for (int i = 1; i <= size; i++) {
					String storageName = metaDc.getName() + "_" + name + "_" + i;
					if (tmpBand == null || tmpNet == null) {
						storageList.add(new HarddriveStorage(storageName, capacity));
					} else {
						double bandwidth = Double.parseDouble(tmpBand);
						double networkLatency = Double.parseDouble(tmpNet);
						storageList.add(new SanStorage(storageName, capacity, bandwidth, networkLatency));
					}
				}
			}
			return storageList;
		}

		private static List<Energy> buildEnergies(MetaDatacenter metaDc) {
			List<Energy> energies = new ArrayList<Energy>();
			List<MetaContainer> metaDcEnergies = metaDc.getEnergies();
			for (MetaContainer metaDcEnergy : metaDcEnergies) {
				int size = metaDcEnergy.getSize();
				for (int i = 0; i < size; i++) {
					energies.add(new Energy(metaDcEnergy));
				}
			}
			return energies;
		}
	}
	
	public List<String> getELog(){
		return elog;
	}

}
