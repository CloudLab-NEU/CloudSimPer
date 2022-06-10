package edu.neu.cloudsimper_simple.power;

import java.util.Random;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelLogarithm extends PowerModelContinue implements PowerModel {

	private double base;
	private double constant;

	@Override
	public double getPower(double utilization) {
		if (utilization == 0) {
			return this.idle;
		}
		this.constant = (max - idle) / (Math.log(100) / Math.log(this.base));
//		return this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base));

		if (utilization == 1) {
			return this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base));
		}
		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.9;
		return ratio * (this.idle + this.constant * (Math.log(utilization * 100) / Math.log(this.base)));
	}

	@Override
	public double getUsedPower(double utilization) {
		if (utilization == 0) {
			return this.idle;
		}
		this.constant = (max - idle) / (Math.log(100) / Math.log(this.base));
		return this.constant * (Math.log(utilization * 100) / Math.log(this.base));
	}

	public void setBase(double base) {
		this.base = base;
		this.constant = (max - idle) / (Math.log(100) / Math.log(this.base));
	}
}
