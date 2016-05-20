import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Provide Logging methods

public class Logger {
	static boolean traceOn = false; // Static used to allow variable or function is shared between all instances of that class
	
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date;
	
	public static void outLine(String outText) {
		date = new Date();
		System.out.println(dateFormat.format(date) + " " + "       " + " " + outText);
	}

	public static void outTest(String outText) {
		date = new Date();
		System.out.println(dateFormat.format(date) + " " + " Test  " + " " + outText);
	}

	public static void outError(String outText) {
		date = new Date();
		System.out.println(dateFormat.format(date) + " " + "*ERROR*" + " " + outText);
	}
	
	public static void outWarning(String outText) {
		date = new Date();
		System.out.println(dateFormat.format(date) + " " + "WARNING" + " " + outText);
	}

	public static void outTrace(String outText) {
		if (traceOn) {
			date = new Date();
			System.out.println(dateFormat.format(date) + " " + " Trace " + " " + outText);
		}
	}

}
