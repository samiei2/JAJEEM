package com.jajeem.command.handler;

import java.awt.Color;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class SetGrantCommandHanlder implements ICommandHandler {

	@SuppressWarnings("static-access")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (((GrantCommand) cmd).isGranted()) {
			
			Student.getFrmJajeemProject().setVisible(true);

			Student.setStudentModel(((GrantCommand) cmd).getStudent());
			com.jajeem.util.Session.setStudent(((GrantCommand) cmd)
					.getStudent());

			StudentLogin.setLoginDialogVisible(false);

		} else {
			StudentLogin.setFieldsColor(Color.decode("#FAD9D9"));
		}
	}
}
