package edu.neu.cloudsimper_simple.power;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface PowerModel {
	
	public double getPower(double utilization);

	public double getIdlePower();

	public double getUsedPower(double utilization);

}
