package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : SquarePanel.java
 * Description  : The SquarePanel implements a display panel, from which
 *                users can choose whether their drawing shape is
 *                square or not. 
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;

/**
 * Class SquarePanel implements a display panel, from which users can
 * choose the type of their drawing shape, whether is square or not.
 */
public class SquarePanel extends WebPanel {

    /** The flag whether the drawing shape is square */
    private boolean isSquare;

    /** The type of drawing shape */
    private com.jajeem.whiteboard.server.Module.Shape.ShapeType shapeType;

    /** The constructor to initialize instance variables */
    public SquarePanel(){
        // the default type is rectangle
        shapeType = com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle;
        // the flag's default value is false 
        isSquare = false;
        // set its size
        this.setSize(50,64);

        this.addMouseListener(new MouseAdapter() {

            // when the mouse is clicked,
            // deciding which choice is clicked
			public void mouseClicked(MouseEvent e) {
				int y = e.getY();
                // determine whether is square according to
                // the point which user clicked.
				if (y >= 4 && y < 32)
                    setIsSquare(false);
				else if (y > 32 && y <= 60)
                    setIsSquare(true);
				Container container=getParent().getParent();
				if (container instanceof ToolPanel) {
					ToolPanel toolPanel=(ToolPanel)container;
                    // set the flag of tool panel
					toolPanel.setIsSquare(isSquare);
				}
			}
		});
    }

    /** Set the type of shape */
    public void setShapeType(com.jajeem.whiteboard.server.Module.Shape.ShapeType shapeType) {
        this.shapeType = shapeType;
        repaint();
    }

    /** Set the value of flag whether the drawing shape is square */
    public void setIsSquare(boolean isSquare) {
        this.isSquare = isSquare;
        repaint();
    }

    /**
     * Override the paint method and provide graphic interface
     * from which user is able to choose.    
     */
    public void paint(Graphics graphics) {
		super.paint(graphics);
		int width = getWidth() - 1;
		int height = getHeight() - 1;

        // draw the background of the panel 
		graphics.setColor(Color.GRAY);
		graphics.drawLine(0,0,width,0);
		graphics.drawLine(0,0,0,height);
		graphics.setColor(Color.WHITE);
		graphics.drawLine(0,height,width,height);
		graphics.drawLine(width,0,width,height);

        // change the boolean value to int
		int is = isSquare ? 1 : 0;
        
		graphics.setColor(new Color(0x33,0x99,0xff));
		graphics.fillRect(3,is*28+3,width-5,28);

        Color firstColor;
        Color secondColor;
        // the white color one stands for the item selected
        if (isSquare) {
            firstColor = Color.BLACK;
            secondColor = Color.WHITE;
        } else {
            firstColor = Color.WHITE;
            secondColor = Color.BLACK;
        }

        // draw the shape in the SquarePanel
        graphics.setColor(firstColor);
        if (shapeType == com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle) {
            graphics.drawRect((width-36)/2,7,36,18);
            graphics.setColor(secondColor);
            graphics.drawRect((width-18)/2,35,18,18);
        }
        else if (shapeType == com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle) {
            graphics.drawRoundRect((width-36)/2,7,36,18,8,8);
            graphics.setColor(secondColor);
            graphics.drawRoundRect((width-18)/2,35,18,18,8,8);
        }
        else if (shapeType == com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval) {
            graphics.drawOval((width-36)/2,7,36,18);
            graphics.setColor(secondColor);
            graphics.drawOval((width-18)/2,35,18,18);
        }
    }
}
