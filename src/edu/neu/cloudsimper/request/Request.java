package edu.neu.cloudsimper.request;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.SimPerEntity;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.plugin.PluginFactory;
import edu.neu.cloudsimper.plugin.PluginFactoryRequest;
import edu.neu.cloudsimper.region.Region;
import edu.neu.cloudsimper.region.RegionManger;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 * @Request: has two concept: first, it is the SimPerEntity, as Energy, contains
 *           RequestGenerator and RequestRandomization. second,it is a Cloneable
 *           object represent the request sending to datacenter, it is the
 *           sources of Cloudlet. Both SimPerEntity and Cloneable has the same
 *           instances of name, region, generator, randomization and
 *           utilizationModels. However, the length, inputSize and outputSize of
 *           Cloneable are randomized from SimPerEntity. requestId is remained
 *           as it is created, and cloudletCount is increased as it is cloned.
 */
public class Request implements SimPerEntity, Cloneable {

	private String name;
	private long length;
	private long inputSize;
	private long outputSize;
	private Region region;

	private RequestGenerator generator;
	private RequestRandomization rdm;
	private UtilizationModel cpu;
	private UtilizationModel ram;
	private UtilizationModel bw;

	private int requestId;
	private int cloudletCount;

	//private RequestBatch requestBatch;

	public Request(MetaContainer container, int id) {
		this.name = container.getName();
		this.length = Long.parseLong(container.getAttribute(Const.V_LENGTH));
		this.inputSize = Long.parseLong(container.getAttribute(Const.V_INPUT_SIZE));
		this.outputSize = Long.parseLong(container.getAttribute(Const.V_OUTPUT_SIZE));
		this.region = RegionManger.getRegion(container.getAttribute(Const.V_REGION));
		PluginFactoryRequest factory = PluginFactory.request(container);
		this.rdm = factory.createRequestRandomization();
		this.generator = factory.createRequestGenerator();
		generator.setRequestTmpl(this);
		this.cpu = factory.createCpuUtilizationModel();
		this.ram = factory.createRamUtilizationModel();
		this.bw = factory.createBwUtilizationModel();
		this.requestId = id;
		this.cloudletCount = 0;

		//requestBatch = new RequestBatch();
	}

	public void randomized() {
		this.length = rdm.randomized(this.length)+(long)Math.random()*100;
		this.inputSize = rdm.randomized(this.inputSize)+(long)Math.random()*20;
		this.outputSize = rdm.randomized(this.outputSize)+(long)Math.random()*300;
		this.cloudletCount += 1;
	}

	public RequestBatch nextBatch(double time) {
		int size = generator.nextBatchSize(time);
		this.cloudletCount = 0;
		List<Request> requests = new ArrayList<Request>(size);
		requests.add(this.clone());
		for (int i = 1; i < size; i++) {
			requests.add(requests.get(i - 1).clone());
		}
//		requestBatch.setRegion(region);
//		requestBatch.setTime(time);
//		requestBatch.setRequests(requests);
//		return requestBatch;
		return new RequestBatch(region, time, requests);
	}

	public String getName() {
		return name;
	}

	public long getLength() {
		return length;
	}

	public long getInputSize() {
		return inputSize;
	}

	public long getOutputSize() {
		return outputSize;
	}

	public Region getRegion() {
		return region;
	}

	public Cloudlet asCloudLet() {
		int cloudletId = Integer.parseInt(requestId + "" + cloudletCount);
	//	int i = (int)(Math.random()*1000);
		//System.out.println("CloudLetID:"+cloudletId+","+"length:"+i+","+"PEs:"+Const.CLOUDLET_PES+",inputSize:"+((long)(Math.random()*100))+",outputSzie:"
		//		+((long)(Math.random()*200))+"  generation");
		return new Cloudlet(cloudletId, length, Const.CLOUDLET_PES, inputSize, outputSize, cpu, ram, bw,
				Const.CLOUDLET_RECROD);
	}

	@Override
	public void start() {
		generator.start();
	}

	@Override
	public void stop(double time) {
		generator.stop(time);
	}

	@Override
	public Request clone() {
		Request request = null;
		try {
			request = (Request) super.clone();
			request.randomized();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return request;
	}
}
