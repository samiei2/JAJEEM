package com.jajeem.licensing.exception;

public class UninitializedLicensingContextException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	public UninitializedLicensingContextException(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
