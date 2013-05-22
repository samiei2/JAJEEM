package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : GradientPanel.java
 * Description  : The GradientPanel implements an choice panel
 *                of which types of gradient user want to draw
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.jajeem.whiteboard.client.Module.Data.ColorData;
import com.jajeem.whiteboard.client.Module.Data.GradientData;

/**
 * Class GradientPanel provides an implementation of a panel
 * which displays the choices of gradient type to users.
 */
public class GradientPanel extends JPanel implements Observer {

    /** The gradient data. */
    private GradientData gradientData;

    /** The color data. */
    private ColorData colorData;

    /**
     * The constructor method of GradientPanel
     * @param colorData The data of color is able to be
     *      observed by GradientPanel
     * @param observer The observer of the gradient data
     */
    public GradientPanel(ColorData colorData, Observer observer){
        // add this to the observer list of color data
        colorData.addObserver(this);

        gradientData = new GradientData();
        
        // add this to the observer list of gradient data
        gradientData.addObserver(observer);

        this.colorData = colorData;

        // Set the initial type of gradient
        this.gradientData.setGradientType(GradientData.GradientType.Normal);

        // Set the size of this panel
        this.setSize(50, 64);

        // add the mouse listener to this panel to
        // listen the action of user's click.
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent event){
                // get the position of click
                int x = event.getX();
                int y = event.getY();

                GradientData.GradientType gradientType 
                        = GradientData.GradientType.Normal;
                // compute that the user clicks which type of
                // gradient 
                if(x < 25 && y < 32) {
                    gradientType = GradientData.GradientType.Normal;
                } else if( x < 25 && y > 32) {
                    gradientType = GradientData.GradientType.Vertical;
                } else if( x > 25 && y < 32) {
                    gradientType = GradientData.GradientType.Horizontal;
                } else if( x > 25 && y > 32) {
                    gradientType = GradientData.GradientType.Diagonal;
                }
                // Set the current gradient type
                setGradientMode(gradientType);

            }
        });
    }

    /**
     * Set the gradient type
     * @param gradientType The specified type of gradient
     */
    public void setGradientMode(GradientData.GradientType gradientType)
    {
        // Get the new gradient type, and then repaint the
        // whole panel
        this.gradientData.setGradientType(gradientType);
        repaint();
    }

    /**
     * The update method of observer, when the observer is
     * notified that the subject has been changed, it will
     * implement this method.
     * @param obsv The changed subject
     * @param arg The parameters passed by the subject
     */
    public void update(Observable obsv, Object arg){
        if(obsv instanceof ColorData) {
            this.repaint();
        }
    }

    /**
     * Override the paint method.
     * @param graphics The graphics of this panel
     */
    public void paint(Graphics graphics){
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        int width = getWidth()-1;
        int height = getHeight()-1;

        // Draw the whole background of the panel
        graphics2D.setColor(Color.gray);
        graphics2D.drawLine(0,0,width,0);
        graphics2D.drawLine(0,0,0,height);
        graphics2D.setColor(Color.white);
        graphics2D.drawLine(0,height,width,height);
        graphics2D.drawLine(width,0,width,height);

        // The choose effect of user's clicking
        graphics2D.setColor(new Color(0x33,0x99,0xff));
        graphics2D.fillRect((this.gradientData.getGradientType().ordinal()%2)*22+3,
                (this.gradientData.getGradientType().ordinal()/2)*28+3,
                22,28);
        graphics2D.setColor(colorData.getForwardColor());
        graphics2D.fillRect(6,6,16,22);

        // draw the horizontal gradient
        graphics2D.setPaint(new GradientPaint(new Point(28,6),
                colorData.getForwardColor(),
                new Point(44,6), colorData.getBackgroundColor()));
        graphics2D.fillRect(28,6,16,22);

        // draw the vertical gradient
        graphics2D.setPaint(new GradientPaint(new Point(6,34),
                colorData.getForwardColor(),
                new Point(6,56), colorData.getBackgroundColor()));
        graphics2D.fillRect(6,34,16,22);

        // draw the diagonal gradient
        graphics2D.setPaint(new GradientPaint(new Point(28,34),
                colorData.getForwardColor(),
                new Point(44,56), colorData.getBackgroundColor()));
        graphics2D.fillRect(28,34,16,22);
    }
}
