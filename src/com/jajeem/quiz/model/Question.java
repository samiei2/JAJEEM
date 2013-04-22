package com.jajeem.quiz.model;

public class Question {
	
	//properties
	private int id;
	private int instructorId;
	private String title;
	private int quizId;
	private byte type;
	private byte point;
	private String imagePath;
	private String url;
	private String asnwer1;
	private String asnwer2;
	private String asnwer3;
	private String asnwer4;
	private String asnwer5;
	
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
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getPoint() {
		return point;
	}
	public void setPoint(byte point) {
		this.point = point;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAsnwer1() {
		return asnwer1;
	}
	public void setAsnwer1(String asnwer1) {
		this.asnwer1 = asnwer1;
	}
	public String getAsnwer2() {
		return asnwer2;
	}
	public void setAsnwer2(String asnwer2) {
		this.asnwer2 = asnwer2;
	}
	public String getAsnwer3() {
		return asnwer3;
	}
	public void setAsnwer3(String asnwer3) {
		this.asnwer3 = asnwer3;
	}
	public String getAsnwer4() {
		return asnwer4;
	}
	public void setAsnwer4(String asnwer4) {
		this.asnwer4 = asnwer4;
	}
	public String getAsnwer5() {
		return asnwer5;
	}
	public void setAsnwer5(String asnwer5) {
		this.asnwer5 = asnwer5;
	}
}
