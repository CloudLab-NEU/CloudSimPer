package edu.neu.cloudsimper.request;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Random;

import edu.neu.cloudsimper.Const;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestGeneratorPopulationTime extends RequestGeneratorAbstract implements RequestGenerator {
	
	private static TimeZone timeZoneHK = TimeZone.getTimeZone("Asia/Hong_Kong");
	private Random random = new Random();
	
	@Override
	public int nextBatchSize(double time) {
		// TODO requestSize
		// generator according to the population density and time table
		
		double relativeSize = 3.0;//this.getRegion().getPopulationDensity() / Const.HK_POPULATION_DENSITY;
		int days = (int) time/(3600 * 24);
		int timeQuantum = (int)(time - days * 3600 * 24)/3600;
	
		TimeZone timeZoneNow = this.getRegion().getTimeZone();
	//	System.out.println(timeZoneNow);
		int timeLag = calculateTimeLag(timeZoneNow);
	//	System.out.println(timeLag);
	//	long averageAmount = (long) (Const.AVERGE[Math.abs(timeQuantum - timeLag)] * relativeSize);
	//	long standardDeviation = (long) (Const.STARNDDEVIATION[Math.abs(timeQuantum - timeLag)] * relativeSize);
	//	long nextBatchSize = (long) Math.abs(standardDeviation * random.nextGaussian() + averageAmount);	
		return 500;
	}
	

	public int calculateTimeLag(TimeZone T_Now) {
		int Hour_Now;
		int Hour_HK;
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(T_Now);
		Hour_Now = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTimeZone(RequestGeneratorPopulationTime.timeZoneHK);
		Hour_HK = calendar.get(Calendar.HOUR_OF_DAY);
		return (Hour_Now - Hour_HK);
	}

}