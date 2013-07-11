package com.jajeem.command.handler;

import java.net.InetAddress;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.util.Config;

public class IntercomRequestCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		int response = WebOptionPane.showConfirmDialog(
				InstructorNoa.getCenterPanel(),
				"Do you want to start intercom with" + cmd.getFrom(), "Confirm",
				WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE);

		if (response == 0) {// yes!
			if (InstructorNoa.getTransmitter().isTransmitting()) {
				InstructorNoa.getTransmitter().stop();
				StopIntercomCommand si;
				si = new StopIntercomCommand(InetAddress.getLocalHost()
						.getHostAddress(), InstructorNoa.getTransmitter()
						.getRemoteAddr().getHostAddress(),
						Integer.parseInt(Config.getParam("port")));
				InstructorNoa.getServerService().send(si);
			}
			
			StartIntercomCommand startI = new StartIntercomCommand(InetAddress
					.getLocalHost().getHostAddress(), cmd.getFrom(),
					Integer.parseInt(Config.getParam("port")));
			InstructorNoa.getServerService().send(startI);
			InstructorNoa.getTransmitter().setRemoteAddr(
					InetAddress.getByName(cmd.getFrom()));
			InstructorNoa.getTransmitter().start();
			InstructorNoa.setIntercomText("Stop");

		}
	}
}
