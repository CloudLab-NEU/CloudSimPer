package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorEmpty extends EnergyGeneratorAbstract {

	@Override
	public double getPower(double time) {
		return 5;
	}

	@Override
	public double getEnergy(double startTime, double endTime) {
		return 5;
	}

}
