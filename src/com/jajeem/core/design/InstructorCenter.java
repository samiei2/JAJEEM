package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import jrdesktop.viewer.Viewer;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.toolbar.WebToolBar;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.util.Config;

public class InstructorCenter {

	public static WebDesktopPane desktopPane = new WebDesktopPane();

	public static WebPanel createPanel(WebPanel panel) {
		desktopPane.setOpaque(false);
		panel.add(desktopPane);

		return panel;
	}

	/**
	 * Adds a new internal frame to desktop panel for a new student
	 * 
	 * @param desktopPane
	 *            WebDesktopPane
	 * @param hostIp
	 *            String
	 * @param hostName
	 *            String
	 */
	public static WebInternalFrame createFrame(
			final WebDesktopPane desktopPane, String hostIp, String hostName)
			throws NumberFormatException, Exception {
		final WebInternalFrame internalFrame = new WebInternalFrame(hostName,
				false, false, false, true);

		jrdesktop.Config con = new jrdesktop.Config(false, "", hostIp,
				Integer.parseInt(Config.getParam("vncPort")), "admin", "admin",
				false, false);
		final Viewer vnc = new Viewer(con);

		// get current list of students, if some one is new, add him/her
		JInternalFrame[] frames = desktopPane.getAllFrames();
		List<String> listOfStudents = new ArrayList<String>();
		for (JInternalFrame frame : frames) {
			listOfStudents.add((String) frame.getClientProperty("ip"));
		}

		if (listOfStudents.contains(hostIp)) {
			return null;
		}

		internalFrame.putClientProperty("ip", hostIp);
		internalFrame.putClientProperty("username", hostName);

		internalFrame.setFrameIcon(new ImageIcon(ImageIO
				.read(InstructorCenter.class
						.getResourceAsStream("/icons/menubar/student.png"))));
		vnc.StartThumbs(internalFrame);

		internalFrame.open();
		desktopPane.add(internalFrame);

		internalFrame.setBounds(0 + (desktopPane.getComponentCount() * 200), 0,
				200, 200);

		final WebToolBar toolBar = new WebToolBar();
		toolBar.setRound(0);
		toolBar.setFloatable(false);
		WebPanel panel = new WebPanel();
//		 panel.add(toolBar);

		internalFrame.getContentPane().add(panel, BorderLayout.SOUTH);

		final WebCheckBox checkBox = new WebCheckBox("");
		checkBox.setRolloverDarkBorderOnly(true);
		checkBox.setRound(0);
		checkBox.setShadeWidth(0);
		toolBar.add(checkBox);
		internalFrame.revalidate();
		toolBar.setVisible(true);
		panel.setVisible(true);

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

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorCenter.class
									.getResourceAsStream("/icons/menubar/student.png"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkBox.setSelected(false);
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

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorCenter.class
									.getResourceAsStream("/icons/menubar/tick.png"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkBox.setSelected(true);
			}
		});

		return internalFrame;
	}
}
