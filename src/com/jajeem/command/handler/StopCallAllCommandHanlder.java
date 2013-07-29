package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVReceiveOnly;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class StopCallAllCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.getReceiverOnly() != null) {
			Student.getReceiverOnly().close();
			Student.setReceiverOnly(null);
		}
	}
}
