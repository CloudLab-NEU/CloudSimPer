package edu.neu.cloudsimper_simple.battery;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.Datacenter;
import edu.neu.cloudsimper_simple.LogUnit;
import edu.neu.cloudsimper_simple.SimPerEntity;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.meta.MetaDatacenter;
import edu.neu.cloudsimper_simple.meta.MetaManager;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Battery extends Component implements SimPerEntity {

	private double capacity;
	private double power;
	private double dod;
	private int lifecyle;

	private double currentPower;
	private double currentCapacity;

	protected double price;
	protected Datacenter datacenter;

	private Battery() {
	}

	private Battery(MetaContainer container, int index) {
		this.name = container.getName() + index;
		this.price = Double.parseDouble(container.getAttribute(Const.V_PRICE)) / 3600;
		this.capacity = Integer.parseInt(container.getAttribute(Const.V_CAPACITY));
		this.power = Integer.parseInt(container.getAttribute(Const.V_POWER));
		this.dod = Integer.parseInt(container.getAttribute(Const.V_DOD));
		this.lifecyle = Integer.parseInt(container.getAttribute(Const.V_LIFECYLE));
		this.currentPower = 0;
	}

	public static List<Battery> bulid4Datacenter(MetaDatacenter container) {
		return BatteryBuilder.bulid4Datacenter(container);
	}

	@Override
	public void runTick(int tick, LogUnit unit) {
		double result = 0;
		if (unit.getDcConsume() > unit.getReSupply()) {
			result = discharge(unit.getDcConsume() - unit.getReSupply());
			unit.setReBaUsage(0);
			unit.setReUsage(1);
			unit.setBaPrice(result * this.price);
			result = 0 - result;
		} else {
			result = charge(unit.getReSupply() - unit.getDcConsume());
			double reBaUsage = result / unit.getReSupply() > 1 ? 1 : result / unit.getReSupply();
			unit.setReBaUsage(reBaUsage);
			double reUsage = (result + unit.getDcConsume()) / unit.getReSupply() > 1 ? 1 : (result + unit.getDcConsume()) / unit.getReSupply();
			unit.setReUsage(reUsage);
			unit.setBaPrice(0);
		}
		unit.setBaSupply(result);
	}

	public void setDatacenter(Datacenter datacenter) {
		this.datacenter = datacenter;
	}

	private double charge(double cpower) {
		double result = capacity - currentPower;
		this.currentPower += cpower;
		if (currentPower > capacity) {
			currentPower = capacity;
			return result;
		}
		return cpower;
	}

	private double discharge(double needpower) {
		double result = currentPower;
		if (currentPower >= needpower) {
			currentPower -= needpower;
			return needpower;
		}
		currentPower = 0;
		return result;
	}

	private static class BatteryBrown extends Battery {
		
		public BatteryBrown(MetaContainer container) {
			super();
			this.price = Double.parseDouble(container.getAttribute(Const.V_PRICE)) / 3600;
		}

		@Override
		public void runTick(int tick, LogUnit unit) {
			double dcPrice = 0;
			if (unit.getDcConsume() > unit.getReSupply()) {
				double brownEnergy = unit.getDcConsume() - unit.getReSupply() + unit.getBaSupply();
				dcPrice = brownEnergy * this.price;
			}
			unit.setDcPrice(dcPrice);
		}
	}

	private static class BatteryBuilder {
		private static List<Battery> bulid4Datacenter(MetaDatacenter container) {
			List<Battery> batteries = new ArrayList<Battery>();
			List<MetaContainer> metaDcBatteries = container.getBatteries();
			for (MetaContainer metaDcBattery : metaDcBatteries) {
				String name = metaDcBattery.getName();
				int size = metaDcBattery.getSize();
				MetaContainer metaBattery = MetaManager.getBattery(name);
				for (int i = 1; i <= size; i++) {
					batteries.add(new Battery(metaBattery, i));
				}
			}
			MetaContainer metaBattery = MetaManager.getBattery(container.getName());
			batteries.add(new BatteryBrown(metaBattery));
			return batteries;
		}
	}

}
