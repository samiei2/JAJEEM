package com.jajeem.quiz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Quiz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private UUID id;
	private int instructorId;
	private int courseId;
	private String title = "";
	private String category = "";
	private String description = "";
	private int points;
	private int pointing;
	private int time;
	private byte shuffle;
	private Response response = new Response();
	private ArrayList<Question> questions = new ArrayList<>();

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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPointing() {
		return pointing;
	}

	public void setPointing(int pointing) {
		this.pointing = pointing;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public byte getShuffle() {
		return shuffle;
	}

	public void setShuffle(byte shuffle) {
		this.shuffle = shuffle;
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}
