package edu.neu.cloudsimper.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PowerModelConst implements PowerModel {

	@Override
	public double getPower(double utilization) {
		return 100;
	}

}
