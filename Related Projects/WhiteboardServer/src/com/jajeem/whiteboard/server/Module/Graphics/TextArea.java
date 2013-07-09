package com.jajeem.whiteboard.server.Module.Graphics;
/**
 * File Name    : TextArea.java
 * Description  : The TextArea class implements the information and
 *                drawing method of an TextArea.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import com.jajeem.whiteboard.server.Module.Shape;
import com.jajeem.whiteboard.server.Module.Data.FontData;

/**
 * Class TextArea provides the method to draw the string which
 * the users input. It gets the point stored in the vector, and
 * drawing the string in this point.
 */
public class TextArea extends com.jajeem.whiteboard.server.Module.Shape {
    /** The data of font */
    private FontData fontData;

    /** The constructor of Pencil class */
    public TextArea(Color color, FontData fontData){
        super(color);
        this.forwardColor = color;
        this.fontData = new FontData(fontData);
        this.shapeType = Shape.ShapeType.TextArea;
        this.textInput = new StringBuffer();
    }
    
    /** Define the method inherited from the abstract class Shape */
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;

        // get the point 
        Point p1 =  pointVector.elementAt(0);

        graphics2D.setColor(this.forwardColor);

        // set the style of font
        int style = Font.PLAIN;
        if (fontData.getIsBold()) {
            // add the bold
            style += Font.BOLD;
        }
        if (fontData.getIsItalic()) {
            // add the italic 
            style += Font.ITALIC;
        }

        // the input text
        String inputMessages = this.textInput.toString();

        if(inputMessages.length() != 0) {
            // create the attributed string
            AttributedString string = new AttributedString(inputMessages);
            string.addAttribute(TextAttribute.FONT, new Font(
                    fontData.getFont(),style,fontData.getFontSize()));

            if (fontData.getIsBaseline()) {
                // if the font has the baseline
                string.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            }
            // draw the string
            graphics2D.drawString(string.getIterator(), p1.x , p1.y );
        }
    }
}
