package com.jajeem.quiz.model;

import java.io.Serializable;

public class Response implements Serializable {
	
	//properties
	private int id;
	private int runId;
	private int studentId;
	private String answer;
	private boolean[] boolAnswer;
	
	//getter & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean[] getBoolAnswer() {
		return boolAnswer;
	}
	public void setBoolAnswer(boolean[] boolAnswer) {
		this.boolAnswer = boolAnswer;
	}
}
