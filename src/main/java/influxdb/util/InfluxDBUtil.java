package influxdb.util;


public class InfluxDBUtil {
	
	public static influxdb.util.InfluxDBConnection getConn() throws Exception {

		String username = "admin";
		String password = "admin";
		String host = "10.20.31.138";
		String port = "8086";
		String dataBaseName = "ROBOT_LOG_DB";
		
		return new influxdb.util.InfluxDBConnection(username, password, "http://"+host+":"+port, dataBaseName, null);
		
	}

}
