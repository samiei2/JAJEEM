package com.jajeem.command.handler;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.RequestRejectedCommand;
import com.jajeem.core.design.Student;

public class RequestRejectedCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		RequestRejectedCommand command = (RequestRejectedCommand) cmd;
		Student.getButtonContactInstructor().setText("Contact Instructor");
		Student.getButtonContactInstructor().setEnabled(true);
		WebOptionPane.showMessageDialog(null, "Teacher rejected your request!");
	}
}
