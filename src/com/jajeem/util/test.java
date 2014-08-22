package com.jajeem.util;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalIconFactory;

import org.omg.CORBA.Environment;

import com.jajeem.command.handler.RestartStudentProgramCommandHandler;
import com.jajeem.licensing.LicenseManager;

public class test {

	private static Image getImage() throws HeadlessException {

		Icon defaultIcon = MetalIconFactory.getTreeComputerIcon();

		Image img = new BufferedImage(defaultIcon.getIconWidth(),

		defaultIcon.getIconHeight(),

		BufferedImage.TYPE_4BYTE_ABGR);

		defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);

		return img;

	}

	private static PopupMenu createPopupMenu() throws

	HeadlessException {

		PopupMenu menu = new PopupMenu();

		MenuItem exit = new MenuItem("Exit");

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}

		});

		menu.add(exit);

		return menu;

	}

	public static void main(String[] args) throws Exception {
		
//		LicenseManager mgr = new LicenseManager();
//		int result = mgr.Authenticate(true);
//		System.out.println(result);
	}

}