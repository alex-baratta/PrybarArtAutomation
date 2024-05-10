package managers;
import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());
	public static void startTestCase(String testCaseName) {
		Log.info("##########################     "+ testCaseName +"     ##########################");
	}
	
	public static void endTestCase(String testCaseName) {
		Log.info("####################     END "+ testCaseName +" END     ####################");	
	}
	
	public static void info(String message) {
		Log.info(message);
	}

	public static void warn(String message) {
		Log.warn(message);
	}
	
	public static void error(String message) {
		Log.error(message);
	}
	
	public static void fatal(String message) {
		Log.fatal(message);
	}
	
	public static void debug(String message) {
		Log.debug(message);
	}	
}
