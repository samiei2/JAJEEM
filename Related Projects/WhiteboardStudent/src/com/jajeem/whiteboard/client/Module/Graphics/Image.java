package com.jajeem.whiteboard.client.Module.Graphics;
/**
 * File Name    : Image.java
 * Description  : The Image class implements the information and
 *                drawing method of an image.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * Class Image provides implements the information and
 * drawing method of an image. First, when the user does
 * not finish the choosing which area to draw, it will
 * draw a dashed rectangle. And when the choosing area
 * operation finished, the dashed rectangle will disappear
 * and the picture will be displayed.
 */
public class Image extends com.jajeem.whiteboard.server.Module.Shape{
    /** The byte array to store the image */
    private byte[] imageData = null;

    /**
     * The flag whether have already
     * read the byte array into the buffered image
     */
    private transient boolean hasRead = false;

    /** The buffered image which is not the part of the Serializable */
    private transient java.awt.Image image = null;

    /** The constructor of Image class */
    public Image(Color color, byte[] imageData) {

        // invoke the parent constructor
        super(color);

        // set the type of shape
        this.shapeType = com.jajeem.whiteboard.server.Module.Shape.ShapeType.Image;

        // set the flag of finish
        this.isFinished = false;

        // get the reference of the byte array
        this.imageData = imageData;
    }
    
    /** Define the method inherited from the abstract class Shape */
    public void draw(Graphics graphics) {
        // get the number of points 
        int n = pointVector.size();

        if ( n == 1)
            return;
        Graphics2D graphics2D = (Graphics2D)graphics;

        Point p1, p2;

        // get the first point
        p1 = pointVector.elementAt(0);

        // remove the middle points
        for (int i = 1; i < n-1; i++) {
            pointVector.removeElementAt(1);
        }

        // get the last point
        p2 = pointVector.elementAt(1);

        // compute the correct start position
        // and size to draw the choosing area
        int x = p2.x > p1.x ? p1.x : p2.x;
        int y = p2.y > p1.y ? p1.y : p2.y;
        int width = Math.abs(p2.x - p1.x);
        int height = Math.abs(p2.y - p1.y);

        // get the current stroke
        Stroke graphicsStroke = graphics2D.getStroke();
        // create the dash stroke
        Stroke dashStroke = new BasicStroke(1,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{16, 4}, 0);
        // set the current stroke
        graphics2D.setStroke(dashStroke);
        
        graphics2D.setColor(this.forwardColor);

        Rectangle2D rectangle = new Rectangle2D.Double(
                x, y, width, height);
        // draw the choosing area
        graphics2D.draw(rectangle);

        // reset the stroke
        graphics2D.setStroke(graphicsStroke);
    }

    /** Get the flag whether drawing is finished */
    public boolean getDrawingFinished(){
        return  this.isFinished;
    }
    
    /** The method to draw the image */
    public void draw(Graphics graphics, ImageObserver imageObserver) {
        // get the number of points
        int n = pointVector.size();

        if ( n == 1)
            return;
        Graphics2D graphics2D = (Graphics2D)graphics;

        Point p1, p2;
        // get the first point
        p1 = pointVector.elementAt(0);
        // remove the middle points
        for (int i = 1; i < n-1; i++) {
            pointVector.removeElementAt(1);
        }
        // get the last point
        p2 = pointVector.elementAt(1);
        
        // compute the correct start position
        // and size to draw the choosing area
        int x = p2.x > p1.x ? p1.x : p2.x;
        int y = p2.y > p1.y ? p1.y : p2.y;
        int width = Math.abs(p2.x - p1.x);
        int height = Math.abs(p2.y - p1.y);

        if(!hasRead)  {
            // if the image didn't read before
            try {
                // get the image from the byte array
                BufferedImage bufferedImage = getDecompressedImage(imageData);

                // create an image from the buffered image
                image = toImage(bufferedImage);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                            "Error happens while drawing the image.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            hasRead = true;
        }

        // draw this image
        graphics2D.drawImage(image, x, y, width, height, imageObserver);
    }

    /**
     * This method returns a buffered image with the contents of an image
     * Reference from:
     * http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
     */
    public static BufferedImage toBufferedImage(java.awt.Image image) {

       // This code ensures that all the pixels in the image are loaded
       image = new ImageIcon(image).getImage();

       // Determine if the image has transparent pixels; for this method's
       // implementation, see Determining If an Image Has Transparent Pixels
       boolean hasAlpha = hasAlpha(image);

       // Create a buffered image with a format that's compatible with the screen
       BufferedImage bimage = null;
       GraphicsEnvironment ge = GraphicsEnvironment
              .getLocalGraphicsEnvironment();
       try {
           // Determine the type of transparency of the new buffered image
           int transparency = Transparency.OPAQUE;
           if (hasAlpha) {
              transparency = Transparency.BITMASK;
           }

           // Create the buffered image
           GraphicsDevice gs = ge.getDefaultScreenDevice();
           GraphicsConfiguration gc = gs.getDefaultConfiguration();
           bimage = gc.createCompatibleImage(image.getWidth(null), image
                  .getHeight(null), transparency);
       } catch (HeadlessException e) {
           // The system does not have a screen
           return null;
       }

       if (bimage == null) {
           // Create a buffered image using the default color model
           int type = BufferedImage.TYPE_INT_RGB;
           if (hasAlpha) {
              type = BufferedImage.TYPE_INT_ARGB;
           }
           bimage = new BufferedImage(image.getWidth(null), image
                  .getHeight(null), type);
       }

       // Copy image to buffered image
       Graphics g = bimage.createGraphics();

       // Paint the image onto the buffered image
       g.drawImage(image, 0, 0, null);
       g.dispose();

       return bimage;
    }

    /**
     * This method returns true if the specified image has transparent pixels
     * Reference from:
     * http://www.exampledepot.com/egs/java.awt.image/HasAlpha.html
     */
    public static boolean hasAlpha(java.awt.Image image) {
       // If buffered image, the color model is readily available
       if (image instanceof BufferedImage) {
           BufferedImage bimage = (BufferedImage) image;
           return bimage.getColorModel().hasAlpha();
       }

       // Use a pixel grabber to retrieve the image's color model;
       // grabbing a single pixel is usually sufficient
       PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
       try {
           pg.grabPixels();
       } catch (InterruptedException e) {
       }

       // Get the image's color model
       ColorModel cm = pg.getColorModel();
       return cm.hasAlpha();
    }



   /**
    * This method returns an Image object from a buffered image.
    * Reference from:
    * http://www.exampledepot.com/egs/java.awt.image/Buf2Image.html
    */
    public static java.awt.Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    /**
     * Create a byte array from a Buffered Image
     */
    public static byte[] getCompressedImage(BufferedImage image)
            throws IOException{
        byte[] imageData = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        imageData = baos.toByteArray();

        return imageData;
    }

    /**
     * Create a Buffered Image from a byte array
     */
    public static BufferedImage getDecompressedImage(byte[] imageData)
        throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }
}
