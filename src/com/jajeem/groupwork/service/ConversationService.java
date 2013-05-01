package com.jajeem.groupwork.service;

import com.jajeem.command.model.StartCaptureCommand;
import com.jajeem.command.model.StartViewerCommand;
import com.jajeem.command.service.ServerService;

public class ConversationService implements IConversationService {

	@Override
	public void create(String leader, String[] groupList, int port) throws NumberFormatException, Exception {
		ServerService serverService = new ServerService();
		
		StartCaptureCommand leaderCmd = new StartCaptureCommand(leader, port);
		serverService.send(leaderCmd);
		
		StartViewerCommand viewCommand = new StartViewerCommand("", port, "");
		
		for(int i =0; i < groupList.length; i++) {
			viewCommand.setHost(groupList[i]);
			viewCommand.setLeader(leader);
			serverService.send(viewCommand);
		}
	}

}
