package com.jajeem.util;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;
import com.jajeem.filemanager.InstructorServer;

public class StartUp {

	static H2Connection conn;

	public StartUp() {
		conn = new H2Connection();
		BaseDAO.setH2Connection(conn);
		initDatabase.initialize();
		if (!new File("util").exists()) {
			JOptionPane
					.showMessageDialog(null,
							"Util folder does not exist.Please contact administrator!\nShutting Down!");
			System.exit(1);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				new InstructorServer().Startup();
			}
		}).start();

		// new initServices();
		// com.jajeem.whiteboard.server.Server.WhiteboardServer.main(new
		// String[0]);

		try {
			// Setting up WebLookAndFeel style
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
