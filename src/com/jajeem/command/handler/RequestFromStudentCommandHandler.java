package com.jajeem.command.handler;

import java.net.InetAddress;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.RequestFromStudentCommand;
import com.jajeem.command.model.RequestRejectedCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.model.Student;
import com.jajeem.util.Config;

public class RequestFromStudentCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		RequestFromStudentCommand command = (RequestFromStudentCommand) cmd;
		Student std = command.getStudent();
		if (std == null || std.getFullName() == "" || std.getFullName() == null)
			std.setFullName("Anonymous");
		String[] options = new String[] { "Chat", "Intercom", "Cancel" };
		int selectedOption = WebOptionPane
				.showOptionDialog(
						null,
						std.getFullName()
								+ " wants to contact you.Please select what you want to do with this request.",
						std.getFullName() + " wants to contact you...",
						WebOptionPane.DEFAULT_OPTION,
						WebOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
		if (selectedOption == 0) {
			// chat
		} else if (selectedOption == 0) {
			// intercom
		} else if (selectedOption == 0) {
			// cancel
			RequestRejectedCommand rejectCmd = new RequestRejectedCommand(
					InetAddress.getLocalHost().getHostAddress(), 
					command.getFrom(),
					Integer.parseInt(Config.getParam("port")));
			InstructorNoa.getServerService().send(rejectCmd);
		}
	}
}
