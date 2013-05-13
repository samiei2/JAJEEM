package com.jajeem.util;

import com.jajeem.quiz.design.client.QuizWindow;

public class ClientSession {
	private static QuizWindow quizWindowHndl;

	public static QuizWindow getQuizWindowHndl() {
		return quizWindowHndl;
	}

	public static void setQuizWindowHndl(QuizWindow quizWindowHndl) {
		ClientSession.quizWindowHndl = quizWindowHndl;
	}
}
