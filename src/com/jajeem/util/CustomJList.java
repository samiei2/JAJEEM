package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alee.laf.list.WebList;

public class CustomJList extends WebList{
	String path = "C:\\Users\\Armin\\Desktop\\13\\jscrollpanebackground.png";
	BufferedImage background;
	public CustomJList(){
		try {
			background = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CustomJList(String imagePath){
		path = imagePath;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int x = getWidth() - background.getWidth();
            int y = getHeight() - background.getHeight();
            g2d.drawImage(background, x, y, this);
            g2d.dispose();
        }
    }
}
