package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.util.KeyHook;
import com.jajeem.util.MouseHook;

public class SetLockCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		if (StudentLogin.getKeyHook() == null) {
			KeyHook keyHook = new KeyHook();
			StudentLogin.setKeyHook(keyHook);
			keyHook.setIgnoreCallback(true);
			keyHook.start();
			System.out.println("KeyHook applied.");
		} else {
			if (StudentLogin.getKeyHook().isIgnoreCallback()) {
				StudentLogin.getKeyHook().setIgnoreCallback(false);
				
				System.out.println("KeyHook removed.");
			} else {
				StudentLogin.getKeyHook().setIgnoreCallback(true);
				System.out.println("KeyHook applied.");
			}
		}

		if (StudentLogin.getMouseHook() == null) {
			MouseHook mouseHook = new MouseHook();
			StudentLogin.setMouseHook(mouseHook);
			mouseHook.setIgnoreCallback(true);
			mouseHook.start();
			System.out.println("MouseHook applied.");
		} else {
			if (StudentLogin.getMouseHook().isIgnoreCallback()) {
				StudentLogin.getMouseHook().setIgnoreCallback(false);
				System.out.println("MouseHook removed.");
			} else {
				StudentLogin.getMouseHook().setIgnoreCallback(true);
				System.out.println("MouseHook applied.");
			}
		}
	}
}
