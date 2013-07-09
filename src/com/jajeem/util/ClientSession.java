package com.jajeem.util;

import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.survey.design.client.SurveyWindow;
//import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class ClientSession {
	private static Quiz_Window quizWindowHndl;
	private static SurveyWindow surveyWindowHndl;
//	private static WhiteboardClient whiteboardWindowHndl;

	public static Quiz_Window getQuizWindowHndl() {
		return quizWindowHndl;
	}

	public static void setQuizWindowHndl(Quiz_Window quizWindowHndl) {
		ClientSession.quizWindowHndl = quizWindowHndl;
	}

//	public static WhiteboardClient getWhiteboardWindowHndl() {
//		return whiteboardWindowHndl;
//	}
//	
//	public static void setQuizWindowHndl(WhiteboardClient wbWindowHndl) {
//		whiteboardWindowHndl = wbWindowHndl;
//	}

	public static void setSurveyWindowHndl(SurveyWindow surveyWindow) {
		surveyWindowHndl = surveyWindow;
	}

	public static SurveyWindow getSurveyWindowHndl() {
		return surveyWindowHndl;
	}
}
