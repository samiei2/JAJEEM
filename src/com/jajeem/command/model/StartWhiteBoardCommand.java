package com.jajeem.command.model;

public class StartWhiteBoardCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014963068838321817L;

	private int sessionID;
	private String server;

	private String SessionPort;
	private String WhiteboardPort;

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

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionPort(String sessionPort) {
		this.SessionPort = sessionPort;
	}

	public String getSessionPort() {
		return this.SessionPort;
	}

	public String getWhiteboardPort() {
		return WhiteboardPort;
	}

	public void setWhiteboardPort(String whiteboardPort) {
		WhiteboardPort = whiteboardPort;
	}

}
