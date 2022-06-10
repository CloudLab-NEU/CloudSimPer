package edu.neu.cloudsimper_simple.request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestRandomizationEmpty implements RequestRandomization {

	@Override
	public int randomized(int value) {
		return value;
	}

}
