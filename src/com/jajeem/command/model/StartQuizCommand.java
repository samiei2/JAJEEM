package com.jajeem.command.model;

import com.jajeem.quiz.model.Quiz;

public class StartQuizCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014963068838321817L;
	private Quiz quiz;
	private String server;
	public StartQuizCommand(String host, int port2) {
		super(host, port2);
		
	}

	public void setQuiz(Quiz currentQuiz) {
		quiz = currentQuiz;
	}
	
	public Quiz getQuiz(){
		return quiz;
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

}
