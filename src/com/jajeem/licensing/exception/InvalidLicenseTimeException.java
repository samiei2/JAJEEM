package com.jajeem.licensing.exception;

public class InvalidLicenseTimeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	public InvalidLicenseTimeException(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
