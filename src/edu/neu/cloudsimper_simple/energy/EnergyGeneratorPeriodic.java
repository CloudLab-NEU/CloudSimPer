package edu.neu.cloudsimper_simple.energy;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class EnergyGeneratorPeriodic extends EnergyGeneratorAbstract implements EnergyGenerator {

	protected int size;
	protected int interval;
	protected double capacity;

	protected double currentPlace;

	/**
	 * the possible max value of one cycle, the min value is 0.
	 */
	protected long cycle;
	protected double[] energies;

	public final void start() {
		this.cycle = interval * size;
		this.energies = new double[size];
		initEnergy();
		for (double energy : energies) {
			this.capacity += energy;
		}
		currentPlace = 0;
	}

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
	//CloudSimper的实验 根据文件并计算生成

//	@Override
//	public double nextEnergy(int duration) {
//
//		double result = 0;
//		double nextPlace = 0;
//		if (duration > cycle) {
//			long loop = duration / cycle;
//			result += capacity * loop;
//			duration %= cycle;
//		}
//		if (currentPlace + duration > cycle) {
//			nextPlace = (currentPlace + duration) % cycle;
//			int currentPlaceRight = (int)Math.floor(currentPlace / interval);
//			int nextPlaceRight = (int)Math.floor(currentPlace / interval);
//			double oddment = currentPlace % interval;
//			result += energies[currentPlaceRight - 1] * (1 - oddment);
//			oddment = nextPlace % interval;
//			result += energies[nextPlaceRight - 1] * oddment;
//			for (int i = currentPlaceRight + 1; i < size -1; i++) {
//				result += energies[i];
//			}
//			for (int i = 0; i < nextPlaceRight -1; i++) {
//				result +=energies[i];
//			}
//		} else {
//			nextPlace = currentPlace + duration;
//			int currentPlaceRight = (int)Math.floor(currentPlace / interval);
//			int nextPlaceRight = (int)Math.floor(currentPlace / interval);
//			if (currentPlaceRight == nextPlaceRight) {
//				double ratio = (nextPlace - currentPlace) / interval;
//				result += energies[currentPlaceRight] * ratio;
//			} else {
//				double oddment = currentPlace % interval;
//				result += energies[currentPlaceRight - 1] * (1 - oddment);
//				oddment = nextPlace % interval;
//				result += energies[nextPlaceRight - 1] * oddment;
//				for (int i = currentPlaceRight + 1; i < nextPlaceRight - 1; i++) {
//					result += energies[i];
//				}
//			}
//		}
//		currentPlace = nextPlace;
//		double ratio = new Random().nextDouble() * 0.1 + 0.95;
//		result = (int)(result * ratio);
//		return result;
//	}
	//读取太阳能文件与风能文件的生成方式


	//对应每个时间段读取文件生成
	@Override
	public double nextEnergy(int duration) {
		return energies[CloudSimPer_Simple.getClock()/600];
	}
	//测试 读取下一次生成的数据 便于根据下一次的能源进行任务分配
//	@Override
//	public double nextnextEnergy(int duration) {
//		return energies[(CloudSimPer.getClock() + duration)/600];
//	}

	protected abstract void initEnergy();

	private double energyStartTime2CycleEnd(double startTime) {
		return capacity - energyCycleStart2EndTime(startTime);
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
