package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.message.design.MessageReceive;

public class MessageCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		@SuppressWarnings("unused")
		MessageReceive messageDialog = new MessageReceive(((MessageCommand) cmd).getMessage());
	}

}
