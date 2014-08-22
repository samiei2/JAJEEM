package com.jajeem.command.handler;

import jrdesktop.Config;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartViewerCommand;
import com.jajeem.share.service.VNCCaptureService;

public class StartViewerCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {

		Config conf = new Config(false, "",
				((StartViewerCommand) cmd).getLeader(), cmd.getPort(), "admin",
				"admin", false, false);
		VNCCaptureService vnc = new VNCCaptureService();
		vnc.startClient(conf);
	}

}
