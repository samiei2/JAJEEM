package com.jajeem.events;

import java.util.EventObject;

import com.jajeem.quiz.model.Quiz;

@SuppressWarnings("serial")
public class QuizResponse extends EventObject{
	private Quiz quiz;
	public QuizResponse(Object source) {
	    super(source);
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
}
