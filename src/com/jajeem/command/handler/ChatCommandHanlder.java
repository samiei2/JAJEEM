package com.jajeem.command.handler;

import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.message.design.Chat;
import com.jajeem.util.Config;

public class ChatCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		new Config();

		Chat currentChat = null;

		if (Integer.parseInt(Config.getParam("server")) != 1) {
			for (Chat chat : Student.getChatList()) {
				if (chat.getTo().equals(cmd.getFrom())) {
					currentChat = chat;
					currentChat.addLine(((ChatCommand) cmd).getMessage());
					break;
				}
			}
			if (currentChat == null) {
				currentChat = new Chat(cmd.getFrom(), Integer.parseInt(Config.getParam("serverPort")));
				currentChat.addLine(((ChatCommand) cmd).getMessage());
			}
			
		} else {

		}
	}
}
