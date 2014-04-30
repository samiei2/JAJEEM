package com.jajeem.licensing.exception;

public class LicenseServerErrorException extends Exception {

	int responseCode;
	public LicenseServerErrorException(int responseCode) {
		this.responseCode = responseCode;
	}
	
}
