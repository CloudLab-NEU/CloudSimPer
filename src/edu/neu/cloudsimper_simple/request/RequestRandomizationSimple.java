package edu.neu.cloudsimper_simple.request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestRandomizationSimple implements RequestRandomization {

	@Override
	public int randomized(int value) {
		double result = (Math.random() + 0.5) * value;
		return (int) Math.ceil(result);
	}
}
