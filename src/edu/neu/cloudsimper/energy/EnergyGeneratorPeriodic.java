package edu.neu.cloudsimper.energy;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorPeriodic extends EnergyGeneratorAbstract implements EnergyGenerator {

	protected long cycle;
	protected int size;
	protected int interval;
	/**
	 * the possible max value of one cycle, the min value is 0.
	 */
	protected double capacity;
	protected double[] energies;

	protected double energyCycle;
	protected Energy energy;

	public void setSize(int size) {
		this.size = size;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	/**
	 * statrPartEnergy:energyStartTime2CycleEnd; loopPartEnergy:energyCycle * loop;
	 * endPartEnergy:energyCycleStart2EndTime
	 */
	@Override
	public double getEnergy(double startTime, double endTime) {
		double result = 0;
		if (startTime >= endTime) {
			result = 0;
		} else {
			double loop = Math.floor((endTime - startTime) / cycle);
			result = energyStartTime2CycleEnd(startTime) + energyCycle * loop + energyCycleStart2EndTime(endTime);
		}
		return result;
	}

	@Override
	public double getPower(double time) {
		double start = time - 1 < 0 ? 0 : time - 1;
		return getEnergy(start, time);
	}

	@Override
	public void start() {
		this.cycle = interval * size;
		this.energies = new double[size];
		initEnergy();
		for (double energy : energies) {
			this.energyCycle += energy;
		}
	}

	protected abstract void initEnergy();

	private double energyStartTime2CycleEnd(double startTime) {
		return energyCycle - energyCycleStart2EndTime(startTime);
	}

	private double energyCycleStart2EndTime(double endTime) {
		double endTimeOffset = endTime % cycle;

		double result = 0;
		int endIndex = (int) Math.floor(endTimeOffset / interval);
		double oddment = endTimeOffset % interval;
		for (int i = 0; i < endIndex; i++) {
			result += energies[i];
		}
		result += oddment / interval * energies[endIndex];
		return result;
	}

}
