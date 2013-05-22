package com.jajeem.command.model;

public class ChatCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ChatCommand(String from, String to, int port, String msg) {
		super(from, to, port);
		
		this.setMessage(msg);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
