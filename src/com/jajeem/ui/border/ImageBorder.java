package com.jajeem.ui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.border.AbstractBorder;

public class ImageBorder extends AbstractBorder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Component component;
	BufferedImage borderImage;
	Insets inset;

	public ImageBorder(Insets m, String uri, Component c) throws IOException {
		component = c;
		borderImage = ImageIO.read(ImageBorder.class.getResource(uri));
		inset = m;
	}
	
	public ImageBorder(Insets m, String uri, Component c,int stroke) throws IOException {
		component = c;
		borderImage = ImageIO.read(ImageBorder.class.getResource(uri));
		inset = m;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		// super.paintBorder(c, g, x, y, width, height);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(borderImage, x, y, width, height, c);
		g2d.dispose();
	}
}
