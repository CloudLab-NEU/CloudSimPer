package edu.neu.cloudsimper;



/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Const {

	public static final String CONIF_FILE_ROOT = "src/";
	public static final String CONIF_FILE_DATACENTER = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Datacenter.xml";
	public static final String CONIF_FILE_HOST = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Host.xml";
	public static final String CONIF_FILE_PLUGIN_INFOR = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/PluginInfor.xml";
	public static final String CONIF_FILE_REGION = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Region.xml";
	public static final String CONIF_FILE_REQUEST = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Request.xml";
	public static final String CONIF_FILE_STORAGE = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Storage.xml";
	public static final String CONIF_FILE_VM = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/Vm.xml";
	public static final String CONIF_FILE_ENERGY = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/energy/";
	public static final String CONIF_FILE_SOLARENERGY = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/energy/solar";
	public static final String CONIF_FILE_WINDENERGY = CONIF_FILE_ROOT + "edu/neu/cloudsimper/conf/energy/wind";
	public static final String CONIF_FILE_DATACENTERRECORD = "E://CloudSimperRecord//DatacenterRecord//datacenterRecord";
	public static final String CONIF_FILE_ENERGYRECORD = "E://CloudSimperRecord//EnergyRecord//energyRecord";
	public static final String CONIF_FILE_REQUESTRECORD = "E://CloudSimperRecord//RequestRecord//requestRecord";
	public static final String CONIF_SUFFIX_ENERGY = ".re";

	public static final String A_NAME = "name";
	public static final String A_SIZE = "size";
	public static final String T_ATTRIBUTE = "Attribute";
	public static final String T_PLUGINS = "Plugins";
	public static final String T_HOSTS = "Hosts";
	public static final String T_STORAGES = "Storages";
	public static final String T_VMS = "Vms";
	public static final String T_ENERGIES = "Energies";

	public static final String V_SIZE = "size";
	public static final String V_REGION = "region";
	public static final String V_LONGITUDE = "longitude";
	public static final String V_LATITUDE = "latitude";
	public static final String V_START_LONGITUDE = "startLongitude";
	public static final String V_START_LATITUDE = "startLatitude";
	public static final String V_END_LONGITUDE = "endLongitude";
	public static final String V_END_LATITUDE = "endLatitude";
	public static final String V_POPULATION = "population";
	public static final String V_TIME_ZONE = "timeZone";
	public static final String V_START_DAY_OF_YEAR = "startDayOfYear";
	public static final String V_LENGTH = "length";
	public static final String V_INPUT_SIZE = "inputSize";
	public static final String V_OUTPUT_SIZE = "outputSize";
	public static final String V_INTERVAL = "interval";
	public static final String V_CAPACITY = "capacity";
	public static final String V_PRICE_KWH = "priceKwh";
	public static final String V_MIPS = "mips";
	public static final String V_PES_NUMBER = "cpuNumber";
	public static final String V_RAM = "ram";
	public static final String V_BW = "bw";
	public static final String V_STORAGE = "storage";
	public static final String V_LOCATION = "location";
	public static final String V_COST_PER_SECOND = "costPerSecond";
	public static final String V_COST_PER_MEM = "costPerMem";
	public static final String V_COST_PER_STORAGE = "costPerStorage";
	public static final String V_COST_PER_BW = "costPerBw";
	public static final String V_DISABLE_MIGRATIONS = "disableMigrations";
	public static final String V_SCHEDULING_INTERVAL = "schedulingInterval";
	public static final String V_BANDWIDTH = "bandwidth";
	public static final String V_NETWORK_LATENCY = "networkLatency";

	public static final String P_REQUEST_DISPATCHER = "RequestDispatcher";
	public static final String P_VM_ALLOCATION_POLICY = "VmAllocationPolicy";
	public static final String P_BW_PROVISIONER = "BwProvisioner";
	public static final String P_PE_PROVISIONER = "PeProvisioner";
	public static final String P_RAM_PROVISIONER = "RamProvisioner";
	public static final String P_POWER_MODEL = "PowerModel";
	public static final String P_VM_SCHEDULER = "VmScheduler";
	public static final String P_CLOUDLET_SCHEDULER = "CloudletScheduler";
	public static final String P_CPU_UTILIZATION_MODEL = "CpuUtilizationModel";
	public static final String P_RAM_UTILIZATION_MODEL = "RamUtilizationModel";
	public static final String P_BW_UTILIZATION_MODEL = "BwUtilizationModel";
	public static final String P_REQUEST_GENERATOR = "RequestGenerator";
	public static final String P_REQUEST_RANDOMIZATION = "RequestRandomization";
	public static final String P_ENERGY_GENERATOR = "EnergyGenerator";
	public static final String P_DEFAULT = "default";
	public static final String P_ROUNDROBIN = "RoundRobin";
	public static final String P_RANDOM = "Random";
	public static final String P_NEARNEST = "Nearnest";
	public static final String P_GREEDY = "Greedy";
	public static final String P_MINCOST = "MinCost";
	public static final String P_MAXGREEN = "MaxGreen";

	public static final int ENERGY_LOG_INTERVAL = 600;
	public static final int HOST_POWER = 100;

	public static final long AVERGE[] = {5087,3312,2235,1676,1523,1886,2962,4150,5842,6918,
			7337,7142,7058,7323,7463,7603,7379,6932,6876,7687,8204,8442,8022,6708};
	public static final double STARNDDEVIATION[] = {953.0163052,620.5332065,418.9968667,314.2436792,285.4981352,353.3673445,
			555.0881717,777.5645493,1094.437429,1295.891897,1374.602929,1337.818161,1322.167442,1371.940098,1398.115354,
			1424.35957,1382.384584,1298.723967,1288.208072,1440.113114,1537.029277,1581.558689,1502.880551,1256.86677};
	public static final double HK_POPULATION_DENSITY = 1.92E-10;
	public static final double AIR_DENSITRY =  1.29;
	public static final double ATMOSPHERIC_TRANSPARENCY = 0.532;
	public static final double VANE_ENGINE_RADIUS = 52;
	public static final double SOLAR_CONVERSION_RATE = 0.31;
	public static final int SOLAR_CONSTANT = 1357;
	public static final String ARCHITECTURE = "x86";
	public static final String OS = "Linux";
	public static final String VMM = "Xin";
	public static final int CLOUDLET_PES = 1;
	public static final boolean CLOUDLET_RECROD = false;
	public static final int SIMPER_RUN_SLEEP = 200;
	public static final int ID_PREFIX = 10;
	public static final int COUNT = 10000;
	public static final double EARTH_RADIUS = 6371000;
}
