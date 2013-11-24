package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.swt.graphics.Image;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;

public class CustomLogoLabel extends WebLabel{
	BufferedImage background;
	String Uri;
	
	public CustomLogoLabel(String Uri){
		init(Uri);
		this.Uri = Uri;
	}
	
	public void init(String Uri){
		try {
			URL inp = CustomLogoLabel.class.getResource(Uri);
			background = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		org.eclipse.swt.graphics.Image background = ImageResizer.resize(this.background, (float) 0.5);
		if(background != null){
			g2.drawImage(background, 0, 0,getWidth(),getHeight(), this);
	    }
        g2.dispose();
        super.paintComponent(g);
	}	
}
