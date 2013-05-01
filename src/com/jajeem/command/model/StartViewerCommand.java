package com.jajeem.command.model;

public class StartViewerCommand extends Command {
	
	private String leader;
	
	public StartViewerCommand(String host, int port, String leader) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}


}
