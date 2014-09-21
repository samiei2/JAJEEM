package com.jajeem.command.model;

public class StopConversationCommand extends Command{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String conversationToIP="",conversationFromIP="";
	public StopConversationCommand(String from, String to, int port) {
		super(from, to, port);
	}

	public String getConversationTo() {
		return conversationToIP;
	}
	
	public void setConversationTo(String ipTo){
		conversationToIP = ipTo;
	}
}
