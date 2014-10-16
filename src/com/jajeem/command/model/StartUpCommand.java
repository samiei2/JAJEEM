package com.jajeem.command.model;

import com.jajeem.core.design.teacher.ScreenImageContainer;

public class StartUpCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2287770406725442741L;

	private String sender;
	private String senderName;
	private ScreenImageContainer screenImage;

	public StartUpCommand(String from, String to, int port, String sender,
			String senderName) {
		super(from, to, port);

		this.sender = sender;
		this.senderName = senderName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public ScreenImageContainer getScreenImage() {
		return screenImage;
	}

	public void setScreenImage(ScreenImageContainer screenImage) {
		this.screenImage = screenImage;
	}
}
