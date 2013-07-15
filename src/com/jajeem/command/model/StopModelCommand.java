package com.jajeem.command.model;

public class StopModelCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3270420351962975144L;

	public StopModelCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
