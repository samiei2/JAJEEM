package com.jajeem.command.handler;


import java.io.IOException;
import java.io.InputStream;

import uk.co.caprica.vlcj.test.basic.TestPlayer;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartVideoCommand;

public class StartVideoCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartVideoCommand command = (StartVideoCommand)cmd;
		//TestPlayer player = new TestPlayer(command.getStreamAddress(),command.isClient());
		// Run a java app in a separate system process
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec("java -jar /util/videoplayer.jar "+command.getStreamAddress()+" "+command.isClient());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Then retreive the process output
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
	}
}
