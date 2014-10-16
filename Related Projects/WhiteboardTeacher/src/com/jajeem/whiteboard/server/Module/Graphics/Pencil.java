package com.jajeem.whiteboard.server.Module.Graphics;
/**
 * File Name    : Pencil.java
 * Description  : The Pencil class implements the information and
 *                drawing method of an Pencil.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.jajeem.whiteboard.server.Module.Shape;

/**
 * Class Pencil provides implements the information and
 * drawing method of an Pencil. It draws according to 
 * all the points stored in the vector.
 */
public class Pencil extends com.jajeem.whiteboard.server.Module.Shape {
    
    /** The constructor of Pencil class */
    public Pencil(Color color, float stroke)
    {
        // invoke the parent constructor
        super(color, stroke);
        // set the type of shape
        this.shapeType = Shape.ShapeType.Pencil;
    }
    /** Define the method inherited from the abstract class Shape */
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        // get the stroke of current graphics
        Stroke graphicsStroke = graphics2D.getStroke();

        Point p1, p2;
        int n = pointVector.size();

        graphics2D.setColor(this.forwardColor);
        graphics2D.setStroke(new BasicStroke(stroke));

        // draw the route of the points
        for ( int i = 0; i< n-2; i++) {
            p1 =  pointVector.elementAt(i);
            p2 =  pointVector.elementAt(i + 1);

            // create a line between the two points
            Line2D line = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
            graphics2D.draw(line);
        }
        // reset the stroke 
        graphics2D.setStroke(graphicsStroke);
    }
}
