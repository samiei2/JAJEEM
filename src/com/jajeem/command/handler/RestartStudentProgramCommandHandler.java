package com.jajeem.command.handler;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import com.jajeem.command.model.Command;
import com.jajeem.util.Config;

public class RestartStudentProgramCommandHandler implements ICommandHandler {

	static Object lock = new Object();
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		synchronized(lock){
			if (Integer.parseInt(Config.getParam("server")) != 1) {
				try{
					File currentLocation = new File(RestartStudentProgramCommandHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					ArrayList<String> commands = new ArrayList<>();
					commands.add(currentLocation.getParent()+"\\run.exe");
					ProcessBuilder builder = new ProcessBuilder(commands);
					builder.start();
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Program restart failure!\nPlease close program through Task Manager and start again.");
				}
				System.exit(0);
			}
		}
	}
}
