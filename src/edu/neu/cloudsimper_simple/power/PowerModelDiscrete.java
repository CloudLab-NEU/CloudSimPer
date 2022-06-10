package edu.neu.cloudsimper_simple.power;

import java.util.Random;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelDiscrete implements PowerModel {

	private double[] powerData;
	private int length;

	public PowerModelDiscrete() {
	}

//	@Override
//	public double getPower(double utilization) throws IllegalArgumentException {
//		if (utilization % (100 / length) == 0) {
//			return powerData[(int) (utilization * (100 / length))];
//		}
//		int lowerUtil = (int) Math.floor(utilization * (100 / length));
//		int upperUtil = (int) Math.ceil(utilization * (100 / length));
//		double lowerPower = powerData[lowerUtil];
//		double upperPower = powerData[upperUtil];
//		double delta = (upperPower - lowerPower) / (100 / length);
//		double power = lowerPower + delta * (utilization - (double) lowerUtil / (100 / length)) * 100;
//		if (utilization == 1) {
//			return power;
//		}
//
//		Random rand = new Random();
//		double ratio = rand.nextDouble() * 0.1 + 0.95;
//		return power * ratio;
//	}

	@Override
	public double getPower(double utilization) {
		int offset = (int) Math.floor(utilization / (1 / (double)(length - 1)));
		double power =  powerData[offset];
		if (utilization == 1) {
			return power * 0.9;
		}

		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.9;
		return power * ratio * 0.9;
	}

	//baseline
//	@Override
//	public double getPower(double utilization) {
//		if (utilization == 0) {
//			return powerData[0] * 0.85;
//		} else {
//			return powerData[length - 1] * 0.85;
//		}
//	}


	@Override
	public double getIdlePower() {
		return powerData[0];
	}

	@Override
	public double getUsedPower(double utilization) {
		int offset = (int) Math.floor(utilization / (1 / (double)(length - 1)));
		return (powerData[offset] - powerData[0]) * 0.85;
	}

	public void setPowerData(String powerStr) {
		String[] array = powerStr.split("[,]+");
		this.length = array.length;
		powerData = new double[length];
		for (int i = 0; i < length; i++) {
			powerData[i] = Double.parseDouble(array[i]);
		}
	}

}
