package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.PowerCommand;

public class SetPowerCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		String command = "";

		switch (((PowerCommand) cmd).getType()) {
		case "turnOff":
			command = "shutdown -s";
			break;
		case "logOff":
			command = "shutdown -l";
			break;
		case "restart":
			command = "shutdown -r";
			break;
		default:
			break;
		}

		Process child = Runtime.getRuntime().exec(command);
	}
}
