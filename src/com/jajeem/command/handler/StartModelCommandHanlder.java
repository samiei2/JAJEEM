package com.jajeem.command.handler;

import java.net.InetAddress;

import javax.swing.WindowConstants;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.core.design.Student;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;

public class StartModelCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (InetAddress.getLocalHost().getHostAddress()
				.equals(((StartModelCommand) cmd).getLeader())) {

			// i am the supreme leader!//@A:liiidi!
			if (Student.getSendOnly() == null) {
				AVSendOnly as = new AVSendOnly("5010", "224.5.6.7", "10010");
				Student.setSendOnly(as);
				as.start("audio");
			} else {
				Student.getSendOnly().start("audio");
			}

			return;
		} else {

			if (Student.getReceiverOnly() == null) {
				AVReceiveOnly ar = new AVReceiveOnly("10010",
						((StartModelCommand) cmd).getLeader(), "5010");
				ar.initialize("audio");
				Student.setReceiverOnly(ar);
			} else {
				Student.getReceiverOnly().setRemoteAddr(
						InetAddress.getByName(((StartModelCommand) cmd)
								.getLeader()));
				Student.getReceiverOnly().initialize("audio");
			}

			jrdesktop.Config conf = null;
			try {
				conf = new jrdesktop.Config(false, "",
						((StartModelCommand) cmd).getLeader(),
						Integer.parseInt(Config.getParam("vncPort")), "admin",
						"admin", false, false);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Student.getVncViewer() == null) {
				VNCCaptureService vnc = new VNCCaptureService();
				vnc.startClient(conf);
				vnc.getViewer().getRecorder().setViewOnly(true);
				vnc.getViewer().getRecorder().viewerGUI
						.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				Student.setVncViewer(vnc);
			} else {
				Student.getVncViewer().getViewer().getRecorder()
						.setViewOnly(true);
				Student.getVncViewer().getViewer().getRecorder().viewerGUI
						.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				Student.getVncViewer().startClient(conf);
			}
		}
	}
}