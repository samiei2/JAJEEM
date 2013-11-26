package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;

public class StopModelCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.getVncViewer() == null) {

			Student.getSendOnly().stop();
			return;
		} else {
			Student.getVncViewer().stopClient();
			Student.getVncViewer().getViewer().getRecorder().viewerGUI
					.dispose();
			Student.getReceiverOnly().close();
		}
	}
}