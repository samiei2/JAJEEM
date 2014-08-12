package com.jajeem.licensing.exception;

public class LicenseServerErrorException extends Exception {

	int responseCode;
	public LicenseServerErrorException(int responseCode) {
		this.responseCode = responseCode;
	}
	
	private static final long serialVersionUID = 1L;
	
	String message;
	public LicenseServerErrorException(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public int getErrorCode() {
		return responseCode;
	}
}
