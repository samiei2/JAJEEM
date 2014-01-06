package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.service.libjitsi.LibJitsi;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;
import com.jajeem.exception.JajeemExceptionHandler;

public class StartVideoChatCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		// We need three parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVReceive2
		// -Drun.example.arg.line="--local-port-base=10000 --remote-host=129.130.131.132 --remote-port-base=5000"

		try {

			LibJitsi.start();

			Student.getReceiverOnly().setRemoteAddr(
					InetAddress.getByName(cmd.getFrom()));
			Student.getReceiverOnly().initialize("both");
			// Student.setIntercomButtonStop();

		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
