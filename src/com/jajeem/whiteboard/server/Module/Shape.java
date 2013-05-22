package com.jajeem.whiteboard.server.Module;
/**
 * File Name    : Shape.java
 * Description  : The Shape is an abstract class of the drawing shape.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

/**
 * Abstract class Shape is the original model of all the shape. The most
 * important abstract method is the draw method, which are implemented
 * according to different shape.
 */
public abstract class Shape implements Serializable {

    /** Declare a enum of shape type */
	public enum ShapeType {
		Pencil,
		Line,
        Rectangle,
        RoundedRectangle,
        Oval,
        Erase,
        TextArea,
        Image
	}

    /** The type of shape */
	protected ShapeType shapeType;

    /** The forward color */
    protected Color forwardColor;

    /** The background color */
    protected Color backgroundColor;

    /** The place to store the points */
    protected Vector<Point> pointVector;

    /** The flag whether the shape is filled */
    protected boolean isFilled;

    /** The flag whether the shape is square */
    protected boolean isSquare;

    /** The size of stroke */
    protected float stroke;

    /** The buffered string of text */ 
    protected StringBuffer textInput;

    /** The flag whether the drawing is finished */
    protected boolean isFinished;

    /**
     * The constructor of Shape class
     * @param color The specific color 
     */
    public Shape(Color color) {
        this.forwardColor = color;
        this.pointVector = new Vector<Point>();
    }

    /**
     * The constructor of Shape class
     * @param forwardColor The specific forward color
     * @param backgroundColor The background forward color
     */
    public Shape(Color forwardColor, Color backgroundColor){
        this.forwardColor = forwardColor;
        this.backgroundColor = backgroundColor;
        this.pointVector = new Vector<Point>();
    }

    /**
     * The constructor of Shape class
     * @param color The specific color
     * @param stroke The specific stroke 
     */
    public Shape(Color color, float stroke) {
        this.forwardColor = color;
        this.pointVector = new Vector<Point>();
        this.stroke = stroke;
    }

    /**
     * The constructor of Shape class
     * @param forwardColor The specific forward color
     * @param backgroundColor The background forward color
     * @param stroke The specific stroke
     * @param isSquare The flag whether it is square
     */
    public Shape(Color forwardColor, Color backgroundColor, float stroke, boolean isSquare) {
        this.forwardColor = forwardColor;
        this.backgroundColor = backgroundColor;
        this.pointVector = new Vector<Point>();
        this.stroke = stroke;
        this.isSquare = isSquare;
    }

    /**
     * Add the point to the shape
     * @param point The specific point 
     */
    public void addPoint(Point point)
    {
        pointVector.addElement(point);
    }

    /**
     * The abstract method draw
     * @param graphics The specific graphics
     */
    public abstract void draw(Graphics graphics);

    /** Get the type of this shape */
    public ShapeType getShapeType(){
        return shapeType;
    }

    /** Append the character to the text */
    public void textAppend(char text) {
        textInput.append(text);
    }

    /** Delete the last character of the text */
    public void textDeleteTheLastLetter(){
        int length = textInput.length();
        if(length != 0) {
            textInput.deleteCharAt(length-1);
        }
    }

    /** Set the drawing shape finished */
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

}
