package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class SetGrantCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (((GrantCommand) cmd).isGranted()) {
			@SuppressWarnings("unused")
			Student student = new Student();
			StudentLogin.setLoginDialogVisible(true);
		} else {
			StudentLogin.setLoginDialogVisible(false);
			System.out.println("Wrong!");
		}
	}

}
