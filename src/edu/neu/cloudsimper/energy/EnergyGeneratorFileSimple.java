package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorFileSimple extends EnergyGeneratorFile implements EnergyGenerator {

	@Override
	protected double parseEnergy(String line) {
		return Double.parseDouble(line);
	}

}
