package com.jajeem.core.design.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CustomMainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	public CustomMainPanel(String imageURI) {
		try {
			originalImage = ImageIO.read(CustomMainPanel.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}

	@Override
	public int getWidth() {
		int w = super.getWidth();
		return w;
	}

	@Override
	public int getHeight() {
		int h = super.getHeight();
		return h;
	}
}