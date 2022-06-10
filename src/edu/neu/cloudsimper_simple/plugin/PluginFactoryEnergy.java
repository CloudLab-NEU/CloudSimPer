package edu.neu.cloudsimper_simple.plugin;

import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.energy.EnergyPrice;
import edu.neu.cloudsimper_simple.energy.EnergyGenerator;
import edu.neu.cloudsimper_simple.meta.MetaContainer;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryEnergy extends PluginFactoryAbstract implements PluginFactory {
	protected PluginFactoryEnergy() {
	}

	@Override
	public PluginFactoryEnergy createFacorty(MetaContainer container) {
		return (PluginFactoryEnergy) super.createFacorty(container);
	}

	public EnergyGenerator createEnergyGenerator() {
		prepare(Const.P_ENERGY_GENERATOR);
		createInstance();
		setPluginAttribute();
		return (EnergyGenerator) this.instance;
	}
	
	public EnergyPrice createEnergyPrice() {
		prepare(Const.P_ENERGY_PRICE);
		createInstance();
		setPluginAttribute();
		return (EnergyPrice) this.instance;
	}
}
