package com.jajeem.events;

import java.util.EventListener;

public interface QuizEventListener extends EventListener{
	public void questionAnswered(QuizResponse e);
	public void quizStoped(QuizStop e);
	public void quizFinished(QuizFinished e);
}
