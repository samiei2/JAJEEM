package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

public class QuizStop extends EventObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public QuizStop(Object source) {
		super(source);
	}

}
