package com.jajeem.command.model;

public class BlackoutCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5659948009402787525L;
	
	private boolean black = false;

	public BlackoutCommand(String host, int port2, boolean black) {
		super(host, port2);
		
		this.black = black;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

}
