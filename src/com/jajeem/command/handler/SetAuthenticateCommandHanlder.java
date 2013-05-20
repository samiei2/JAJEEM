package com.jajeem.command.handler;

import java.sql.SQLException;

import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.core.dao.h2.InstructorDAO;
import com.jajeem.core.dao.h2.StudentDAO;

public class SetAuthenticateCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws SQLException {
		if (((AuthenticateCommand) cmd).getRole() == "instructor") {
			GrantCommand grantCommand = new GrantCommand();
			InstructorDAO instructorDAO = new InstructorDAO();
			instructorDAO.authenticate(((AuthenticateCommand) cmd).getUsername(), ((AuthenticateCommand) cmd).getPassword());
		} else {
			StudentDAO studentDAO = new StudentDAO();
			studentDAO.authenticate(((AuthenticateCommand) cmd).getUsername(), ((AuthenticateCommand) cmd).getPassword());
		}
	}
}
