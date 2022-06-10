package edu.neu.cloudsimper.request;

import java.util.List;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public interface RequestDispatcher {

	public boolean allocateBroker4Request(List<RequestBatch> requestBatches);

	public boolean doDispatch();

}