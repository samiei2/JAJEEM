package com.jajeem.command.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.util.FileUtil;
import com.jajeem.util.WinRegistry;

public class StartApplicationCommandHanlder implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		String pathToStartMenu = WinRegistry
				.readString(
						WinRegistry.HKEY_LOCAL_MACHINE,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
						"Common Start Menu")
				+ "\\Programs";

		FileUtil fileUtil = new FileUtil();
		File[] fileList = fileUtil.finder(pathToStartMenu);

		File[] tempfileList = fileUtil.finder(pathToStartMenu);
		ArrayList<File> listOfAllLinks = new ArrayList<>();
		for (int i = 0; i < tempfileList.length; i++) {
			if (tempfileList[i].isDirectory()) {
				listOfAllLinks
						.addAll(getPath(getDirectoryContent(tempfileList[i])));
			} else {
				listOfAllLinks.add(tempfileList[i]);
			}
		}

		for (int i = 0; i < listOfAllLinks.size(); i++) {
			File file = listOfAllLinks.get(i);
			if (file.getPath().contains(
					((StartApplicationCommand) cmd).getFileName())) {

				ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
						file.getPath());
				pb.start();
				break;

			}
		}
	}

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected Collection<? extends File> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<File> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i));
		}
		return list;
	}
}
