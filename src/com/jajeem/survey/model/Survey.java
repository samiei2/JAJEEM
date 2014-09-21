package com.jajeem.survey.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import com.jajeem.room.model.Course;

public class Survey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private UUID id;
	private int instructorId = -1;
	private String title = "";
	private String category = "";
	private String description = "";
	private Response response = new Response();
	private ArrayList<Question> questions = new ArrayList<>();
	private Course course = new Course();

	// getter & setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
