package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.quiz.model.Run;

public class QuizFinished extends EventObject implements Serializable {
	private Run quizRun;
	private static final long serialVersionUID = 1L;

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
