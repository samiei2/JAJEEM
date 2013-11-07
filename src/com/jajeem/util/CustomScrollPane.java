package com.jajeem.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import com.alee.laf.scroll.WebScrollPane;

public class CustomScrollPane extends WebScrollPane{
	BufferedImage background;
	public CustomScrollPane(Component arg0) {
		super(arg0);
		try {
			URL inp = CustomScrollPane.class.getResource("/icons/noa_en/jscrollpanebackground.png");
			background = ImageIO.read(inp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	
	@Override
	protected void paintComponent(Graphics g) {
		if (background != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int x = getWidth();
            int y = getHeight();
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2d.dispose();
        }
        super.paintComponent(g);
	}
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setContentPane(new JPanel());
		frame.getContentPane().add(new CustomScrollPane(new JList<>()));
		frame.setVisible(true);
	}

}