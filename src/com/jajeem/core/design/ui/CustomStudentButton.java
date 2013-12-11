package com.jajeem.core.design.ui;

import java.awt.Graphics;
import java.awt.Image;
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

public class CustomStudentButton extends WebButton{
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	
	boolean isRollOver, isPressed;
	
	void init(){
		try {
			URL inp = CustomMainButton.class.getResource("/icons/noa_en/buttonBackgroundstudent.png");
			background = ImageIO.read(inp);
			inp = CustomStudentButton.class.getResource("/icons/noa_en/buttondisablestudent.png");
			rollover = ImageIO.read(inp);
			inp = CustomStudentButton.class.getResource("/icons/noa_en/buttonselectstudent.png");
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
		
		setUndecorated(false);
	}
	public CustomStudentButton(ImageIcon imageURI){
		super(imageURI);
		init();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			if(isRollOver)
				if(isPressed)
					g2.drawImage(selected.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0,0,getWidth(), getHeight(), null);
				else
					g2.drawImage(rollover.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0,0,getWidth(), getHeight(), null);
			else
				g2.drawImage(background.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0,0,getWidth(), getHeight(), null);
		}
		g2.dispose();
		super.paintComponent(g);
	}
}
