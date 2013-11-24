package com.jajeem.command.handler;

import java.awt.Color;

import javax.swing.JInternalFrame;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.model.StudentLogoutCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.InstructorNoaUtil;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Session;

public class SetStudentLogoutCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		JInternalFrame[] frames = InstructorNoa.getDesktopPane()
				.getAllFrames();
		for (JInternalFrame frame : frames) {
			if (cmd.getFrom().compareTo(
					(String) frame.getClientProperty("ip")) == 0) {
				try {
					Session.getLoggedInStudents().remove(frame.getClientProperty("ip").toString());
				} catch (Exception e) {
				}
				try{
					frame.dispose();
				}
				catch(Exception e){
				}
				try {
					InstructorNoaUtil.createFrame(InstructorNoa.getDesktopPane(),
							((StudentLogoutCommand) cmd).getFrom(),
							((StudentLogoutCommand) cmd).getSenderName());
				} catch (Exception e) {
				}
				
				break;
			}
		}
	}
}
