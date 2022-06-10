package edu.neu.cloudsimper_simple.request;

import java.util.List;

import edu.neu.cloudsimper_simple.Datacenter;
import edu.neu.cloudsimper_simple.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class RequestDispatcherAbstract implements RequestDispatcher {

	protected List<Datacenter> datacenters;
	protected List<RequestCore> requests;
	protected Region region;

	@Override
	public final void setDatacenters(List<Datacenter> datacenters) {
		this.datacenters = datacenters;

	}

	@Override
	public final void setRegion(Region region) {
		this.region = region;
	}

}