package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class RequestGeneratorAbstract implements RequestGenerator {

	protected int startDayOfYear;
	protected double lastTime;
	protected double duration;

	protected Request request;

	public RequestGeneratorAbstract() {
		this.lastTime = 0;
		this.duration = 0;
	}

	public void setStartDayOfYear(int startDayOfYear) {
		this.startDayOfYear = startDayOfYear;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop(double time) {
		this.duration = time;
	}

	@Override
	public double getLastTime() {
		return lastTime;
	}
	
	@Override
	public Region getRegion() {
		return request.getRegion();
	}

	@Override
	public void setRequestTmpl(Request request) {
		this.request = request;
	}
}
