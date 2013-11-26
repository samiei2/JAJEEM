package com.jajeem.command.model;

public class SendFileAssignmentCommand extends Command {

	String file;
	private String time;

	public SendFileAssignmentCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
