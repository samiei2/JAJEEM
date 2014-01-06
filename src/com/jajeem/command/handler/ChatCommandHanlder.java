package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Config;

public class ChatCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		new Config();

		Chat currentChat = null;

		if (Integer.parseInt(Config.getParam("server")) != 1) {

			for (Chat chat : Student.getChatList()) {
				if (chat.getGroupId() == ((ChatCommand) cmd).getGroupId()) {
					currentChat = chat;
					currentChat.addLine(((ChatCommand) cmd).getMessage());
					currentChat.scrollDown();
					currentChat.setVisible(true);
					break;
				}
			}
//
//			if(ClientSession.getInstructorChatWindow()!=null && cmd.getFrom().equals(StudentLogin.getServerIp())){
//				currentChat = ClientSession.getInstructorChatWindow();
//				currentChat.addLine(((ChatCommand) cmd).getMessage());
//				Student.getChatList().add(currentChat);
//				currentChat.scrollDown();
//				currentChat.setVisible(true);
//				ClientSession.getInstructorChatWindow().toFront();
//			}
			
			if (currentChat == null) {
				currentChat = new Chat(String.valueOf(((ChatCommand) cmd)
						.getGroupId()), Integer.parseInt(Config
						.getParam("serverPort")),
						((ChatCommand) cmd).isMutli(),
						((ChatCommand) cmd).getGroupId(), "Chat");
				currentChat.addLine(((ChatCommand) cmd).getMessage());
				Student.getChatList().add(currentChat);
				currentChat.setVisible(true);
				currentChat.scrollDown();
			}
		} else {
			if (!InstructorNoa.getChatList().isEmpty()) {
				if (((ChatCommand) cmd).getGroupId() > -1) {
					for (Chat chat : InstructorNoa.getChatList()) {
						if (chat.getGroupId() == ((ChatCommand) cmd)
								.getGroupId()) {
							currentChat = chat;
							currentChat.addLine(((ChatCommand) cmd)
									.getMessage());
							currentChat.scrollDown();
							currentChat.setVisible(true);

							// broadcast this message to other group members
							ChatCommand chatCommand = null;
							Group group = InstructorNoa.getGroups().get(
									chat.getGroupId());
							chatCommand = new ChatCommand(InetAddress
									.getLocalHost().getHostAddress(), "",
									Integer.parseInt(Config.getParam("port")),
									((ChatCommand) cmd).getMessage(),
									((ChatCommand) cmd).isMutli(),
									group.getId());
							for (String studentIp : group.getStudentIps()) {
								if (studentIp.equals(((ChatCommand) cmd)
										.getFrom())) {
									continue; // do not send message to himself!
								} else {
									chatCommand.setTo(studentIp);
									InstructorNoa.getServerService().send(
											chatCommand);
								}
							}

							break;
						}
					}
				} else {
					for (Chat chat : InstructorNoa.getChatList()) {
						if (chat.getTo().equals(cmd.getFrom())) {
							currentChat = chat;
							currentChat.addLine(((ChatCommand) cmd)
									.getMessage());
							currentChat.scrollDown();
							currentChat.setVisible(true);
							break;
						}
					}
				}
			} else {
				if (currentChat == null) {
					currentChat = new Chat(cmd.getFrom(),
							Integer.parseInt(Config.getParam("port")),
							((ChatCommand) cmd).isMutli(),
							((ChatCommand) cmd).getGroupId(), cmd.getFrom());
					currentChat.addLine(((ChatCommand) cmd).getMessage());
					InstructorNoa.getChatList().add(currentChat);
					currentChat.setVisible(true);
					currentChat.scrollDown();
				}
			}
		}
	}
}