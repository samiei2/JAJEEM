package com.jajeem.command.service;

import java.io.DataInputStream;

import com.jajeem.command.model.Command;

public interface IConnectorSevice {

	void start();

	void stop();

	void broadcast();
	
	void process(DataInputStream d);

	void send(Command cmd);

}
