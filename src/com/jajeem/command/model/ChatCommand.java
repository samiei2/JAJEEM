package com.jajeem.command.model;

public class ChatCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private boolean mutli = false;

	public ChatCommand(String from, String to, int port, String msg, boolean multi) {
		super(from, to, port);
		
		this.setMessage(msg);
		setMutli(multi);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isMutli() {
		return mutli;
	}

	public void setMutli(boolean mutli) {
		this.mutli = mutli;
	}
}