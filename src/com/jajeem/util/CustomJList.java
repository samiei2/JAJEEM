package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.alee.laf.list.WebList;

public class CustomJList extends WebList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;

	public CustomJList() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/jscrollpanebackground.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		if (background != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
			g2d.dispose();
		}
		super.paintComponent(g);
	}
}
