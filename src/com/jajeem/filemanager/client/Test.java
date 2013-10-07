package com.jajeem.filemanager.client;

import com.jajeem.util.Audio;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Audio.playSound("util/Ding.aiff");
				//new ClientFileServer().Startup();
			}
		}).start();
		//new ClientFileManagerMain().setVisible(true);
	}

}
