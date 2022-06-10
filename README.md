# Readme for CloudSimPer 

## Version 1.0 development

### 1. Intentions

- **CloudSimPer** is a short name of  CLOUD SIMulator hybrid-Powered by rEnewable eneRgy
- **CloudSimPer** introduce the energy supplying mechanism for datacenters in a simulation. CloudSimPer provide the energy generators of renewable energies that are periodicity, intermittency and instability. CloudSimPer logs the powers and energy consumption of every hosts, and the energy supplement of every renewable energy generator, in any simulation time. For example, how much energy is consumed, renewable energy is consumed, brown energy is consumed, and renewable energy is wasted. Any scheduler can implement real-time power- or energy-aware scheduling using such information, accessed via APIs.
- **CloudSimPer** introduce the geographical distributed datacenter and request generation. The simulation is not only in the dimension of temporal, but also of spatial. Both Temporal Load Balancing (Workload Scheduling) and Spatial Load Balancing (Geographical Load Scheduling) are supported. 

- **CloudSimPer** provide a long-term simulation, such as months and years, using cycling requests generation. Meanwhile the requests generation polices are also modeled and customizable. On the contrary, CloudSim only support one-time execution on pre-defined workloads. What’s more, CloudSimPer synchronized the time of request generators, energy generators and datacenters.

- **CloudSimPer** completely implement the configurable case-building process against the programmable one, the scientific researches only need to design  configuration files in XML format, and implement the own plugins in Java.

### 2. Highlights

- In ClousSimPer, there are not concepts of week, month and year. The Date is presentation the *n*th-day-of-a-year. All time in CloudSimPer is calculate in second with a *double* variable.
- Units in CloudSimPer are implicated, they should be consistent not matter what they are. We suggest MB for storage, MHz for CUP and MB/s for bandwidth, and let 1GB= 100MB. However, the units of energy, power and energy price are Joule, Watt and KWH, respectively.
- In CloudSimPer, the time and mips of CPU are *double*, the energy, power and cost are *double*, and the storage and bandwidth are *long*.
- In CloudSimPer, one Broker maps one Datacenter only, that is, the Broker is the proxy of a Datacenter. Requests are dispatched among Datacenters, while the Cloudlet and Vm are scheduled among Hosts in a Datacenters. CloudSimPer believes the network latency between Datacenters is unaffordable, but network latency in a datacenter, the overlay network, is satisfiable.
- The line 555 in *org.cloudbus.cloudsim.core.CloudSim*. has to be commented out, otherwise the CloudSimPer will be actually stopped at the first cycle .

![](D:/GitRepository/paperGit/CloudSimPer/2.5.jpg)

​		We know that the extension should not break the encapsulation of the original one, but sorry, just once. 

### 3. Entities

![](D:/GitRepository/paperGit/CloudSimPer/entities.jpg)

### 4. Plugins

- **RequestDispatcher**；For CloudSimPer. Mapping Requests to Brokers. CloudSimPer need one and only one RequestDispatcher with name “default”;
- **VmAllocationPolicy**: For Datacenter. Mapping VMs (of a Broker) to Hosts (of a Datacenter), VM migration is possible in Hosts of the same Datacenters.
- **BwProvisioner**: For Host. Mapping bandwidths of the Host to VMs.
- **PeProvisioner**: For Host. Mapping CPUs of the Host to VMs.
- **RamProvisioner**: For Host. Mapping RAMs of the Host to VMs.
- **PowerModel**: For Host. Mapping powers of the Host to it CPU utilization. 
- **VmScheduler**: For Host. Mapping VMs of a Host to the resources of the Host
- **CloudletScheduler**: For Vm. Mapping Cloudlets of a VM to the resources of the Host
- **CpuUtilizationModel**: For Request/Cloudlet. Mapping the CPU utilization of a VM to the time of a running Cloudlet on the VM. It could define the scale of Cloudlets by the time serialized CPU utilization .
- **RamUtilizationModel:** For Request/Cloudlet. Mapping the RAM utilization of a VM to the time of a running Cloudlet on the VM. It could define the scale of Cloudlets by the time serialized RAM utilization.
- **BwUtilizationModel**: For Request/Cloudlet. Mapping the bandwidth utilization of a VM to the time of a running Cloudlet on the VM. It could define the scale of Cloudlets by the time serialized bandwidth utilization.
- **RequestRandomization**: For Request. Customizable randomization algorithms of length, input and output size of a request.
- **RequestGenerator**: For Request. Mapping the number of Request to any timeslot during the simulation.
- **EnergyGenerator**: For Energy. Mapping the amount of renewable energy to any timeslot during the simulation.

### 5. Packages

- **conf**: Configuration files.
- **energy**: Renewable energy simulation
- **meta**: Initializing  all configuration files, provide the data structures and access points of them.
- **plugin**: plugins factory.
- **power**: The power models of hosts.
- **region**: The region information, including start longitude and latitude, end longitude and latitude , time zone, population density. Also the location with longitude and latitude only.
- **request**: The rules and metadata for generating requests. A request has two dimensions, size and frequency. 

### 6.Configuration

#### 6.1 Configuration files

- **Datacenter.xml:** configuration of all datacenters, it referred the other configurations except the Request.
- **Host.xml:** the host information, such information is reused in each *datacenter* in Datacenter.xml
- **PluginInfor.xml:** the name and class of all plugins in CloudSimPer, no matter they are in-use or not. Plugins are grouped in their types. The mandatory plugin, named as *default*, conventionally placed as the frits one. Configurations of *Datacenter Host Request* and *Vm* contain the definition of their required plugins. If the definitions are abbreviated, then the default plugins are adopted. 
- **Region.xml:** Region and locations information
- **Request.xml:** Request information, where, when, what and how is generated 
- **Storage.xml:** Datacenter storage, basically they are just the configuration.
- **Vm.xml**: the information of virtual machines, such information is reused in each datacenter in Datacenter.xml

