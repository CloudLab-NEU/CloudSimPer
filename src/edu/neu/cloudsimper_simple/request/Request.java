package edu.neu.cloudsimper_simple.request;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;
import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.Datacenter;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.meta.MetaManager;
import edu.neu.cloudsimper_simple.plugin.PluginFactory;
import edu.neu.cloudsimper_simple.plugin.PluginFactoryRequest;
import edu.neu.cloudsimper_simple.region.Region;
import edu.neu.cloudsimper_simple.region.RegionManger;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Request extends Component {

	private int length;
	private int deadline;
	private int maxWaitTime;
	private Region region;

	private RequestGenerator generator;
	private RequestDispatcher dispatcher;
	private RequestRandomization rdm;

	private int executeCount;

	private Request(MetaContainer container) {
		this.name = container.getName();
		this.length = Integer.parseInt(container.getAttribute(Const.V_LENGTH));
		this.deadline = Integer.parseInt(container.getAttribute(Const.V_DEADLINE));
		this.maxWaitTime = Integer.parseInt(container.getAttribute(Const.V_MAXWAITTIME));
		this.region = RegionManger.getRegion(container.getAttribute(Const.V_REGION));
		this.dispatcher = PluginFactory.cloudsimper.createRequestDispatcher();
		dispatcher.setRegion(this.region);
		PluginFactoryRequest factory = PluginFactory.request(container);
		this.rdm = factory.createRequestRandomization();
		this.generator = factory.createRequestGenerator();
		this.generator.setRegion(this.region);
		this.generator.start();
		this.executeCount = 0;
	}

	public static List<Request> buildAll() {
		return RequestBuilder.buildAll();
	}

	public void initDispatcher(List<Datacenter> datacenters) {
		dispatcher.setDatacenters(datacenters);
	}

	public void runDispatch(int tick, List<Datacenter> datacenters) {
		dispatcher.dispatch(nextBatch(tick));
	}

	public long getLength() {
		return length;
	}

	public int getDeadLine() {
		return deadline;
	}

	public Region getRegion() {
		return region;
	}

	public int getExecuteCount() {
		return executeCount;
	}

	private RequestCore asCore() {
		int length = rdm.randomized(this.length);
		RequestCore core = new RequestCore(length, deadline, name + executeCount, CloudSimPer_Simple.getClock(), maxWaitTime);
		executeCount++;
		return core;
	}

	private List<RequestCore> nextBatch(int time) {
		int size = generator.nextBatchSize(time);
		List<RequestCore> requests = new ArrayList<RequestCore>(size);
		for (int i = 0; i < size; i++) {
			requests.add(this.asCore());
		}
		return requests;
	}

	private static class RequestBuilder {
		private static List<Request> buildAll() {
			List<Request> requests = new ArrayList<Request>();
			List<MetaContainer> requestMeta = MetaManager.getRequests();
			for (MetaContainer metaContainer : requestMeta) {
				requests.add(new Request(metaContainer));
			}
			return requests;
		}
	}
}
