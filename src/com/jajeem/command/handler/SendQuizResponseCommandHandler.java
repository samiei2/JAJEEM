package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendQuizResponseCommand;
import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizResponse;

public class SendQuizResponseCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendQuizResponseCommand command = (SendQuizResponseCommand) cmd;
		QuizResponse resp = command.getEvent();
		new QuizEvent().fireResponseEvent(resp);
	}
}
