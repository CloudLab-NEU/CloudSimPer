package edu.neu.cloudsimper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaDatacenter;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.plugin.PluginFactoryHost;
import edu.neu.cloudsimper.power.PowerModel;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyMixHost extends Host {

	private PowerModel powerModel;
	private double previousCpuUtilization;
	private SortedMap<Double, Double> cpuUtilHistory;

	private EnergyMixHost(int id, RamProvisioner ramProvisioner, BwProvisioner bwProvisioner, long storage,
			List<? extends Pe> peList, VmScheduler vmScheduler, PowerModel powerModel) {
		super(id, ramProvisioner, bwProvisioner, storage, peList, vmScheduler);
		this.powerModel = powerModel;
		previousCpuUtilization = 0;
		cpuUtilHistory = new TreeMap<Double, Double>();
	}

	public static List<EnergyMixHost> createHosts4Datacenter(MetaDatacenter container) {
		List<EnergyMixHost> hosts = new ArrayList<EnergyMixHost>();
		List<MetaContainer> metaDcHosts = container.getHosts();
		int idPrefix = Const.ID_PREFIX;
		for (MetaContainer metaDcHost : metaDcHosts) {
			String name = metaDcHost.getName();
			int size = metaDcHost.getSize();
			MetaContainer metaHost = MetaManager.getHost(name);
			PluginFactoryHost factory = PluginFactory.host(metaHost);
			for (int i = 1; i <= size; i++) {
				int id = Integer.parseInt(idPrefix + "" + i);
				RamProvisioner ramProvisioner = factory.createRamProvisioner();
				BwProvisioner bwProvisioner = factory.createBwProvisioner();
				long storage = Long.parseLong(metaHost.getAttribute(Const.V_STORAGE));
				int peNumber = Integer.parseInt(metaHost.getAttribute(Const.V_PES_NUMBER));
				double mips = Double.parseDouble(metaHost.getAttribute(Const.V_MIPS));
				List<Pe> peList = new ArrayList<Pe>();
				for (int j = 1; j <= peNumber; j++) {
					PeProvisioner peProvisioner = factory.createPeProvisioner();
					peProvisioner.setMips(mips);
					peList.add(new Pe(j, peProvisioner));
				}
				VmScheduler vmScheduler = factory.createVmScheduler(peList);
				PowerModel powerModel = factory.createPowerModel();
				EnergyMixHost host = new EnergyMixHost(id, ramProvisioner, bwProvisioner, storage, peList, vmScheduler,
						powerModel);
				hosts.add(host);
			}
			idPrefix += Const.ID_PREFIX;
		}
		return hosts;
	}

	@Override
	public double updateVmsProcessing(double currentTime) {
		previousCpuUtilization = getCpuUtilization();
		double smallerTime = super.updateVmsProcessing(currentTime);
		cpuUtilHistory.put(currentTime, getCpuUtilization());
		return smallerTime;
	}

	public double getCpuUtilization() {
		return 1-this.getAvailableMips() / this.getTotalMips();
	}

	public double getPreviousCpuUtilization() {
		return this.previousCpuUtilization;
	}

	public double getPower() {
		return getPower(getCpuUtilization());
	}

	public double getPreviousPower() {
		return getPower(previousCpuUtilization);
	}

	public double getHistoryPower(double time) {
		List<Double> history = new ArrayList<Double>(cpuUtilHistory.keySet());
		for (int i = history.size() - 1; i <= 0; i--) {
			double htime = history.get(i);
			if (htime < time) {
				return getHistoryPower(cpuUtilHistory.get(htime));
			}
		}
		return 0;
	}

	public double getEnergyConsumption(double start, double end) {
		double reuslt = 0;
		List<Double> history = new ArrayList<Double>(cpuUtilHistory.keySet());
		for (int i = history.size() - 1; i >= 0; i--) {
			double time = history.get(i);
			if (time < end && time > start) {
				reuslt += getPower(cpuUtilHistory.get(time)) * (end - time)/3600;
				end = time;
			} else if (time <= start) {
				reuslt += getPower(cpuUtilHistory.get(time)) * (end - start)/3600;
				break;
			}
		}
		return reuslt;
	}

	private double getPower(double utilization) {
		return powerModel.getPower(utilization);
	}
	

}
