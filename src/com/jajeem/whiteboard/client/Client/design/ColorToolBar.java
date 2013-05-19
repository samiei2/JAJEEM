package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : ColorToolBar.java
 * Description  : The ColorToolBar implements a container of
 *                color components.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */

import javax.swing.*;

import com.jajeem.whiteboard.client.Module.Data.ColorData;
import com.jajeem.whiteboard.client.Module.Data.FontData;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Class ColorToolBar provides an implementation of a container
 * of color components, in which user can choose a color by
 * clicking the color button. And the current color of the
 * whiteboard is displayed in ColorPanel.
 */
public class ColorToolBar extends JToolBar implements MouseListener {

    /** creates a panel which displays the current colors */
    private ColorPanel colorPanel;

    /** creates the font panel which displays */
    private FontPanel fontPanel;

    /**
     * The constructor of ColorToolBar
     */
    public ColorToolBar() {
        // the number of buttons
        final int numOfColorButtons = 28;

        // the panel contains the colorpanel and colorbuttons
        JPanel colorDisplayPanel;

        // creates the array of color buttons
        ColorButton[] colorButtons = new ColorButton[numOfColorButtons];

        // the colors of each color buttons, which are made of three main
        // colors, red, green, blue.
	    int[] redColors={0x00,0x46,0x78,0x99,0xED,0xFF,0xFF,0xFF,0xA8,0x22,0x60,0x4D,0x2F,0x6F,
				   0xFF,0xDC,0xB4,0x9C,0xFF,0xE5,0xF5,0xFF,0x03,0x9D,0x95,0x70,0x54,0xB5};
	    int[] greenColors={0x00,0x46,0x78,0x00,0x1C,0x7E,0xC2,0xF2,0xE6,0xB1,0xB7,0x60,0x36,0x31,
				   0xFF,0xDC,0xB4,0x5A,0xA3,0xAA,0xE4,0xF9,0xF9,0xBB,0xD9,0x9A,0x6D,0xA5};
	    int[] blueColors={0x00,0x46,0x78,0x30,0x24,0x00,0x0E,0x00,0x1D,0x4C,0xEF,0xF3,0x99,0x98,
				   0xFF,0xDC,0xB4,0x3C,0xB1,0x7A,0x9C,0xBD,0xBC,0x61,0xEA,0xD1,0x8E,0xD5};

        // sets the layout of this component
        this.setLayout(new BorderLayout());

        // creates a instance of JPanel
        colorDisplayPanel = new JPanel(null);

        // creates a instance of ColorPanel
        colorPanel = new ColorPanel();

        // sets its position and size
        colorPanel.setBounds(0,1,30,31);

        // adds the colorpanel
        colorDisplayPanel.add(colorPanel);

        // initializes the color buttons and sets their positions
        for(int i=0; i < numOfColorButtons; i++) {

            // creates the color according to the RGB
            Color color = new Color(redColors[i],
                    greenColors[i], blueColors[i]);

            // creates the instance of the color buttons
            colorButtons[i] = new ColorButton(color);

            // computes the position of the button
            int x = 31 + 16 * ((i < 14)? i : (i-14));
            int y = 1 + 16 * (i/14);

            // sets its position and size
            colorButtons[i].setBounds(x,y,15,15);

            // sets this component to listen the mouse
            // operation in buttons
            colorButtons[i].addMouseListener(this);

            // adds this button to the panel
            colorDisplayPanel.add(colorButtons[i]);
        }

        // set the panel's size
        colorDisplayPanel.setPreferredSize(new Dimension(280,36));

        // add the panel to the west of the layout
        this.add("West", colorDisplayPanel);

        // creates a font panel, which displays the
        // font buttons, such as bold, italic, baseline
        fontPanel = new FontPanel( );

        // sets the default visible of font panel to false
        fontPanel.setVisible(false);

        // adds the font panel to the east
        this.add("East", fontPanel);

        // sets the background color of this component
        this.setBackground(MainFrame.TOOL_BACKGROUNDCOLOR);
        
        // the component is fixed
        this.setFloatable(false);
    }

    /**
     * The event is triggered by mouse clicking
     * @param event The event information
     */
    public void mouseClicked(MouseEvent event)
    {
        // get the source of the event, which is from the clicking of
        // buttons
        ColorButton colorButton = (ColorButton)event.getSource();
        // the user clicks the button on twice times.
        if(event.getClickCount() == 2) {
            // the JColorChooser is the Class of JAVA, which
            // provides a dialog of editing colors.
            Color color = JColorChooser.showDialog(getParent(),
                    "Color Editor", colorButton.getColor());
            // sets the changed color to the corresponding button
            colorButton.setColor(color);
            // changes the current display color
            colorPanel.setForwardColor(color);
        }
        else
        {
            // if the user clicks one time, then decide which
            // button of mouse is pressed.
            if(event.getButton() == MouseEvent.BUTTON1) {
                // the left button in the mouse
                // get the color of button clicked
                Color color = colorButton.getColor();

                // changes the current display forward color
                colorPanel.setForwardColor(color);
            }
            else if (event.getButton() == MouseEvent.BUTTON3)
            {
                // changes the current display background color
                colorPanel.setBackgroundColor(colorButton.getColor());
            }
        }
    }

    /**
     * Provides the method to sets the visibility of
     * font panel
     * @param isVisble The visibility of font panel
     */
    public void setFontPanelVisble(boolean isVisble){
        // sets the visibility of font panel
        this.fontPanel.setVisible(isVisble);
    }

    /**
     * Get the font information
     * @return The data of font
     */
    public FontData getFontData(){
        return this.fontPanel.getFontData();
    }

    /**
     * Get the font information
     * @return The data of font
     */
    public ColorData getColorData(){
        return this.colorPanel.getColorData();
    }
                                         
    // those method are empty, but they are the methods
    // of the interface 'MouseListener'
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
}
