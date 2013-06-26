package com.jajeem.command.handler;


import uk.co.caprica.vlcj.test.basic.TestPlayer;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartVideoCommand;

public class StartVideoCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartVideoCommand command = (StartVideoCommand)cmd;
		TestPlayer player = new TestPlayer(command.getStreamAddress(),command.isClient());
	}
}
