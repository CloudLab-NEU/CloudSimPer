package edu.neu.cloudsimper_simple.request;

import java.util.List;

import edu.neu.cloudsimper_simple.Datacenter;
import edu.neu.cloudsimper_simple.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface RequestDispatcher {
	
	public void setDatacenters(List<Datacenter> datacenters);

	public void setRegion(Region region);

	public void dispatch(List<RequestCore> requests);

}