package com.jajeem.command.handler;

import static com.jajeem.core.design.StudentLogin.getServerService;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.IntercomRequestCommand;
import com.jajeem.command.model.RequestAcceptedCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.core.design.Student;
import com.jajeem.core.design.StudentLogin;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.message.design.MessageSend;
import com.jajeem.util.CommunicationType;
import com.jajeem.util.Config;

public class RequestAcceptedCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		RequestAcceptedCommand command = (RequestAcceptedCommand) cmd;
		CommunicationType type = command.getCommunicationType();
		if (type == CommunicationType.Just_Chat) {
			MessageSend.main(new String[] { StudentLogin.getServerIp(),
					Config.getParam("serverPort") });
		} else if (type == CommunicationType.Just_Intercom) {
			try {
				if (Student.getTransmitter().isTransmitting()) {
					Student.getTransmitter().stop();
					StopIntercomCommand sic = new StopIntercomCommand(
							InetAddress.getLocalHost().getHostAddress(),
							StudentLogin.getServerIp(), Integer.parseInt(Config
									.getParam("serverPort")));
					getServerService().send(sic);
				} else {
					IntercomRequestCommand irc = new IntercomRequestCommand(
							InetAddress.getLocalHost().getHostAddress(),
							StudentLogin.getServerIp(), Integer.parseInt(Config
									.getParam("serverPort")),
							Student.getStudentModel());
					StudentLogin.getServerService().send(irc);
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
		} else if (type == CommunicationType.Both) {
			MessageSend.main(new String[] { StudentLogin.getServerIp(),
					Config.getParam("serverPort") });
			try {
				if (Student.getTransmitter().isTransmitting()) {
					Student.getTransmitter().stop();
					StopIntercomCommand sic = new StopIntercomCommand(
							InetAddress.getLocalHost().getHostAddress(),
							StudentLogin.getServerIp(), Integer.parseInt(Config
									.getParam("serverPort")));
					getServerService().send(sic);
				} else {
					IntercomRequestCommand irc = new IntercomRequestCommand(
							InetAddress.getLocalHost().getHostAddress(),
							StudentLogin.getServerIp(), Integer.parseInt(Config
									.getParam("serverPort")),
							Student.getStudentModel());
					StudentLogin.getServerService().send(irc);
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
		}
	}
}
