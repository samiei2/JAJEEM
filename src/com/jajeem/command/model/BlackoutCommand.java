package com.jajeem.command.model;

public class BlackoutCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5659948009402787525L;
	
	private boolean black;

	public BlackoutCommand(String host, int port2) {
		super(host, port2);
		
		this.black = false;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

}
