package edu.neu.cloudsimper.meta;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaHelper {

	public static Element parseFile(String fileName) {
		Element root = null;
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dbuider = df.newDocumentBuilder();
			Document document = dbuider.parse(fileName);
			root = document.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}

	public static Map<String, MetaContainer> parseContainers(Node node) {
		Map<String, MetaContainer> containers = new HashMap<String, MetaContainer>();
		NodeList containerNodeList = node.getChildNodes();
		for (int i = 0; i < containerNodeList.getLength(); i++) {
			Node containerNode = containerNodeList.item(i);
			if (containerNode instanceof Element) {
				MetaContainer container = new MetaContainer(containerNode);
				containers.put(container.getName(), container);
			}
		}
		return containers;
	}

	public static Map<String, MetaDatacenter> parseDatacenters(Node node) {
		Map<String, MetaDatacenter> datacenters = new HashMap<String, MetaDatacenter>();
		NodeList datacenterNodeList = node.getChildNodes();
		for (int i = 0; i < datacenterNodeList.getLength(); i++) {
			Node datacenterNode = datacenterNodeList.item(i);
			if (datacenterNode instanceof Element) {
				MetaDatacenter datacenter = new MetaDatacenter(datacenterNode);
				datacenters.put(datacenter.getName(), datacenter);
			}
		}
		return datacenters;
	}

	public static Map<String, MetaPlugin> parsePlugin(Node child) {
		Map<String, MetaPlugin> plugins = new HashMap<String, MetaPlugin>();
		NodeList pluginNodeList = child.getChildNodes();
		for (int i = 0; i < pluginNodeList.getLength(); i++) {
			Node pluginNode = pluginNodeList.item(i);
			if (pluginNode instanceof Element) {
				MetaPlugin plugin = new MetaPlugin(pluginNode);
				plugins.put(plugin.getType(), plugin);
			}
		}
		return plugins;
	}
}
