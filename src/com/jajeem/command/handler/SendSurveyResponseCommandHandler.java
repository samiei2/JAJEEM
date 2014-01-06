package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendSurveyResponseCommand;
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyResponse;
import com.jajeem.exception.JajeemExceptionHandler;

public class SendSurveyResponseCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendSurveyResponseCommand command = (SendSurveyResponseCommand) cmd;
		JajeemExceptionHandler.logMessage("Surevey Response Received from "
				+ command.getFrom());
		SurveyResponse resp = command.getEvent();
		new SurveyEvent().fireResponseEvent(resp);
	}

}
