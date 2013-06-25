package com.jajeem.command.handler;

import javaQuery.core.RKey;
import javaQuery.core.jqReg;
import javaQuery.core.keyType;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.util.WinRegistry;

public class SetWhiteBlackAppCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		
		jqReg _jr = new jqReg();
		String message = _jr
				.jqReg(RKey.HKEY_CURRENT_USER,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer",
						keyType.Dword, "DisallowRun", "00000001");
		String cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
		String cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
		Process process1 = Runtime.getRuntime().exec(cmdToRefresh1);
		Process process2 = Runtime.getRuntime().exec(cmdToRefresh2);
		
		if (((WhiteBlackAppCommand) cmd).isBlack()) {
			WinRegistry
					.createKey(WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun");

			WinRegistry
					.writeStringValue(
							WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer",
							"DisallowRun", "1");
			WinRegistry
					.writeStringValue(
							WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun",
							((WhiteBlackAppCommand) cmd).getAppName(),
							((WhiteBlackAppCommand) cmd).getAppName());
			cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
			cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
			process1 = Runtime.getRuntime().exec(cmdToRefresh1);
			process2 = Runtime.getRuntime().exec(cmdToRefresh2);
		} else {
			WinRegistry
					.deleteValue(
							WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun",
							((WhiteBlackAppCommand) cmd).getAppName());
			cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
			cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
			process1 = Runtime.getRuntime().exec(cmdToRefresh1);
			process2 = Runtime.getRuntime().exec(cmdToRefresh2);
		}

	}

}
