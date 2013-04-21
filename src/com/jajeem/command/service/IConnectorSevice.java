package com.jajeem.command.service;

import java.io.DataInputStream;

public interface IConnectorSevice {

	void start();

	void stop();

	void send();

	void broadcast();
	
	void process(DataInputStream d);

}
