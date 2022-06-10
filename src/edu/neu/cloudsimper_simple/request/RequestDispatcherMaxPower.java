package edu.neu.cloudsimper_simple.request;

import edu.neu.cloudsimper_simple.CloudSimPer_Simple;

import java.util.List;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class RequestDispatcherMaxPower extends RequestDispatcherAbstract implements RequestDispatcher{
    @Override
    public void dispatch(List<RequestCore> requests) {
        if (CloudSimPer_Simple.getClock() == 0) {
            int length = datacenters.size();
            int index = 0;
            while (!requests.isEmpty()) {
                int last = requests.size() - 1;
                if (datacenters.get(index++ % length).
                        accept(requests.get(last))) {
                    requests.remove(last);
                }
            }
        } else {
            double[] lastUsage = new double[this.datacenters.size()];
            for (int i = 0; i < lastUsage.length; i++) {
                lastUsage[i] = this.datacenters.get(i).getDcUsage();
            }


            int[] Index = Arraysort(lastUsage);


            int totalRequestNum = requests.size();
            //System.out.println(totalRequestNum);
            for (int i = 0; i < datacenters.size(); i++) {
                if (totalRequestNum != 0) {
                    //int availableRequestNum = dcMaxNum[Index[i]] - this.datacenters.get(Index[i]).getQueueSize();
                    int availableRequestNum = this.datacenters.get(Index[i]).getCapacity() * 600 / 10000 - this.datacenters.get(Index[i]).getQueueSize()
                            - this.datacenters.get(Index[i]).getNextRequestNum();
                    //System.out.println(this.datacenters.get(Index[i]).getName() + " " + availableRequestNum);
                    if (availableRequestNum > 0) {
                        if (totalRequestNum > availableRequestNum) {
                            totalRequestNum -= availableRequestNum;
                            this.datacenters.get(Index[i]).setNextRequestNum(this.datacenters.get(Index[i]).getNextRequestNum() + availableRequestNum);//dcRequestNum[Index[i]] = availableRequestNum;
                        } else {
                            this.datacenters.get(Index[i]).setNextRequestNum(this.datacenters.get(Index[i]).getNextRequestNum() + totalRequestNum);//dcRequestNum[Index[i]] = totalRequestNum;
                            totalRequestNum = 0;
                        }
                    }

                } else {
                    break;
                }
            }
            if (totalRequestNum != 0) {
                int result = (int) Math.ceil((double)totalRequestNum / this.datacenters.size());
                for (int i = 0; i < datacenters.size(); i++) {
                    this.datacenters.get(i).setNextRequestNum(this.datacenters.get(i).getNextRequestNum() + result);//dcRequestNum[i] += result;
                }
            }
            for (int i = 0; i < this.datacenters.size(); i++) {
//                System.out.println(datacenters.get(i).getName() + " " + datacenters.get(i).getNextRequestNum());
                for (int j = 0; j < this.datacenters.get(i).getNextRequestNum(); j++) {
                    if (!requests.isEmpty()) {
                        int last = requests.size() -1;
                        this.datacenters.get(i).accept(requests.get(last));
                        requests.remove(last);
                    } else {
                        break;
                    }

                }
            }
        }
    }

    public static int[] Arraysort(double[]arr)
    {
        double temp;
        int index;
        int k=arr.length;
        int[]Index= new int[k];
        for(int i=0;i<k;i++)
        {
            Index[i]=i;
        }

        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length-i-1;j++)
            {
                if(arr[j]<arr[j+1])
                {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;

                    index=Index[j];
                    Index[j] = Index[j+1];
                    Index[j+1] = index;
                }
            }
        }
        return Index;
    }
}
