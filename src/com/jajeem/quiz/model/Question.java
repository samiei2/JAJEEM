package com.jajeem.quiz.model;

import java.io.Serializable;

public class Question implements Serializable {
	
	//properties
	private int id;
	private int instructorId;
	private String title;
	private int quizId;
	private byte type;
	private int point;
	private String imagePath;
	private String url;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private boolean[] correctAnswer; //its a map.for example : false,true,false,false,false means ,1:wrong,2:right,....
	private boolean[] studentAnswer;
	private String studentTextAnswer;
	private Response response;
	
	public Question(int id2) {
		this.id = id2;
	}
	public Question() {
		
	}
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
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
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
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public String getAnswer4() {
		return answer4;
	}
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	public String getAnswer5() {
		return answer5;
	}
	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}
	public boolean[] getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(boolean[] correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public void setStudentAnswer(boolean[] bs) {
		response.setBoolAnswer(bs);
	}
	public void setStudentAnswer(String text) {
		response.setAnswer(text);
	}
	public boolean[] getStudentAnswer() {
		return response.getBoolAnswer();
	}
	public String getStudentTextAnswer() {
		return response.getAnswer();
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
}
