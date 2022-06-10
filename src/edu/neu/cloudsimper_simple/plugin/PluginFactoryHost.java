package edu.neu.cloudsimper_simple.plugin;

import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.power.PowerModel;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryHost extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryHost() {
	}

	@Override
	public PluginFactoryHost createFacorty(MetaContainer container) {
		return (PluginFactoryHost) super.createFacorty(container);
	}
	
	public PowerModel createPowerModel() {
		prepare(Const.P_POWER_MODEL);
		createInstance();
		setPluginAttribute();
		return (PowerModel) this.instance;
	}
}
