package edu.neu.cloudsimper_simple;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper_simple.meta.MetaManager;
import edu.neu.cloudsimper_simple.region.RegionManger;
import edu.neu.cloudsimper_simple.request.Request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class CloudSimPer_Simple {

	private static final int TICK = 60 * 10;
	private static final int SIM_TIME = 3600 * 24 * 7;

	private static int clock = 0;

	private static List<Datacenter> datacenters = new ArrayList<Datacenter>();
	private static List<Request> requests = new ArrayList<Request>();
	// private static List<LogUnit> logUnits = new ArrayList<LogUnit>(SIM_TIME / TICK);

	public static void main(String[] args) {
		CloudSimPer_Simple.init();
		while (clock <= SIM_TIME) {
			CloudSimPer_Simple.runTick(TICK);
			clock += TICK;
		}
	}

	private static void init() {
		MetaManager.init();
		RegionManger.init();
		datacenters = Datacenter.buildAll();
		requests = Request.buildAll();
		for (Request request : requests) {
			request.initDispatcher(datacenters);
		}
	}

	private static void runTick(int tick) {
		for (Request request : requests) {
			request.runDispatch(tick, datacenters);
		}
		LogUnit unit;
		for (Datacenter datacenter : datacenters) {
			unit = new LogUnit();
			unit.setTime(clock);
			datacenter.runTick(tick, unit);
			System.out.println(unit);
			//logUnits.add(unit);
		}
	}

	public static int getClock() {
		return clock;
	}
}
