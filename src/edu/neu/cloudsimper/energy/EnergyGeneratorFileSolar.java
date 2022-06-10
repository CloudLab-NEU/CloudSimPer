package edu.neu.cloudsimper.energy;

import java.io.BufferedReader;
import java.io.FileReader;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorFileSolar extends EnergyGeneratorFile implements EnergyGenerator {
	@Override
	protected double parseEnergy(String line) {
		double solarEnergy = 0.0;
		// TODO parseSolar2Energy
		solarEnergy = Const.SOLAR_CONVERSION_RATE*Double.valueOf(line);
		return solarEnergy;
	}

}
