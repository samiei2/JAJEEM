package com.jajeem.core.design;


import javax.swing.ImageIcon;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import jrdesktop.viewer.Viewer;

import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.panel.WebPanel;

public class TeacherCenter {

	public static WebDesktopPane desktopPane = new WebDesktopPane();

	public static WebPanel createPanel(WebPanel panel) {
		desktopPane.setOpaque(false);
		panel.add(desktopPane);

		return panel;
	}

	public static WebInternalFrame createFrame(
			final WebDesktopPane desktopPane, String host) {
		final WebInternalFrame internalFrame = new WebInternalFrame(host,
				false, false, false, true);
		
		internalFrame.putClientProperty("ip", host);

		internalFrame.setFrameIcon(new ImageIcon("icons/menubar/student.png"));

		jrdesktop.Config con = new jrdesktop.Config(false, "", host, 1099,
				"admin", "admin", false, false);
		Viewer vnc = new Viewer(con);
		vnc.Start(internalFrame);

		internalFrame.open();
		desktopPane.add(internalFrame);

		internalFrame.setBounds(0 + (desktopPane.getComponentCount() * 200), 0,
				200, 200);
		
		internalFrame.addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
				
			}
		});


		return internalFrame;
	}
}
