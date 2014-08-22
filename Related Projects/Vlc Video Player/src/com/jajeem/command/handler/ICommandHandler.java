package com.jajeem.command.handler;

import com.jajeem.command.model.Command;

public interface ICommandHandler {

	void run(Command cmd) throws NumberFormatException, Exception;
}
