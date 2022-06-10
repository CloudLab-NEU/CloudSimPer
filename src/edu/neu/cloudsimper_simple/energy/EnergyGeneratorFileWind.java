package edu.neu.cloudsimper_simple.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorFileWind extends EnergyGeneratorFile implements EnergyGenerator {
	private static final double AIR_DENSITRY = 1.29;
	private static final double VANE_ENGINE_RADIUS = 52;

	@Override
	protected double parseEnergy(String line) {
		double windSpeed = 0.0;
		double windEnergy = 0.0;
		windSpeed = Double.parseDouble(line);
		windEnergy = 0.5 * Math.pow(windSpeed, 3) * Math.PI * Math.pow(VANE_ENGINE_RADIUS, 2) * AIR_DENSITRY;
		return windEnergy * 10;
	}

}
