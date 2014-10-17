package com.jajeem.command.model;


public class GetProgramListCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2841209549619891774L;

	public GetProgramListCommand(String from, String to, int port) {
		super(from, to, port);
	}
}
