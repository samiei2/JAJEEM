package com.jajeem.command.model;

import com.jajeem.events.SurveyResponse;

public class SendSurveyResponseCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SurveyResponse event;

	public SendSurveyResponseCommand(String from, String to, int port) {
		super(from, to, port);
		// TODO Auto-generated constructor stub
	}

	public void setEvent(SurveyResponse evt) {
		event = evt;
	}

	public SurveyResponse getEvent() {
		return event;
	}

}
