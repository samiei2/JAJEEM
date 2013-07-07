package com.jajeem.util;

import com.jajeem.core.design.StudentLogin;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;

public class Session {

	private static Student student;
	private static Instructor instructor;
	private static com.jajeem.room.model.Session session;
	private static boolean isQuizWindowOpen = false;
	private static boolean isSurveyWindowOpen = false;
	private static boolean isWhiteboardWindowOpen = false;
	
	public static void setInstructor(Instructor instructor) {
		Session.instructor = instructor;
	}
	
	public static Instructor getInstructor(){
		return Session.instructor;
	}

	public static Student getStudent() {
		return student;
	}

	public static void setStudent(Student student) {
		Session.student = student;
	}

	public static void setSession(com.jajeem.room.model.Session session) {
		Session.session = session;
	}
	
	public static com.jajeem.room.model.Session getSession(){
		return Session.session;
	}

	public static boolean isQuizWindowOpen() {
		return isQuizWindowOpen;
	}

	public static void setQuizWindowOpen(boolean isQuizWindowOpen) {
		Session.isQuizWindowOpen = isQuizWindowOpen;
	}

	public static boolean isSurveyWindowOpen() {
		return isSurveyWindowOpen;
	}

	public static void setSurveyWindowOpen(boolean isSurveyWindowOpen) {
		Session.isSurveyWindowOpen = isSurveyWindowOpen;
	}

	public static boolean isWhiteboardWindowOpen() {
		return isWhiteboardWindowOpen;
	}

	public static void setWhiteboardWindowOpen(boolean isWhiteboardWindowOpen) {
		Session.isWhiteboardWindowOpen = isWhiteboardWindowOpen;
	}

}
