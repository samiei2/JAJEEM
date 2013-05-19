package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorCenter;
import com.jajeem.util.Config;

public class StartUpCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		new Config();

		int port = Integer.parseInt(Config.getParam("serverPort"));

		if (Integer.parseInt(Config.getParam("server")) != 1) {
			StartCaptureCommandHandler startCaptureCommandHandler = new StartCaptureCommandHandler();
			startCaptureCommandHandler.run(cmd);
			ServerService serverService = new ServerService();
			StartUpCommand cmdToSend = new StartUpCommand(
					((StartUpCommand) cmd).getSender(), port, InetAddress
							.getLocalHost().getHostAddress(), System.getProperty("user.name"));
			cmdToSend.setSender(InetAddress.getLocalHost().getHostAddress());
			cmdToSend.setPort(port);
			serverService.send(cmdToSend);

		} else if (Integer.parseInt(Config.getParam("server")) == 1
				&& cmd.getPort() == Integer.parseInt(Config
						.getParam("serverPort"))) {
			InstructorCenter.createFrame(InstructorCenter.desktopPane,
					((StartUpCommand) cmd).getSender(), ((StartUpCommand) cmd).getSenderName());
		}
	}

}
