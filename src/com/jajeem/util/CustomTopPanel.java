package com.jajeem.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alee.laf.panel.WebPanel;

public class CustomTopPanel extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	public CustomTopPanel() {
		try {
			originalImage = ImageIO.read(CustomTopPanel.class
					.getResource("/icons/noa_en/toppanel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// g2.drawImage(originalImage, 0, 0, newW, newH, null);
		super.paint(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}
}