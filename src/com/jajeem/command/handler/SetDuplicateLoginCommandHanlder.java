package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.StudentLogin;

public class SetDuplicateLoginCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StudentLogin.getLoginDialog().setVisible(false);
		JOptionPane
				.showMessageDialog(
						null,
						"Duplicate login detected!\nAnother user with the same user name has already logged in!");
		StudentLogin.getLoginDialog().setVisible(true);
	}
}
