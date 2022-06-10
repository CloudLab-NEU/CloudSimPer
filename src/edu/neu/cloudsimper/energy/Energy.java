package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixDatacenter;
import edu.neu.cloudsimper.SimPerEntity;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.plugin.PluginFactory;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Energy implements SimPerEntity {

	private String name;
	private double duration;
	private double priceKwh;
	private int size;

	private EnergyGenerator generator;
	private EnergyMixDatacenter datacenter;

	public Energy(MetaContainer container) {
		this.name = container.getName();
		this.setSize(container.getSize());
		this.priceKwh = Double.parseDouble(container.getAttribute(Const.V_PRICE_KWH));
		this.duration = 0;
		generator = PluginFactory.energy(container).createEnergyGenerator();
		generator.setEnergyObj(this);
	}

	@Override
	public void start() {
		this.duration = 0;
		generator.start();
	}

	@Override
	public void stop(double time) {
		this.duration = time;
		generator.stop(time);

	}

	public String getName() {
		return this.name;
	}

	public double getPower(double time) {
		return generator.getPower(time);
	}

	public double getEnergy(double startTime, double endTime) {
		return generator.getEnergy(startTime, endTime);
	}

	public double getEnergy() {
		return generator.getEnergy(0, duration);
	}

	public double getCost(double startTime, double endTime) {
		return generator.getEnergy(startTime, endTime) / 1000 / 3600 * priceKwh;
	}

	public double getCost() {
		return getCost(0, duration);
	}

	public EnergyMixDatacenter getDatacenter() {
		return this.datacenter;
	}

	public void setDatacenter(EnergyMixDatacenter datacenter) {
		this.datacenter = datacenter;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
