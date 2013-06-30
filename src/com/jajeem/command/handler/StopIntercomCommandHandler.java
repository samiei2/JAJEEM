package com.jajeem.command.handler;


import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.exception.JajeemExcetionHandler;

public class StopIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			
//			Student.getReceiver().close();
			Student.getTransmitter().stop();
			
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
