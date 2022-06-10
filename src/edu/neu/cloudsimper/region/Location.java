package edu.neu.cloudsimper.region;

import java.util.TimeZone;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Location extends GeoInfor {


	private Region region;

	public Location(MetaContainer container, Region region) {
		super(container);
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public double getLongitude() {
		return Double.parseDouble(getAttribute(Const.V_LONGITUDE));
	}

	public double getLatitude() {
		return Double.parseDouble(getAttribute(Const.V_LATITUDE));
	}

	public TimeZone getTimeZone() {
		return region.getTimeZone();
	}

	public double distance(Location location) {
		double loX = getLongitude();
		double laX = getLatitude();
		double loY = location.getLongitude();
		double laY = location.getLatitude();
		return distance(loX,laX,loY,laY);
	}
}
