package com.jajeem.command.model;

public class RequestEndedCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;

	public RequestEndedCommand(String from, String to, int port) {
		super(from, to, port);

	}
}
