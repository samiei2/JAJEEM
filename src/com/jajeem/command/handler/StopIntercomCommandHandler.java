package com.jajeem.command.handler;


import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;

public class StopIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			
			Student.getReceiver().close();
			Student.getTransmitter().stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
