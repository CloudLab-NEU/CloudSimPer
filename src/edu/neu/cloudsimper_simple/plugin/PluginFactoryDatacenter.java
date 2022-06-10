package edu.neu.cloudsimper_simple.plugin;

import edu.neu.cloudsimper_simple.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryDatacenter extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryDatacenter() {
	}

	@Override
	public PluginFactoryDatacenter createFacorty(MetaContainer container) {
		return (PluginFactoryDatacenter) super.createFacorty(container);
	}

}
