package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

public class CustomButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	protected boolean isRollOver;
	protected boolean isPressed;

	public CustomButton() {
		init();
	}

	public CustomButton(ImageIcon img) {
		super(img);
		init();
	}

	public CustomButton(String label, ImageIcon img) {
		super(label, img);
		init();
	}

	public CustomButton(String label) {
		super(label);
		init();
	}

	public void init() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/buttonBackground.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/buttondisable.png");
			rollover = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/buttonselect.png");
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

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			if (isPressed) {
				g2.drawImage(selected, 5, 0, getWidth() - 8, getHeight(), this);
			} else {
				if (isRollOver) {
					g2.drawImage(rollover, 5, 0, getWidth() - 8, getHeight(), this);
				} else {
					g2.drawImage(background, 5, 0, getWidth() - 8, getHeight(), this);
				}
			}
		}
		g2.dispose();
		super.paintComponent(g);
	}
}
