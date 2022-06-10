package edu.neu.cloudsimper.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaContainer extends MetaAbstract {

	protected Map<String, MetaContainer> containers;

	public MetaContainer(Node node) {
		super(node);
	}

	public List<MetaContainer> getContainers() {
		return new ArrayList<MetaContainer>(containers.values());
	}

	public MetaContainer getContainer(String name) {
		return containers.get(name);
	}

	public boolean hasContainers() {
		if (containers != null) {
			return containers.isEmpty();
		}
		return false;
	}

	@Override
	protected void initChildNode(Node child) {
		if (!Const.T_ATTRIBUTE.equals(child.getNodeName()) && !Const.T_PLUGINS.equals(child.getNodeName())) {
			containers = MetaHelper.parseContainers(child);
		}
	}

}
