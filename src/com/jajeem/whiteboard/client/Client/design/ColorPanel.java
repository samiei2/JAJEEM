package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : ColorPanel.java
 * Description  : The color panel implements a component to
 *                display the current color
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import com.alee.laf.panel.WebPanel;
import com.jajeem.whiteboard.client.Module.Data.ColorData;

/**
 * Class ColorPanel provides an implementation of the display
 * of current color, which contains two small square. The above one
 * represents the forward color, and another one stands for the
 * background color.
 */
public class ColorPanel extends WebPanel {
    /** The data of colors */
    private ColorData colorData;
    
    /**
     * The constructor of ColorPanel initializes
     * the colors and size of this component.
     */
    public ColorPanel() {
        /** The length of the color square */
        final int lengthOfColorPanel = 30;

        this.colorData = new ColorData(Color.BLACK, Color.WHITE);


        // Sets the size of this component
        this.setPreferredSize(
                new Dimension(lengthOfColorPanel,
                        lengthOfColorPanel));
    }

    /**
     * Sets the forward color square.
     * @param color The specified color
     */
    public void setForwardColor(Color color) {
        this.colorData.setForwardColor(color);
        System.out.println("ssss");       
        paint(this.getGraphics());
    }

    /**
     * Sets the background color square.
     * @param color The specified color
     */
    public void setBackgroundColor(Color color) {
        this.colorData.setBackgroundColor(color);
        paint(this.getGraphics());
    }

    /**
     * Overrides the paint method to stimulate
     * the color display tool in the MS Paint
     * @param graphics The graphics of current component
     */
    public void paint(Graphics graphics) {
        int width = this.getWidth();
        int height = this.getHeight();

        // Sets the drawing line's color
        graphics.setColor(Color.GRAY);
        graphics.drawLine(0,0,0,height);
        graphics.drawLine(0,0,width,0);

        // Sets the default background color
        graphics.setColor(new Color(230,230,230));
        graphics.drawRect(1,1,width-2,height-2);

        // Sets the panel color
        graphics.setColor(Color.WHITE);
        graphics.fillRect(2,2,width-3,height-3);

        // Draw the two square in this panel
        drawColorSmallPanel(graphics,
                this.colorData.getBackgroundColor(),11,12,15,15);
        drawColorSmallPanel(graphics,
                this.colorData.getForwardColor(),4,5,15,15);
    }

    /**
     * Draws the squre in the color panel
     * @param graphics The graphics of current component
     * @param color The specified color
     * @param x The x position of this component
     * @param y The y position of this component
     * @param width The width of this component
     * @param height The height of this component
     */
    private void drawColorSmallPanel(Graphics graphics, Color color,
                        int x, int y, int width, int height)
    {
        // Draw the background of this color square
        graphics.setColor(new Color(230,230,230));
        graphics.drawRect(x+1,y+1,width-3,height-3);
        graphics.setColor(Color.GRAY);
        graphics.drawLine(x+1,y+height-1,x+width-1,y+height-1);
        graphics.drawLine(x+width-1,y+1,x+width-1,y+height-1);

        // Draw the color in the square
        graphics.setColor(color);
        graphics.fillRect(x+2,y+2,width-4,height-4);
    }

    /**
     * Get the data of colors
     * @return The data of colors
     */
    public ColorData getColorData(){
        return this.colorData;
    }
}
