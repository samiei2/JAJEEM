package com.jajeem.survey.model;

import java.io.Serializable;
import java.util.UUID;

public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private UUID id;
	private int instructorId = -1;
	private UUID surveyId;
	private String title = "";
	private byte type;
	private String imagePath = "";
	private String url = "";
	private String answer1 = "";
	private String answer2 = "";
	private String answer3 = "";
	private String answer4 = "";
	private String answer5 = "";
	private Response response = new Response();

	public Question(Question toCopy) {
		this.id = toCopy.id;
		this.instructorId = toCopy.instructorId;
		this.title = toCopy.title;
		this.surveyId = toCopy.surveyId;
		this.type = toCopy.type;
		this.imagePath = toCopy.imagePath;
		this.url = toCopy.url;
		this.answer1 = toCopy.answer1;
		this.answer2 = toCopy.answer2;
		this.answer3 = toCopy.answer3;
		this.answer4 = toCopy.answer4;
		this.answer5 = toCopy.answer5;

		if (toCopy.getResponse().getBoolAnswer() != null) {
			this.response.setBoolAnswer(new boolean[] {
					toCopy.getResponse().getBoolAnswer()[0],
					toCopy.getResponse().getBoolAnswer()[1],
					toCopy.getResponse().getBoolAnswer()[2],
					toCopy.getResponse().getBoolAnswer()[3],
					toCopy.getResponse().getBoolAnswer()[4] });
		}
		this.response.setAnswer(toCopy.getResponse().getAnswer());
		this.response = new Response();
		this.response.setAnswer(toCopy.response.getAnswer());
		this.response.setBoolAnswer(toCopy.response.getBoolAnswer());
		this.response.setId(toCopy.response.getId());
		this.response.setRunId(toCopy.response.getRunId());
		this.response.setStudentId(toCopy.response.getStudentId());
	}

	public Question() {
		// TODO Auto-generated constructor stub
	}

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

	public UUID getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(UUID surveyId) {
		this.surveyId = surveyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
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
