package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.core.model.Student;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Response;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.model.Run;

@SuppressWarnings("serial")
public class SurveyResponse extends EventObject implements Serializable{
	private Student student;
	private Response response;
	private Question question;
	private Run SurveyRun;
	public SurveyResponse(Object source) {
	    super(source);
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Response getResponse() {
		return this.response;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question){
		this.question = question;
	}
	public Run getSurveyRun() {
		return SurveyRun;
	}
	public void setSurveyRun(Run SurveyRun) {
		this.SurveyRun = SurveyRun;
	}
}
