package com.jajeem.licensing.exception;

public class InvalidLicenseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	String message;
	public InvalidLicenseException(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
