package com.jajeem.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class CustomScrollbar extends MetalScrollBarUI {
	private Image imageThumb, imageTrack;
	private JButton b = new JButton() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(0, 0);
		}

	};

	CustomScrollbar() {
		try {
			imageThumb = ImageIO.read(new File(
					"C:\\Users\\Armin\\Desktop\\kar\\scrollthumb.png"));
			imageTrack = ImageIO.read(new File(
					"C:\\Users\\Armin\\Desktop\\kar\\scrolltrack.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		// g.setColor(Color.blue);
		((Graphics2D) g).drawImage(imageThumb, r.x, r.y, r.width, r.height,
				null);
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		((Graphics2D) g).drawImage(imageTrack, r.x, r.y, r.width, r.height,
				null);
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return b;
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return b;
	}
}
