package com.jajeem.quiz.model;

import java.util.ArrayList;

public class Quiz {
	
	//properties
	private int id;
	private int instructorId;
	private String title;
	private String category;
	private String description;
	private int points;
	private int pointing;
	private int time;
	private byte shuffle;
	private Response response;
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
}
