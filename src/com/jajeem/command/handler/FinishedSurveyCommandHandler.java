package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.FinishedSurveyCommand;
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyFinished;

public class FinishedSurveyCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		FinishedSurveyCommand command = (FinishedSurveyCommand) cmd;
		SurveyFinished evt = new SurveyFinished(this);
		evt.setSurveyRun(command.getRun());
		new SurveyEvent().fireSurveyFinished(evt);
	}
}
