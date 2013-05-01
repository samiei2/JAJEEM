package com.jajeem.share.service;

import jrdesktop.Config;
import jrdesktop.server.*;
import jrdesktop.viewer.ConnectionDialog;
import jrdesktop.viewer.Viewer;

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
	public void startClient(Config viewerConfig) {
		new Viewer(viewerConfig).Start();
	}

	@Override
	public void stopClient() {
		
	}
	
	public void startClientDialog() {
		ConnectionDialog.showOptions();
	}
}
