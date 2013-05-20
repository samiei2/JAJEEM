package com.jajeem.command.model;

public class VolumeCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925835076684834400L;
	
	private String type = "";
	private int vol = 0;

	public VolumeCommand(String from, String to, int port, String type, int vol) {
		super(from, to, port);
		
		this.setType(type);
		this.setVol(vol);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVol() {
		return vol;
	}

	public void setVol(int vol) {
		this.vol = vol;
	}

}
