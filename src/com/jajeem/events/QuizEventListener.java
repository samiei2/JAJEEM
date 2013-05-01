package com.jajeem.events;

import java.util.EventListener;

public interface QuizEventListener extends EventListener{
	public void eventOccured(QuizAction e);
}
