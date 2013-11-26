package com.jajeem.command.model;

import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;

public class StartQuizCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Quiz quiz;
	private String server;
	private Run run;
	private int receivePort;

	public StartQuizCommand(String from, String to, int port) {
		super(from, to, port);

	}

	public void setQuiz(Quiz currentQuiz) {
		quiz = currentQuiz;
	}

	public Quiz getQuiz() {
		return quiz;
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

	public void setReceivePort(int i) {
		receivePort = i;
	}

	public int getReceivePort() {
		return receivePort;
	}

}
