package edu.neu.cloudsimper.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Region extends GeoInfor {

	protected Map<String, Location> locations;

	public Region(MetaContainer container) {
		super(container);
		initLocations();
	}

	public double getStratLongitude() {
		return Double.parseDouble(getAttribute(Const.V_START_LONGITUDE));
	}

	public double getStartLatitude() {
		return Double.parseDouble(getAttribute(Const.V_START_LATITUDE));
	}

	public double getEndLongitude() {
		return Double.parseDouble(getAttribute(Const.V_END_LONGITUDE));
	}

	public double getEndLatitude() {
		return Double.parseDouble(getAttribute(Const.V_END_LATITUDE));
	}

	public double getArea() {
		double loX = getStratLongitude();
		double laX = getStartLatitude();
		double loY = getEndLongitude();
		double laY = getEndLatitude();
		return distance(loX, laX, loX, laY) * distance(loX, laX, loY, laX);
	}

	public double getPopulationDensity() {
		double poputlation = Double.parseDouble(getAttribute(Const.V_POPULATION));
		return poputlation / getArea();
	}

	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(getAttribute(Const.V_TIME_ZONE));
	}

	public List<Location> getLocations() {
		return new ArrayList<Location>(locations.values());
	}

	public Location getLocation(String name) {
		return locations.get(name);
	}

	public void initLocations() {
		this.locations = new HashMap<String, Location>();
		List<MetaContainer> metaLocations = container.getContainers();
		for (MetaContainer metaLocation : metaLocations) {
			Location location = new Location(metaLocation, this);
			this.locations.put(location.getName(), location);
		}
	}

}
