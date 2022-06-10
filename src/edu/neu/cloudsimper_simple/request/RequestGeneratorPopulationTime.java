package edu.neu.cloudsimper_simple.request;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;

import java.util.Random;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestGeneratorPopulationTime extends RequestGeneratorFile implements RequestGenerator {

//google
	@Override
	public int nextBatchSize(int duration) {
		int hour = CloudSimPer_Simple.getClock() / 3600;
		int place = (int) ((hour + this.region.getTimeShift() + 720) % 720);
		int num = (int)(traces[place] * this.region.getPopulation() / (3600 / 600));
		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.2 + 0.9;
//		double ratio = rand.nextDouble() * 0.1 + 0.95;
		num = (int)(num * ratio);
		return num;
	}

//对应单位时间生成
//	@Override
//	public int nextBatchSize(int duration) {
//		int num = traces[CloudSimPer.getClock()/600];
//		return num;
//	}
	@Override
	protected int parseRequestNum(String line) {
		return Integer.parseInt(line);
	}

}