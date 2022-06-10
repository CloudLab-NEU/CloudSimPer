package edu.neu.cloudsimper.energy;

import java.util.Random;

import org.cloudbus.cloudsim.distributions.WeibullDistr;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyGeneratorTheoryWind extends EnergyGeneratorTheory implements EnergyGenerator {

	private double weibullScaleMin = 2.0;
	private double weibullScaleMax = 3.0;
	private double weibullShapeMin_WindSpeed = 0.0;
	private double weibullShapeMax_WindSpeed = 10.0;
	private int startDayOfYear;
	private double windEnergy;//���ܡ�
	private int[] locationList;//�����б�
	private double[] scaleList;//ĳ����һ���ÿ30��ı���������
	private double[] shapeList;//ĳ����һ���ÿ30�����״������
	private WeibullDistr[] WeibullList = new WeibullDistr[12];//ĳ����ÿ���µ��������ֲ���
	
	public void setStartDayOfYear(int startDayOfYear) {
		this.startDayOfYear = startDayOfYear;
	}

	@Override
	public double getPower(double time) {
		// TODO TheoryWind
		// generate the wind speeds by
		// org.cloudbus.cloudsim.distributions.WeibullDistr
		// the parameters should be changed every 30 days and make sure days
		// with strong wind for different locations occur twice per year
		// WeibullDistr Scale randomly between boundary values
		// WeibullDistr Shape randomly between boundary values
		
		double max = 0.0;
		double max2 = 0.0;
		int indexMax = 0;
		int indexMax2 = 0;
		double median =0.0;
		int monthNow = 0; 
		double[] windSpeed = new double[12];//����
		locationList = randomCommon(1, 66, 8);
		int monthMax = (int) Math.sqrt(2 * locationList[0]) + 2;
		int monthSecondMax = locationList[0] - (locationList[0] * (locationList[0] + 1))/2;

		scaleList = randomCommon(weibullScaleMin,weibullScaleMax,12);
		shapeList = randomCommon(weibullShapeMin_WindSpeed, weibullShapeMax_WindSpeed,12);

		for(int i=0; i < 12 ; i++) {
			WeibullDistr Weibull;
			Weibull = new WeibullDistr(new Random(10),scaleList[i],shapeList[i]);
			WeibullList[i] = Weibull;
		}
	
		for(int i = 0; i < 12 ;i++) {
			windSpeed[i] = WeibullList[i].sample();
		}

		for(int i = 0; i < windSpeed.length; i++){
			if(max < windSpeed[i]){
			    max2 = max;
			    max = windSpeed[i];
			    indexMax = i;
			}else if((max>windSpeed[i])&&(windSpeed[i]>max2)){
				max2 = windSpeed[i];
			    indexMax2 = i;
			}
		}

		median = windSpeed[indexMax];
		windSpeed[indexMax] = windSpeed[monthMax];
		windSpeed[monthMax] = median;
		
		median = windSpeed[indexMax2];
		windSpeed[indexMax] = windSpeed[monthSecondMax];
		windSpeed[monthSecondMax] = median;
		
		double nowDay = (startDayOfYear+time/(60*60*24))%365;
		if (Math.ceil(nowDay) <=31 && Math.ceil(nowDay)>0) {
			monthNow = 1;
		}else if (Math.ceil(nowDay) <=59 && Math.ceil(nowDay)>31) {
			monthNow = 2;
		}else if (Math.ceil(nowDay) <=90 && Math.ceil(nowDay)>59) {
			monthNow = 3;
		}else if (Math.ceil(nowDay) <=120 && Math.ceil(nowDay)>90) {
			monthNow = 4;
		}else if (Math.ceil(nowDay) <=151 && Math.ceil(nowDay)>120) {
			monthNow = 5;
		}else if (Math.ceil(nowDay) <=181 && Math.ceil(nowDay)>151) {
			monthNow = 6;
		}else if (Math.ceil(nowDay) <=212 && Math.ceil(nowDay)>181) {
			monthNow = 7;
		}else if (Math.ceil(nowDay) <=243 && Math.ceil(nowDay)>212) {
			monthNow = 8;
		}else if (Math.ceil(nowDay) <=273 && Math.ceil(nowDay)>243) {
			monthNow = 9;
		}else if (Math.ceil(nowDay) <=304 && Math.ceil(nowDay)>273) {
			monthNow = 10;
		}else if (Math.ceil(nowDay) <=334 && Math.ceil(nowDay)>304) {
			monthNow = 11;
		}else {
			monthNow = 12;
		}
		
		
		windEnergy = 0.5 * Const.AIR_DENSITRY * Math.pow(windSpeed[monthNow], 3) * Math.PI * Math.pow(Const.VANE_ENGINE_RADIUS,2);
		//1/2*�����ܶ�*����^3*��PI*ҶƬ��^2�����
		return windEnergy;
	}
	
	public static int[] randomCommon(int min, int max, int n){ 
	  if (n > (max - min + 1) || max < min) { 
	      return null; 
	    } 
	  int[] result = new int[n]; 
	  int count = 0; 
	  while(count < n) { 
	    int num = (int) (Math.random() * (max - min)) + min; 
	    boolean flag = true; 
	    for (int j = 0; j < n; j++) { 
	      if(num == result[j]){ 
	        flag = false; 
	        break; 
	      } 
	    } 
	    if(flag){ 
	      result[count] = num; 
	      count++; 
	    } 
	  } 
	  return result; 
	}
	
	public static double[] randomCommon(double min, double max, int n){ 
		  if ( max < min) { 
		      return null; 
		    } 
		  double[] result = new double[n]; 
		  int count = 0; 
		  while(count < n) { 
		    double num =  (Math.random() * (max - min)) + min; 
		    boolean flag = true; 
		    for (int j = 0; j < n; j++) { 
		      if(num == result[j]){ 
		        flag = false; 
		        break; 
		      } 
		    } 
		    if(flag){ 
		      result[count] = num; 
		      count++; 
		    } 
		  } 
		  return result; 
		}
}
