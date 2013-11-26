package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;

public class RequestRejectedCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		Student.getButtonContactInstructor().setText("Contact Instructor");
		Student.getButtonContactInstructor().setEnabled(true);
		JOptionPane.showMessageDialog(null, "Teacher rejected your request!");
	}
}
