package edu.neu.cloudsimper_simple.request;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestCore {

	private String id;
	private long length;
	private int deadline;
	private int beginTime;
	private int maxWaitTime;

	public RequestCore(int length, int deadline, String id, int beginTime, int maxWaitTime) {
		this.length = length;
		this.deadline = deadline;
		this.id = id;
		this.beginTime = beginTime;
		this.maxWaitTime = maxWaitTime;
	}

	public long getLength() {
		return length;
	}

	public int getDeadline() {
		return deadline;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}
}
