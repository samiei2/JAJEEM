package com.jajeem.command.model;

import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizResponse;

public class SendQuizResponseCommand extends Command {
	QuizResponse event;
	public SendQuizResponseCommand(String host, int port2) {
		super(host, port2);
		// TODO Auto-generated constructor stub
	}
	
	public void setEvent(QuizResponse evt){
		event = evt;
	}

	public QuizResponse getEvent() {
		return event;
	}

}
