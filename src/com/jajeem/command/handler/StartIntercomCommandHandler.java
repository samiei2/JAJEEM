package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.service.libjitsi.LibJitsi;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.exception.JajeemExcetionHandler;

public class StartIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		// We need three parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVReceive2
		// -Drun.example.arg.line="--local-port-base=10000 --remote-host=129.130.131.132 --remote-port-base=5000"

		try {

			LibJitsi.start();

//			Student.getReceiver().setRemoteAddr(
//					InetAddress.getByName(cmd.getFrom()));
			Student.getTransmitter().setRemoteAddr(
					InetAddress.getByName(cmd.getFrom()));
			Student.getTransmitter().start();
//			Student.getReceiver().initialize();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
