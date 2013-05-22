package com.jajeem.whiteboard.client.Module.Data;
/**
 * File Name    : ColorData.java
 * Description  : A Class to store the color data
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.Color;
import java.io.Serializable;
import java.util.Observable;

/**
 * Class ColorData provides an implementation of the place
 * to store the current forward color of background color
 * of the system. And it extends the Observable interface
 * to become the 'subject' described in the Observer pattern
 * by the Design Pattern books.
 */
public class ColorData extends Observable implements Serializable {

    /** The forward color */
    private Color forwardColor;

    /** The background color */
    private Color backgroundColor;

    /**
     * The constructor of ColorData
     * @param forwardColor The forword color
     * @param backgroundColor The background color
     */
    public ColorData(Color forwardColor, Color backgroundColor) {
        this.forwardColor = forwardColor;
        this.backgroundColor = backgroundColor; 
    }

    /**
     * Set the forward color
     * @param forwardColor The specific forward color
     */
    public void setForwardColor(Color forwardColor){
        this.forwardColor = forwardColor;
        // sets this data has changed
        this.setChanged();
        // notify all the observers to update
        this.notifyObservers();
    }

    /**
     * Get the forward color
     * @return The forward color
     */
    public Color getForwardColor(){
        return this.forwardColor;
    }

    /**
     * Set the background color
     * @param backgroundColor The specific background color
     */
    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        // sets this data has changed
        this.setChanged();
        // notify all the observers to update
        this.notifyObservers();
    }

    /**
     * Get the background color
     * @return The background color
     */
    public Color getBackgroundColor(){
        return this.backgroundColor;
    }

}
