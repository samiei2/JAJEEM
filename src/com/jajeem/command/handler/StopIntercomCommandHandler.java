package com.jajeem.command.handler;

import org.jitsi.service.libjitsi.LibJitsi;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;

public class StopIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			Student.getReceiver().close();
			
			LibJitsi.stop();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
