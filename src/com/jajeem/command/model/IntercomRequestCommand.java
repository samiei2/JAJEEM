package com.jajeem.command.model;

public class IntercomRequestCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4780626236611125738L;

	public IntercomRequestCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
