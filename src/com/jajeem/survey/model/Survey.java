package com.jajeem.survey.model;

import java.util.ArrayList;

import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Response;

public class Survey {
	
	//properties
	private int id;
	private int instructorId;
	private String title;
	private String category;
	private String description;
	private Response response = new Response();
	private ArrayList<Question> questions = new ArrayList<>();
	
	//getter & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	public ArrayList<Question> getQuestionList() {
		return questions;
	}
}
