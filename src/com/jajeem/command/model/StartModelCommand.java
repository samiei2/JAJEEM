package com.jajeem.command.model;

public class StartModelCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410671719479931631L;
	private String leader;

	public StartModelCommand(String from, String to, int port, String leader) {
		super(from, to, port);

		this.leader = leader;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
}
