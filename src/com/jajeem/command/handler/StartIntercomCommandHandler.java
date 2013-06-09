package com.jajeem.command.handler;

import java.net.InetAddress;


import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;

public class StartIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		// We need three parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVReceive2
		// -Drun.example.arg.line="--local-port-base=10000 --remote-host=129.130.131.132 --remote-port-base=5000"

		try {

			Student.receiver
					.setRemoteAddr(InetAddress.getByName(cmd.getFrom()));
			Student.receiver.initialize();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
