package edu.neu.cloudsimper.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.analysis.integration.gauss.SymmetricGaussIntegrator;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.lists.HostList;
import org.cloudbus.cloudsim.lists.VmList;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixDatacenter;
import edu.neu.cloudsimper.EnergyMixHost;
import edu.neu.cloudsimper.energy.Energy;
import edu.neu.cloudsimper.request.Request;
import jdk.nashorn.internal.ir.annotations.Ignore;
import sun.awt.image.IntegerInterleavedRaster;

import com.csvreader.CsvWriter;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class WriteCSV {
	private boolean judge = false;
	private  int indexRequest = 0;
	private boolean requestJudge = false;
	private static int requestPage = 1;
	private boolean energyJudge = false;
	private String indent = "    ";	
	public  void writeCSV(String path, List<Cloudlet> list,String[] header,List<Vm> vms,List<EnergyMixHost> hosts,EnergyMixDatacenter datacenter) {
		try {
			int index = 0;
			double result = 0;
			int page =0;
			String path1 = path+datacenter.getName()+"-"+page/Const.COUNT+".csv";
			File file = new File(path1);
			if (!file.exists()) {
				judge = true;
			}else {
				judge = false;
			}
			System.out.println(path1);
			//new File(path1);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path1,true), "GBK"));
			CsvWriter csvWriter = new CsvWriter(out,'\n');
			if (judge == true){
				csvWriter.writeRecord(header);
			}
			int size = list.size();
			Cloudlet cloudlet;
			DecimalFormat dft = new DecimalFormat("###.##");
			if (size > Const.COUNT) {
				String[] writeLine = new String[Const.COUNT];
				for (int i = 0; i < size; i++) {
					cloudlet = list.get(i);
					if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
						if (index  < Const.COUNT-1) {
							writeLine[index] = cloudlet.getCloudletId() + indent+indent +indent+indent +"SUCCESS"+indent+indent+indent + cloudlet.getResourceId()+indent+indent+ indent +indent+ cloudlet.getVmId()
							+indent+indent+VmList.getById(vms, cloudlet.getVmId()).getHost().getId()
							+ indent + indent + indent +dft.format(cloudlet.getActualCPUTime()) + indent + indent
							+indent + dft.format(cloudlet.getExecStartTime()) + indent + indent
							+ indent +dft.format(cloudlet.getFinishTime()) 
							+indent+indent+indent+HostList.getById(hosts, VmList.getById(vms, cloudlet.getVmId()).getHost().getId()).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());//+hosts.get(1).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());
							index++;
							page++;
							if (i == size-1) {
								csvWriter.writeRecord(writeLine, true);
							}
						}else {
							writeLine[index] = cloudlet.getCloudletId() + indent+indent +indent+indent +"SUCCESS"+indent+indent+indent + cloudlet.getResourceId()+indent+indent+ indent +indent+ cloudlet.getVmId()
							+indent+indent+VmList.getById(vms, cloudlet.getVmId()).getHost().getId()
							+ indent + indent + indent +dft.format(cloudlet.getActualCPUTime()) + indent + indent
							+indent + dft.format(cloudlet.getExecStartTime()) + indent + indent
							+ indent +dft.format(cloudlet.getFinishTime()) 
							+indent+indent+indent+HostList.getById(hosts, VmList.getById(vms, cloudlet.getVmId()).getHost().getId()).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());//+hosts.get(1).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());						
							csvWriter.writeRecord(writeLine, true);	
							index=0;
							page++;
							if (i+1 < (size-1)) {
								out.flush();
								csvWriter.close();
								String path2 = path+datacenter.getName()+"-"+page/Const.COUNT+".csv";
								 file = new File(path2);
								if (!file.exists()) {
									judge = true;
								}else {
									judge = false;
								}
								 out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2,true), "GBK"));
								 csvWriter = new CsvWriter(out,'\n');
								if (judge == true){
									csvWriter.writeRecord(header);
								}
							}
						}
					}
				}
			}else {
				String[] writeLine = new String[size];
				for (int i = 0; i < size; i++) {
					cloudlet = list.get(i);
					if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
						if (index  < Const.COUNT-1) {
							writeLine[i] = cloudlet.getCloudletId() + indent+indent +indent+indent +"SUCCESS"+indent+indent+indent + cloudlet.getResourceId()+indent+indent+ indent +indent+ cloudlet.getVmId()
							+indent+indent+VmList.getById(vms, cloudlet.getVmId()).getHost().getId()
							+ indent + indent + indent +dft.format(cloudlet.getActualCPUTime()) + indent + indent
							+indent + dft.format(cloudlet.getExecStartTime()) + indent + indent
							+ indent +dft.format(cloudlet.getFinishTime()) 
							+indent+indent+indent+HostList.getById(hosts, VmList.getById(vms, cloudlet.getVmId()).getHost().getId()).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());//+hosts.get(1).getEnergyConsumption(cloudlet.getExecStartTime(), cloudlet.getFinishTime());
							index++;
						}
					}
				}
				csvWriter.writeRecord(writeLine, true);
			}
