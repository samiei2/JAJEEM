package com.jajeem.events;

import java.io.Serializable;
import java.util.EventObject;

@SuppressWarnings("serial")
public class QuizStop extends EventObject implements Serializable{

	public QuizStop(Object source) {
		super(source);
	}

}
