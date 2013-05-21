package com.jajeem.command.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JInternalFrame;

import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorCenter;
import com.jajeem.core.service.StudentService;
import com.jajeem.util.Config;

public class SetAuthenticateCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException,
			UnknownHostException, Exception {
		new Config();
		boolean grant = false;
		GrantCommand grantCommand = new GrantCommand(InetAddress.getLocalHost()
				.getHostAddress(), ((AuthenticateCommand) cmd).getFrom(),
				Integer.parseInt(Config.getParam("port")), false);

		StudentService studentService = new StudentService();
		grant = studentService.authenticate(
				((AuthenticateCommand) cmd).getUsername(),
				((AuthenticateCommand) cmd).getPassword());

		grantCommand.setGranted(grant);

		if (grant) {
			JInternalFrame[] frames = InstructorCenter.desktopPane
					.getAllFrames();
			for (JInternalFrame frame : frames) {
				if (cmd.getFrom().compareTo((String) frame.getClientProperty("ip")) == 0) {
					frame.setTitle(((AuthenticateCommand) cmd).getUsername());
					frame.updateUI();
					break;
				}
			}
		}

		ServerService serverService = new ServerService();
		serverService.send(grantCommand);
	}
}
