package edu.neu.cloudsimper_simple.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelConst implements PowerModel {

	@Override
	public double getPower(double utilization) {
		return 100;
	}

	@Override
	public double getIdlePower() {
		return 0;
	}

	@Override
	public double getUsedPower(double utilization) {
		return 100;
	}

}
