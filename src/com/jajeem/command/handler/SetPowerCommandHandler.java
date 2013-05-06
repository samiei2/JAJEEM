package com.jajeem.command.handler;

import com.jajeem.command.model.Command;

public class SetPowerCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		String shutdownCmd = "shutdown -l";
		Process child = Runtime.getRuntime().exec(shutdownCmd);

		/*
		 * -i Display GUI interface, must be the first option
		 * -l Log off (cannot be used with -m option)
		 * -r Shutdown and restart the computer
		 * -m \computername (Remote computer to shutdown/restart/abort)
		 * -t xx Set timeout for shutdown to xx seconds
		 * -c "comment" Shutdown comment (maximum of 127 characters)
		 */

	}
}
