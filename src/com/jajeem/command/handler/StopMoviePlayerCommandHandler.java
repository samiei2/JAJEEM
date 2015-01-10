package com.jajeem.command.handler;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import com.jajeem.command.model.Command;

public class StopMoviePlayerCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
//		StopMoviePlayerCommand command = (StopMoviePlayerCommand) cmd;
//		ProcessBuilder builder = new ProcessBuilder("taskkill", "/IM", "vlc.exe" );
//		builder.start();
		try{
			String line = "taskkill"+" /IM "+"vlc.exe";
			CommandLine cmdLine = CommandLine.parse(line);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			int exitValue = executor.execute(cmdLine);
		}
		catch(Exception e){
			
		}
	}
}
