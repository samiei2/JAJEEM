package com.jajeem.command.model;

public class StartSpeechCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String server;

	public StartSpeechCommand(String from, String to, int port) {
		super(from, to, port);

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
