package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVTransmit2;
import org.jitsi.service.libjitsi.LibJitsi;
import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartConversationCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Config;

public class StartConversationCommandHanlder implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartConversationCommand command = (StartConversationCommand) cmd;
		String ipTo = command.getConversationTo();
		StartIntercomCommand intercomCommand = new StartIntercomCommand(
				InetAddress.getLocalHost().getHostAddress(), ipTo,
				Integer.parseInt(Config.getParam("port")));
		if(StudentLogin.getServerService()!=null){
			LibJitsi.start();
			
			StudentLogin.getServerService().send(intercomCommand);
			Student.getConversationtransmitter().setRemoteAddr(InetAddress.getByName(ipTo));
			Student.getConversationtransmitter().start("audio");
			
			TransparentIntercomInProgressFrame frame = new TransparentIntercomInProgressFrame();
			if (ClientSession.getStudentIntercomPanel() != null) {
				ClientSession.getStudentIntercomPanel().dispose();
			}
			ClientSession.setStudentIntercomInProgress(frame);
			frame.setVisible(true);
		}
	}
}
