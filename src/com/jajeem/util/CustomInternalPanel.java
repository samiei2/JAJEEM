package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JInternalFrame;

public class CustomInternalPanel extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;

	public CustomInternalPanel(String frameName, boolean b, boolean c,
			boolean d, boolean e) {
		super(frameName, b, c, d, e);
		init();
	}

	public void init() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/new/jinternalpanel.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			g2.drawImage(background, 3, 0, getWidth(), getHeight(), this);
		}
		g2.dispose();
		// super.paintComponent(g);
	}

	@Override
	public void paintComponents(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			g2.drawImage(background, 3, 0, getWidth(), getHeight(), this);
		}
		g2.dispose();
		// super.paintComponents(g);
	}
}
