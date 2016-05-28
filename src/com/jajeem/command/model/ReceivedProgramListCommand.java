package com.jajeem.command.model;


public class ReceivedProgramListCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2841209549619891774L;

	public ReceivedProgramListCommand(String from, String to, int port) {
		super(from, to, port);
	}
}
