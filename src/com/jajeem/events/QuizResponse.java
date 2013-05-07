package com.jajeem.events;

import java.util.EventObject;

import com.jajeem.core.model.Student;
import com.jajeem.quiz.model.Response;
import com.jajeem.quiz.model.Quiz;

@SuppressWarnings("serial")
public class QuizResponse extends EventObject{
	private Student student;
	private Response response;
	public QuizResponse(Object source) {
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
}
