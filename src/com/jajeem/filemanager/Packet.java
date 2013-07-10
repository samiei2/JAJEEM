package com.jajeem.filemanager;

import java.io.Serializable;

public class Packet implements Serializable{
	private static final long serialVersionUID = 1L;
	String fileName = "";
	byte[] bytes;

	public void setFileName(String name) {
		fileName = name;
	}
	
	public String getFileName(){
		return fileName;
	}

	public void setBytes(byte[] b) {
		bytes = b;
	}
	
	public byte[] getBytes(){
		return bytes;
	}
	
}
