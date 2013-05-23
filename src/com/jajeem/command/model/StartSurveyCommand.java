package com.jajeem.command.model;

import com.jajeem.survey.model.Survey;

public class StartSurveyCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -692510020782073836L;

	public StartSurveyCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public void setSurvey(Survey currentSurvey) {
		// TODO Auto-generated method stub
		
	}

	public void setServer(String hostAddress) {
		// TODO Auto-generated method stub
		
	}

}
