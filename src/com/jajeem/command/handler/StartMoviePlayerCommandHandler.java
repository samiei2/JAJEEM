package com.jajeem.command.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

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
			System.out.println("Mrl: "+command.getMrl());
			proc = Runtime.getRuntime().exec(new String[]{
					Paths.get(".").toAbsolutePath().normalize().toString()+"/util/vlcwin/vlc.exe",command.getMrl()});
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Then retreive the process output
//		InputStream in = proc.getInputStream();
//		InputStream err = proc.getErrorStream();
	}
}
