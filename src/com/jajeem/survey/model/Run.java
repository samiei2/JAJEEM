package com.jajeem.survey.model;

import java.io.Serializable;
import java.util.UUID;

import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.room.model.Session;

public class Run implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//properties
	private UUID id; 
	private Instructor instructor;
	private Session session;
	private Survey survey;
	private Student student;
	private long start;
	private long end;
	
	//getter & setters
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getInstructorId() {
		return instructor.getId();
	}
	public void setInstructorId(int instructorId) {
		this.instructor.setId(instructorId);
	}
	public int getSessionId() {
		return session.getId();
	}
	public void setSessionId(int sessionId) {
		this.session.setId(sessionId);
	}
	public UUID getSurveyId() {
		return survey.getId();
	}
	public void setSurveyId(UUID surveyId) {
		this.survey.setId(surveyId);
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public Survey getSurvey(){
		return this.survey;
	}
	public void setSurvey(Survey currentSurvey) {
		this.survey = currentSurvey;
	}
	public Instructor getInstructor(){
		return this.instructor;
	}
	public void setInstructor(Instructor currentInstructor) {
		this.instructor = currentInstructor;
	}
	public Session getSession(){
		return this.session;
	}
	public void setSession(Session currentSession) {
		this.session = currentSession;
	}
	public Student getStudent(){
		return this.student;
	}
	public void setStudent(Student privateStudent) {
		this.student = privateStudent;
	}
}
