package edu.neu.cloudsimper_simple.energy;

import java.util.Random;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyPriceFunction extends EnergyPriceAbstract implements EnergyPrice {

	private double p0;
	private double pm;
	private double t;

	private double[] coef = new double[] {0.1, 0.1, 0.1, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.9,
										1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.1, 0.1};

	public void setP0(double p0) {
		this.p0 = p0;
	}

	public void setPm(double pm) {
		this.pm = pm;
	}

	@Override
	public double calculate(double energy) {
		this.t = (CloudSimPer_Simple.getClock() % (3600 * 24) / 3600);
		double c = coef[(int)(12 - Math.abs(t - 12))];
		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.95;
		double ew = pm / p0;
		double cost = pm * (ew + Math.exp(1) * c * ratio);
		return cost * energy;
	}

}
