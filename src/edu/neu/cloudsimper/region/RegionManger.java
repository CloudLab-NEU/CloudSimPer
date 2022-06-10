package edu.neu.cloudsimper.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaManager;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RegionManger {

	private static Map<String, Region> regions = new HashMap<String, Region>();
	private static Map<String, Location> locations = new HashMap<String, Location>();

	public static void init() {
		regions = new HashMap<String, Region>();
		locations = new HashMap<String, Location>();
		List<MetaContainer> metaRegion = MetaManager.getRegions();
		for (MetaContainer container : metaRegion) {
			Region region = new Region(container);
			regions.put(region.getName(), region);
			List<Location> locationList = region.getLocations();
			for (Location location : locationList) {
				locations.put(location.getName(), location);
			}
		}
	}

	public static void refresh() {
		regions.clear();
		locations.clear();
		init();
	}

	public static List<Region> getRegions() {
		return new ArrayList<Region>(regions.values());
	}

	public static Region getRegion(String name) {
		return regions.get(name);
	}

	public static List<Location> getLocations() {
		return new ArrayList<Location>(locations.values());
	}

	public static Location getLocation(String name) {
		return locations.get(name);
	}
}
