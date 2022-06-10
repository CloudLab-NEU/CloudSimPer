package edu.neu.cloudsimper_simple.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyPriceConstant extends EnergyPriceAbstract implements EnergyPrice {

	@Override
	public double calculate(double energy) {
		return this.getPrice() * energy;
	}
}
