package edu.neu.cloudsimper_simple.request;

import java.util.List;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherNearest extends RequestDispatcherAbstract implements RequestDispatcher{
    @Override
    public void dispatch(List<RequestCore> requests) {
        if ("CA".equals(this.region.getName()) || "US".equals(this.region.getName()) || "LA".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_LA"}, requests);
        } else if ("NE".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_OS"}, requests);
        } else if ("NA".equals(this.region.getName()) || "ME".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_CR"}, requests);
        } else if ("SA".equals(this.region.getName()) || "WA".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_MB"}, requests);
        } else if ("EE".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_SH", "DC_OS"}, requests);
        } else if ("WE".equals(this.region.getName()) || "SE".equals(this.region.getName())) {
            dispatchRequest4DC(new String[] {"DC_CR", "DC_LA"}, requests);
        } else {
            dispatchRequest4DC(new String[] {"DC_MB", "DC_SH"}, requests);
        }
    }

    private void dispatchRequest4DC(String[] dcName, List<RequestCore> requests) {
        int dcNum = dcName.length;
        int requestNum = (int)Math.ceil(requests.size() / dcNum);
        for (int i = 0; i < dcNum; i++) {
            for (int j = 0; j < datacenters.size(); j++) {
                if (dcName[i].equals(datacenters.get(j).getName())) {
                    int num = 0;
                    while (num < requestNum) {
                        if (!requests.isEmpty()) {
                            int last = requests.size() - 1;
                            datacenters.get(j).accept(requests.get(last));
                            requests.remove(last);
                        }
                        num += 1;
                    }
                    break;
                }
            }
        }
    }
}
