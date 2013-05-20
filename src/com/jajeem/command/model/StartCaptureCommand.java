package com.jajeem.command.model;

public class StartCaptureCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 234719420543980990L;

	public StartCaptureCommand(String from, String to, int port) {
		super(from, to, port);
	}


}
