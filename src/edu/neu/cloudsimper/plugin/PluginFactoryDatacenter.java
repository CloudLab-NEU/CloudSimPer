package edu.neu.cloudsimper.plugin;

import java.util.List;

import org.cloudbus.cloudsim.VmAllocationPolicy;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixHost;
import edu.neu.cloudsimper.meta.MetaContainer;

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

	public VmAllocationPolicy createVmAllocationPolicy(List<EnergyMixHost> hostList) {
		prepare(Const.P_VM_ALLOCATION_POLICY);
		defineConstructor(List.class); 
		createInstance(hostList);
		return (VmAllocationPolicy) this.instance;
	}
}
