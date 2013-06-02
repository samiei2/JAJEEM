package com.jajeem.command.handler;


import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartVideoCommand;
import com.jajeem.videoplayer.design.VideoPlayer;

public class StartVideoCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartVideoCommand command = (StartVideoCommand)cmd;
		VideoPlayer quiz = new VideoPlayer(command.getStreamAddress());
		
	}
}
