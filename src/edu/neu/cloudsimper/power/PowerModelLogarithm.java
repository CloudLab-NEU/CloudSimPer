package edu.neu.cloudsimper.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelLogarithm extends PowerModelContinue implements PowerModel {

	private double base;
	private double constant;

	@Override
	public double getPower(double utilization) {
		return this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base));
	}

	public void setBase(double base) {
		this.base = base;
		this.constant = (max - idle) / (Math.log(100) / Math.log(this.base));
	}
}
