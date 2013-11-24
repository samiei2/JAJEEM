package com.jajeem.command.handler;

import java.net.InetAddress;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.RequestFromStudentCommand;
import com.jajeem.command.model.RequestRejectedCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.Student2;
import com.jajeem.core.model.Student;
import com.jajeem.util.Config;

public class RequestRejectedCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		RequestRejectedCommand command = (RequestRejectedCommand) cmd;
		Student2.getButtonContactInstructor().setText("Contact Instructor");
		Student2.getButtonContactInstructor().setEnabled(true);
		WebOptionPane.showMessageDialog(null, "Teacher rejected your request!");
	}
}
