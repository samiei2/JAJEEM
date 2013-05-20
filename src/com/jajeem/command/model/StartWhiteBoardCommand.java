package com.jajeem.command.model;

import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class StartWhiteBoardCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014963068838321817L;
	
	private WhiteboardClient client;
	private String server;
	public StartWhiteBoardCommand(String from, String to, int port) {
		super(from, to, port);
		
	}

	public WhiteboardClient getWhiteboardClient() {
		return client;
	}
	
	public void setWhiteboardClient(WhiteboardClient cli) {
		client = cli;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

}
