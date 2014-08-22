package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;
import com.jajeem.core.design.teacher.InstructorNoaUtil;

public class CustomCallAllButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;
	BufferedImage backgroundactive;

	public CustomCallAllButton() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/topbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/topbuttonactive.png");
			backgroundactive = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomCallAllButton(ImageIcon img) {
		super(img);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/topbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/topbuttonactive.png");
			backgroundactive = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomCallAllButton(String label, ImageIcon img) {
		super(label, img);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/topbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/topbuttonactive.png");
			backgroundactive = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomCallAllButton(String label) {
		super(label);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/topbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/topbuttonactive.png");
			backgroundactive = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Graphics g2 = g.create();
		// if(background != null){
		// g2.drawImage(background, 0, 0, this);
		// }
		// g2.dispose();
		// super.paintComponent(g);

		Graphics g2 = g.create();
		if (InstructorNoaUtil.getCallAllActive()) {
			g2.drawImage(backgroundactive, 0, 0, getWidth(), getHeight(), null);
		} else {
			g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		}
		g2.dispose();
		super.paintComponent(g);
	}
}
