package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.util.ClientSession;

public class StopCallAllCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.getReceiverOnly() != null) {
			Student.getReceiverOnly().close();
			Student.setReceiverOnly(null);
			if (ClientSession.getStudentIntercomPanel() != null) {
				ClientSession.getStudentIntercomPanel().dispose();
			}
		}
	}
}
