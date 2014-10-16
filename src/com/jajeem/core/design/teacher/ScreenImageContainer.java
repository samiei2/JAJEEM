package com.jajeem.core.design.teacher;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ScreenImageContainer implements Serializable{

	int width; int height; int[] pixels;

    public ScreenImageContainer(BufferedImage bi) {
         width = bi.getWidth();
         height = bi.getHeight();
         pixels = new int[width * height];
         pixels =bi.getRGB(0,0,width,height,pixels,0,width);
    }

    public BufferedImage getImage() {
         BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
         bi.setRGB(0,0,width,height,pixels,0,width);
         return bi;
    }
}