package com.jajeem.command.model;

public class WebsiteCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 919800256781526114L;
	private String URL;
	
	public WebsiteCommand(String host, int port2, String URL) {
		super(host, port2);
		this.URL = URL;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

}
