package com.jajeem.util;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;
import com.jajeem.util.H2Connection;

public class StartUp {
	
	static H2Connection conn;
	
	public StartUp () {
		conn = new H2Connection();
		BaseDAO.setH2Connection(conn);
		initDatabase.initialize();
		
//		new initServices();
//		com.jajeem.whiteboard.server.Server.WhiteboardServer.main(new String[0]);
		
		try
		{
		    // Setting up WebLookAndFeel style
		    UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );
		}
		catch ( Throwable e )
		{
		    // Something went wrong
		}
	}
	
}
