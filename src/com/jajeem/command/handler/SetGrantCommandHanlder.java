package com.jajeem.command.handler;

import javax.swing.JInternalFrame;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.design.InstructorCenter;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;

public class SetGrantCommandHanlder implements ICommandHandler {

	@SuppressWarnings("static-access")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (((GrantCommand) cmd).isGranted()) {
			Student student = new Student();
			student.main(null);
			StudentLogin.setLoginDialogVisible(false);
			
			JInternalFrame[] frames = InstructorCenter.desktopPane.getAllFrames();
			for (JInternalFrame frame : frames) {
				if ((String) frame.getClientProperty("ip") == cmd.getFrom()) {
					frame.setTitle(StudentLogin.LoginDialog.name);
					break;
				}
			}
		} else {
			StudentLogin.setLoginDialogVisible(true);
			System.out.println("Wrong!");
		}
	}

}
