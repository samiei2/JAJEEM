package com.jajeem.share.service;

import jrdesktop.server.*;
import jrdesktop.viewer.ConnectionDialog;

public class VNCCaptureService implements ICaptureService {

	@Override
	public void startServer() {
		Server.Start();
		
	}

	@Override
	public void stopServer() {
		Server.Stop();
		
	}

	@Override
	public void startClient() {
		ConnectionDialog.showOptions();		
	}

	@Override
	public void stopClient() {
		
	}

}
