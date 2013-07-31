package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.FinishedQuizCommand;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizFinished;
import com.jajeem.quiz.design.client.alt.Quiz_Window;

public class FinishedQuizCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		FinishedQuizCommand command = (FinishedQuizCommand)cmd;
		QuizFinished evt = new QuizFinished(this);
		evt.setQuizRun(command.getRun());
		new QuizEvent().fireQuizFinished(evt);
	}
}
