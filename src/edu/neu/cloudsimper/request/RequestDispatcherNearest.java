package edu.neu.cloudsimper.request;

import java.util.List;

import edu.neu.cloudsimper.Const;
import edu.neu.cloudsimper.EnergyMixDatacenterBroker;
import javafx.scene.layout.Border;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherNearest extends RequestDispatcherAbstract implements RequestDispatcher {
	
	public RequestDispatcherNearest() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public boolean allocateBroker4Request(RequestBatch requestBatch) {
		// TODO Auto-generated method stub
		List<Request> requests = requestBatch.getRequests();
		int minDistanceIndex = 0;
		for (Request request : requests) {
			double min = Double.MAX_VALUE;
			for (int i = 0; i < brokers.size(); i++) {
				double brokerLatitude = this.brokers.get(i).getDatacenter().getLocation().getLatitude();
				double brokerLongitude = this.brokers.get(i).getDatacenter().getLocation().getLongitude();
				double aveLatitude = (request.getRegion().getStartLatitude() + request.getRegion().getEndLatitude())/2;
				double aveLongitude = (request.getRegion().getStratLongitude() + request.getRegion().getEndLongitude())/2;
				if (min > distance(aveLongitude, aveLatitude, brokerLongitude, brokerLatitude)) {
					min = distance(aveLongitude, aveLatitude, brokerLongitude, brokerLatitude);
					minDistanceIndex = i;
				}
			}
			EnergyMixDatacenterBroker broker = this.brokers.get(minDistanceIndex);
			List<Request> disList = rTable.get(broker);
			disList.add(request);
		}
		return true;
	}
	
	public double distance(double loX, double laX, double loY, double laY) {
		double radLoX = loX * Math.PI / 180.0;
		double radLaX = laX * Math.PI / 180.0;
		double radLoY = loY * Math.PI / 180.0;
		double radLaY = laY * Math.PI / 180.0;
		double lo = radLoX - radLoY;
		double la = radLaX - radLaY;
		double distance = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(la / 2), 2) + Math.cos(radLaX) * Math.cos(radLaY) * Math.pow(Math.sin(lo / 2), 2)));
		distance *= Const.EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		return distance;
	}
}
