package com.jajeem.command.model;

import java.io.File;

public class SendSpeechFileCommand extends Command{

	String file;
	public SendSpeechFileCommand(String from, String to, int port) {
		super(from, to, port);
	}
	
	public void setFile(String file){
		this.file = file;
	}
	
	public String getFile(){
		return file;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
