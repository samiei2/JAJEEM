package com.jajeem.exception;

public class VideoConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VideoConnectionException() {
	}

	public VideoConnectionException(String message) {
		super(message);
	}

	public VideoConnectionException(Throwable cause) {
		super(cause);
	}

	public VideoConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public VideoConnectionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
