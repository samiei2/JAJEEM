package com.jajeem.survey.model;

public class CopyOfQuiz {
	
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
	
	//gettte & setters
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
}