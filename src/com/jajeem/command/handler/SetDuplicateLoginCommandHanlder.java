package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;

public class SetDuplicateLoginCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		JOptionPane
				.showMessageDialog(
						null,
						"Duplicate login detected!\nAnother user with the same user name has already logged in!");
	}
}
