package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.share.service.VNCCaptureService;


public class StartCaptureCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {
		
		VNCCaptureService vnc = new VNCCaptureService();
		vnc.startServer();
	}

}
