package com.jajeem.filemanager.design;

import com.jajeem.filemanager.InstructorServer;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new InstructorServer().Startup();
			}
		}).start();
		new FileManagerMain().setVisible(true);

	}

}
