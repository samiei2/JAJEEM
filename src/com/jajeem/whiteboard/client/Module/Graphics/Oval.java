package com.jajeem.whiteboard.client.Module.Graphics;
/**
 * File Name    : Oval.java
 * Description  : The Oval class implements the information and
 *                drawing method of an Oval.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.*;
import java.awt.geom.Ellipse2D;

import com.jajeem.whiteboard.client.Module.Data.GradientData;
import com.jajeem.whiteboard.server.Module.Shape;

/**
 * Class Oval provides implements the information and
 * drawing method of an Oval. It draws the Oval according
 * to the two points, the first one and the last one,
 * which are stored in the vector. And it also implements
 * a method to draw the shape when the users move their cursor
 * in different quadrant.
 */
public class Oval extends com.jajeem.whiteboard.server.Module.Shape {

    /** The type of gradient of this shape */
    private GradientData.GradientType gradientType;

    /** The flag whether this shape is transparent */
    private boolean isTransparent;

    /** The constructor of Oval class in empty mode */
    public Oval(Color forwardColor, Color backgroundColor,
                float stroke, boolean isSquare, boolean isTransparent)
    {
        // invoke the parent constructor
        super(forwardColor, backgroundColor, stroke, isSquare);
        // set the type of shape
        this.shapeType = Shape.ShapeType.Oval;
        // set the flag of transparent
        this.isTransparent = isTransparent;
    }
    
    /** The constructor of Oval class in filled mode */
    public Oval(Color forwardColor, Color backgroundColor,
                boolean isFilled, boolean isSquare,
                GradientData.GradientType gradientType) {
        super(forwardColor,backgroundColor);
        this.shapeType = Shape.ShapeType.Oval;
        this.isFilled = isFilled;
        this.isSquare = isSquare;
        this.gradientType = gradientType;
    }

    /** Define the method inherited from the abstract class Shape */
    public void draw(Graphics graphics) {
        // get the size of points
        int n = pointVector.size();

        if ( n == 1)
            return;

        Graphics2D graphics2D = (Graphics2D)graphics;

        Point p1, p2;

        // get the first point
        p1 = pointVector.elementAt(0);
        // remote the middle points
        for (int i = 1; i < n-1; i++) {
            pointVector.removeElementAt(1);
        }
        // get the last point
        p2 = pointVector.elementAt(1);

        int x,y,width,height;
        if (!isSquare) {
            // if ths shape is not square
            x = p2.x > p1.x ? p1.x : p2.x;
            y = p2.y > p1.y ? p1.y : p2.y;
            width = Math.abs(p2.x - p1.x);
            height = Math.abs(p2.y - p1.y);
        }
        else
        {
            // if the shape is square 
            int lengthY = Math.abs(p2.y - p1.y);
            int lengthX = Math.abs(p2.x - p1.x);
            height = width = lengthY > lengthX ? lengthX : lengthY;
            x = p1.x < p2.x ? p1.x : p1.x - width;
            y = p1.y < p2.y ? p1.y : p1.y - width;
        }
        if( !isFilled)
        {
            // if the shape is in the empty mode
            if(!isTransparent) {
                // if the shape is not transparent 
                graphics2D.setColor(this.backgroundColor);
                graphics2D.fillOval(
                        x,y,width,height);
            }

            graphics2D.setColor(this.forwardColor);
            // get the current stroke 
            Stroke graphicsStroke = graphics2D.getStroke();
            graphics2D.setStroke(new BasicStroke(stroke));

            Ellipse2D ellipse = new Ellipse2D.Double(
                    x,y,width, height);
            graphics2D.draw(ellipse);
            // reset the stroke
            graphics2D.setStroke(graphicsStroke);
            
        }
        else
        {
            // if the shape is in the filled mode
            // determine which type of gradient is used
            if(gradientType == GradientData.GradientType.Normal){
                graphics2D.setColor(this.forwardColor);
            } else if(gradientType == GradientData.GradientType.Horizontal){
                graphics2D.setPaint(new GradientPaint(new Point(x,y),forwardColor,
                        new Point(x+width,y),backgroundColor));
            } else if(gradientType == GradientData.GradientType.Vertical){
                graphics2D.setPaint(new GradientPaint(new Point(x,y),forwardColor,
                        new Point(x,y+height),backgroundColor));
            } else if(gradientType == GradientData.GradientType.Diagonal){
                graphics2D.setPaint(new GradientPaint(new Point(x,y),forwardColor,
                        new Point(x+width,y+height),backgroundColor));
            }
            graphics2D.fillOval(
                    x,y,width,height);
        }
    }
}
