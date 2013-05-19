package com.jajeem.whiteboard.client.Module.Graphics;
/**
 * File Name    : Line.java
 * Description  : The Line class implements the information and
 *                drawing method of an Line.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.*;
import java.awt.geom.Line2D;

import com.jajeem.whiteboard.server.Module.Shape;

/**
 * Class Line provides implements the information and
 * drawing method of an line. It draws the line according
 * to the two points, the first one and the last one,
 * which are stored in the vector. 
 */
public class Line extends Shape {
    
    /** The constructor of Line class */
    public Line(Color color, float stroke)
    {
        // invoke the parent constructor
        super(color, stroke);
        // set the type of shape
        this.shapeType = com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line;
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

        // get the first point
        p1 = pointVector.elementAt(0);
        // remove the middle points
        for (int i = 1; i < n-1; i++) {
            pointVector.removeElementAt(1);
        }
        // get the last point
        p2 = pointVector.elementAt(1);

        // set the color
        graphics2D.setColor(this.forwardColor);

        // set the stroke 
        graphics2D.setStroke(new BasicStroke(stroke));

        // create the line and draw it
        Line2D line = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
        graphics2D.draw(line);

        // reset the stroke
        graphics2D.setStroke(graphicsStroke);
    }
}
