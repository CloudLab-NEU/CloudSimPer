package edu.neu.cloudsimper_simple.plugin;

import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.request.RequestGenerator;
import edu.neu.cloudsimper_simple.request.RequestRandomization;

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
