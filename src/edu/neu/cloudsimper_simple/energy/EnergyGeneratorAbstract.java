package edu.neu.cloudsimper_simple.energy;

import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.region.Location;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorAbstract extends Component implements EnergyGenerator {

	protected Location location;

	@Override
	public final void setLocation(Location location) {
		this.location = location;
	}

}
