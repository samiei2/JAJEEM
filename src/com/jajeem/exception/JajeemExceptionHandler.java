package com.jajeem.exception;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@SuppressWarnings("serial")
public class JajeemExceptionHandler extends Exception {
	private static Logger logger = Logger
			.getLogger(JajeemExceptionHandler.class);

	static {
		PropertyConfigurator.configure(JajeemExceptionHandler.class
				.getResource("/com/jajeem/conf/log4j.conf"));
	}

	public JajeemExceptionHandler(Exception e) {
		ExceptionHandler(e);
	}

	public void ExceptionHandler(Exception e) {
		switch (e.getClass().getName()) {
		case "SQLException":
			SqlExceptionHandler(e);
			break;
		default:
			break;
		}
		logger.error(e.getMessage(), e);
	}

	private void SqlExceptionHandler(Exception e) {
		logger.error(e.getMessage(), e);
	}

	public static void logError(Exception e) {
		logger.error(e.getMessage());
	}

	@SuppressWarnings("rawtypes")
	public static void logError(Exception e, Class type) {
		Logger internalLogger = Logger.getLogger(type);
		internalLogger.error(e.getMessage());
	}

	public static void logMessage(Object e) {
		if (e instanceof String) {
			logger.info(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void logMessage(Object e, Class type) {
		Logger internalLogger = Logger.getLogger(type);
		if (e instanceof String) {
			internalLogger.info(e);
		}
	}
}
