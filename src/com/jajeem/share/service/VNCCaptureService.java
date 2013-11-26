package com.jajeem.share.service;

import jrdesktop.Config;
import jrdesktop.server.Server;
import jrdesktop.viewer.ConnectionDialog;
import jrdesktop.viewer.Viewer;

public class VNCCaptureService implements ICaptureService {

	private Viewer viewer;

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
		viewer = new Viewer(viewerConfig);
		setViewer(viewer);
		viewer.Start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stopClient() {
		viewer.getRecorder().Stop();
		viewer.getRecorder().viewerGUI.dispose();
		viewer.Stop();
		viewer.destroy();
	}

	public void startClientDialog() {
		ConnectionDialog.showOptions();
	}

	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
}
