package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.quiz.design.client.QuizWindow2;

public class StartQuizCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartQuizCommand command = (StartQuizCommand)cmd;
		QuizWindow2 quiz = new QuizWindow2(command.getQuiz());
	}

}
