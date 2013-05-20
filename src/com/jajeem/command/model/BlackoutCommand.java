package com.jajeem.command.model;

public class BlackoutCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5659948009402787525L;
	
	private boolean black = false;

	public BlackoutCommand(String from, String to, int port, boolean black) {
		super(from, to, port);
		
		this.black = black;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

}
