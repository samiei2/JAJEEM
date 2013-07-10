package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.quiz.model.Run;

@SuppressWarnings("serial")
public class QuizFinished extends EventObject implements Serializable{
	private Run quizRun;
	public QuizFinished(Object source) {
	    super(source);
	}
	
	public Run getQuizRun() {
		return quizRun;
	}
	public void setQuizRun(Run quizRun) {
		this.quizRun = quizRun;
	}
}
