package edu.neu.cloudsimper_simple.request;

import edu.neu.cloudsimper_simple.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface RequestGenerator {
	
	public void start();
	
	public int nextBatchSize(int duration);

	public void setRegion(Region region);

	public String getName();

}