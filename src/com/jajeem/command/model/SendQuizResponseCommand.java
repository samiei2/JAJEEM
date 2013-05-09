package com.jajeem.command.model;

import com.jajeem.events.QuizResponse;

public class SendQuizResponseCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5868787236750407407L;
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
