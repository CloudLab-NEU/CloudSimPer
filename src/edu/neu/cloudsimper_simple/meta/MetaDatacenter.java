package edu.neu.cloudsimper_simple.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.neu.cloudsimper_simple.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaDatacenter extends MetaContainer {

	private Map<String, MetaContainer> hosts;
	private Map<String, MetaContainer> energies;
	private Map<String, MetaContainer> batteries;

	public MetaDatacenter(Node node) {
		super(node);
	}

	public List<MetaContainer> getHosts() {
		return new ArrayList<MetaContainer>(hosts.values());
	}

	public MetaContainer getHost(String name) {
		return hosts.get(name);
	}

	public List<MetaContainer> getEnergies() {
		return new ArrayList<MetaContainer>(energies.values());
	}

	public MetaContainer getEnergy(String name) {
		return energies.get(name);
	}

	public List<MetaContainer> getBatteries() {
		return new ArrayList<MetaContainer>(batteries.values());
	}

	public MetaContainer getBatteriy(String name) {
		return batteries.get(name);
	}

	@Override
	protected void initChildNode(Node child) {
		if (Const.T_HOSTS.equals(child.getNodeName())) {
			hosts = MetaHelper.parseContainers(child);
		} else if (Const.T_ENERGIES.equals(child.getNodeName())) {
			energies = MetaHelper.parseContainers(child);
		} else if (Const.T_BATTERY.equals(child.getNodeName())) {
			batteries = MetaHelper.parseContainers(child);
		}
	}
}
