package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopMoviePlayerCommand;

public class StopMoviePlayerCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopMoviePlayerCommand command = (StopMoviePlayerCommand) cmd;
		ProcessBuilder builder = new ProcessBuilder("taskkill", "/IM", "vlc.exe" );
		builder.start();
	}
}
