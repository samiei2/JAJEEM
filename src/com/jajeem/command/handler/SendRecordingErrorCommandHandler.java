package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendRecordingErrorCommand;
import com.jajeem.util.Config;
import com.jajeem.util.Session;

public class SendRecordingErrorCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendRecordingErrorCommand command = (SendRecordingErrorCommand) cmd;
		if (Config.getParam("server").equals("1")) {
			if (Session.getRecordingList().contains(command.getFrom())) {
				Session.getRecordingList().remove(command.getFrom());
			}
			JOptionPane
					.showMessageDialog(
							null,
							"An error occured while recording the student.please try again!\nIf the problem persists please contact your administrator.");
		}
	}
}
