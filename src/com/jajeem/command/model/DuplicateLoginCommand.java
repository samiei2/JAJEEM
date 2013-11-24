package com.jajeem.command.model;

public class DuplicateLoginCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public DuplicateLoginCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
