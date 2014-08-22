package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.core.design.student.Student;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.util.ClientSession;

public class StartCallAllCommandHanlder implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartCallAllCommand command = (StartCallAllCommand) cmd;
		if (Student.getReceiverOnly() == null) {
			String ip = InetAddress.getLocalHost().getHostAddress().toString();
			ip = ip.substring(0, ip.lastIndexOf(".")) + ".255";
			AVReceiveOnly ar = new AVReceiveOnly("10010", ip, "5010");
			ar.initialize("audio");
			Student.setReceiverOnly(ar);
			TransparentIntercomInProgressFrame frame = new TransparentIntercomInProgressFrame();
			if (ClientSession.getStudentIntercomPanel() != null) {
				ClientSession.getStudentIntercomPanel().dispose();
			}
			ClientSession.setStudentIntercomInProgress(frame);
			frame.setVisible(true);
		} else {
			Student.getReceiverOnly().setRemoteAddr(
					InetAddress.getByName(StudentLogin.getServerIp()));
			Student.getReceiverOnly().initialize("audio");
			TransparentIntercomInProgressFrame frame = new TransparentIntercomInProgressFrame();
			if (ClientSession.getStudentIntercomPanel() != null) {
				ClientSession.getStudentIntercomPanel().dispose();
			}
			ClientSession.setStudentIntercomInProgress(frame);
			frame.setVisible(true);
		}
	}
}
