package com.jajeem.quiz.model;

import java.io.Serializable;

import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.room.model.Session;

public class Run implements Serializable{
	
	//properties
	private int id; 
	private Instructor instructor;
	private Session session;
	private Quiz quiz;
	private Student student;
	private int score;
	private long start;
	private long end;
	
	//getter & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getQuizId() {
		return quiz.getId();
	}
	public void setQuizId(int quizId) {
		this.quiz.setId(quizId);
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
	public int getStudentId() {
		return student.getId();
	}
	public void setStudentId(int studentId) {
		this.student.setId(studentId);
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
}
