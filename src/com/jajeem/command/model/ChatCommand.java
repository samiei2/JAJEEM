package com.jajeem.command.model;

public class ChatCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private boolean mutli = false;
	private int groupId = -1;

	public ChatCommand(String from, String to, int port, String msg,
			boolean multi, int groupId) {
		super(from, to, port);

		this.setMessage(msg);
		setMutli(multi);
		setGroupId(groupId);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isMutli() {
		return mutli;
	}

	public void setMutli(boolean mutli) {
		this.mutli = mutli;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}