package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendRecordingSuccessCommand;
import com.jajeem.util.Config;
import com.jajeem.util.Session;

public class SendRecordingSuccessCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendRecordingSuccessCommand command = (SendRecordingSuccessCommand) cmd;
		if (Config.getParam("server").equals("1")) {
			if (Session.getRecordingList().contains(command.getFrom())) {
				Session.getRecordingList().remove(command.getFrom());
			}
			JOptionPane.showMessageDialog(
					null,
					"Recording has successfuly been received from "
							+ command.getFrom());
		}
	}
}
