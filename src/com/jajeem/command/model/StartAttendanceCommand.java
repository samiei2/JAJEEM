package com.jajeem.command.model;

public class StartAttendanceCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7144758501020760265L;

	public StartAttendanceCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
