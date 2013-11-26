package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

public class SurveyStop extends EventObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public SurveyStop(Object source) {
		super(source);
	}

}
