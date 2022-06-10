package edu.neu.cloudsimper.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelLinear extends PowerModelContinue implements PowerModel {

	@Override
	public double getPower(double utilization) {
		return this.idle + (max - idle)* utilization ;
	}

}
