package edu.neu.cloudsimper.plugin;

import org.cloudbus.cloudsim.UtilizationModel;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.request.RequestGenerator;
import edu.neu.cloudsimper.request.RequestRandomization;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryRequest extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryRequest() {
	}

	@Override
	public PluginFactoryRequest createFacorty(MetaContainer container) {
		return (PluginFactoryRequest) super.createFacorty(container);
	}

	public UtilizationModel createCpuUtilizationModel() {
		prepare(Const.P_CPU_UTILIZATION_MODEL);
		createInstance();
		setPluginAttribute();
		return (UtilizationModel) this.instance;
	}

	public UtilizationModel createRamUtilizationModel() {
		prepare(Const.P_RAM_UTILIZATION_MODEL);
		createInstance();
		setPluginAttribute();
		return (UtilizationModel) this.instance;
	}

	public UtilizationModel createBwUtilizationModel() {
		prepare(Const.P_BW_UTILIZATION_MODEL);
		createInstance();
		setPluginAttribute();
		return (UtilizationModel) this.instance;
	}

	public RequestGenerator createRequestGenerator() {
		prepare(Const.P_REQUEST_GENERATOR);
		createInstance();
		setPluginAttribute();
		return (RequestGenerator) this.instance;
	}

	public RequestRandomization createRequestRandomization() {
		prepare(Const.P_REQUEST_RANDOMIZATION);
		createInstance();
		setPluginAttribute();
		return (RequestRandomization) this.instance;
	}
}
