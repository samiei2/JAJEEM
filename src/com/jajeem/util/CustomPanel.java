package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alee.laf.panel.WebPanel;

public class CustomPanel extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	public CustomPanel(String Uri) {
		try {
			originalImage = ImageIO.read(CustomPanel.class.getResource(Uri));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}
}