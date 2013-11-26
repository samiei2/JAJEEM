package com.jajeem.events;

import java.util.EventListener;
import java.util.UUID;

public interface QuizEventListener extends EventListener {
	public void questionAnswered(QuizResponse e);

	public void quizStoped(QuizStop e);

	public void quizFinished(QuizFinished e);

	public UUID getQuizId();
}
