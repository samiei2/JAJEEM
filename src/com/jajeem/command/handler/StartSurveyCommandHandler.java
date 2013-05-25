package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.survey.design.client.SurveyWindow;

public class StartSurveyCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartSurveyCommand command = (StartSurveyCommand)cmd;
		SurveyWindow quiz = new SurveyWindow(command.getSurvey());
		quiz.setServer(command.getServer());
	}

}
