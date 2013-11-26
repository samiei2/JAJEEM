package com.jajeem.command.model;

import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;

public class StartSurveyCommand extends Command {

	/**
	 * 
	 */
	private Survey survey;
	private String server;
	private Run run;
	private int receivingPort;
	private static final long serialVersionUID = -692510020782073836L;

	public StartSurveyCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public void setSurvey(Survey currentSurvey) {
		survey = currentSurvey;
	}

	public Survey getSurvey() {
		return survey;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public Run getRun() {
		return run;
	}

	public void setReceivingPort(int i) {
		receivingPort = i;
	}

	public int getReceivingPort() {
		return receivingPort;
	}

}
