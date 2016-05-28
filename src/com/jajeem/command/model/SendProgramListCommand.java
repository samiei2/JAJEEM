package com.jajeem.command.model;

import java.io.File;
import java.util.ArrayList;


public class SendProgramListCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8747785786677445385L;
	/**
	 * 
	 */

	/**
	 * @param exeList 
	 * @param lnkList 
	 * 
	 */
	private ArrayList<File> lnkList;
	private ArrayList<File> exeList;

	public SendProgramListCommand(String from, String to, int port, ArrayList<File> lnkList, ArrayList<File> exeList) {
		super(from, to, port);
		this.lnkList = lnkList;
		this.exeList = exeList;
	}

	public SendProgramListCommand(String hostAddress, String serverIp,
			int parseInt) {
		super(hostAddress, serverIp, parseInt);
	}

	public ArrayList<File> getLnkList() {
		return lnkList;
	}

	public void setLnkList(ArrayList<File> lnkList) {
		this.lnkList = lnkList;
	}

	public ArrayList<File> getExeList() {
		return exeList;
	}

	public void setExeList(ArrayList<File> exeList) {
		this.exeList = exeList;
	}
}
