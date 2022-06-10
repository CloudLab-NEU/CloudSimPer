package edu.neu.cloudsimper.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaDatacenter extends MetaContainer {

	private Map<String, MetaContainer> hosts;
	private Map<String, MetaContainer> storages;
	private Map<String, MetaContainer> vms;
	private Map<String, MetaContainer> energies;

	public MetaDatacenter(Node node) {
		super(node);
	}

	public List<MetaContainer> getHosts() {
		return new ArrayList<MetaContainer>(hosts.values());
	}

	public MetaContainer getHost(String name) {
		return hosts.get(name);
	}

	public List<MetaContainer> getStorages() {
		return new ArrayList<MetaContainer>(storages.values());
	}

	public MetaContainer getStorage(String name) {
		return storages.get(name);
	}

	public List<MetaContainer> getVms() {
		return new ArrayList<MetaContainer>(vms.values());
	}

	public MetaContainer getVm(String name) {
		return vms.get(name);
	}

	public List<MetaContainer> getEnergies() {
		return new ArrayList<MetaContainer>(energies.values());
	}

	public MetaContainer getEnergy(String name) {
		return energies.get(name);
	}

	@Override
	protected void initChildNode(Node child) {
		if (Const.T_STORAGES.equals(child.getNodeName())) {
			storages = MetaHelper.parseContainers(child);
		} else if (Const.T_HOSTS.equals(child.getNodeName())) {
			hosts = MetaHelper.parseContainers(child);
		} else if (Const.T_VMS.equals(child.getNodeName())) {
			vms = MetaHelper.parseContainers(child);
		}else if (Const.T_ENERGIES.equals(child.getNodeName())) {
			energies = MetaHelper.parseContainers(child);
		}
	}
}
