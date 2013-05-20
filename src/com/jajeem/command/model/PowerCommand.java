package com.jajeem.command.model;

public class PowerCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2909074411682367952L;
	
	private String type;

	public PowerCommand(String from, String to, int port, String type) {
		super(from, to, port);
		
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
