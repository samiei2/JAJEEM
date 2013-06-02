package com.jajeem.util;

import com.jajeem.quiz.design.client.QuizWindow;
import com.jajeem.survey.design.client.SurveyWindow;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class ClientSession {
	private static QuizWindow quizWindowHndl;
	private static SurveyWindow surveyWindowHndl;
	private static WhiteboardClient whiteboardWindowHndl;

	public static QuizWindow getQuizWindowHndl() {
		return quizWindowHndl;
	}

	public static void setQuizWindowHndl(QuizWindow quizWindowHndl) {
		ClientSession.quizWindowHndl = quizWindowHndl;
	}

	public static WhiteboardClient getWhiteboardWindowHndl() {
		return whiteboardWindowHndl;
	}
	
	public static void setQuizWindowHndl(WhiteboardClient wbWindowHndl) {
		whiteboardWindowHndl = wbWindowHndl;
	}

	public static void setSurveyWindowHndl(SurveyWindow surveyWindow) {
		surveyWindowHndl = surveyWindow;
	}

	public static SurveyWindow getSurveyWindowHndl() {
		return surveyWindowHndl;
	}
}
