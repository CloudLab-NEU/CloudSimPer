package edu.neu.cloudsimper_simple.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class PowerModelContinue implements PowerModel {

	protected double idle;
	protected double max;
	
	public void setIdle(double idle) {
		this.idle = idle;
	}

	public void setMax(double max) {
		this.max = max;
	}

	@Override
	public abstract double getPower(double utilization);

	@Override
	public double getIdlePower() {
		return this.idle;
	}

	@Override
	public abstract double getUsedPower(double utilization);

}
