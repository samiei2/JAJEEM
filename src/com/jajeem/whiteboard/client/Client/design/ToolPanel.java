package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : ToolPanel.java
 * Description  : The ToolPanel provides a container of the tools, including
 *                the gradient panel, stroke panel, the buttons of drawing
 *                shape and etc.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import javax.swing.*;

import com.jajeem.whiteboard.client.Client.WhiteboardClient;
import com.jajeem.whiteboard.client.Module.Data.ColorData;
import com.jajeem.whiteboard.client.Module.Data.GradientData;
import com.jajeem.whiteboard.server.Module.Shape;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class ToolPanel is a container of tools, including the gradient
 * panel, stroke panel, the buttons of drawing shape and etc. The
 * users can choose the tool to draw the picture.
 */
public class ToolPanel extends JToolBar implements ActionListener {

    /** Declare a reference of PaintPanel class */
    private PaintPanel paintPanel;

    /** The tool tips name */
    private String[] toolText = {"Cursor","Erase","Pencil",
                "Line","Rectangle","Filled Rectangle","Rounded Rectangle",
                "Filled Rounded Rectangle","Oval","Filled Oval","TextArea","Image"};
    /** The image file's name */
    private String[] imageFileName = {"Cursor","Erase","Pencil",
                "Line","Rectangle","FilledRectangle","RoundedRectangle",
                "FilledRoundedRectangle","Oval","FilledOval","TextArea","Image"};

    /** The tool buttons */
    private JToggleButton[] btnTool;
    
    /** The panel to change the size of stroke */
    private StrokePanel strokePanel;

    /** The size of current stroke */
    private float currentStroke;

    /** The panel to choose whether the drawing shape is square */
    private SquarePanel squarePanel;

    /** The flag whether the drawing shape is square */
    private boolean isSquare = false;

    /** Declare a reference of main frame */
    private MainFrame mainFrame;

    /** The data of gradient of drawing shape */
    private GradientData.GradientType gradientType;

    /** The panel to change the type of graident */
    private GradientPanel gradientPanel;

    /** The path of image file */
    private String imageFilePath;

    /** The flag whether is drawing an image */
    private boolean isDrawImage;

    /** The constructor of tool panel to initialize its components */
    public ToolPanel(PaintPanel paintPanel, MainFrame mainFrame,
                     ColorData colorData) {
        // set the tool bar is displayed in vertical type
        super(JToolBar.VERTICAL);
        // create the array of buttons
        this.btnTool = new JToggleButton[toolText.length];
        // create the icons of image
        ImageIcon[] imageIcons = new ImageIcon[toolText.length];
        // create the panel contains buttons 
        JPanel buttonPanel;

        // get the reference from the passing parameters
        this.paintPanel = paintPanel;
        this.mainFrame = mainFrame;

        // the default value of gradient type
        this.gradientType = GradientData.GradientType.Normal;

        buttonPanel = new JPanel(null);
        // initialize the buttons and add them to the button panel
        for( int i=0; i< btnTool.length; i++) {
            imageIcons[i] = new ImageIcon(WhiteboardClient.class.getResource("/com/jajeem/whiteboard/client/Client/Image/" + imageFileName[i] + ".gif"));

            btnTool[i] = new JToggleButton("", imageIcons[i]);
            btnTool[i].setToolTipText(toolText[i]);
            btnTool[i].setActionCommand(imageFileName[i]);

            btnTool[i].setFocusable(false);
            btnTool[i].addActionListener(this);

            btnTool[i].setBounds((i%2)*27+2, (i/2)*30+2, 25, 25);
            buttonPanel.add(btnTool[i]);
        }

        // sets the size of button panel 
        buttonPanel.setPreferredSize(new Dimension(58,(btnTool.length/2)*30));

        strokePanel = new StrokePanel(6);
        strokePanel.setLocation(4,(btnTool.length/2)*30 + 5);
        strokePanel.setVisible(false);

        squarePanel = new SquarePanel();
        squarePanel.setLocation(4,strokePanel.getLocation().y+strokePanel.getHeight()+10);
		squarePanel.setVisible(false);

        gradientPanel = new GradientPanel(colorData, paintPanel);
        gradientPanel.setLocation(4,strokePanel.getLocation().y);
        gradientPanel.setVisible(false);
        
        buttonPanel.add(gradientPanel);
        buttonPanel.add(strokePanel);
        buttonPanel.add(squarePanel);

        this.add(buttonPanel);

        // set the default selected item
        this.buttonChangeEvent(0);
        // set this fixed
        this.setFloatable(false);
    }

