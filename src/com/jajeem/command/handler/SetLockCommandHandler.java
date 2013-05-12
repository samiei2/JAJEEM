package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.LockCommand;
import com.jajeem.util.KeyboardBlocker;
import com.jajeem.util.MouseBlocker;

public class SetLockCommandHandler implements ICommandHandler {

	private static MouseBlocker mouseBlocker = new MouseBlocker();
	private static KeyboardBlocker keyboardBlocker = new KeyboardBlocker();

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (((LockCommand) cmd).isLock()) {
			mouseBlocker.stop();
			keyboardBlocker.stop();

		} else {
				mouseBlocker.start();
				keyboardBlocker.start();
			}
		}
	}
