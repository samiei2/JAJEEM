package com.jajeem.command.model;

import java.io.Serializable;

public class Command implements Serializable {

	private String host;
	private int port;
	private String type;

	public Command(String host, int port2, String type) {
		this.setHost(host);
		this.setPort(port2);
		this.setType(type);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