    /** listen the event of button clicked */
    public void actionPerformed(ActionEvent event) {
        // get the command
        String command = event.getActionCommand();

        for( int i=0; i<imageFileName.length; i++) {
            if(command.equals(imageFileName[i])) {
                // trigger the event of the selected item changed
                this.buttonChangeEvent(i);
            }
        }
        // pass this command to paintpanel,
        // and set its current drawing shape 
        paintPanel.setCurrentShapeType(command);
    }

    /** The method deals with the event of selected item changed. */
    private void buttonChangeEvent(int index) {
        // alter all the items selected to false
        for( int i=0; i < btnTool.length; i++) {
            btnTool[i].setSelected(false);
        }
        // set the target item to true 
        btnTool[index].setSelected(true);
        
        // determine whether some panels should be displayed or not,
        // according to which button is clicked 
        if( btnTool[index].getActionCommand().equals("Pencil")
         || btnTool[index].getActionCommand().equals("Line"))
        {
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(true);
            squarePanel.setVisible(false);
            gradientPanel.setVisible(false);
        }
        else if (btnTool[index].getActionCommand().equals("Rectangle")){
            squarePanel.setShapeType(Shape.ShapeType.Rectangle);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(true);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(false);
        }
        else if (btnTool[index].getActionCommand().equals("RoundedRectangle")){
            squarePanel.setShapeType(Shape.ShapeType.RoundedRectangle);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(true);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(false);
        }
        else if (btnTool[index].getActionCommand().equals("Oval")){
            squarePanel.setShapeType(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(true);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(false);
        }
        else if (btnTool[index].getActionCommand().equals("FilledRectangle")){
            squarePanel.setShapeType(Shape.ShapeType.Rectangle);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(false);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(true);
        }
        else if (btnTool[index].getActionCommand().equals("FilledRoundedRectangle")){
            squarePanel.setShapeType(Shape.ShapeType.RoundedRectangle);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(false);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(true);
        }
        else if (btnTool[index].getActionCommand().equals("FilledOval")){
            squarePanel.setShapeType(Shape.ShapeType.Oval);
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(false);
            squarePanel.setVisible(true);
            gradientPanel.setVisible(true);
        }
        else if (btnTool[index].getActionCommand().equals("TextArea")){
            this.mainFrame.setFontPanelVisble(true);
            strokePanel.setVisible(false);
            squarePanel.setVisible(false);
            gradientPanel.setVisible(false);
        }
        else if (btnTool[index].getActionCommand().equals("Image")) {
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(false);
            squarePanel.setVisible(false);
            gradientPanel.setVisible(false);
            // when clicking the inserting image button,
            // display the dialog to choose the specific file
            imageFilePath = openAImageFile();
        }
        else
        {
            this.mainFrame.setFontPanelVisble(false);
            strokePanel.setVisible(false);
            squarePanel.setVisible(false);
            gradientPanel.setVisible(false);
        }
    }

    /** Set size of current stroke */
    public void setCurrentStroke(float stroke) {
        currentStroke = stroke;
    }

    /** Get the size of current stroke */
    public float getCurrentStroke(){
        return currentStroke;
    }

    /** Set the flag of square */
    public void setIsSquare(boolean isSquare) {
        this.isSquare = isSquare;
    }

    /** Get the flag of square */
    public boolean getIsSquare() {
        return this.isSquare;
    }

    /** Open a image file */
    private String openAImageFile(){
        int flag;
        // create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open An Image");
        // add the filter to the file chooser
        MyFileFilter jpegFileFilter = new MyFileFilter(".jpeg","Image Files(*.jpeg)");
        MyFileFilter jpgFileFilter = new MyFileFilter(".jpg","Image Files(*.jpg)");
        fileChooser.setFileFilter(jpegFileFilter);
        fileChooser.addChoosableFileFilter(jpgFileFilter);

        flag = fileChooser.showOpenDialog(mainFrame);
        if( flag == JFileChooser.APPROVE_OPTION) {
            this.isDrawImage = true;
            // get the path of selected file 
            return fileChooser.getSelectedFile().getPath();
        }
        this.isDrawImage = false;
        return null;
    }
    
    /** Get the path of image file */
    public String getImageFilePath() {
        return this.imageFilePath;
    }

    /** The flag whether is drawing an image */
    public boolean isDrawImage(){
        return this.isDrawImage;
    }

    /** Reset the cursor */
    public void resetCursor(){
        this.buttonChangeEvent(0);
    }
    
}
