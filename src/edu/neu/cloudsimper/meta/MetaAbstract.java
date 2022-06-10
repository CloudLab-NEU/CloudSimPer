package edu.neu.cloudsimper.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class MetaAbstract {

	protected String name;
	protected String type;
	protected int size;
	protected Node node;
	protected Map<String, String> attributes;
	protected Map<String, MetaPlugin> plugins;

	public MetaAbstract(Node node) {
		this.node = node;
		attributes = new HashMap<String, String>();
		plugins = new HashMap<String, MetaPlugin>();
		initNode();
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public int getSize() {
		return this.size;
	}

	public List<String> getAttributes() {
		return new ArrayList<String>(attributes.values());
	}

	public String getAttribute(String name) {
		return attributes.get(name);
	}

	public List<MetaPlugin> getPlugins() {
		return new ArrayList<MetaPlugin>(plugins.values());
	}

	public MetaPlugin getPlugin(String type) {
		return plugins.get(type);
	}

	public boolean hasPlugin() {
		if (plugins != null) {
			return plugins.isEmpty();
		}
		return false;
	}

	private void initNode() {
		this.name = node.getAttributes().getNamedItem(Const.A_NAME).getTextContent();
		Node tmp = node.getAttributes().getNamedItem(Const.A_SIZE);
		if (tmp != null) {
			this.size = Integer.parseInt(tmp.getTextContent());
		}
		this.type = node.getNodeName();
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node child = nodes.item(i);
			if (child instanceof Element) {
				if (Const.T_ATTRIBUTE.equals(child.getNodeName())) {
					String name = child.getAttributes().getNamedItem(Const.A_NAME).getTextContent();
					String value = child.getFirstChild().getNodeValue();
					attributes.put(name, value);
				} else if (Const.T_PLUGINS.equals(child.getNodeName())) {
					plugins = MetaHelper.parsePlugin(child);
				} else {
					initChildNode(child);
				}
			}
		}
	}

	protected abstract void initChildNode(Node child);

}
