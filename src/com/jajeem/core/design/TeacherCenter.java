package com.jajeem.core.design;

import javax.swing.ImageIcon;

import jrdesktop.viewer.Viewer;

import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.panel.WebPanel;

public class TeacherCenter {
	
	public static WebDesktopPane desktopPane = new WebDesktopPane ();
	
	public static WebPanel createPanel(WebPanel panel) {
        desktopPane.setOpaque ( false );
        panel.add(desktopPane);
        createFrame(desktopPane);
		return panel;
	}
	
	public static WebInternalFrame createFrame(final WebDesktopPane desktopPane) {
		final WebInternalFrame internalFrame = new WebInternalFrame(
				"Mohammad", false, false, false, true);
		
		internalFrame.setFrameIcon(new ImageIcon("icons/menubar/student.png"));

		jrdesktop.Config con = new jrdesktop.Config(false, "", "192.168.0.15", 1099, "admin", "admin", false, false);
		Viewer vnc = new Viewer(con);
 		vnc.Start(internalFrame);

		internalFrame.open();
		internalFrame.setVisible(true);
		desktopPane.add(internalFrame);

		internalFrame.setBounds(25 + 100 + 50, 50, 200, 200);
		
		return internalFrame;
	}
}
