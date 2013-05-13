package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.quiz.design.client.QuizWindow;
import com.jajeem.util.ClientSession;

public class StopQuizCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopQuizCommand command = (StopQuizCommand)cmd;
		QuizWindow wndHndl = ClientSession.getQuizWindowHndl();
		if(wndHndl != null)
			wndHndl.dispose();
	}

}
