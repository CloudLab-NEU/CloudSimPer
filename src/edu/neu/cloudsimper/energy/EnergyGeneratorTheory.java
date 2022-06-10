package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorTheory extends EnergyGeneratorAbstract implements EnergyGenerator {

	public double getEnergy(double startTime, double endTime) {
		double result = 0;
		int startInt = (int) Math.ceil(startTime);
		int endInt = (int) Math.floor(endTime);

		for (int i = startInt; i < endInt; i++) {
			result += getPower(i)*this.getEnergyObj().getSize();
		}

		result += getPower(startInt - 1) * (startInt - startTime);
		result += getPower(endInt) * (endTime - endInt);
		return result;
	}
}
