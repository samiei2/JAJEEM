package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alee.laf.text.WebTextField;

public class CustomTextField extends WebTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;

	public CustomTextField(String imageURI) {
		try {
			background = ImageIO.read(test.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// setUndecorated(false);
	}

	@Override
	public void paintComponents(Graphics g) {

		super.paintComponents(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = g.create();
		if (background != null) {
			g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		}
		g2.dispose();
	}
}
