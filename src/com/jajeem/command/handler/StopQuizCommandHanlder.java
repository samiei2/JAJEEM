package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.util.ClientSession;

public class StopQuizCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		@SuppressWarnings("unused")
		StopQuizCommand command = (StopQuizCommand)cmd;
		Quiz_Window wndHndl = ClientSession.getQuizWindowHndl();
		if(wndHndl != null)
			wndHndl.dispose();
	}

}
