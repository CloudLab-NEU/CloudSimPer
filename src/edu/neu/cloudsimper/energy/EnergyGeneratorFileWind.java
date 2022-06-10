package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorFileWind extends EnergyGeneratorFile implements EnergyGenerator {
	@Override
	protected double parseEnergy(String line) {
		// TODO parseWind2Energy
		double windSpeed = 0.0;
		double windEnergy = 0.0;
		windSpeed = Double.parseDouble(line);
		windEnergy = 0.5 * Math.pow(windSpeed, 3) * Math.PI * Math.pow(Const.VANE_ENGINE_RADIUS,2) * Const.AIR_DENSITRY;
		return windEnergy;
	}

}
