package com.jajeem.command.handler;

import java.io.File;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.util.FileUtil;
import com.jajeem.util.WinRegistry;

public class StartApplicationCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		String pathToStartMenu = WinRegistry
				.readString(
						WinRegistry.HKEY_LOCAL_MACHINE,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
						"Common Start Menu")
				+ "\\Programs";

		FileUtil fileUtil = new FileUtil();
		final File[] fileList = fileUtil.finder(pathToStartMenu);

		for (int i = 0; i < fileList.length; i++) {
			File file = fileList[i];
			if (file.getName().indexOf(".") != -1) {
				if (file.getName().substring(0, file.getName().length() - 4)
						.equals(((StartApplicationCommand) cmd).getFileName())) {
					ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
							file.getPath());
					pb.start();
					break;
				}
			}
		}
	}
}
