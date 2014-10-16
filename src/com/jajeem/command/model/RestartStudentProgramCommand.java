package com.jajeem.command.model;

public class RestartStudentProgramCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2287770406725442741L;


	public RestartStudentProgramCommand(String from, String to, int port) {
		super(from, to, port);
	}
}
