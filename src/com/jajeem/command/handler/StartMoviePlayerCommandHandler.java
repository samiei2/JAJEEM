package com.jajeem.command.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartMoviePlayerCommand;
import com.jajeem.command.model.StopMoviePlayerCommand;

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
			String path=System.getProperty("user.dir");
			System.out.println(System.getProperty("user.dir"));
			ProcessBuilder builder = new ProcessBuilder(path+"/util/vlcwin/vlc.exe","-vvv",command.getMrl());
			proc = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Then retreive the process output
//		InputStream in = proc.getInputStream();
//		InputStream err = proc.getErrorStream();
	}
}
