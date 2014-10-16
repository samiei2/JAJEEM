package com.jajeem.whiteboard.client.Module.Data;
/**
 * File Name    : GradientData.java
 * Description  : The GradientData Class provides a place to store
 *                the information of gradient type of the system.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.io.Serializable;
import java.util.Observable;

/**
 * Class GraidentData provides the information of gradient type 
 * and data which can be shared by the system components.
 */
public class GradientData extends Observable implements Serializable{

    /** Define the gradient type */
    public enum GradientType {
		Normal,
		Horizontal,
        Vertical,
        Diagonal
	}

    /** The current type of gradient. */
	private GradientData.GradientType gradientType;

    /**
     * The constructor
     */
    public GradientData(){
        this.gradientType = GradientData.GradientType.Normal;
    }

    /**
     * Get the type of gradient
     * @return The type of current gradient
     */
    public GradientType getGradientType(){
        return this.gradientType;
    }

    /**
     * Set the type of gradient
     * @param gradientType The specific type of gradient
     */
    public void setGradientType(GradientType gradientType) {
        this.gradientType = gradientType;
        // sets this data has changed
        this.setChanged();
        // notify all the observers to update
        this.notifyObservers();
    }
}
