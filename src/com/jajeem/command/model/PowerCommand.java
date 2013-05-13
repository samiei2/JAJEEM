package com.jajeem.command.model;

public class PowerCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2909074411682367952L;
	
	private String type;

	public PowerCommand(String host, int port2, String type) {
		super(host, port2);
		
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
