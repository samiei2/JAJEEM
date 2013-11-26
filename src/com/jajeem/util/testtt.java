package com.jajeem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JInternalFrame;
import javax.swing.plaf.InternalFrameUI;

import com.alee.laf.desktoppane.WebInternalFrame;
import com.sun.corba.se.impl.orbutil.graph.Graph;

public class testtt extends JFrame{
	public testtt() {
		
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JInternalFrame internalFrame = new JInternalFrame("jlk");
		internalFrame.setBounds(10, 11, 182, 160);
		desktopPane.add(internalFrame);
		
		WebInternalFrame webInternalFrame = new WebInternalFrame();
		webInternalFrame.setBounds(202, 11, 222, 160);
		desktopPane.add(webInternalFrame);
		internalFrame.setVisible(true);
	}
}

class CustomInterFrame extends JInternalFrame{
	BufferedImage background;
	
	public CustomInterFrame() {
		try {
			background = ImageIO.read(getClass().getResource("/icons/noa_en/new/jinternalpanel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
		Graphics g2 = g.create();
		g2.drawImage(background, 0, 0, getWidth(), getHeight(),null);
		g2.dispose();
	}
}

class MyInternalFrameUI extends InternalFrameUI{
	
}