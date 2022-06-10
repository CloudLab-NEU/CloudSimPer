package edu.neu.cloudsimper.plugin;

import java.util.List;

import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;


import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.power.PowerModel;

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

	public BwProvisioner createBwProvisioner() {
		prepare(Const.P_BW_PROVISIONER);
		defineConstructor(long.class);
		long bw = Long.parseLong(container.getAttribute(Const.V_BW));
		createInstance(bw);
		setPluginAttribute();
		return (BwProvisioner) this.instance;
	}

	public PeProvisioner createPeProvisioner() {
		prepare(Const.P_PE_PROVISIONER);
		defineConstructor(double.class);
		double mips = Double.parseDouble(container.getAttribute(Const.V_MIPS));
		createInstance(mips);
		setPluginAttribute();
		return (PeProvisioner) this.instance;
	}

	public RamProvisioner createRamProvisioner() {
		prepare(Const.P_RAM_PROVISIONER);
		defineConstructor(int.class);
		int ram = Integer.parseInt(container.getAttribute(Const.V_RAM));
		createInstance(ram);
		setPluginAttribute();
		return (RamProvisioner) this.instance;
	}

	public PowerModel createPowerModel() {
		prepare(Const.P_POWER_MODEL);
		createInstance();
		setPluginAttribute();
		return (PowerModel) this.instance;
	}

	public VmScheduler createVmScheduler(List<Pe> peList) {
		prepare(Const.P_VM_SCHEDULER);
		defineConstructor(List.class); 
		createInstance(peList);
		setPluginAttribute();
		return (VmScheduler) this.instance;
	}
}
