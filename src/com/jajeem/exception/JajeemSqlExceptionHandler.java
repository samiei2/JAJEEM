package com.jajeem.exception;

public class JajeemSqlExceptionHandler extends JajeemExcetionHandler{

	public JajeemSqlExceptionHandler(Exception e) {
		super(e);
	}

	@Override
	public void ExceptionHandler(Exception e) {
		logger.error(e.getMessage(), e);
	}
	

}
