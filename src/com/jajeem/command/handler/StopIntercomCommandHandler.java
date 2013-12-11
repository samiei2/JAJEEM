package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Config;

public class StopIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			if (Integer.parseInt(Config.getParam("server")) == 1) {
				InstructorNoa.getTransmitter().stop();
			} else {
				Student.getTransmitter().stop();
				// Student.setIntercomButtonStart();
				if (ClientSession.getStudentIntercomPanel() != null) {
					ClientSession.getStudentIntercomPanel().dispose();
				}
			}

		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
