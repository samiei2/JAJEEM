package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

import com.jajeem.survey.model.Run;

public class SurveyFinished extends EventObject implements Serializable {
	private static final long serialVersionUID = 1L;
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
