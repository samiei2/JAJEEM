package com.jajeem.filemanager.client;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new ClientServer().Startup();
			}
		}).start();
		new ClientFileManagerMain().setVisible(true);
	}

}