package com.jajeem.command.model;

public class StopIntercomCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376902458256471791L;

	public StopIntercomCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
