package edu.neu.cloudsimper_simple.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.neu.cloudsimper_simple.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaManager {
	public static Map<String, MetaDatacenter> datacenters;
	public static Map<String, MetaContainer> hosts;
	public static Map<String, MetaContainer> plugins;
	public static Map<String, MetaContainer> regions;
	public static Map<String, MetaContainer> requests;
	public static Map<String, MetaContainer> energies;
	public static Map<String, MetaContainer> batteries;

	public static void init() {
		initDatacenters();
		initHosts();
		initPluginInfors();
		initRegions();
		initRequests();
		initEnergies();
		initBatteries();
	}

	public static List<MetaDatacenter> getDatacenters() {
		return new ArrayList<MetaDatacenter>(datacenters.values());
	}

	public static MetaDatacenter getDatacenter(String name) {
		return datacenters.get(name);
	}

	public static List<MetaContainer> getHosts() {
		return new ArrayList<MetaContainer>(hosts.values());
	}

	public static MetaContainer getHost(String name) {
		return hosts.get(name);
	}

	public static List<MetaContainer> getPluginInfors() {
		return new ArrayList<MetaContainer>(plugins.values());
	}

	public static MetaContainer getPluginInfor(String name) {
		return plugins.get(name);
	}

	public static List<MetaContainer> getRegions() {
		return new ArrayList<MetaContainer>(regions.values());
	}

	public static MetaContainer getRegion(String name) {
		return regions.get(name);
	}

	public static List<MetaContainer> getRequests() {
		return new ArrayList<MetaContainer>(requests.values());
	}

	public static MetaContainer getRequest(String name) {
		return requests.get(name);
	}

	public static List<MetaContainer> getEnergies() {
		return new ArrayList<MetaContainer>(energies.values());
	}

	public static MetaContainer getEnergy(String name) {
		return energies.get(name);
	}

	public static List<MetaContainer> getBatteries() {
		return new ArrayList<MetaContainer>(batteries.values());
	}

	public static MetaContainer getBattery(String name) {
		return batteries.get(name);
	}

	private static void initDatacenters() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_DATACENTER);
		datacenters = MetaHelper.parseDatacenters(root);
	}

	private static void initPluginInfors() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_PLUGIN_INFOR);
		plugins = MetaHelper.parseContainers(root);
	}

	private static void initHosts() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_HOST);
		hosts = MetaHelper.parseContainers(root);
	}

	private static void initRegions() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_REGION);
		regions = MetaHelper.parseContainers(root);
	}

	private static void initRequests() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_REQUEST);
		requests = MetaHelper.parseContainers(root);
	}

	private static void initEnergies() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_ENERGY);
		energies = MetaHelper.parseContainers(root);
	}

	private static void initBatteries() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_BATTERY);
		batteries = MetaHelper.parseContainers(root);
	}
}