//			for (Energy energy : energys) {
//				result += energy.getEnergy(0, 3600);
//			}
			Collection<Double> supplys = datacenter.getHistoryEnergySupplyment().values();
			for (Double supply: supplys) {
				result+=supply;
			}
			String[] s = {"该数据中心从仿真开始到结束共提供能量： "};
			csvWriter.writeRecord(s, false);
			String[] supply = {String.valueOf(result)};
			csvWriter.writeRecord(supply,true);
			out.flush();
			csvWriter.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void writeCSV(String path, List<Request> requestList, List<Cloudlet> cloudletList,String[] header) {
		try {
			String path1 = path+requestPage/Const.COUNT+".csv";
			File file = new File(path1);
			if (!file.exists()) {
				requestJudge = true;
			}else {
				requestJudge = false;
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path1,true), "GBK"));
			CsvWriter csvWriter = new CsvWriter(out,'\n');
			if (requestJudge == true){
				csvWriter.writeRecord(header);
			}
			int size = cloudletList.size();
			Cloudlet cloudlet;
			Request request;
			if (size > Const.COUNT) {
				String[] writeLine = new String[Const.COUNT];
				for (int i = 0; i < size; i++) {
					cloudlet = cloudletList.get(i);
					request = requestList.get(i);
					if ((requestPage % Const.COUNT) >0) {
						writeLine[indexRequest] =  request.getLength()+indent+indent +indent+indent
								+indent+indent +indent+indent+request.getInputSize()+indent +indent+indent+indent+indent +indent+indent
								+request.getOutputSize()+indent +indent+indent+indent+indent +indent+indent+request.getRegion().getName()
								+indent +indent+indent+indent+indent +indent+indent+cloudlet.getCloudletId();
						indexRequest++;
						requestPage++;
						if (i == size-1) {
							String[] writeLine1 = new String[indexRequest];
							for (int j = 0; j < indexRequest;j++) {
								writeLine1[j] = writeLine[j];
							}	
							csvWriter.writeRecord(writeLine1, true);
							csvWriter.close();
							indexRequest = 0;
							break;
						}
					}else {
						writeLine[indexRequest] =  request.getLength()+indent+indent +indent+indent
								+indent+indent +indent+indent+request.getInputSize()+indent +indent+indent+indent+indent +indent+indent
								+request.getOutputSize()+indent +indent+indent+indent+indent +indent+indent+request.getRegion().getName()
								+indent +indent+indent+indent+indent +indent+indent+cloudlet.getCloudletId();
						csvWriter.writeRecord(writeLine, true);
						out.flush();
						csvWriter.close();
						indexRequest =0;
						requestPage++;
						if (i+1 < size) {
							String path2 = path+requestPage/Const.COUNT+".csv";
							 file = new File(path2);
							if (!file.exists()) {
								requestJudge = true;
							}else {
								requestJudge = false;
							}
							out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2,true), "GBK"));
							csvWriter = new CsvWriter(out,'\n');
							if (requestJudge == true){
								csvWriter.writeRecord(header);
							}
						}
					}	
				}
				out.flush();
				csvWriter.close();
			}else {
				String[] writeLine = new String[size];
				for (int i = 0; i < size; i++) {
					cloudlet = cloudletList.get(i);
					request = requestList.get(i);
					if ((requestPage % Const.COUNT) >0) {
						writeLine[i] =  request.getLength()+indent+indent +indent+indent
								+indent+indent +indent+indent+request.getInputSize()+indent +indent+indent+indent+indent +indent+indent
								+request.getOutputSize()+indent +indent+indent+indent+indent +indent+indent+request.getRegion().getName()
								+indent +indent+indent+indent+indent +indent+indent+cloudlet.getCloudletId();
						requestPage++;
						if (i == size-1) {
							csvWriter.writeRecord(writeLine, true);
							out.flush();
							csvWriter.close();
							break;
						}
					}else {
						writeLine[i] =  request.getLength()+indent+indent +indent+indent
								+indent+indent +indent+indent+request.getInputSize()+indent +indent+indent+indent+indent +indent+indent
								+request.getOutputSize()+indent +indent+indent+indent+indent +indent+indent+request.getRegion().getName()
								+indent +indent+indent+indent+indent +indent+indent+cloudlet.getCloudletId();
						csvWriter.writeRecord(writeLine, true);
						out.flush();
						csvWriter.close();
						requestPage++;
						if (i < (size-1)) {
							String path2 = path+requestPage/Const.COUNT+".csv";
							 file = new File(path2);
							if (!file.exists()) {
								requestJudge = true;
							}else {
								requestJudge = false;
							}
							out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2,true), "GBK"));
							csvWriter = new CsvWriter(out,'\n');
							if (requestJudge == true){
								csvWriter.writeRecord(header);
							}
						}	
					}
				}
				
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeCSV(String path,String datacenterName,List<String> eLog,String[] header) {
		try {
			int index = 0;
			double result = 0;
			int page =0;
			String path1 = path+datacenterName+"-"+page/Const.COUNT+".csv";
			File file = new File(path1);
			if (!file.exists()) {
				judge = true;
			}else {
				judge = false;
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path1,true), "GBK"));
			CsvWriter csvWriter = new CsvWriter(out,'\n');
			if (judge == true){
				csvWriter.writeRecord(header);
			}
			int size = eLog.size();
			if (size > Const.COUNT) {
				String[] writeLine = new String[Const.COUNT];
				for (int i = 0; i < size; i++) {
					if (index  < Const.COUNT-1) {
						writeLine[index] = eLog.get(i);
						if (i == size-1) {
							csvWriter.writeRecord(writeLine, true);
						}
					}else {
						writeLine[index] = eLog.get(i);
						csvWriter.writeRecord(writeLine, true);	
						index=0;
						page++;
						if (i+1 < (size-1)) {
							out.flush();
							csvWriter.close();
							String path2 = path+datacenterName+"-"+page/Const.COUNT+".csv";
							 file = new File(path2);
							if (!file.exists()) {
								judge = true;
							}else {
								judge = false;
							}
							 out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2,true), "GBK"));
							 csvWriter = new CsvWriter(out,'\n');
							if (judge == true){
								csvWriter.writeRecord(header);
							}
						}
					}
				}
			}else {
				String[] writeLine = new String[size];
				for (int i = 0; i < size; i++) {
					if (index  < Const.COUNT-1) {
						writeLine[i] = eLog.get(i);
						index++;
					}	
				}
				csvWriter.writeRecord(writeLine, true);
				out.flush();
				csvWriter.close();
			}
		}catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
