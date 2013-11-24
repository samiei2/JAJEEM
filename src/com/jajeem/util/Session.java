package com.jajeem.util;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.room.model.Course;

public class Session {

	private static Student student;
	private static Instructor instructor;
//	private static com.jajeem.room.model.Session session;
	private static boolean isQuizWindowOpen = false;
	private static boolean isSurveyWindowOpen = false;
	private static boolean isWhiteboardWindowOpen = false;
	private static ArrayList<Object> fileRequestList = new ArrayList<>();
//	private static Map<int, boolean> s = 
	private static boolean[] isQuizWindowsOpen = new boolean[15];
	private static boolean[] isSurveyWindowsOpen = new boolean[15];
	private static boolean[] isWhiteboardWindowsOpen = new boolean[15];
	private static ArrayList<String> recordingList = new ArrayList<>();
	private static HashMap<String,String> loggedInStudents = new HashMap();
	private static Course course;
	
	
	
	public static void setInstructor(Instructor instructor) {
		Session.instructor = instructor;
	}
	
	public static Instructor getInstructor(){
		return Session.instructor;
	}

	public static Student getStudent() {
		if(student == null || student.getFullName().equals("")){
			Student temp = new Student();
			temp.setFullName(System.getProperty("user.name"));
		}
		return student;
	}

	public static void setStudent(Student student) {
		Session.student = student;
	}

//	public static void setSession(com.jajeem.room.model.Session session) {
//		Session.session = session;
//	}
//	
//	public static com.jajeem.room.model.Session getSession(){
//		return Session.session;
//	}

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

	public static ArrayList<Object> getFileRequestList() {
		return fileRequestList;
	}

	public static void setFileRequestList(ArrayList<Object> fileRequestList) {
		Session.fileRequestList = fileRequestList;
	}

	public static boolean[] getIsQuizWindowsOpen() {
		return isQuizWindowsOpen;
	}

	public static void setIsQuizWindowsOpen(boolean[] isQuizWindowsOpen) {
		Session.isQuizWindowsOpen = isQuizWindowsOpen;
	}

	public static boolean[] getIsSurveyWindowsOpen() {
		return isSurveyWindowsOpen;
	}

	public static void setIsSurveyWindowsOpen(boolean[] isSurveyWindowsOpen) {
		Session.isSurveyWindowsOpen = isSurveyWindowsOpen;
	}

	public static boolean[] getIsWhiteboardWindowsOpen() {
		return isWhiteboardWindowsOpen;
	}

	public static void setIsWhiteboardWindowsOpen(
			boolean[] isWhiteboardWindowsOpen) {
		Session.isWhiteboardWindowsOpen = isWhiteboardWindowsOpen;
	}

	public static ArrayList<String> getRecordingList() {
		return recordingList;
	}

	public static Course getCourse() {
		return Session.course;
	}

	public static void setCourse(Course temp){
		Session.course = temp;
	}

	public static HashMap<String,String> getLoggedInStudents() {
		return loggedInStudents;
	}

	public static void setLoggedInStudents(HashMap<String,String> loggedInStudents) {
		Session.loggedInStudents = loggedInStudents;
	}
}
