package com.jajeem.command.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartWhiteBoardCommand;

public class StartWhiteBoardCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		final StartWhiteBoardCommand command = (StartWhiteBoardCommand) cmd;
		// StudentWhiteboard whiteboard = new
		// StudentWhiteboard(command.getFrom());
		// whiteboard.Join(command.getSessionID());

		new Thread(new Runnable() {

			@Override
			public void run() {
				String userName;
				if (com.jajeem.util.Session.getStudent() != null) {
					userName = com.jajeem.util.Session.getStudent()
							.getFullName();
				} else {
					userName = "Anonymous";
				}
				if (userName == "" || userName == null) {
					userName = "Anonymous";
				}
				try {
					// wait();
					String runCommand = "java -jar WhiteboardStudent.jar "
							+ command.getFrom() + " "
							+ command.getSessionPort() + " "
							+ command.getWhiteboardPort() + " \"" + userName
							+ "\" 0";
					final Process proc = Runtime.getRuntime().exec(runCommand,
							null, new File("util/"));
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								BufferedReader in = new BufferedReader(
										new InputStreamReader(
												proc.getInputStream()));
								String line = null;
								while ((line = in.readLine()) != null) {
									System.out.println(line);
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
