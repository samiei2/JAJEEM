package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.core.model.Student;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Response;
import com.jajeem.quiz.model.Run;

public class QuizResponse extends EventObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Student student;
	private Response response;
	private Question question;
	private Run quizRun;
	private int receivingPort;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question){
		this.question = question;
	}
	public Run getQuizRun() {
		return quizRun;
	}
	public void setQuizRun(Run quizRun) {
		this.quizRun = quizRun;
	}
	public void setListeningPort(int listenPort) {
		receivingPort = listenPort;
	}
	public int getListeningPort(){
		return receivingPort;
	}
}
