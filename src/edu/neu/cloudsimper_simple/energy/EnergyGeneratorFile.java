package edu.neu.cloudsimper_simple.energy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.neu.cloudsimper_simple.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorFile extends EnergyGeneratorPeriodic implements EnergyGenerator {

	@Override
	protected final void initEnergy() {
		try {
			FileReader fr = new FileReader(
					Const.CONIF_FILE_ENERGY_DATA + this.getName() + Const.CONIF_SUFFIX_ENERGY);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			for (int i = 0; i < this.size; i++) {
				if ((line = br.readLine()) != null) {
					this.energies[i] = parseEnergy(line);
				} else {
					this.energies[i] = 0;
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected abstract double parseEnergy(String line);

}
