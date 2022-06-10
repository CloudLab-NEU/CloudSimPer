package edu.neu.cloudsimper_simple.meta;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class MetaPlugin extends MetaAbstract {

	@Deprecated
	@Override
	public List<MetaPlugin> getPlugins() {
		return new ArrayList<MetaPlugin>();
	}

	public MetaPlugin(Node node) {
		super(node);
	}

	@Deprecated
	@Override
	public MetaPlugin getPlugin(String type) {
		return null;
	}

	@Deprecated
	@Override
	public boolean hasPlugin() {
		return false;
	}

	@Override
	protected void initChildNode(Node child) {
	}
}
