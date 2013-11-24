package com.jajeem.command.model;

import com.jajeem.core.model.Student;

public class RequestRejectedCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;
	
	public RequestRejectedCommand(String from, String to, int port) {
		super(from, to, port);
		
		
	}
}
