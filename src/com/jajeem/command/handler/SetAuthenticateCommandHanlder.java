package com.jajeem.command.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.dao.h2.StudentDAO;
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

		StudentDAO studentDAO = new StudentDAO();
		grant = studentDAO.authenticate(
				((AuthenticateCommand) cmd).getUsername(),
				((AuthenticateCommand) cmd).getPassword());
		
		grantCommand.setGranted(grant);
		
		ServerService serverService = new ServerService();
		serverService.send(grantCommand);
	}
}
