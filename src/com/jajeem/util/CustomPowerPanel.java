package com.jajeem.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alee.laf.panel.WebPanel;

public class CustomPowerPanel extends WebPanel{
	
	private BufferedImage originalImage;

    public CustomPowerPanel() {
		try {
			originalImage = ImageIO.read(
					CustomTopPanel.class.getResource("/icons/noa_en/powerpanel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpaque(false);
	}
    
	@Override
    protected void paintComponent(Graphics g){
        Graphics g2 = g.create();
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }
}
