package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.Config;

public class StopVideoChatCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		try {
			if (Integer.parseInt(Config.getParam("server")) == 1) {
				InstructorNoa.getReceiverOnly().close();
			} else {
				Student.getReceiverOnly().close();
			}

		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
