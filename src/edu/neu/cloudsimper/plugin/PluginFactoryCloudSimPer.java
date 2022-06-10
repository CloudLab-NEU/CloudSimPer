package edu.neu.cloudsimper.plugin;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.request.RequestDispatcher;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class PluginFactoryCloudSimPer extends PluginFactoryAbstract implements PluginFactory {

	protected PluginFactoryCloudSimPer() {
	}

	@Override
	public PluginFactoryCloudSimPer createFacorty(MetaContainer container) {
		return (PluginFactoryCloudSimPer) super.createFacorty(container);
	}

	public RequestDispatcher createRequestDispatcher() {
		this.clazzName = MetaManager.getPluginInfor(Const.P_REQUEST_DISPATCHER).getAttribute(Const.P_ROUNDROBIN);
		try {
			this.instance = Class.forName(clazzName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (RequestDispatcher) this.instance;
	}
}
