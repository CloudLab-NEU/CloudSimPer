package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.SimPerEntity;
import edu.neu.cloudsimper.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface RequestGenerator extends SimPerEntity {

	public int nextBatchSize(double time);

	public double getLastTime();

	public Region getRegion();

	public void setRequestTmpl(Request request);
}