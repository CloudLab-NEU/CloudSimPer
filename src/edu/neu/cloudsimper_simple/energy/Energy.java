package edu.neu.cloudsimper_simple.energy;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.Datacenter;
import edu.neu.cloudsimper_simple.LogUnit;
import edu.neu.cloudsimper_simple.SimPerEntity;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.meta.MetaDatacenter;
import edu.neu.cloudsimper_simple.meta.MetaManager;
import edu.neu.cloudsimper_simple.plugin.PluginFactory;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Energy extends Component implements SimPerEntity {

	private EnergyGenerator generator;
	private EnergyPrice price;
	private Datacenter datacenter;
	
	private double current;
	private double ePrice;

	private double next;

	private Energy(MetaContainer container, int index) {
		this.name = container.getName() + index;
		this.generator = PluginFactory.energy(container).createEnergyGenerator();
		this.generator.start();
		this.price = PluginFactory.energy(container).createEnergyPrice();
	}

	public static List<Energy> bulid4Datacenter(MetaDatacenter container) {
		return EnergyBuilder.bulid4Datacenter(container);
	}

	public Datacenter getDatacenter() {
		return this.datacenter;
	}

	public void setDatacenter(Datacenter datacenter) {
		this.datacenter = datacenter;
		this.generator.setLocation(datacenter.getLocation());
	}

	@Override
	public void runTick(int tick, LogUnit unit) {
//		this.next = generator.nextnextEnergy(tick)/1000;
		this.next = 0;
		this.current = generator.nextEnergy(tick)/1000;
		this.ePrice = price.calculate(current);
		unit.setRePrice(ePrice);
		unit.setReSupply(current);
		unit.setNextReSupply(next);
	}

	private static class EnergyBuilder {
		private static List<Energy> bulid4Datacenter(MetaDatacenter container) {
			List<Energy> energies = new ArrayList<Energy>();
			List<MetaContainer> metaDcEnergies = container.getEnergies();
			for (MetaContainer metaDcEnergy : metaDcEnergies) {
				String name = metaDcEnergy.getName();
				int size = metaDcEnergy.getSize();
				MetaContainer metaEnergy = MetaManager.getEnergy(name);
				for (int i = 1; i <= size; i++) {
					energies.add(new Energy(metaEnergy, i));
				}
			}
			return energies;
		}
	}
}
