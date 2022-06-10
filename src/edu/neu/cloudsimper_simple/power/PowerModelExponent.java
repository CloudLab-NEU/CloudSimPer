package edu.neu.cloudsimper_simple.power;

import java.util.Random;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelExponent extends PowerModelContinue implements PowerModel {

	private double exp;
	protected double constant;

	@Override
	public double getPower(double utilization) {
		this.constant = (max - idle) / Math.pow(100, exp);
//		return this.idle + this.constant * Math.pow(utilization * 100, this.exp);
		if (utilization == 1) {
			return this.idle + this.constant * Math.pow(utilization * 100, this.exp);
		}
		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.9;
		return (this.idle + this.constant * Math.pow(utilization * 100, this.exp)) * ratio;
	}

	@Override
	public double getUsedPower(double utilization) {
		this.constant = (max - idle) / Math.pow(100, exp);
		return this.constant * Math.pow(utilization * 100, this.exp);
	}

	public void setExp(double exp) {
		this.exp = exp;
		this.constant = (max - idle) / Math.pow(100, exp);
	}
}
