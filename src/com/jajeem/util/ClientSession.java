package com.jajeem.util;

import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.survey.design.client.SurveyWindow;
//import com.jajeem.whiteboard.client.Client.WhiteboardClient;
import com.jajeem.survey.design.client.alt.Survey_Window;

public class ClientSession {
	private static Quiz_Window quizWindowHndl;
	private static Survey_Window surveyWindowHndl;
	private static SurveyWindow surveyWindowHndl1;
	private static CaptureScreenToFile recorder;
	private static String recordingFileName;
	private static String recordingServerAddress;
//	private static WhiteboardClient whiteboardWindowHndl;

	public static Quiz_Window getQuizWindowHndl() {
		return quizWindowHndl;
	}

	public static void setQuizWindowHndl(Quiz_Window quizWindowHndl) {
		ClientSession.quizWindowHndl = quizWindowHndl;
	}

	public static void setSurvey_WindowHndl(Survey_Window surveyWindow) {
		surveyWindowHndl = surveyWindow;
	}

	public static Survey_Window getSurvey_WindowHndl() {
		return surveyWindowHndl;
	}

	public static SurveyWindow getSurveyWindowHndl() {
		return surveyWindowHndl1;
	}

	public static void setSurveyWindowHndl(SurveyWindow surveyWindowHndl1) {
		ClientSession.surveyWindowHndl1 = surveyWindowHndl1;
	}

	public static CaptureScreenToFile getRecorder() {
		return recorder;
	}

	public static void setRecorder(CaptureScreenToFile recorder) {
		ClientSession.recorder = recorder;
	}
	

	public static void setRecordedFileName(String fileName) {
		recordingFileName = fileName;
	}
	
	public static String getRecordedFileName(){
		return recordingFileName;
	}

	public static void setReturnRecordedFileServer(String from) {
		recordingServerAddress = from;
	}
	
	public static String getReturnRecordedFileServer(){
		return recordingServerAddress;
	}

}
