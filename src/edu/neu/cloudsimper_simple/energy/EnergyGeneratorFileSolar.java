package edu.neu.cloudsimper_simple.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorFileSolar extends EnergyGeneratorFile implements EnergyGenerator {
	
	private static final double SOLAR_CONVERSION_RATE = 0.31;
	private static final double SOLAR_ILLUMINATION_COEFFICIENT = 1224.489796;

	@Override
	protected double parseEnergy(String line) {
		double solarEnergy = 0.0;
//		solarEnergy = SOLAR_CONVERSION_RATE * Double.valueOf(line) * Math.pow(SOLAR_ILLUMINATION_COEFFICIENT, 2);
		solarEnergy = Double.valueOf(line) * 1000;
		return solarEnergy;
	}

}
