package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

public class CustomButtonStudent extends WebButton{
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	protected boolean isRollOver;
	protected boolean isPressed;
	
	public CustomButtonStudent(){
		init();
	}
	
	public CustomButtonStudent(ImageIcon img){
		super(img);
		init();
	}
	
	public CustomButtonStudent(String label,ImageIcon img){
		super(label,img);
		init();
	}
	
	public CustomButtonStudent(String label){
		super(label);
		init();
	}
	
	public void init(){
		try {
			URL inp = CustomButtonStudent.class.getResource("/icons/noa_en/buttonBackgroundstudent.png");
			background = ImageIO.read(inp);
			inp = CustomButtonStudent.class.getResource("/icons/noa_en/buttondisablestudent.png");
			rollover = ImageIO.read(inp);
			inp = CustomButtonStudent.class.getResource("/icons/noa_en/buttonselectstudent.png");
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
	            if(model.isPressed()){
	            	isPressed = true;
	            }
	            else{
	            	isPressed = false;
	            }
	         }
		});
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics g2 = g.create();
		if(background != null){
			if(isPressed)
				g2.drawImage(selected, 3, 0 ,getWidth(),getHeight(), this);
			else{
				if(isRollOver)
					g2.drawImage(rollover, 3, 0,getWidth(),getHeight(), this);
				else
					g2.drawImage(background, 3, 0,getWidth(),getHeight(), this);
			}
	    }
        g2.dispose();
        super.paintComponent(g);
	}	
}
