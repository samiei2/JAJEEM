package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.survey.design.client.SurveyWindow;
import com.jajeem.util.ClientSession;

public class StopSurveyCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		@SuppressWarnings("unused")
		StopSurveyCommand command = (StopSurveyCommand)cmd;
		SurveyWindow wndHndl = ClientSession.getSurveyWindowHndl();
		if(wndHndl != null)
			wndHndl.dispose();
	}

}
