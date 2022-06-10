package edu.neu.cloudsimper.request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestRandomizationSimple implements RequestRandomization {

	@Override
	public long randomized(long value) {
		double result = (Math.random() + 0.5) * value;
		return (long) Math.ceil(result);
	}

}
