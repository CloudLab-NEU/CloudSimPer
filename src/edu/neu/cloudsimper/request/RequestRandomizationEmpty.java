package edu.neu.cloudsimper.request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestRandomizationEmpty implements RequestRandomization {

	@Override
	public long randomized(long value) {
		return value;
	}

}
