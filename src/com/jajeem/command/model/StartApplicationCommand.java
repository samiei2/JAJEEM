package com.jajeem.command.model;

public class StartApplicationCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058750792408215170L;

	private String fileName;

	public StartApplicationCommand(String from, String to, int port,
			String fileName) {
		super(from, to, port);

		setFileName(fileName);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
