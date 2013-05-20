package com.jajeem.command.model;

public class InternetCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;

	public InternetCommand(String from, String to, int port, boolean grant) {
		super(from, to, port);
	}

}
