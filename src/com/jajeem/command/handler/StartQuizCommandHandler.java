package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.quiz.design.client.alt.Quiz_Window;

public class StartQuizCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartQuizCommand command = (StartQuizCommand)cmd;
		Quiz_Window quiz = new Quiz_Window(command.getRun());
		quiz.setServer(command.getServer());
		quiz.setReceivePort(command.getReceivePort());
	}
}
