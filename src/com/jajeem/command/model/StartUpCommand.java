package com.jajeem.command.model;

@SuppressWarnings("serial")
public class StartUpCommand extends Command {
	
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
