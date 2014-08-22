package com.jajeem.command.model;

public class StartViewerCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4275059783011679327L;
	private String leader;

	public StartViewerCommand(String from, String to, int port, String leader) {
		super(from, to, port);
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

}
