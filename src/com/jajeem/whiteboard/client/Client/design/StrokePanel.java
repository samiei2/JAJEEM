package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : StrokePanel.java
 * Description  : The StrokePanel displays the different degree of thickness
 *                in lines which can be choosen by users.
 *                and the user.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Class StrokePanel provides the choice of different degrees of thickness,
 * which users can draw different lines. 
 */
public class StrokePanel extends JPanel {

    /** The number of stroke displayed */
    private int length;

    /** The height of each stroke */
    private int size;

    /** The size of stroke */
    private float stroke;

    public StrokePanel(int length) {
        // initialize  the default values
        this.stroke = 1.0f;
        this.length = length;
        this.size = 12;
        // sets the size of the panel
        this.setSize(50,length*12 + 6);

        // listen the mouse event
        this.addMouseListener(new MouseAdapter() {
            // when the mouse is clicked,
            // deciding which choice is clicked
            public void mouseClicked(MouseEvent event) {
                // compute which one is selected
                // according to the point clicked.
                int y = event.getY() - 4;
                y = y/size;
                y++;
                // set the stroke size 
                setStroke((float)y);
                
                Container container = getParent().getParent();
                if(container instanceof ToolPanel) {
                    ToolPanel toolPanel = (ToolPanel)container;
                    // set the stroke size of tool panel
                    toolPanel.setCurrentStroke((float)y);
                }
                
            }
        });
    }

    /** Set the size of the stroke */
    public void setStroke(float stroke) {
        if(stroke > (float)length)
            stroke = (float)length;
        this.stroke = stroke;
        repaint();
    }

    /**
     * Override the paint method which provides a graphic interface
     * to choose the size of the stroke. 
     */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        // draw the top and left line of the panel,
        // which the gray color makes the panel like
        // having a shadow. 
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawLine(0,0,width,0);
        graphics2D.drawLine(0,0,0,height);
        // draw the right and bottom line of the panel.
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(0,height,width,height);
        graphics2D.drawLine(width,0,width,height);
        
        int iStroke = (int)stroke;
        if (iStroke > length)
            iStroke = length;
        iStroke--;
        // draw the blue cursor to display the current
        // stroke.
        graphics2D.setColor(new Color(0x33,0x99,0xff));
        graphics2D.fillRect(3,iStroke*size+3,width-5,size-1);
        
        // draw the strokes 
        graphics2D.setColor(Color.BLACK);
        for (int i=0; i<length; i++) {
            if(iStroke == i) {
                graphics2D.setColor(Color.WHITE);
            }
            
            for (int j=0; j<=i; j++) {
				int y=(int)(i*(size-0.5))+j+8;
				graphics2D.drawLine(5,y,width-7,y);
			}
            
            if(iStroke == i) {
                graphics2D.setColor(Color.BLACK);
            }
        }
    }
}
