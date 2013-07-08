package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : PaintPanel.java
 * Description  : The PaintPanel provides an implementation of
 *                drawing area, which is responsible for the event
 *                made by users.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import com.jajeem.whiteboard.client.Module.Data.GradientData;
import com.jajeem.whiteboard.client.Module.Graphics.*;
import com.jajeem.whiteboard.client.Module.Graphics.Image;
import com.jajeem.whiteboard.client.Module.Graphics.Rectangle;
import com.jajeem.whiteboard.client.Module.Graphics.TextArea;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;
import com.jajeem.whiteboard.client.Module.*;
import com.jajeem.whiteboard.client.Module.Data.ColorData;
import com.jajeem.whiteboard.client.Module.Data.FontData;
import com.jajeem.whiteboard.server.Module.Shape;
import com.jajeem.whiteboard.server.Module.Whiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Class PaintPanel provides the implementation of the drawing area
 * of the local whiteboard, which deals with the events made by users.
 * And it calls the method of the remote 'whiteboard' object to add
 * and receive the information.
 */
public class PaintPanel extends JPanel implements Observer {

    /** Declare a reference of whiteboard */
    private Whiteboard whiteboard;

    /** Declare a reference of shape*/
    private com.jajeem.whiteboard.server.Module.Shape shape;

    /** The type of current drawing shape */
    private String currentShapeType;

    /** Declare a reference of main frame */
    private MainFrame mainFrame;

    /** The flag whether the keyboard mode is open */
    private boolean isKeyboardModeOpen;

    /** The data of font */
    private FontData fontData;

    /** The data of color */
    private ColorData colorData;

    /** The local shapes buffer */
    private Vector<Shape> shapeVector;

    /** The data of gradient type */
    private GradientData.GradientType gradientType;

