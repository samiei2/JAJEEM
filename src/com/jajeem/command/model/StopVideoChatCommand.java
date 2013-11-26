package com.jajeem.command.model;

public class StopVideoChatCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376902458256471791L;

	public StopVideoChatCommand(String from, String to, int port) {
		super(from, to, port);
	}

}
