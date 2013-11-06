package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;

public class CustomBottomButton extends WebButton{
	BufferedImage background;
	
	public CustomBottomButton(){
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttombutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomBottomButton(ImageIcon img){
		super(img);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttombutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomBottomButton(String label,ImageIcon img){
		super(label,img);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttombutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CustomBottomButton(String label){
		super(label);
		try {
			URL inp = CustomButton.class.getResource("/icons/noa_en/buttombutton.png");
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
//		Graphics g2 = g.create();
//		if(background != null){
//	        g2.drawImage(background, 0, 0, this);
//	    }
//        g2.dispose();
//        super.paintComponent(g);
		
		Graphics g2 = g.create();
        g2.drawImage(background, 0, 0, getWidth()+5, getHeight(), null);
        g2.dispose();
        super.paintComponent(g);
	}
}
