package com.jajeem.command.model;

import java.io.Serializable;

public class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5200765941186849924L;
	private String host;
	private int port;

	public Command(String host, int port2) {
		this.setHost(host);
		this.setPort(port2);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
