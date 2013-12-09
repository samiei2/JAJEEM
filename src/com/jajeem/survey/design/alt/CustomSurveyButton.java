package com.jajeem.survey.design.alt;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

public class CustomSurveyButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	protected boolean isRollOver;
	protected boolean isPressed;
	private String uri;

	public CustomSurveyButton(String Uri) {
		uri = Uri;
		init();
	}

	public void init() {
		try {
			URL inp = CustomSurveyButton.class.getResource(uri);
			background = ImageIO.read(inp);
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
		// if(background != null){
		// if(isPressed)
		// g2.drawImage(selected, 3, 0,getWidth(),getHeight(), this);
		// else{
		// if(isRollOver)
		// g2.drawImage(rollover, 3, 0,getWidth(),getHeight(), this);
		// else
		g2.drawImage(background.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, getWidth(), getHeight(), this);
		// }
		// }
		g2.dispose();
		super.paintComponent(g);
	}
}
