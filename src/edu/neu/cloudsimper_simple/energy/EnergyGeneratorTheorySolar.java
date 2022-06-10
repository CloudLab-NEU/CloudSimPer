package edu.neu.cloudsimper_simple.energy;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorTheorySolar extends EnergyGeneratorAbstract implements EnergyGenerator {

	private static final double ATMOSPHERIC_TRANSPARENCY = 0.532;
	private static final int SOLAR_CONSTANT = 1357;
	private static final double SOLAR_CONVERSION_RATE = 0.31;

	private int startDayOfYear;

//	public void setStartDayOfYear(int startDayOfYear) {
//		this.startDayOfYear = startDayOfYear;
//	}

	@Override
	public double nextEnergy(int duration) {

		boolean ideal = true;

		double beta = 0;

		double latitude = this.location.getLatitude();

		this.startDayOfYear = CloudSimPer_Simple.getClock() / (3600 * 24);

		double solarMediationIntensity = SOLAR_CONSTANT * (1 + 0.034 * Math.cos((360 * startDayOfYear) / 365.25));

		double sunDeclinationAngle = 23.45 * Math.sin(360 * ((284 + startDayOfYear) / 365.0));

		double hourAngle = 15 * (duration - 12);

		double sinH = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(sunDeclinationAngle))
				+ Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(sunDeclinationAngle))
						* Math.cos(Math.toRadians(hourAngle));

		double cosSita = Math.sin(Math.toRadians(latitude - beta)) * Math.sin(Math.toRadians(sunDeclinationAngle))
				+ Math.cos(Math.toRadians(latitude - beta)) * Math.cos(Math.toRadians(sunDeclinationAngle))
						* Math.cos(Math.toRadians(hourAngle));

		double sinHReciprocal = 1.0 / sinH;

		double regionalRadiationIntensity;

		if (ideal) {
			regionalRadiationIntensity = solarMediationIntensity * sinH * cosSita;
		} else {
			regionalRadiationIntensity = solarMediationIntensity * Math.pow(ATMOSPHERIC_TRANSPARENCY, sinHReciprocal);
		}
		return regionalRadiationIntensity * SOLAR_CONVERSION_RATE;
	}

//	@Override
//	public double nextnextEnergy(int duration) {
//		return 0;
//	}

	@Override
	public void start() {
	
	}

}
