package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.Student;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;

public class StopIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			if (Integer.parseInt(Config.getParam("server")) == 1) {
				InstructorNoa.getTransmitter().stop();
			} else {
				Student.getTransmitter().stop();
			}

		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
