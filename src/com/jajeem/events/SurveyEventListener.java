package com.jajeem.events;

import java.util.EventListener;

public interface SurveyEventListener extends EventListener {
	public void questionAnswered(SurveyResponse e);

	public void surveyStoped(SurveyStop e);

	public void surveyFinished(SurveyFinished e);

}
