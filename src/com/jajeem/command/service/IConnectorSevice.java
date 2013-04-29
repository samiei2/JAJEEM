package com.jajeem.command.service;

import java.io.DataInputStream;

public interface IConnectorSevice {

	void start();

	void stop();

	void broadcast();
	
	void process(DataInputStream d);

	void send(int destination);

}
