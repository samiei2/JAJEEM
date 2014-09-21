package com.jajeem.command.model;

public class AuthenticateCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6190735680180412086L;

	private String username;
	private char[] password;

	public AuthenticateCommand(String from, String to, int port,
			String username, char[] password) {
		super(from, to, port);

		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password2) {
		this.password = password2;
	}
}
