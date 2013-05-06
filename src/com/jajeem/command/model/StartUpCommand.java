package com.jajeem.command.model;

public class StartUpCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2287770406725442741L;
	private String sender;
	
	public StartUpCommand(String host, int port, String sender) {
		super(host, port);
		this.sender = sender;
		// TODO Auto-generated constructor stub
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
}
