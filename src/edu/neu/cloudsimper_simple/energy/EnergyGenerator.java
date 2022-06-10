package edu.neu.cloudsimper_simple.energy;

import edu.neu.cloudsimper_simple.region.Location;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface EnergyGenerator {
	
	public void start();
	
	public double nextEnergy(int duration);

//	public double nextnextEnergy(int duration);

	public String getName();

	public void setLocation(Location location);
}