package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;

public class CustomButton extends WebButton{
	BufferedImage background;
	
	public CustomButton(){
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttonBackground.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomButton(ImageIcon img){
		super(img);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttonBackground.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomButton(String label,ImageIcon img){
		super(label,img);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttonBackground.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomButton(String label){
		super(label);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttonBackground.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics g2 = g.create();
		if(background != null){
	        g2.drawImage(background, 3, 0, this);
	    }
        g2.dispose();
        super.paintComponent(g);
	}
}
