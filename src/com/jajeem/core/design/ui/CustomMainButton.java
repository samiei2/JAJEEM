package com.jajeem.core.design.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

public class CustomMainButton extends WebButton {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
	int width, height = 20;
	int x = 10;
	int y = 5;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;

	boolean isRollOver, isPressed;

	CustomMainButton(String imageURI) {
		try {
			originalImage = ImageIO.read(CustomMainButton.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setUndecorated(false);
	}

	CustomMainButton(String imageURI, int w, int h) {

		{
			try {
				URL inp = CustomMainButton.class.getResource(imageURI + ".png");
				background = ImageIO.read(inp);
				inp = CustomMainButton.class.getResource(imageURI + "Hover.png");
				rollover = ImageIO.read(inp);
				inp = CustomMainButton.class.getResource(imageURI + "Pressed.png");
				selected = ImageIO.read(inp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			getModel().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						isRollOver = true;
					} else {
						isRollOver = false;
					}
					if (model.isPressed()) {
						isPressed = true;
					} else {
						isPressed = false;
					}
				}
			});
		}

		setOpaque(false);
		width = w;
		height = h;
	}

	public CustomMainButton(String imageURI, int w, int h, int x, int y) {
		{
			try {
				URL inp = CustomMainButton.class.getResource(imageURI + ".png");
				background = ImageIO.read(inp);
				// inp = CustomButton.class.getResource(imageURI+"Hover.png");
				// rollover = ImageIO.read(inp);
				// inp = CustomButton.class.getResource(imageURI+"Pressed.png");
				// selected = ImageIO.read(inp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			getModel().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						isRollOver = true;
					} else {
						isRollOver = false;
					}
					if (model.isPressed()) {
						isPressed = true;
					} else {
						isPressed = false;
					}
				}
			});
		}
		try {
			originalImage = ImageIO.read(CustomMainButton.class.getResource(imageURI
					+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
		width = w;
		height = h;
		this.x = x;
		this.y = y;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		}
		g2.dispose();
	}
}