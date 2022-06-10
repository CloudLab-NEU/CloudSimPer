package edu.neu.cloudsimper.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelDiscrete implements PowerModel {

	private double[] powerData;
	private int length;

	public PowerModelDiscrete() {
	}

	@Override
	public double getPower(double utilization) throws IllegalArgumentException {
		if (utilization % (100 / length) == 0) {
			return powerData[(int) (utilization * (100 / length))];
		}
		int lowerUtil = (int) Math.floor(utilization * (100 / length));
		int upperUtil = (int) Math.ceil(utilization * (100 / length));
		double lowerPower = powerData[lowerUtil];
		double upperPower = powerData[upperUtil];
		double delta = (upperPower - lowerPower) / (100 / length);
		double power = lowerPower + delta * (utilization - (double) lowerUtil / (100 / length)) * 100;
		return power;
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
