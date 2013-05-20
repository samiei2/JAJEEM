package com.jajeem.command.model;

public class GrantCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6651359089589532508L;
	
	private boolean granted = false;

	public GrantCommand(String from, String to, int port, boolean grant) {
		super(from, to, port);
		
		this.setGranted(grant);
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}
}
