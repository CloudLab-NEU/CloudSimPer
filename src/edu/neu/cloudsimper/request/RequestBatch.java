package edu.neu.cloudsimper.request;

import java.util.List;

import edu.neu.cloudsimper.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestBatch {
	private Region region;
	private double time;
	private List<Request> requests;

	public RequestBatch(Region region, double time, List<Request> requests) {
		this.region = region;
		this.time = time;
		this.requests = requests;
	}
	/*
	public RequestBatch() {
		this.region = null;
		this.time = 0;
		this.requests = null;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
*/
	public Region getRegion() {
		return region;
	}

	public double getTime() {
		return time;
	}

	public List<Request> getRequests() {
		return requests;
	}
	
}
