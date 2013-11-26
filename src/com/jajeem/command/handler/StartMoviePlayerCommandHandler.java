package com.jajeem.command.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartMoviePlayerCommand;

public class StartMoviePlayerCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartMoviePlayerCommand command = (StartMoviePlayerCommand) cmd;
		// TestPlayer player = new
		// TestPlayer(command.getStreamAddress(),command.isClient());
		// Run a java app in a separate system process
		Process proc = null;
		try {
			proc = Runtime.getRuntime()
					.exec("java -jar videoplayer.jar "
							+ command.getStreamAddress() + " "
							+ command.isClient(), null, new File("util/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Then retreive the process output
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
	}
}
