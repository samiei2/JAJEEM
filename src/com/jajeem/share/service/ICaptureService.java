package com.jajeem.share.service;

import jrdesktop.Config;

public interface ICaptureService {

	void startServer();
	void stopServer();
	void stopClient();
	void startClient(Config viewerConfig);
}