#### 6.2 Structure

- All configuration files of CloudSimPer follow the same structure. <*Container*> in <*Containers*>, <*Plugins*> and <*Attribute*> in <*Container*>, <*Plugin*> in <*Plugins*>, and <*Attribute*> in <*Plugin*>. Maybe there is a nest <*Containers*> in <*Container*>.

  ![](D:/GitRepository/paperGit/CloudSimPer/structure.jpg)

- Elements in the structure are *Containers*, *Attributes* and *Plugins*. 

- The available *Container* are Datacenter, Host, Storage, Vm, Energy, Region, Location and PluginInfor. 

- All *Container* contain *Attributes,* and some of them contain *Plugin*, and some of them contain *Container*. 

- *Plugin* is a specific *Container* only contains *Attributes*. 

- Every *Container* has *name* tag-attribute and Datacenter *Container* has size tag-attribute. 

- *Container* may in different files. For example. the *Container* Host occurs both in Host.xml and Datacenter.xml, the former are the template of the host, and the later are the instances of the host.

- The attributes of a *Container*, <name-value> pairs, are predefined, and set to the corresponding instances (Java objects) by CloudSimPer. The attributes of a *Plugin*, customizable <name-value> pairs according to the properties of implantation class (Java), are set to instances of plugins by CloudSimPer via the corresponding setter method. Such as attribute with name “theFoo” are initialized via setTheFoo (). 

#### 6.3 Naming convention of configuration file

- **File names**: mandatory, except the energy files in */energy* . the name of energy files is user-defined with suffix “*.re*”.
- **Tag names**: mandatory and pascal case.
- **Attribute names**: camel case.
- **Attribute and tag values**: camel case is suggested for text value, in support of *String*, *int*, *long*, *float*, *double* and *boolean* 
- **Some mandatory terms**: see the variables start with *A_*, *T_*, *V_* and *P_* in *edu.neu.cloudsimper.Const* .

#### 6.4 Attributes and values

**Region.xml**

- *startLongitude*, *startLatitude*, *endLongitude*, *endLatitude*, *longitude*, *latitude*: As x.xx or -x.xx degree, double.
- *population*: In million, double value.
- *timeZone*: For java.util.TimeZone.getTimeZone(Sting), abbreviation such as "PST".

**Request.xml**

- *region*: the name of region referring to Region.xml.*
- *length*: scale of request/cloudlet in MIPS (MHz).*
- *inputSize*: The input file size of this Request/Cloudlet before execution (MB). This size has to be considered the program + input data sizes.*
- *outputSize*: The output file size of this Request/Cloudlet after execution (MB). 

**Vm.xml**

- *size*: VM image size (MB)*
- *mips*: cpu frequency (MHz)*
- *cpuNumber*: amount of CPUs*
- *ram*: amount of memory (MB)*
- *bw*: amount of bandwidth (MB/s)

**Datacenter.xml**

- *location*: the name of location referring to Region.xml*
- *costPerSecond*,*costPerMem*,*costPerStorage*: the price of CPU (per second), Ram (per MB) and Storage (per MB)*
- *schedulingInterval*: Scheduling interval for VM allocation (second)

**PluginInfors.xml**

- No attribute need to be explained

**Some attributes for Plugins provide by CloudSimPer**

- EnergyGenerator (*simple*): 

  *interval*: how long each line in “filename.re” represents (second),.

  *size*: how many lines in “filename.re”

- EnergyGenerator (*windTheory*, *solarTheory*): 

  *startDayofYear*: In 00: 00 of which date the EnergyGenerator is start. For example, 1 is the first day and 365 is the last day of a year.

- EnergyGenerator (battery): 

  *interval*: how long each data point represents (second).

  *size*: how many data is generated in a cycle.

  *capacity*: the largest value of these data points, i.e. maximum energy in interval (Joule).

- PowerModel (*discrete*): 

  *powerData*: a equal-width data series with n data points (doule) separated by “,”. The *i*-th points represents the CPU power of *i*/*n* utilization. For example of 10 data points, “93.7,97,101, 105,110,116,121,125,129,133,135”, in which the idle power is 93.7 Watt, the maximum power is 135 Watt, and power for 50% utilization is 110 Watt.

- EnergyGenerator (*exponent*, *linear,* *logarithm*): 

  *max*: power when cpu is full utilizated.

  *idel*: power when cpu is completely idle.

  *exponent*: the *exponent* value (double) of exponential model.

  *base* : the *base* value (double) of logarithmic model.

### 7. Execution

- The execution of CloudSimPer is extremely simple, preparing the configuration and plugins, then simply invoke the following three fuctions:

  CloudSimPer.*init*(); CloudSimPer.*execute*(long *duration*); CloudSimPer.*log*();

  Notice that the *duration* represents how long CloudSimPer will stop in simulation time. For example a year is 31,536,000 (60*60*24*365). However, it is a simulation time, you will actually wait one minute for a one-month-simulation with a middle-size-case and a normal computer. Actually the logs is time consuming.

## Version 2.0 development

- Added CloudSimPer_ Simple package, its architecture is the same as CloudSimPer.
- CloudSimPer_ Simple is a simplified version of CloudSimPer. It does not depend on CloudSim.
- Experiments show that CloudSimPer_Simple has similar accuracy to CloudSimPer.
- The execution speed of CloudSimPer_Simple is very fast compared with CloudSimPer (one month's execution time in the data center can be simulated within 5 minutes). Researchers who need execution speed can use this platform.