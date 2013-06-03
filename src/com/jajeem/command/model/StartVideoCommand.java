package com.jajeem.command.model;

public class StartVideoCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014963068838321817L;
	
	private String streamServer;
	private String streamPort;
	private String streamAddress;
	private boolean isClient;
	
	public StartVideoCommand(String from, String to, int port) {
		super(from, to, port);
		
	}
	public String getStreamServer() {
		return streamServer;
	}
	public void setStreamServer(String streamServer) {
		this.streamServer = streamServer;
	}
	public String getStreamPort() {
		return streamPort;
	}
	public void setStreamPort(String streamPort) {
		this.streamPort = streamPort;
	}
	public String getStreamAddress() {
		return streamAddress;
	}
	
	public void setStreamAddress(String address){
		this.streamAddress = address;
	}
	
	public void setClient(boolean b) {
		isClient = b;
	}

	public boolean isClient(){
		return isClient;
	}

}
