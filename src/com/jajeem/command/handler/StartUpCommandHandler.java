package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.core.design.teacher.InstructorNoaUtil;
import com.jajeem.util.Config;

public class StartUpCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartUp(cmd);
	}
	
	public synchronized void StartUp(Command cmd) throws NumberFormatException, Exception{
		new Config();

		int port = Integer.parseInt(Config.getParam("serverPort"));

		if (Integer.parseInt(Config.getParam("server")) != 1) {
			StartCaptureCommandHandler startCaptureCommandHandler = new StartCaptureCommandHandler();
			startCaptureCommandHandler.run(cmd);
			StartUpCommand cmdToSend = new StartUpCommand(InetAddress
					.getLocalHost().getHostAddress(),
					((StartUpCommand) cmd).getSender(), port, InetAddress
							.getLocalHost().getHostAddress(),
					System.getProperty("user.name"));
			cmdToSend.setSender(InetAddress.getLocalHost().getHostAddress());
			cmdToSend.setPort(port);
			StudentLogin.getServerService().send(cmdToSend);
			StudentLogin.setServerIp(((StartUpCommand) cmd).getSender());

			if (!Student.getFrmJajeemProject().isVisible()) {
				StudentLogin.getLoginDialog().setVisible(true);
			} else {
				StudentLogin.getLoginDialog().setVisible(false);
			}

			Student.setCountdown(30000);
		} else if (Integer.parseInt(Config.getParam("server")) == 1
				&& cmd.getPort() == Integer.parseInt(Config
						.getParam("serverPort"))) {
			if (InstructorNoa.getDesktopPaneScroll().getDesktopMediator() != null) {
				if (InstructorNoa.getDesktopPane() != null) {
					InstructorNoaUtil.createFrame(
							InstructorNoa.getDesktopPane(),
							((StartUpCommand) cmd).getSender(),
							((StartUpCommand) cmd).getSenderName());
				}
			}
		}
	}
}
