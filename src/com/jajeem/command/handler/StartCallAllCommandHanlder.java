package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVReceiveOnly;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.core.design.Student;

public class StartCallAllCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.getReceiverOnly() == null) {
			AVReceiveOnly ar = new AVReceiveOnly("10010",
					((StartModelCommand) cmd).getLeader(), "5010");
			ar.initialize();
			Student.setReceiverOnly(ar);
		} else {
			Student.getReceiverOnly()
					.setRemoteAddr(
							InetAddress.getByName(((StartModelCommand) cmd)
									.getLeader()));
			Student.getReceiverOnly().initialize();
		}
	}
}
