package com.jajeem.command.handler;

import com.jajeem.share.service.*;

public class StartCaptureCommandHandler implements ICommandHandler {

	@Override
	public void run(String cmd) {
		VNCCaptureService vnc = new VNCCaptureService();
		vnc.startServer();
		
	}

}
