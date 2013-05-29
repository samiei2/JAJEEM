package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.whiteboard.client.Client.StudentWhiteboard;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class StartWhiteBoardCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartWhiteBoardCommand command = (StartWhiteBoardCommand)cmd;
		StudentWhiteboard whiteboard = new StudentWhiteboard(command.getFrom());
		whiteboard.Join(command.getSessionID());
	}

}
