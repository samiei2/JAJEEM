package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.survey.design.client.alt.Survey_Window;

public class StartSurveyCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartSurveyCommand command = (StartSurveyCommand) cmd;
		Survey_Window survey = new Survey_Window(command.getRun());
		survey.setServer(command.getServer());
		survey.setReceivePort(command.getReceivingPort());
	}

}
