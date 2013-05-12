package com.jajeem.command.model;

public class LockCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5914316878590345225L;
	
	private boolean lock;

	public LockCommand(String host, int port2) {
		super(host, port2);
		
		this.lock = false;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

}
