package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;

public class CustomPowerButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;

	public CustomPowerButton() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/powerbutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomPowerButton(ImageIcon img) {
		super(img);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/powerbutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomPowerButton(String label, ImageIcon img) {
		super(label, img);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/powerbutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomPowerButton(String label) {
		super(label);
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/powerbutton.png");
			background = ImageIO.read(inp);
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
		super.paintComponent(g);
	}
}
