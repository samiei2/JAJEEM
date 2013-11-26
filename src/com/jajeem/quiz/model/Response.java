package com.jajeem.quiz.model;

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
	private boolean answerValid = false;
	private UUID quizQuestionId;

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

	public boolean isAnswerValid() {
		return answerValid;
	}

	public void setAnswerValid(boolean answerValid) {
		this.answerValid = answerValid;
	}

	public UUID getQuizQuestionId() {
		return quizQuestionId;
	}

	public void setQuizQuestionId(UUID quizQuestionId) {
		this.quizQuestionId = quizQuestionId;
	}
}
