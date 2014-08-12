package com.jajeem.licensing;

public class InvalidActivationKey extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	public InvalidActivationKey(String string) {
		message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
