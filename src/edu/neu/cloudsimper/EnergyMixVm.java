package edu.neu.cloudsimper;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.Vm;

import edu.neu.cloudsimper.meta.MetaContainer;
import edu.neu.cloudsimper.meta.MetaDatacenter;
import edu.neu.cloudsimper.meta.MetaManager;
import edu.neu.cloudsimper.plugin.PluginFactory;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class EnergyMixVm extends Vm {

	private EnergyMixVm(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String vmm,
			CloudletScheduler cloudletScheduler) {
		super(id, userId, mips, numberOfPes, ram, bw, size, vmm, cloudletScheduler);
	}

	public static void createVms4Broker(EnergyMixDatacenterBroker broker) {
		List<Vm> vms = new ArrayList<Vm>();

		EnergyMixDatacenter datacenter = broker.getDatacenter();
		MetaDatacenter metaDc = MetaManager.getDatacenter(datacenter.getName());
		List<MetaContainer> metaDcVms = metaDc.getVms();

		int idPrefix = Const.ID_PREFIX;
		for (MetaContainer metaDcVm : metaDcVms) {

			CloudletScheduler scheduler = PluginFactory.vm(metaDcVm).createCloudletScheduler();
			String name = metaDcVm.getName();
			int vmDcSize = metaDcVm.getSize();
			MetaContainer metaVm = MetaManager.getVm(name);
			for (int i = 0; i < vmDcSize; i++) {
				int id = Integer.parseInt(idPrefix + "" + i);
				int brokerId = broker.getId();
				double mips = Double.parseDouble(metaVm.getAttribute(Const.V_MIPS));
				int numberOfPes = Integer.parseInt(metaVm.getAttribute(Const.V_PES_NUMBER));
				int ram = Integer.parseInt(metaVm.getAttribute(Const.V_RAM));
				long bw = Long.parseLong(metaVm.getAttribute(Const.V_BW));
				long size = Long.parseLong(metaVm.getAttribute(Const.V_SIZE));
				vms.add(new EnergyMixVm(id, brokerId, mips, numberOfPes, ram, bw, size, Const.VMM, scheduler));
			}
			idPrefix += Const.ID_PREFIX;
		}
		broker.submitVmList(vms);
	}
}
