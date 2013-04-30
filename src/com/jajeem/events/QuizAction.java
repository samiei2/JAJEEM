package com.jajeem.events;

import java.util.EventObject;

@SuppressWarnings("serial")
public class QuizAction extends EventObject{
	public QuizAction(Object source) {
	    super(source);
	  }
}
