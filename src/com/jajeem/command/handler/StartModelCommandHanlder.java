package com.jajeem.command.handler;

import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.core.design.Student;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;

public class StartModelCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (InetAddress.getLocalHost().getHostAddress().equals(((StartModelCommand) cmd).getLeader())) {

			// i am the supreme leader!
			return;
		} else {
			jrdesktop.Config conf = null;
			try {
				conf = new jrdesktop.Config(false, "",
						((StartModelCommand) cmd).getLeader(),
						Integer.parseInt(Config
								.getParam("vncPort")),
						"admin", "admin", false, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			VNCCaptureService vnc = new VNCCaptureService();
			vnc.startClient(conf);
			vnc.getViewer().getRecorder().setViewOnly(true);
			Student.setVncViewer(vnc);
		}
	}
}