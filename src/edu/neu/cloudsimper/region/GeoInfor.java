package edu.neu.cloudsimper.region;

import edu.neu.cloudsimper.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class GeoInfor {

	private static final double EARTH_RADIUS = 6371000;

	protected MetaContainer container;

	public GeoInfor(MetaContainer container) {
		this.container = container;
	}

	public String getName() {
		return container.getName();
	}

	public String getAttribute(String name) {
		return container.getAttribute(name);
	}

	public double distance(double loX, double laX, double loY, double laY) {
		double radLoX = loX * Math.PI / 180.0;
		double radLaX = laX * Math.PI / 180.0;
		double radLoY = loY * Math.PI / 180.0;
		double radLaY = laY * Math.PI / 180.0;
		double lo = radLoX - radLoY;
		double la = radLaX - radLaY;
		double distance = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(la / 2), 2) + Math.cos(radLaX) * Math.cos(radLaY) * Math.pow(Math.sin(lo / 2), 2)));
		distance *= EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		return distance;
	}
}