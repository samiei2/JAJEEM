package com.jajeem.command.model;

import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class StartWhiteBoardCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private int sessionID;
	private String server;
	public StartWhiteBoardCommand(String from, String to, int port) {
		super(from, to, port);
		
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public int getSessionID(){
		return sessionID;
	}
}