    /**
     * The constructor of PaintPanel to initialize the components
     */
    public PaintPanel(MainFrame mainFrame, Whiteboard whiteboard,
                      FontData fontData, ColorData colorData) {      
        // get a reference of font data.
        this.fontData = fontData;
        // get a reference of color data
        this.colorData = colorData;
        // set the default type of drawing shape
        this.currentShapeType = "Cursor";

        // initialize the type of gradient
        gradientType = GradientData.GradientType.Normal;

        // initialize instance variables 
        this.mainFrame = mainFrame;
        this.whiteboard = whiteboard;
        this.shapeVector = new Vector<Shape>();

        // set background color to White
        this.setBackground(Color.WHITE);

        // listen the event of focus
        this.addFocusListener(new FocusAdapter()
        {
            public void focusLost(FocusEvent event){
                // when losing the focus,
                // close the keyboard mode.
                CloseKeyboardMode();
            }
        });

        // listen the related event of key 
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent keyEvent) {
                // get the code of key pressed
                int keycode = keyEvent.getKeyCode();
                // press the "enter" key, when
                // finish the input
                if(keycode == 10) {                    
                    CloseKeyboardMode();
                    return;
                } else if(keycode == 27) {
                    // when the users input the "Esc"
                    // cancel all the input
                    PaintPanel paintPanel = (PaintPanel)keyEvent.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();
                    try {
                        // clean the text
                        whiteboard.removeTheCurrentShape();
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while canceling the input text.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Close the keyboard listening mode
                    CloseKeyboardMode();
                    repaint();
                    return;
                }

                if(isKeyboardModeOpen){
                    // if the keyboard mode is open
                    PaintPanel paintPanel = (PaintPanel)keyEvent.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();
                    try {
                        if(keycode == 8) {
                            // if the key pressed is Backspace
                            whiteboard.textDeleteTheLastLetter();
                        } else {
                            // append this character
                            whiteboard.textAppend(keyEvent.getKeyChar());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while canceling the input text.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    repaint();
                }
            }
        });

        // listen action of mouse
        this.addMouseListener(new
        MouseAdapter()
        {
        	com.jajeem.whiteboard.server.Module.Shape shape;
            // when the mouse is clicked 
            public void mouseClicked(MouseEvent event) {               
                if(currentShapeType.equals("TextArea"))
                {
                    PaintPanel paintPanel = (PaintPanel)event.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();

                    int currentRightAt;
                    try {
                        // the enquiry of who has the drawing right
                        currentRightAt = whiteboard.getCurrentRightAt();
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while acquiring who has the paint right.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(currentRightAt == paintPanel.getUserid()) {
                        // if this session has the right to draw
                        shape = paintPanel.CreateTextArea();

                        Point point;
                        int x = event.getX();
                        int y = event.getY();
                        point = new Point( x, y);
                        // add the point to the shape
                        shape.addPoint(point);
                        try {
                            // add the shape to the whiteboard
                            whiteboard.addShape(shape);
                            // open the keyboard mode
                            paintPanel.OpenKeyboardMode();
                            // the paintpanel request the focus
                            paintPanel.requestFocus();
                            
                        }catch(Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Error happens while adding the shape.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        repaint();
                    }
                }
            }

            // when the mouse is pressed
            public void mousePressed(MouseEvent event) {
                PaintPanel paintPanel = (PaintPanel)event.getSource();
                Whiteboard whiteboard = paintPanel.getWhiteboard();
                int currentRightAt;
                try {
                    // the enquiry of who has the drawing right
                    currentRightAt = whiteboard.getCurrentRightAt();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,
                        "Error happens while acquiring who has the paint right.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(currentRightAt == paintPanel.getUserid()) {
                    // if this session has the right to draw
                    if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Pencil.toString()))
                    {
                        // creates a pencil
                        shape = new Pencil(paintPanel.getForwardColor(),
                                paintPanel.getCurrentStroke());
                    }
                    else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line.toString()))
                    {
                        // creates a line
                        shape = new Line(paintPanel.getForwardColor(),
                                paintPanel.getCurrentStroke());
                    }
                    else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval.toString()))
                    {
                        // creates a oval
                        shape = new Oval(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                paintPanel.getCurrentStroke(),
                                paintPanel.getIsSquare(),
                                paintPanel.isTransparent());
                    }
                    else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle.toString()))
                    {
                        // creates a rectangle
                        shape = new Rectangle(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                paintPanel.getCurrentStroke(),
                                paintPanel.getIsSquare(),
                                paintPanel.isTransparent());
                    }
                    else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle.toString()))
                    {
                        // creates a round-rectangle
                        shape = new RoundedRectangle(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                paintPanel.getCurrentStroke(),
                                paintPanel.getIsSquare(),
                                paintPanel.isTransparent());
                    }
                    else if(currentShapeType.equals("FilledRectangle"))
                    {
                        // creates a filled rectangle
                        shape = new Rectangle(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                true,
                                paintPanel.getIsSquare(),
                                gradientType);
                    }
                    else if(currentShapeType.equals("FilledRoundedRectangle"))
                    {
                        // creates a filled round-rectangle
                        shape = new RoundedRectangle(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                true,
                                paintPanel.getIsSquare(),
                                gradientType);
                    }
                    else if(currentShapeType.equals("FilledOval"))
                    {
                        // creates a filled oval
                        shape = new Oval(paintPanel.getForwardColor(),
                                paintPanel.getBackgroundColor(),
                                true,
                                paintPanel.getIsSquare(),
                                gradientType);
                    }
                    else if(currentShapeType.equals("Image")
                            && isDrawImage()){
                        // if user cancels the open image dialog,
                        // then there is nothing to draw.
                        java.awt.Image image;
                        image = Toolkit.getDefaultToolkit().getImage(paintPanel.getImageFilePath());
                        // change the image to the buffered image
                        BufferedImage bufferedImage = Image.toBufferedImage(image);

                        byte[] imageData;
                        try {
                            // change the buffered image to byte array
                            imageData = Image.getCompressedImage(bufferedImage);
                        } catch(Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Error happens while getting the image.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // creates an image
                        shape = new com.jajeem.whiteboard.client.Module.Graphics.Image(
                                paintPanel.getForwardColor(),
                                imageData);
                    }
                    else if(currentShapeType.equals("Erase"))
                    {
                        // creates an erase
                        shape = new Erase(Color.white, 10.0f);
                    }
                    else
                    {
                        return;
                    }

                    Point point;
                    int x = event.getX();
                    int y = event.getY();
                    point = new Point( x, y);
                    // add the point to the shape
                    shape.addPoint(point);
                    try {
                        // add the shape to the whiteboard
                        whiteboard.addShape(shape);
                    }catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while adding the "
                            + "shape to the whiteboard.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    repaint();
                }
            }

            // when the mouse is released 
            public void mouseReleased(MouseEvent event) {
                if (currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle.toString())
                    || currentShapeType.equals("FilledRectangle")
                    || currentShapeType.equals("FilledRoundedRectangle")
                    || currentShapeType.equals("FilledOval")
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Erase.toString()))

                {
                    PaintPanel paintPanel = (PaintPanel)event.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();

                    int currentRightAt;
                    try {
                        // the enquiry of who has the drawing right
                        currentRightAt = whiteboard.getCurrentRightAt();
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while acquiring who has the paint right.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(currentRightAt == paintPanel.getUserid()) {
                        // if this session has the right to draw
                        try {
                            int x = event.getX();
                            int y = event.getY();

                            // add the point to the current shape of whiteboard
                            paintPanel.getWhiteboard().addPointToCurrentShape(x, y);
                        }catch(Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Error happens while adding the point "
                                + "to the shape of whiteboard.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        repaint();
                    }
                } else if (currentShapeType.equals(Shape.ShapeType.Image.toString())
                        && isDrawImage()){
                    // when drawing a shape
                    PaintPanel paintPanel = (PaintPanel)event.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();

                    int currentRightAt = 0;
                    try {
                        // the enquiry of who has the drawing right
                        currentRightAt = whiteboard.getCurrentRightAt();
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while acquiring who has the paint right.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(currentRightAt == paintPanel.getUserid()) {
                        try {
                            int x = event.getX();
                            int y = event.getY();
                            
                            // add the point to the current shape of whiteboard
                            paintPanel.getWhiteboard().addPointToCurrentShape(x, y);

                            // finish the choosing area operation
                            whiteboard.setDrawingFinished(true);
                        }catch(Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Error happens while finishing the choosing area.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // reset the tool panel
                        paintPanel.resetToolPanel();
                        repaint();
                    }
                }
            }
        });

        // listen the action of mouse
        this.addMouseMotionListener(new
        MouseMotionAdapter()
        {
            // when the mouse is moved
            public void mouseMoved(MouseEvent event) {
                PaintPanel paintPanel = (PaintPanel)event.getSource();
                // set state of mouse
                paintPanel.setMouseState("Current Mouse ("+
                        String.valueOf(event.getX())+","
                        +String.valueOf(event.getY())+")");
            }

            // when the mouse is exited 
            public void mouseExited(MouseEvent event) {
                PaintPanel paintPanel = (PaintPanel)event.getSource();
                paintPanel.setMouseState("Prepared");
            }

            // when the mouse is dragged
            public void mouseDragged(MouseEvent event){
                if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Pencil.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle.toString())
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle.toString())
                    || currentShapeType.equals("FilledRectangle")
                    || currentShapeType.equals("FilledRoundedRectangle")
                    || currentShapeType.equals("FilledOval")
                    || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Erase.toString())
                    || (currentShapeType.equals(Shape.ShapeType.Image.toString())
                        && isDrawImage()) )
                {
                    PaintPanel paintPanel = (PaintPanel)event.getSource();
                    Whiteboard whiteboard = paintPanel.getWhiteboard();
                    int currentRightAt = 0;
                    try {
                        // the enquiry of who has the drawing right
                        currentRightAt = whiteboard.getCurrentRightAt();
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,
                            "Error happens while acquiring who has the paint right.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(currentRightAt == paintPanel.getUserid()) {
                        try {
                            int x = event.getX();
                            int y = event.getY();
                            
                            // add the point to the current shape of whiteboard
                            paintPanel.getWhiteboard().addPointToCurrentShape(x, y);
                        }catch(Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                "Error happens while adding the point "
                                + "to the shape of whiteboard.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        repaint();
                    }
                }
            }
        });
    }


    /** Override the paint method */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        int n, m;
        try {
            // Synchronize the local buffer and remote whiteboard.
            n = whiteboard.getShapeVectorSize();
            m = shapeVector.size();
            if(n > m) {
                // If the remote whiteboard adds a shape, then add the shape
                // to the local vector of shapes
                shapeVector.addElement(whiteboard.getCurrentShape());
                m++;
            } else if(n == m && m != 0) {
                // Updates the top element of local vector of shapes
                shapeVector.removeElementAt(m-1);
                shapeVector.addElement(whiteboard.getCurrentShape());
            } else if(n < m && m != 0) {
                // If the remote whiteboard removes the top shape, then
                // removes the top shape in the local vector of shapes
                shapeVector.removeElementAt(m-1);
                m--;
            }
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error happens while synchronizing the local buffer.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // draw the shapes of local buffer to the graphics of paintpanel
        for( int i=0; i < m; i++) {
            // get the shape
            shape = shapeVector.elementAt(i);
            
            if(shape != null)
            {
                if(shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Pencil)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Erase)
                    || shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.TextArea))
                {
                    // draw the shape to the graphics
                    shape.draw(graphics);
                } else if (shape.getShapeType().equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Image)) {
                    // if the shape is an image
                	com.jajeem.whiteboard.client.Module.Graphics.Image image = (com.jajeem.whiteboard.client.Module.Graphics.Image)shape;
                    if(!image.getDrawingFinished()) {
                        // if it does not finish the choosing area
                        image.draw(graphics);
                    } else {
                        // draw the inserting image
                        image.draw(graphics,this);
                    }
                }

            }
        }
    }

    /** Get the forward color */
    public Color getForwardColor(){
        return this.colorData.getForwardColor();
    }

    /** Get the background color*/
    public Color getBackgroundColor(){
        return this.colorData.getBackgroundColor();
    }

    /** Set the mouse state*/
    public void setMouseState(String mouseState) {
        mainFrame.setMouseState(mouseState);
    }

    /** Set the type of current drawing shape and set its cursor */
    public void setCurrentShapeType( String currentShapeType) {
        this.currentShapeType = currentShapeType;
        this.mainFrame.setShapeType(currentShapeType);

        if(currentShapeType.equals("Cursor")){
        	this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Pencil.toString())) {
        	Toolkit tk = Toolkit.getDefaultToolkit();
        	Cursor c = tk.createCustomCursor(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/jajeem/whiteboard/client/Client/Image/Cursors/Pencil.gif")),new Point(15,16),"Cross");
        	
//        	this.setCursor("Pencil",new Point(8,23),"Pencil");
        	this.setCursor(c);
        }
        else if(currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Erase.toString())) {
        	Toolkit tk = Toolkit.getDefaultToolkit();
        	Cursor c = tk.createCustomCursor(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/jajeem/whiteboard/client/Client/Image/Cursors/Erase.gif")),new Point(15,16),"Cross");
        	
//        	this.setCursor("Erase",new Point(8,22),"Erase");
        	this.setCursor(c);
        }
        else if (currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Line.toString())
            || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Oval.toString())
            || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Rectangle.toString())
            || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.RoundedRectangle.toString())
            || currentShapeType.equals("FilledRectangle")
            || currentShapeType.equals("FilledRoundedRectangle")
            || currentShapeType.equals("FilledOval")
            || currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.TextArea.toString())){
        	Toolkit tk = Toolkit.getDefaultToolkit();
        	Cursor c = tk.createCustomCursor(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/jajeem/whiteboard/client/Client/Image/Cursors/Cross.gif")),new Point(15,16),"Cross");
//            this.setCursor("Cross",new Point(15,16),"Cross");
        	this.setCursor(c);
        } else if (currentShapeType.equals(com.jajeem.whiteboard.server.Module.Shape.ShapeType.Image.toString())
                && isDrawImage()) {
        	Toolkit tk = Toolkit.getDefaultToolkit();
        	Cursor c = tk.createCustomCursor(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/jajeem/whiteboard/client/Client/Image/Cursors/Cross.gif")),new Point(15,16),"Cross");
//            this.setCursor("Cross",new Point(15,16),"Cross");
        	this.setCursor(c);
        }
    }

    /** Get the reference of whiteboard*/
    public Whiteboard getWhiteboard(){
        return this.whiteboard;
    }

    /** Set the cursor */
    private void setCursor(String fileName, Point point, String Name){
        try {
            // get the image
            java.awt.Image imageCursor =
                    Toolkit.getDefaultToolkit().getImage(
                    WhiteboardClient.class.getResource(
                    "/client/Client/Image/Cursors/" + fileName + ".gif"));
            
            // set the cursor
            this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    imageCursor, point, Name));
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error happens while setting the cursor image.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Get the current stroke */
    public float getCurrentStroke(){
        return mainFrame.getCurrentStroke();
    }

    /** Get the flag whether the drawing shape is square */
    public boolean getIsSquare(){
        return mainFrame.getIsSquare();
    }

    /** Open the keyboard mode to listen the key pressed */
    public void OpenKeyboardMode(){
        isKeyboardModeOpen = true;
    }

    /** Close the keyboard mode */
    public void CloseKeyboardMode(){
        isKeyboardModeOpen = false;
    }

    /** Create a new object of Textarea class */
    public TextArea CreateTextArea(){
        return new TextArea(colorData.getForwardColor(),fontData);
    }

    /** Get the identity of user */
    public int getUserid(){
        return this.mainFrame.getUserid();
    }

    /** Get the flag whether the drawing empty shape is transparent */
    public boolean isTransparent(){
        return this.mainFrame.isTransparent();
    }

    /** Get the path of image file */
    public String getImageFilePath(){
        return this.mainFrame.getImageFilePath();
    }

    /** Get the flag whether is drawing an image */
    public boolean isDrawImage(){
        return this.mainFrame.isDrawImage();
    }

    /** Reset the toolpanel */
    public void resetToolPanel(){
        this.mainFrame.resetToolPanel();
        setCurrentShapeType("Cursor");
    }

    /**
     * The update method of observer, when the observer is
     * notified that the subject has been changed, it will
     * implement this method.
     */
    public void update(Observable obsv, Object arg){
        if(obsv instanceof GradientData) {
            this.gradientType = ((GradientData)obsv).getGradientType();
        }
    }
}
