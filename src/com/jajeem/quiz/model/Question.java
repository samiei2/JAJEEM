package com.jajeem.quiz.model;

import java.io.Serializable;

public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private int id = -1;
	private int instructorId = -1;
	private String title = "";
	private int quizId = -1;
	private byte type = 0;
	private int point = 0;
	private String imagePath = "";
	private String url = "";
	private String answer1 = "";
	private String answer2 = "";
	private String answer3 = "";
	private String answer4 = "";
	private String answer5 = "";
	private boolean[] correctAnswer = new boolean[] { false, false, false,
			false, false }; // its a map.for example :
							// false,true,false,false,false means
							// ,1:wrong,2:right,....
	private boolean[] studentAnswer;
	private String studentTextAnswer;
	private boolean validResponse = false;
	private Response response = new Response();

	public Question(int id2) {
		this.id = id2;
	}

	public Question() {

	}

	public Question(Question toCopy) {
		this.id = toCopy.id;
		this.instructorId = toCopy.instructorId;
		this.title = toCopy.title;
		this.quizId = toCopy.quizId;
		this.type = toCopy.type;
		this.point = toCopy.point;
		this.imagePath = toCopy.imagePath;
		this.url = toCopy.url;
		this.answer1 = toCopy.answer1;
		this.answer2 = toCopy.answer2;
		this.answer3 = toCopy.answer3;
		this.answer4 = toCopy.answer4;
		this.answer5 = toCopy.answer5;
		
		this.correctAnswer = new boolean[] { toCopy.correctAnswer[0],
				toCopy.correctAnswer[1], toCopy.correctAnswer[2],
				toCopy.correctAnswer[3], toCopy.correctAnswer[4] };
		if(toCopy.studentAnswer != null)
		this.studentAnswer = new boolean[] { toCopy.studentAnswer[0],
				toCopy.studentAnswer[1], toCopy.studentAnswer[2],
				toCopy.studentAnswer[3], toCopy.studentAnswer[4] };
		this.studentTextAnswer = toCopy.studentTextAnswer;
		this.validResponse = toCopy.validResponse;
		this.response = new Response();
		this.response.setAnswer(toCopy.response.getAnswer());
		this.response.setAnswerValid(toCopy.response.isAnswerValid());
		this.response.setBoolAnswer(toCopy.response.getBoolAnswer());
		this.response.setId(toCopy.response.getId());
		this.response.setRunId(toCopy.response.getRunId());
		this.response.setStudentId(toCopy.response.getStudentId());
	}

	// getter & setters
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

	public boolean isResponseValid() {
		return response.isAnswerValid();
	}

	public void setValidResponse(boolean validResponse) {
		this.response.setAnswerValid(validResponse);
		;
	}
}
