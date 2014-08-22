package com.jajeem.command.model;

public class StudentLogoutCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String senderName;

	/**
	 * 
	 */

	public StudentLogoutCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

}
