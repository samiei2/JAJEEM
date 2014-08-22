package com.jajeem.survey.model;

import java.io.Serializable;
import java.util.UUID;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private UUID id;
	private UUID runId;
	private int studentId;
	private String answer;
	private boolean[] boolAnswer;
	private UUID surveyQuestionId;

	// getter & setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getRunId() {
		return runId;
	}

	public void setRunId(UUID runId) {
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

	public UUID getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(UUID surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}
}
