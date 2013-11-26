package com.jajeem.command.model;

import java.io.File;

public class SendFileCollectCommand extends Command {

	File file;

	public SendFileCollectCommand(String from, String to, int port) {
		super(from, to, port);
		// TODO Auto-generated constructor stub
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
