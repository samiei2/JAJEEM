package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.core.design.Student;

public class StopModelCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (!InetAddress.getLocalHost().getHostAddress().equals(((StartModelCommand) cmd).getLeader())) {
			// i am the supreme leader!
			Student.getSendOnly().stop();
			return;
		} else {
			Student.getVncViewer().stopClient();
			Student.getReceiverOnly().close();
		}
	}
}