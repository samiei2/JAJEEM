package com.jajeem.util;

import com.jajeem.quiz.design.client.QuizWindow;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class ClientSession {
	private static QuizWindow quizWindowHndl;
	private static WhiteboardClient whiteboardWindowHndl;

	public static QuizWindow getQuizWindowHndl() {
		return quizWindowHndl;
	}

	public static void setQuizWindowHndl(QuizWindow quizWindowHndl) {
		ClientSession.quizWindowHndl = quizWindowHndl;
	}

	public static WhiteboardClient getWhiteboardWindowHndl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void setQuizWindowHndl(WhiteboardClient quizWindowHndl) {
		//ClientSession.quizWindowHndl = quizWindowHndl;
	}
}
