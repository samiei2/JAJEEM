package com.jajeem.util;

import java.io.File;

import javax.swing.UIManager;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.alee.laf.WebLookAndFeel;
import com.alee.utils.SystemUtils;
import com.jajeem.filemanager.InstructorServer;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class StartUp {
	
	static H2Connection conn;
	
	public StartUp () {
		conn = new H2Connection();
		BaseDAO.setH2Connection(conn);
		initDatabase.initialize();
		if(!new File("util").exists())
			Unzipper.unzip("util.zip");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new InstructorServer().Startup();
			}
		}).start();
		
//		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
//				"util/windows/");
//		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
//				"util/windows-64/");
//		Native.loadLibrary("jnawtrenderer",Empty.class);
//		
//		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//		System.out.println("Library is : " + path + "util/windows/jnawtrenderer");
//		
////		System.loadLibrary("jnawtrenderer");
		
		
//		
//		try {
//			Unzipper.unzipToSystem64("winx64.zip");
//			Unzipper.unzipToSystem32("winx64.zip");
//			if(Config.getParam("OS").equals("Windows"))
//				if(Config.getParam("Bitness").equals("X64"))
//					;
//				else
//					;
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
		
		
//		new initServices();
		com.jajeem.whiteboard.server.Server.WhiteboardServer.main(new String[0]);
		
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
