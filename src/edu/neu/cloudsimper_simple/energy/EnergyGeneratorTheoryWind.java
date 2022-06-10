package edu.neu.cloudsimper_simple.energy;

import org.apache.commons.math3.distribution.WeibullDistribution;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorTheoryWind extends EnergyGeneratorAbstract implements EnergyGenerator {
	private static final double AIR_DENSITRY = 1.29;
	private static final double VANE_ENGINE_RADIUS = 52;

	private WeibullDistribution weibull;

	@Override
	public void start() {
		this.weibull = new WeibullDistribution(10, 150);
	}

	@Override
	public double nextEnergy(int duration) {
		// TODO TheoryWind
		// generate the wind speeds by
		// org.cloudbus.cloudsim.distributions.WeibullDistr
		// the parameters should be changed every 30 days and make sure days
		// with strong wind for different locations occur twice per year
		// WeibullDistr Scale randomly between boundary values
		// WeibullDistr Shape randomly between boundary values

		double windEnergy = 0.5 * AIR_DENSITRY * Math.pow(this.weibull.sample(), 3) * Math.PI * Math.pow(VANE_ENGINE_RADIUS, 2);
		return windEnergy / 1000;
	}
//	@Override
//	public double nextnextEnergy(int duration) {
//		return 0;
//	}
}
