package edu.neu.cloudsimper.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaManager {
	public static Map<String, MetaDatacenter> datacenters;
	public static Map<String, MetaContainer> hosts;
	public static Map<String, MetaContainer> pluginInfors;
	public static Map<String, MetaContainer> regions;
	public static Map<String, MetaContainer> requests;
	public static Map<String, MetaContainer> storages;
	public static Map<String, MetaContainer> vms;

	public static void init() {
		initDatacenters();
		initHosts();
		initPluginInfors();
		initRegions();
		initRequests();
		initStorages();
		initVms();
	}

	public static void refresh() {
		datacenters.clear();
		hosts.clear();
		pluginInfors.clear();
		regions.clear();
		requests.clear();
		storages.clear();
		vms.clear();
		init();
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
		return new ArrayList<MetaContainer>(pluginInfors.values());
	}

	public static MetaContainer getPluginInfor(String name) {
		return pluginInfors.get(name);
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

	public static List<MetaContainer> getStorages() {
		return new ArrayList<MetaContainer>(storages.values());
	}

	public static MetaContainer getStorage(String name) {
		return storages.get(name);
	}

	public static List<MetaContainer> getVms() {
		return new ArrayList<MetaContainer>(vms.values());
	}

	public static MetaContainer getVm(String name) {
		return vms.get(name);
	}

	private static void initDatacenters() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_DATACENTER);
		datacenters = MetaHelper.parseDatacenters(root);
	}

	private static void initPluginInfors() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_PLUGIN_INFOR);
		pluginInfors = MetaHelper.parseContainers(root);
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

	private static void initStorages() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_STORAGE);
		storages = MetaHelper.parseContainers(root);
	}

	private static void initVms() {
		Node root = MetaHelper.parseFile(Const.CONIF_FILE_VM);
		vms = MetaHelper.parseContainers(root);
	}
}
