package com.jajeem.events;

import java.io.Serializable;
import java.net.Socket;
import java.util.EventObject;

public class FileTransferObject extends EventObject implements Serializable {

	double progressValue = 0;

	public FileTransferObject(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Socket clientSocket;
	private long requestNumber;
	private String fileName;

	public void setProgressValue(double i) {
		progressValue = i;
	}

	public double getProgressValue() {
		return progressValue;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
