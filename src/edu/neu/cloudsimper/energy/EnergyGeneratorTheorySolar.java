package edu.neu.cloudsimper.energy;


import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorTheorySolar extends EnergyGeneratorTheory implements EnergyGenerator {
	private int startDayOfYear;

	public void setStartDayOfYear(int startDayOfYear) {
		this.startDayOfYear = startDayOfYear;
	}

	@Override
	public double getPower(double time) {
		// TODO TheorySolar
		double solarMediationIntensity;
		double sunDeclinationAngle;
		double regionalRadiationIntensity;
		double hourAngle;
		double beta = 0;
		double sinH;
		double cosSita;
		double sinHReciprocal; 
		boolean think = true;
		double solarPower = 0.0;
		
		
		double latitude =  this.getEnergyObj().getDatacenter().getLocation().getLatitude();
		
		solarMediationIntensity = Const.SOLAR_CONSTANT * (1 + 0.034 * Math.cos((360 * startDayOfYear) / 365.25));
		
		sunDeclinationAngle = 23.45 * Math.sin(360 * ((284 + startDayOfYear) / 365.0));
		
		hourAngle = 15 * (time - 12);
		
		//double SinT = hourAngle / beta; 
		
		sinH = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(sunDeclinationAngle)) + 
				Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(sunDeclinationAngle)) * Math.cos(Math.toRadians(hourAngle));
			
		cosSita = Math.sin(Math.toRadians(latitude - beta)) * Math.sin(Math.toRadians(sunDeclinationAngle)) + 
				Math.cos(Math.toRadians(latitude - beta)) * Math.cos(Math.toRadians(sunDeclinationAngle)) * Math.cos(Math.toRadians(hourAngle));
		
		sinHReciprocal = 1.0/sinH;
		
		if(think) {
			regionalRadiationIntensity =  solarMediationIntensity * sinH * cosSita;
		}
		else {
			regionalRadiationIntensity = solarMediationIntensity * Math.pow(Const.ATMOSPHERIC_TRANSPARENCY, sinHReciprocal);
		}
		solarPower = regionalRadiationIntensity*Const.SOLAR_CONVERSION_RATE;
		return solarPower;
	}

}
