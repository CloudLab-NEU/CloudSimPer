package edu.neu.cloudsimper_simple.request;

import java.util.List;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherRoundRobin extends RequestDispatcherAbstract implements RequestDispatcher {

	@Override
	public void dispatch(List<RequestCore> requests) {
		int length = datacenters.size();
		int index = 0;
		while (!requests.isEmpty()) {
			int last = requests.size() - 1;
			if (datacenters.get(index++ % length).
					accept(requests.get(last))) {
				requests.remove(last);
			}
		}
	}
}
