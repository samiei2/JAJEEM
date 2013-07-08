package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : ColorButton.java
 * Description  : A component of color panel
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import javax.swing.*;
import java.awt.*;


/**
 * Class ColorButton provides a simple implementation of
 * a color component, in which user can choose a color by
 * clicking this button. Actually, it is not a real button,
 * and it extends the JPanel class.
 */
public class ColorButton extends JPanel {
    /** the color of this button */
    private Color color;

    /**
     * Initialize a Color Button instance, specify
     * a color to it.
     * @param color The specific color of this button
     */
    public ColorButton(Color color) {
        this.color = color;
    }

    /**
     * Sets the button's color.
     * @param color The changed color
     */
    public void setColor(Color color){
        this.color = color;
        // redraw the graphics
        paint(this.getGraphics());
    }

    /**
     * Gets the current color of this button
     * @return The current color of this button
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Overrides the paint method. This method provides
     * a color square to imitate the color management
     * tool in MS Paint.
     * @param graphics The graphics of current component
     */
    public void paint(Graphics graphics){
        int width = this.getWidth();
        int height = this.getHeight();

        // Sets the drawing line's color
        graphics.setColor(Color.GRAY);
        graphics.drawLine(0,0,0,height);
        graphics.drawLine(0,0,width,0);
        // Sets the default background color
        graphics.setColor(new Color(230,230,230));
        graphics.drawRect(1,1,width-2,height-2);
        // Sets the forward color of this square
        graphics.setColor(this.color);
        graphics.fillRect(2,2,width-3,height-3);
    }
}
