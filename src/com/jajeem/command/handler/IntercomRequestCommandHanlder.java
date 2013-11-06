package com.jajeem.command.handler;

import java.awt.TrayIcon.MessageType;
import java.net.InetAddress;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.IntercomRequestCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.message.design.MessageReceive;
import com.jajeem.util.Config;

public class IntercomRequestCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		String[] options = new String[] {"Yes", "No", "Maybe", "Cancel"};
		int response = WebOptionPane.showOptionDialog(
				null,
				"Confirm", 
				"Do you want to start intercom with"+((IntercomRequestCommand) cmd).getStudent().getFullName(), 
		        JOptionPane.YES_NO_CANCEL_OPTION, 
		        WebOptionPane.INFORMATION_MESSAGE,
		        null, 
		        options, 
		        options[0]);

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
			InstructorNoa.getTransmitter().start("audio");
			InstructorNoa.setTransmittingType("intercom");
			InstructorNoa.setIntercomText("Stop");

		}
	}
}
