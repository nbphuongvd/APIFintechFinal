package vn.com.payment.ultities;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class FileLogger {
	Logger logger;
	static RootConfig root = new RootConfig();

	public FileLogger(Class clazz) {
		logger = Logger.getLogger(clazz);

	}

	public FileLogger(String className) {
		logger = Logger.getLogger(className);
	}

	public void info(String input) {
		logger.info(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void info(String input, Throwable e) {
		logger.info(input, e);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void info(String input, int e) {
		logger.info(input);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void fatal(String input) {
		logger.error(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fatal(String input, Throwable e) {
		logger.error(input, e);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void debug(String funtion,Object obj,String note) {
		
		String input="[%s](%s):%s";
		
		String objGson=obj.toString();
		input=String.format(input, funtion,note,objGson);
		
		logger.debug(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void debug(String input) {
		logger.debug(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void debug(String input, Throwable e) {
		logger.debug(input, e);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void error(String input) {
		logger.error(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void error(String input, Throwable e) {
		logger.error(input, e);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void trace(String input) {
		logger.trace(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trace(String input, Throwable e) {
		logger.trace(input, e);
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public void monitor(String input) {
		logger.fatal(input);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static {
		DOMConfigurator.configure(root.getRoot() + "/config/log4j.xml");
	}
}
