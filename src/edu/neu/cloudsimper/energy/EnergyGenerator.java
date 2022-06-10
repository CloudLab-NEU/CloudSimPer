package edu.neu.cloudsimper.energy;

import edu.neu.cloudsimper.SimPerEntity;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface EnergyGenerator extends SimPerEntity {

	public double getPower(double time);

	public double getEnergy(double startTime, double endTime);

	public Energy getEnergyObj();

	public void setEnergyObj(Energy energy);
}