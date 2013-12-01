package com.jajeem.util;

import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.core.model.Student;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.survey.design.client.alt.Survey_Window;

//import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class ClientSession {
	private static Quiz_Window quizWindowHndl;
	private static Survey_Window surveyWindowHndl;
	private static CaptureScreenToFile recorder;
	private static String recordingFileName;
	private static String recordingServerAddress;
	private static TransparentIntercomInProgressFrame studentIntercomPanel;
	private static Student currentStudent;

	// private static WhiteboardClient whiteboardWindowHndl;

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

	public static CaptureScreenToFile getRecorder() {
		return recorder;
	}

	public static void setRecorder(CaptureScreenToFile recorder) {
		ClientSession.recorder = recorder;
	}

	public static void setRecordedFileName(String fileName) {
		recordingFileName = fileName;
	}

	public static String getRecordedFileName() {
		return recordingFileName;
	}

	public static void setReturnRecordedFileServer(String from) {
		recordingServerAddress = from;
	}

	public static String getReturnRecordedFileServer() {
		return recordingServerAddress;
	}

	public static TransparentIntercomInProgressFrame getStudentIntercomPanel() {
		return studentIntercomPanel;
	}

	public static void setStudentIntercomInProgress(
			TransparentIntercomInProgressFrame frame) {
		studentIntercomPanel = frame;
	}

	public static Student getCurrentStudent() {
		return currentStudent;
	}

	public static void setCurrentStudent(Student currentStudent) {
		ClientSession.currentStudent = currentStudent;
	}

}
