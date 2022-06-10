package edu.neu.cloudsimper_simple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.neu.cloudsimper_simple.battery.Battery;
import edu.neu.cloudsimper_simple.energy.Energy;
import edu.neu.cloudsimper_simple.meta.MetaDatacenter;
import edu.neu.cloudsimper_simple.meta.MetaManager;
import edu.neu.cloudsimper_simple.region.Location;
import edu.neu.cloudsimper_simple.region.RegionManger;
import edu.neu.cloudsimper_simple.request.RequestCore;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Datacenter extends Component implements SimPerEntity {

	private Location location;
	private List<Host> hosts;
	private List<Energy> energies;
	private List<Battery> batteries;

	private List<RequestCore> queue;

	private double dcUsage;
	private double reDcUsage;
	private double rePrice;
	private int currentRequestNum;
	private int nextRequestNum;
	private double lastReSupply;
	private double nextReSupply;
	private double dcIdlePower;

	private Datacenter(String name, String locationName, List<Host> hosts, List<Energy> energies,
			List<Battery> batteries) {
		this.name = name;
		this.location = RegionManger.getLocation(locationName);
		this.hosts = hosts;
		for (Host host : hosts) {
			host.setDatacenter(this);
			this.dcIdlePower += host.getIdlePower();
		}
		this.energies = energies;
		for (Energy energy : energies) {
			energy.setDatacenter(this);
		}
		this.batteries = batteries;
		for (Battery battery : batteries) {
			battery.setDatacenter(this);
		}
		this.queue = new LinkedList<RequestCore>();

		this.dcUsage = 0;
		this.reDcUsage = 0;
		this.rePrice = 0;
		this.currentRequestNum = 0;
		this.nextRequestNum = 0;
		this.lastReSupply = 0;
		this.nextReSupply = 30000;
		//System.out.println(this.queue.size());
	}

	public static List<Datacenter> buildAll() {
		return DatacenterBuilder.buildAll();
	}

	@Override
	public void runTick(int tick, LogUnit unit) {
		unit.setDcName(this.name);
		unit.setRequestNum(this.queue.size() - this.currentRequestNum);
		//unit.setDcConsume(this.dcIdlePower * tick / 1000);

		for (Host host : hosts) {
			host.runTick(tick, unit);
		}

		for (Energy energy : energies) {
			energy.runTick(tick, unit);
		}

		for (Battery battery : batteries) {
			battery.runTick(tick, unit);
		}
		double tmp =  (double)getUsedCapacity() / (getCapacity() * tick);

		this.dcUsage = tmp > 1 ? 1 : tmp;
		unit.setDcUsage(dcUsage);
		double retmp = unit.getDcConsume() / unit.getReSupply();
		this.reDcUsage = retmp > 1 ? 1 : retmp;
		unit.setReDcUsage(this.reDcUsage);
		unit.setCost(unit.getBaPrice() + unit.getDcPrice() +unit.getRePrice());
		this.rePrice = unit.getRePrice();

		if (this.queue.size() > 1000000) {
			this.queue.clear();
		}

		this.currentRequestNum = this.queue.size();
		this.nextRequestNum = 0;
		this.lastReSupply = unit.getReSupply();
		this.nextReSupply = unit.getNextReSupply();
	}

	public boolean accept(RequestCore request) {
		return queue.add(request);
	}

	public List<RequestCore> getQueue() {
		return queue;
	}

	public Location getLocation() {
		return location;
	}

	public List<Battery> getBatteries() {
		return batteries;
	}

	public List<Energy> getEnergies() {
		return energies;
	}

	public List<Host> getHosts() {
		return hosts;
	}

	public double getDcUsage() {
		return dcUsage;
	}

	public int getQueueSize() {
		return queue.size();
	}

	public int getNextRequestNum() {
		return nextRequestNum;
	}

	public void setNextRequestNum(int nextRequestNum) {
		this.nextRequestNum = nextRequestNum;
	}

	public double getLastReSupply() {
		return lastReSupply;
	}

	public double getNextReSupply() {
		return nextReSupply;
	}

	public double getPower() {
		double result = 0;
		for (Host host : hosts) {
			result += host.getPower();
		}
		return result;
	}

	public int getCapacity() {
		int result = 0;
		for (Host host : hosts) {
			result += host.getCapacity();
		}
		return result;
	}

	public int getUsedCapacity() {
		int result = 0;
		for (Host host : hosts) {
			result += host.getUsedCapacity();
		}
		return result;
	}

	public double getReDcUsage() {
		return reDcUsage;
	}

	public double getRePrice() {
		return rePrice;
	}

	private static class DatacenterBuilder {
		private static List<Datacenter> buildAll() {
			List<Datacenter> datacenters = new ArrayList<Datacenter>();
			List<MetaDatacenter> metaDcs = MetaManager.getDatacenters();
			for (MetaDatacenter metaDc : metaDcs) {
				datacenters.add(build(metaDc));
			}
			return datacenters;
		}

		private static Datacenter build(MetaDatacenter metaDc) {
			String name = metaDc.getName();
			String locationName = metaDc.getAttribute(Const.V_LOCATION);
			List<Host> hosts = Host.bulid4Datacenter(metaDc);
			List<Energy> energies = Energy.bulid4Datacenter(metaDc);
			List<Battery> batteries = Battery.bulid4Datacenter(metaDc);
			return new Datacenter(name, locationName, hosts, energies, batteries);
		}
	}

}
