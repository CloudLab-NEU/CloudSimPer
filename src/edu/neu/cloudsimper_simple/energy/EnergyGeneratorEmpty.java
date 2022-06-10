package edu.neu.cloudsimper_simple.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorEmpty extends EnergyGeneratorAbstract {
	@Override
	public double nextEnergy(int duration) {
		return 0;
	}
	public double nextnextEnergy(int duration) {
		return 0;
	}

	@Override
	public void start() {
		
	}

}
