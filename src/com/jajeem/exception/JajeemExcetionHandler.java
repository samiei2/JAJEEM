package com.jajeem.exception;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.groupwork.dao.h2.GroupDAO;

public abstract class JajeemExcetionHandler extends Exception{
	Logger logger = Logger.getLogger(GroupDAO.class);
	public JajeemExcetionHandler(Exception e){ 
		PropertyConfigurator.configure("conf/log4j.conf");
		ExceptionHandler(e);
	}
	
	public abstract void ExceptionHandler(Exception e);
}
