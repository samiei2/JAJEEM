package com.jajeem.command.model;

public class StopCaptureCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8518931025197523620L;

	public StopCaptureCommand(String from, String to, int port) {
		super(from, to, port);
	}
}
