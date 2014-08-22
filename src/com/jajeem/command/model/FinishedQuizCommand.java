package com.jajeem.command.model;

import com.jajeem.quiz.model.Run;

public class FinishedQuizCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014963068838321817L;
	private String server;
	private Run run;

	public FinishedQuizCommand(String from, String to, int port) {
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

	public Run getRun() {
		return run;
	}

}
