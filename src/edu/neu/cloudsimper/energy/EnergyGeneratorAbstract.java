package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorAbstract implements EnergyGenerator {
	protected Energy energy;

	@Override
	public Energy getEnergyObj() {
		return energy;
	}

	@Override
	public void setEnergyObj(Energy energy) {
		this.energy = energy;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop(double time) {
	}

}
