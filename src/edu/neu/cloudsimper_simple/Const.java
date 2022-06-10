package edu.neu.cloudsimper_simple;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public class Const {

	public static final String CONIF_FILE_ROOT = "src/";
	public static final String CONIF_FILE_DATACENTER = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Datacenter.xml";
	public static final String CONIF_FILE_HOST = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Host.xml";
	public static final String CONIF_FILE_PLUGIN_INFOR = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Plugin.xml";
	public static final String CONIF_FILE_REGION = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Region.xml";
	public static final String CONIF_FILE_REQUEST = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Request.xml";
	public static final String CONIF_FILE_ENERGY = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Energy.xml";
	public static final String CONIF_FILE_BATTERY = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/Battery.xml";

	public static final String CONIF_FILE_ENERGY_DATA = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/energy/";
	public static final String CONIF_FILE_REQUEST_DATA = CONIF_FILE_ROOT + "edu/neu/cloudsimper_simple/_conf/trace/";
	public static final String CONIF_SUFFIX_ENERGY = ".re";
	public static final String CONIF_SUFFIX_REQUEST = ".rs";

	public static final String A_NAME = "name";
	public static final String A_SIZE = "size";
	
	public static final String T_ATTRIBUTE = "Attribute";
	public static final String T_PLUGINS = "Plugins";
	public static final String T_HOSTS = "Hosts";
	public static final String T_ENERGIES = "Energies";
	public static final String T_BATTERY = "Batteries";

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
	public static final String V_POPULATIONDENSITY = "population-density";
	public static final String V_REFIONSIZE = "region-size";
	public static final String V_TIMESHIFT = "time-shift";
	public static final String V_START_DAY_OF_YEAR = "startDayOfYear";
	public static final String V_LENGTH = "length";
	public static final String V_DEADLINE = "deadline";
	public static final String V_MAXWAITTIME = "maxWaitTime";
	public static final String V_INTERVAL = "interval";
	public static final String V_MIPS = "mips";
	public static final String V_PES_NUMBER = "cpuNumber";
	public static final String V_LOCATION = "location";
	public static final String V_CAPACITY = "capacity";
	public static final String V_POWER = "power";
	public static final String V_DOD = "dod";
	public static final String V_LIFECYLE = "lifecyle";
	public static final String V_PRICE = "price";

	public static final String P_REQUEST_DISPATCHER = "RequestDispatcher";
	public static final String P_POWER_MODEL = "PowerModel";
	public static final String P_ENERGY_PRICE = "EnergyPrice";
	public static final String P_REQUEST_GENERATOR = "RequestGenerator";
	public static final String P_REQUEST_RANDOMIZATION = "RequestRandomization";
	public static final String P_ENERGY_GENERATOR = "EnergyGenerator";
	public static final String P_DEFAULT = "default";
	public static final String P_ROUNDROBIN = "roundRobin";
	public static final String P_NEAREST = "nearest";
	public static final String P_MAXPOWER = "maxPower";
	public static final String P_MINTIME = "minTime";
	public static final String P_MAXGREEN = "maxGreen";
	public static final String P_MINCOST = "minCost";
	public static final String P_AVEGREEN = "aveGreen";

	public static final int ENERGY_PRICE = 36;

	// public static final int HOST_POWER = 100;
	//
	// public static final long AVERGE[] =
	// {5087,3312,2235,1676,1523,1886,2962,4150,5842,6918,
	// 7337,7142,7058,7323,7463,7603,7379,6932,6876,7687,8204,8442,8022,6708};
	// public static final double STARNDDEVIATION[] =
	// {953.0163052,620.5332065,418.9968667,314.2436792,285.4981352,353.3673445,
	// 555.0881717,777.5645493,1094.437429,1295.891897,1374.602929,1337.818161,1322.167442,1371.940098,1398.115354,
	// 1424.35957,1382.384584,1298.723967,1288.208072,1440.113114,1537.029277,1581.558689,1502.880551,1256.86677};
	// public static final double HK_POPULATION_DENSITY = 1.92E-10;

	// public static final String ARCHITECTURE = "x86";

	// public static final int COUNT = 10000;
	// public static final double EARTH_RADIUS = 6371000;
}
