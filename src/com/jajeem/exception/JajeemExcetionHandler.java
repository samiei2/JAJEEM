package com.jajeem.exception;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.groupwork.dao.h2.GroupDAO;

@SuppressWarnings("serial")
public class JajeemExcetionHandler extends Exception{
	Logger logger = Logger.getLogger(GroupDAO.class);
	public JajeemExcetionHandler(Exception e){ 
		PropertyConfigurator.configure("conf/log4j.conf");
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
}
