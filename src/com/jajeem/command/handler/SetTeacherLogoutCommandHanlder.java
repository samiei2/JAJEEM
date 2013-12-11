package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;

public class SetTeacherLogoutCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		Student.getFrmJajeemProject().setVisible(false);
	}
}
