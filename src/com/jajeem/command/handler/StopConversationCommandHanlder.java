package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartConversationCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StopConversationCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Config;
import com.jajeem.util.i18n;

public class StopConversationCommandHanlder implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopConversationCommand command = (StopConversationCommand) cmd;
		String ipTo = command.getConversationTo();
		StopIntercomCommand intercomCommand = new StopIntercomCommand(
				InetAddress.getLocalHost().getHostAddress(), ipTo,
				Integer.parseInt(Config.getParam("port")));
		if(StudentLogin.getServerService()!=null){
			if(Student.getConversationtransmitter().isTransmitting()){
				Student.getConversationtransmitter().stop();
				StudentLogin.getServerService().send(intercomCommand);
				if (ClientSession.getStudentIntercomPanel() != null) {
					ClientSession.getStudentIntercomPanel().dispose();
				}
			}
		}
	}
}
