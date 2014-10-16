package com.jajeem.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

@SuppressWarnings("serial")
public class CustomBottomButton extends WebButton {
	BufferedImage background;
	BufferedImage backgroundHover;
	boolean isRollOver = false;
	boolean isPressed = false;
	Color oldColor;

	public CustomBottomButton() {
		init();
	}

	public CustomBottomButton(ImageIcon img) {
		super(img);
		init();
	}

	public CustomBottomButton(String label, ImageIcon img) {
		super(label, img);
		init();
	}

	public CustomBottomButton(String label) {
		super(label);
		init();
	}

	public void init() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/new/bottombuttonmain.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/new/bottombuttonmainshover.png");
			backgroundHover = ImageIO.read(inp);
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

		oldColor = getForeground();
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
		if (background != null) {
			if (isPressed) {
				g2.drawImage(backgroundHover, 0, 0, getWidth(), getHeight(),
						this);
				setForeground(new Color(42, 56, 143));
			} else {
				if (isRollOver) {
					g2.drawImage(backgroundHover, 0, 0, getWidth(),
							getHeight(), this);
					setForeground(new Color(42, 56, 143));
				} else {
					g2.drawImage(background, 0, 0, getWidth(), getHeight(),
							this);
					setForeground(oldColor);
				}
			}
		}
		g2.dispose();
		super.paintComponent(g);
	}
}
