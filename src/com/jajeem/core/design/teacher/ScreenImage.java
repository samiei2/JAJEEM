package com.jajeem.core.design.teacher;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import com.jajeem.core.design.account.CustomAccountButton;

public class ScreenImage extends JLabel{
	private BufferedImage screenImage = null;
	private static BufferedImage dummyImage = null;
	
	static{
		URL inp = ScreenImage.class.getResource("/icons/noa_en/dummyscreen.png");
		try {
			dummyImage = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ScreenImage(BufferedImage screenImage2) {
		screenImage = screenImage2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2d = g.create();
		if(screenImage!=null)
			g2d.drawImage(screenImage, 0, 0, 120, 120, this);
//		else
//			g2d.drawImage(dummyImage, 0, 0, 120, 120, this);
		g2d.dispose();
		super.paintComponent(g);
	}
}
