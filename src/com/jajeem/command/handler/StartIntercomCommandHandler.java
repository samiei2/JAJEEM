package com.jajeem.command.handler;

import java.net.InetAddress;

import org.jitsi.service.libjitsi.LibJitsi;
import org.jitsi.util.swing.TransparentIntercomInProgressFrame;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.student.Student;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.ClientSession;

public class StartIntercomCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		// We need three parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVReceive2
		// -Drun.example.arg.line="--local-port-base=10000 --remote-host=129.130.131.132 --remote-port-base=5000"

		try {

			LibJitsi.start();

			Student.getTransmitter().setRemoteAddr(
					InetAddress.getByName(cmd.getFrom()));
			Student.getTransmitter().start("audio");
			// Student.setIntercomButtonStop();
			TransparentIntercomInProgressFrame frame = new TransparentIntercomInProgressFrame();
			if (ClientSession.getStudentIntercomPanel() != null) {
				ClientSession.getStudentIntercomPanel().dispose();
			}
			ClientSession.setStudentIntercomInProgress(frame);
			frame.setVisible(true);
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

}
