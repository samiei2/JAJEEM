package com.jajeem.command.model;

import com.jajeem.events.QuizResponse;

public class SendQuizResponseCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5868787236750407407L;
	QuizResponse event;
	public SendQuizResponseCommand(String from, String to, int port) {
		super(from, to,  port);
		// TODO Auto-generated constructor stub
	}
	
	public void setEvent(QuizResponse evt){
		event = evt;
	}

	public QuizResponse getEvent() {
		return event;
	}

}
