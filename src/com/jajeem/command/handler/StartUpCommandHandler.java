package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.InstructorNoaUtil;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.util.Config;

public class StartUpCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		new Config();

		int port = Integer.parseInt(Config.getParam("serverPort"));

		if (Integer.parseInt(Config.getParam("server")) != 1) {
			StartCaptureCommandHandler startCaptureCommandHandler = new StartCaptureCommandHandler();
			startCaptureCommandHandler.run(cmd);
			StartUpCommand cmdToSend = new StartUpCommand(InetAddress
					.getLocalHost().getHostAddress(),
					((StartUpCommand) cmd).getSender() , port, InetAddress
							.getLocalHost().getHostAddress(), System.getProperty("user.name"));
			cmdToSend.setSender(InetAddress.getLocalHost().getHostAddress());
			cmdToSend.setPort(port);
			StudentLogin.getServerService().send(cmdToSend);
			StudentLogin.setServerIp(((StartUpCommand) cmd).getSender());
			// TODO: if not visible then set visible
			Student.getFrmJajeemProject().setVisible(true);

		} else if (Integer.parseInt(Config.getParam("server")) == 1
				&& cmd.getPort() == Integer.parseInt(Config
						.getParam("serverPort"))) {
			InstructorNoaUtil.createFrame(InstructorNoa.getDesktopPane(),
					((StartUpCommand) cmd).getSender(), ((StartUpCommand) cmd).getSenderName());
		}
	}

}
