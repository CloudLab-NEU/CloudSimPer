package edu.neu.cloudsimper_simple.energy;

import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyPriceAbstract extends Component implements EnergyPrice {

	private double price = Const.ENERGY_PRICE;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
