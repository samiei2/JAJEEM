package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JViewport;

public class CustomViewport extends JViewport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Paint texture;
	BufferedImage image;

	public CustomViewport() {
		image = loadImage();

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		int w = getWidth();
		int h = getHeight();
		Rectangle2D r = new Rectangle2D.Double(0, 0, w, h);
		texture = new TexturePaint(image, r);
		g2.setPaint(texture);
		g2.fillRect(0, 0, w, h);
	}

	private BufferedImage loadImage() {
		BufferedImage image = null;
		String fileName = "C:\\Users\\Armin\\Desktop\\13\\jscrollpanebackground.png";
		try {
			// URL url = getClass().getResource(fileName);
			image = ImageIO.read(new File(fileName));
		} catch (MalformedURLException mue) {
			System.out.println("url: " + mue.getMessage());
		} catch (IOException ioe) {
			System.out.println("read: " + ioe.getMessage());
		}
		return image;
	}
}