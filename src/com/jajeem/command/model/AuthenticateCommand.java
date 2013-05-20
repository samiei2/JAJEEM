package com.jajeem.command.model;

public class AuthenticateCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6190735680180412086L;
	
	private String username;
	private String password;
	private String role;

	public AuthenticateCommand(String from, String to, int port, String username, String password, String role) {
		super(from, to, port);
		
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
