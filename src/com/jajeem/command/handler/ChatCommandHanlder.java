package com.jajeem.command.handler;

import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.Student;
import com.jajeem.message.design.Chat;
import com.jajeem.util.Config;

public class ChatCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		new Config();

		Chat currentChat = null;

		if (Integer.parseInt(Config.getParam("server")) != 1) {
			if (!Student.getChatList().isEmpty()) {
				for (Chat chat : Student.getChatList()) {
					if (chat.getTo().equals(cmd.getFrom())) {
						currentChat = chat;
						currentChat.addLine(((ChatCommand) cmd).getMessage());
						currentChat.scrollDown();
						currentChat.setVisible(true);
						break;
					}
				}
			} else {
				if (currentChat == null) {
					currentChat = new Chat(cmd.getFrom(),
							Integer.parseInt(Config.getParam("serverPort")),
							((ChatCommand) cmd).isMutli());
					currentChat.addLine(((ChatCommand) cmd).getMessage());
					Student.getChatList().add(currentChat);
					currentChat.setVisible(true);
					currentChat.scrollDown();
				}
			}

		} else {
			if (!InstructorNoa.getChatList().isEmpty()) {
				for (Chat chat : InstructorNoa.getChatList()) {
					if (chat.getTo().equals(cmd.getFrom())) {
						currentChat = chat;
						currentChat.addLine(((ChatCommand) cmd).getMessage());
						currentChat.scrollDown();
						currentChat.setVisible(true);
						break;
					}
				}
			} else {
				if (currentChat == null) {
					currentChat = new Chat(cmd.getFrom(),
							Integer.parseInt(Config.getParam("port")),
							((ChatCommand) cmd).isMutli());
					currentChat.addLine(((ChatCommand) cmd).getMessage());
					InstructorNoa.getChatList().add(currentChat);
					currentChat.setVisible(true);
					currentChat.scrollDown();
				}
			}
		}
	}
}