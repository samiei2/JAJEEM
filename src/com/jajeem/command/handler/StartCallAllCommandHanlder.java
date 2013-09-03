package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVReceiveOnly;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class StartCallAllCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartCallAllCommand command = (StartCallAllCommand)cmd;
		//TODO: change ip here!
		if (Student.getReceiverOnly() == null) {
			String ip = InetAddress.getLocalHost()
					.getHostAddress().toString();
			ip = ip.substring(0,
					ip.lastIndexOf("."))
					+ ".255";
			AVReceiveOnly ar = new AVReceiveOnly("10010",
					ip, "5010");
			ar.initialize("audio");
			Student.setReceiverOnly(ar);
		} else {
			Student.getReceiverOnly()
					.setRemoteAddr(
							InetAddress.getByName(StudentLogin.getServerIp()));
			Student.getReceiverOnly().initialize("audio");
		}
	}
}
