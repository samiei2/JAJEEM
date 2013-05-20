package com.jajeem.command.model;

public class StopAttendanceCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6067071727631508107L;

	public StopAttendanceCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
