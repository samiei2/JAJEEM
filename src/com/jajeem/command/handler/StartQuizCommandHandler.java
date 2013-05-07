package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.quiz.design.client.QuizWindow;

public class StartQuizCommandHandler implements ICommandHandler {

	public void run(String cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartQuizCommand command = (StartQuizCommand)cmd;
		QuizWindow quiz = new QuizWindow(command.getQuiz());
	}

}
