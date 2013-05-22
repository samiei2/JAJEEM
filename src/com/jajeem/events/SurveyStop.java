package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

@SuppressWarnings("serial")
public class SurveyStop extends EventObject implements Serializable{

	public SurveyStop(Object source) {
		super(source);
	}

}
