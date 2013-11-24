package com.jajeem.command.model;

import com.jajeem.survey.model.Run;


public class FinishedSurveyCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String server;
	private Run run;
	public FinishedSurveyCommand(String from, String to, int port) {
		super(from, to, port);
		
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
	
	public Run getRun(){
		return run;
	}

}
