package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.core.model.Student;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Response;
import com.jajeem.survey.model.Run;

@SuppressWarnings("serial")
public class SurveyFinished extends EventObject implements Serializable{
	private Run surveyRun;
	public SurveyFinished(Object source) {
	    super(source);
	}
	
	public Run getSurveyRun() {
		return surveyRun;
	}
	public void setSurveyRun(Run surveyRun) {
		this.surveyRun = surveyRun;
	}
}
