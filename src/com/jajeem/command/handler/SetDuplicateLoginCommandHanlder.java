package com.jajeem.command.handler;

import java.awt.Color;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class SetDuplicateLoginCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		WebOptionPane.showMessageDialog(null,"Duplicate login detected!\nAnother user with the same user name has already logged in!");
	}
}
