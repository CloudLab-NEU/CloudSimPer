package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorBatteryLinear extends EnergyGeneratorPeriodic implements EnergyGenerator {

	@Override
	protected void initEnergy() {
		double k = capacity / (size - 1);
		for (int i = 0; i < energies.length; i++) {
			energies[i] = k * i*this.getEnergyObj().getSize();
		}
	}
}
