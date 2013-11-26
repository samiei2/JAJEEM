package com.jajeem.command.model;

public class WhiteBlackAppCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5292913947835977475L;
	private String appName;
	private boolean black;

	public WhiteBlackAppCommand(String from, String to, int port,
			String appName, boolean black) {
		super(from, to, port);
		this.black = black;
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

}
