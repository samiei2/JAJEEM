package com.jajeem.command.model;

public class StopMoviePlayerCommand extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Process vlcProc;

	public StopMoviePlayerCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public static void setProcess(Process proc) {
		vlcProc = proc;
	}

	public static Process getProcess() {
		return vlcProc;
	}
}
