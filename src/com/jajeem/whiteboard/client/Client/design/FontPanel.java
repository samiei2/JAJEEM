package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : FontPanel.java
 * Description  : The FontPanel provides a container of
 *                font components.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.jajeem.whiteboard.client.Client.WhiteboardClient;
import com.jajeem.whiteboard.client.Module.Data.FontData;

/**
 * Class FontPanel provides an implementation of a container
 * of font components, in which user can choose the fonts,
 * the size, the bold, the italic, and the baseline.
 */
public class FontPanel extends JPanel implements ActionListener {
    /** This combo box provides the choice of fonts. */
    private JComboBox fontComboBox;

    /** The combo box provides the choice of size. */
    private JComboBox sizeComboBox;

    /** The name and text tips of tools. */
    private final String[] toolText = {"Bold","Italic","Baseline"};

    /** The name of image files. */ 
    private final String[] imageFileName = {"Bold","Italic","Baseline"};

    /** The tool buttons. */
    private JToggleButton[] btnTool = new JToggleButton[toolText.length];

    /** The data of font information, which extends from the Observable*/
    private FontData fontData;

    /**
     * The constructor of font panel
     */
    public FontPanel() {

        // sets the size of this component
        this.setPreferredSize(new Dimension(300,36));
        // set the default layout 
        this.setLayout(new BorderLayout());

        // create a new font data.
        fontData = new FontData("Calibri",12 ,false, false,
                false);

        // create the array of image icons
        ImageIcon[] imageIcons = new ImageIcon[toolText.length];
        // create the panel which contains the buttons
        JPanel buttonPanel;
        // creates the array of size
        String[] sizeText ={"8","9","10","11","12","14","16","18",
                                "20","22","24","26","28","36","48","72"};
        sizeComboBox = new JComboBox(sizeText);
        sizeComboBox.setBounds(143,1,50,18);
        sizeComboBox.setSelectedItem("12");

        // gets the local available fonts
        // adds that fonts into the choice box
        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox(fonts);
        fontComboBox.setBounds(0,1,140,18);
        sizeComboBox.setSelectedItem("Calibri");

        buttonPanel = new JPanel(null);

        // initialize the buttons
        for( int i=0; i< btnTool.length; i++) {
            // creates the array of image icons
            imageIcons[i] = new ImageIcon(WhiteboardClient.class.getResource("/com/jajeem/whiteboard/client/Client/Image/" + imageFileName[i] + ".gif"));

            // initialize the buttons with the image icons,
            // and adds them into the button Panel.
            btnTool[i] = new JToggleButton("", imageIcons[i]);
            btnTool[i].setToolTipText(toolText[i]);
            btnTool[i].setActionCommand(imageFileName[i]);
            btnTool[i].setFocusable(false);
            btnTool[i].addActionListener(this);
            btnTool[i].setBounds(200 + (i*25) , 1 , 25, 25);
            buttonPanel.add(btnTool[i]);
        }
        // sets the button panel's position and size
        buttonPanel.setPreferredSize(new Dimension(100,36));
        buttonPanel.setLocation(145,1);


        fontComboBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent itemEvent){
                // the event is triggered by the item state changed
                // in the combobox of font.
                if(itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    // if a item selected
                    fontData.setFont(
                        fontComboBox.getSelectedItem().toString().trim());
                }
            }
        });

        sizeComboBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent itemEvent){
                // the event is triggered by the item state changed
                // in the combobox of font size.
                if(itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    // if a item selected
                    fontData.setFontSize(Integer.parseInt(
                        sizeComboBox.getSelectedItem().toString().trim()));
                }
            }
        });

        // add those components
        this.add(fontComboBox);
        this.add(sizeComboBox);
        this.add(buttonPanel);
    }

    /**
     * The event happens while the clicking of buttons
     * @param event The event information of clicks
     */
    public void actionPerformed(ActionEvent event) {
        // gets the command from the action
        String command = event.getActionCommand();

        for( int i=0; i<imageFileName.length; i++) {
            if(command.equals(imageFileName[i])) {
                // if it equals, then change the
                // type of font according to the
                // corresponding buttons.
                this.buttonChangeEvent(i);
            }
        }
    }

    /**
     * According to the index, changing the buttons
     * @param index The target index of button
     */
    private void buttonChangeEvent(int index) {

        if( btnTool[index].getActionCommand().equals("Bold")) {
            // Sets the "bold" button
            // Implements the toggle buttons function
            if( fontData.getIsBold() ){
                this.fontData.setIsBold(false);
            }
            else {
                this.fontData.setIsBold(true);
            }
        }
        else if(btnTool[index].getActionCommand().equals("Italic"))
        {
            // Sets the "italic" button
            // Implements the toggle buttons function
            if( fontData.getIsItalic()){
                this.fontData.setIsItalic(false);
            } else {
                this.fontData.setIsItalic(true);
            }
        }
        else if(btnTool[index].getActionCommand().equals("Baseline"))
        {
            // Sets the "baseline" button
            // Implements the toggle buttons function
            if( fontData.getIsBaseline() ){
                this.fontData.setIsBaseline(false);
            } else {
                this.fontData.setIsBaseline(true);
            }
        }
    }

    /**
     * Get the font information 
     * @return The data of font
     */
    public FontData getFontData(){
        return this.fontData;
    }
}
