package com.jajeem.command.handler;

import java.io.File;
import java.io.IOException;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartSpeechCommand;

public class StartSpeechCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartSpeechCommand command = (StartSpeechCommand)cmd;
		Process proc = Runtime.getRuntime().exec("util/iCalabo/iCalabo.exe");
	}
	
	public static void main(String[] arg){
		ProcessBuilder pb = new ProcessBuilder(new String[]{"iCalabo.exe"});
		pb.directory(new File("util/iCalabo/"));
		try {
			pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
