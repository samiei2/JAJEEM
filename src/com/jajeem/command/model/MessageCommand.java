package com.jajeem.command.model;

public class MessageCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181676943015164572L;
	
	private String message;

	public MessageCommand(String from, String to, int port, String msg) {
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
