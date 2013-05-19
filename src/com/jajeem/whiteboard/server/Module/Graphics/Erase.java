package com.jajeem.whiteboard.server.Module.Graphics;
/**
 * File Name    : Erase.java
 * Description  : The Erase class implements the drawing method of an Erase.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.*;
import java.awt.geom.Line2D;

import com.jajeem.whiteboard.server.Module.Shape;

/**
 * Class Erase provides a implementation of erase tool.
 * It extends from the abstract class Shape. 
 */
public class Erase extends Shape {

    /** The constructor of Erase class */
    public Erase(Color color, float stroke)
    {
        // invoke the parent constructor
        super(color, stroke);
        // set the type of shape
        this.shapeType = com.jajeem.whiteboard.server.Module.Shape.ShapeType.Erase;
    }

    /** Define the method inherited from the abstract class Shape */
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        // get the stroke of current graphics
        Stroke graphicsStroke = graphics2D.getStroke();

        Point p1, p2;
        int n = pointVector.size();

        if ( n == 1)
            return;
        
        // set the color
        graphics2D.setColor(this.forwardColor);
        // set the stroke
        graphics2D.setStroke(new BasicStroke(stroke));

        // draw the route of the points
        for ( int i = 0; i< n-2; i++) {
            p1 = pointVector.elementAt(i);
            p2 = pointVector.elementAt(i + 1);

            Line2D line = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
            graphics2D.draw(line);
        }
        // reset the stroke 
        graphics2D.setStroke(graphicsStroke);
    }
}
