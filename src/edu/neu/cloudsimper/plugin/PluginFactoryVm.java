package edu.neu.cloudsimper.plugin;

import org.cloudbus.cloudsim.CloudletScheduler;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryVm extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryVm() {
	}

	@Override
	public PluginFactoryVm createFacorty(MetaContainer container) {
		return (PluginFactoryVm) super.createFacorty(container);
	}

	public CloudletScheduler createCloudletScheduler() {
		prepare(Const.P_CLOUDLET_SCHEDULER);
		createInstance();
		setPluginAttribute();
		return (CloudletScheduler) this.instance;
	}
}
