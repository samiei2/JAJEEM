package com.jajeem.command.handler;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendProgramListCommand;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.Config;
import com.jajeem.util.FileUtil;
import com.jajeem.util.LnkParser;
import com.jajeem.util.WinRegistry;

public class GetProgramListCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws Exception {
		try{
			String pathToStartMenu = WinRegistry
					.readString(
							WinRegistry.HKEY_LOCAL_MACHINE,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
							"Common Start Menu")
					+ "\\Programs";

			FileUtil fileUtil = new FileUtil();
			final File[] tempfileList = fileUtil
					.finder(pathToStartMenu);
			final ArrayList<File> listOfAllLinks = new ArrayList<>();
			final ArrayList<File> lnkList = new ArrayList<>();
			final ArrayList<File> exeList = new ArrayList<>();
			for (int i = 0; i < tempfileList.length; i++) {
				if (tempfileList[i].isDirectory()) {
					listOfAllLinks
							.addAll(getPath(getDirectoryContent(tempfileList[i])));
				} else {
					listOfAllLinks.add(tempfileList[i]);
				}
			}

			Collections.sort(listOfAllLinks);

			for (int i = 0; i < listOfAllLinks.size(); i++) {
				if (listOfAllLinks.get(i).getName().indexOf(".") != -1) {
					String ext = listOfAllLinks
							.get(i)
							.getName()
							.substring(
									listOfAllLinks.get(i).getName()
											.indexOf("."));
					if (ext.equals(".lnk")) {
						try {
							LnkParser parser = new LnkParser(
									listOfAllLinks.get(i));
							if (parser.getRealFilename().contains(
									".exe")) {
								lnkList.add(listOfAllLinks.get(i));
								exeList.add(new File(parser
										.getRealFilename()));
							}
						} catch (Exception e) {

						}
					}
				}
			}
			
			SendProgramListCommand sendProgramsCmd;
			try {
				sendProgramsCmd = new SendProgramListCommand(InetAddress
						.getLocalHost().getHostAddress(), StudentLogin
						.getServerIp(), Integer.parseInt(Config
						.getParam("serverPort")),lnkList,exeList);
				if(StudentLogin.getServerService()!=null)
					StudentLogin.getServerService().send(sendProgramsCmd);
			} catch (Exception e1) {
				JajeemExceptionHandler.logError(e1);
				e1.printStackTrace();
			} 
		}
		catch(Exception e){
			
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
