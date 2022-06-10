package edu.neu.cloudsimper_simple.power;

import java.util.Random;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelLinear extends PowerModelContinue implements PowerModel {

	@Override
	public double getPower(double utilization) {
//		return this.idle + (max - idle)* utilization;
		if (utilization == 1) {
			return this.idle + (max - idle) * utilization * 0.9;
		}
		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.9;
		return ratio * (this.idle + (max - idle)* utilization) * 0.9;
	}

	@Override
	public double getUsedPower(double utilization) {
		//return (max - idle) * utilization * 0.9;

		//baseline
		if (utilization == 0) {
			return 0;
		}
		return (max - idle) * 0.8;
	}

}
