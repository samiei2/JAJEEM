package com.jajeem.command.handler;

import java.awt.Color;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.util.ClientSession;

public class SetTeacherLogoutCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		Student.getFrmJajeemProject().setVisible(false);
	}
}
