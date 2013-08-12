package com.jajeem.exception;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@SuppressWarnings("serial")
public class JajeemExcetionHandler extends Exception{
	private static Logger logger = Logger.getLogger(JajeemExcetionHandler.class);
	
	static{
		PropertyConfigurator.configure(JajeemExcetionHandler.class.getResource("/com/jajeem/conf/log4j.conf"));
	}
	public JajeemExcetionHandler(Exception e){
		ExceptionHandler(e);
	}
	
	public void ExceptionHandler(Exception e){
		switch (e.getClass().getName()){  
			case "SQLException" :  
				SqlExceptionHandler(e);
				break;
			default : 
				break;
		}
		logger.error(e.getMessage(),e);
	}

	private void SqlExceptionHandler(Exception e) {
		logger.error(e.getMessage(),e);
	}

	public static void logError(Exception e) {
		logger.error(e.getMessage());
	}
	
	public static void logError(Exception e,Class type)
	{
		Logger internalLogger = Logger.getLogger(type);
		internalLogger.error(e.getMessage());
	}
	
	public static void logMessage(Object e){
		if(e instanceof String)
			logger.info((String)e);
	}

	public static void logMessage(Object e, Class type) {
		Logger internalLogger = Logger.getLogger(type);
		if(e instanceof String)
			internalLogger.info((String)e);
	}
}
