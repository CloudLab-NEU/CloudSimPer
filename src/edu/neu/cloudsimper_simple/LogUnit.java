package edu.neu.cloudsimper_simple;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class LogUnit {

	private String split = ",";

	private long time;
	private String dcName;
	private int requestNum;

	private int finishedRequestNum;
	private double dcUsage;
	private double dcConsume;
	private double dcPrice;

	private double reSupply;
	private double reUsage;
	private double reDcUsage;
	private double reBaUsage;
	private double rePrice;

	private double nextReSupply;

	private double baSupply;
	private double baPrice;

	private double cost;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public int getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(int requestNum) {
		this.requestNum = requestNum;
	}

	public int getFinishedRequestNum() {
		return finishedRequestNum;
	}

	public void setFinishedRequestNum(int finishedRequestNum) {
		this.finishedRequestNum += finishedRequestNum;
	}

	public double getDcUsage() {
		return dcUsage;
	}

	public void setDcUsage(double dcUsage) {
		this.dcUsage = dcUsage;
	}

	public double getDcConsume() {
		return dcConsume;
	}

	public void setDcConsume(double dcConsume) {
		this.dcConsume += dcConsume;
	}

	public double getDcPrice() {
		return dcPrice;
	}

	public void setDcPrice(double dcPrice) {
		this.dcPrice = dcPrice;
	}

	public double getReSupply() {
		return reSupply;
	}

	public void setReSupply(double reSupply) {
		this.reSupply += reSupply;
	}

	public double getReUsage() {
		return reUsage;
	}

	public void setReUsage(double reUsage) {
		this.reUsage = reUsage;
	}

	public double getReDcUsage() {
		return reDcUsage;
	}

	public void setReDcUsage(double reDcUsage) {
		this.reDcUsage = reDcUsage;
	}

	public double getReBaUsage() {
		return reBaUsage;
	}

	public void setReBaUsage(double reBaUsage) {
		this.reBaUsage = reBaUsage;
	}

	public double getRePrice() {
		return rePrice;
	}

	public void setRePrice(double rePrice) {
		this.rePrice = +rePrice;
	}

	public double getBaSupply() {
		return baSupply;
	}

	public void setBaSupply(double baSupply) {
		this.baSupply += baSupply;
	}

	public double getBaPrice() {
		return baPrice;
	}

	public void setBaPrice(double baPrice) {
		this.baPrice = baPrice;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getNextReSupply() {
		return nextReSupply;
	}

	public void setNextReSupply(double nextReSupply) {
		this.nextReSupply = nextReSupply;
	}

	public String toString() {

		this.dcUsage = Math.ceil(dcUsage * 1000) / 1000.0;
		this.dcConsume = Math.ceil(dcConsume * 1000) / 1000.0;
		this.dcPrice = Math.ceil(dcPrice * 1000) / 1000.0;
		this.reSupply = Math.ceil(reSupply * 1000) / 1000.0;
		this.reUsage = Math.ceil(reUsage * 1000) / 1000.0;
		this.reDcUsage = Math.ceil(reDcUsage * 1000) / 1000.0;
		this.reBaUsage = Math.ceil(reBaUsage * 1000) / 1000.0;
		this.rePrice = Math.ceil(rePrice * 1000) / 1000.0;
		this.baSupply = Math.ceil(baSupply * 1000) / 1000.0;
		this.baPrice = Math.ceil(baPrice * 1000) / 1000.0;
		this.cost = Math.ceil(cost * 1000) / 1000.0;
		this.nextReSupply = Math.ceil(nextReSupply* 1000) / 1000.0;

		StringBuffer sb = new StringBuffer();
		sb.append(this.time).append(split).append(this.dcName).append(split).append(this.requestNum).append(split)
				.append(this.finishedRequestNum).append(split)
				.append(this.dcUsage).append(split).append(this.dcConsume).append(split).append(this.dcPrice).append(split)
				.append(this.reSupply).append(split).append(this.reUsage).append(split).append(this.reDcUsage)
				.append(split).append(this.reBaUsage).append(split).append(this.rePrice).append(split)
				.append(this.baSupply).append(split).append(this.baPrice).append(split).append(this.cost).append(split)
				.append(this.nextReSupply).append(split);
		return sb.toString();
	}
}
