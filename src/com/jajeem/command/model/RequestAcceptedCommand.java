package com.jajeem.command.model;

import com.jajeem.core.model.Student;
import com.jajeem.util.CommunicationType;

public class RequestAcceptedCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;
	private CommunicationType comType = CommunicationType.None;
	
	public RequestAcceptedCommand(String from, String to, int port) {
		super(from, to, port);
		
		
	}

	public CommunicationType getCommunicationType() {
		return comType;
	}
	
	public void setCommunicationType(CommunicationType type){
		comType = type;
	}
}