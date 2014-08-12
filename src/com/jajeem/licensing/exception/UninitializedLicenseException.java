package com.jajeem.licensing.exception;

public class UninitializedLicenseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	public UninitializedLicenseException(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
