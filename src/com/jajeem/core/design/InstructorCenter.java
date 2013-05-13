package com.jajeem.core.design;


import javax.swing.ImageIcon;

import jrdesktop.viewer.Viewer;

import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.panel.WebPanel;
import com.jajeem.util.Config;

public class InstructorCenter {

	public static WebDesktopPane desktopPane = new WebDesktopPane();

	public static WebPanel createPanel(WebPanel panel) {
		desktopPane.setOpaque(false);
		panel.add(desktopPane);

		return panel;
	}

	public static WebInternalFrame createFrame(
			final WebDesktopPane desktopPane, String host) throws NumberFormatException, Exception {
		final WebInternalFrame internalFrame = new WebInternalFrame(host,
				false, false, false, true);
		
		internalFrame.putClientProperty("ip", host);

		internalFrame.setFrameIcon(new ImageIcon("icons/menubar/student.png"));

		jrdesktop.Config con = new jrdesktop.Config(false, "", host, Integer.parseInt(Config.getParam("vncPort")),
				"admin", "admin", false, false);
		Viewer vnc = new Viewer(con);
		vnc.StartThumbs(internalFrame);

		internalFrame.open();
		desktopPane.add(internalFrame);

		internalFrame.setBounds(0 + (desktopPane.getComponentCount() * 200), 0,
				200, 200);
		
		return internalFrame;
	}
}
