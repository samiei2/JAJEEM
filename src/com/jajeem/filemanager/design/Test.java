package com.jajeem.filemanager.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.jajeem.events.FileTransferEvent;
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
