package com.jajeem.command.model;

import com.jajeem.quiz.model.Quiz;

public class StartQuizCommand extends Command {
	private Quiz quiz;
	public StartQuizCommand(String host, int port2) {
		super(host, port2);
		
	}

	public void setQuiz(Quiz currentQuiz) {
		quiz = currentQuiz;
	}
	
	public Quiz getQuiz(){
		return quiz;
	}
	
	

}
