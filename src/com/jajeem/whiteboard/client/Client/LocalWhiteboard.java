package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : LocalWhiteboard.java
 * Description  : The LocalWhiteboard implements the Whiteboard interface,
 *                and can provide a whiteboard locally. 
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */

import java.awt.*;
import java.rmi.RemoteException;
import java.util.Stack;
import java.util.Vector;

import com.jajeem.whiteboard.server.Module.Shape;
import com.jajeem.whiteboard.server.Module.Whiteboard;

/**
 * Class LocalWhiteboard implements a whiteboard locally, which can be used
 * without connecting to the server. 
 */
public class LocalWhiteboard implements Whiteboard {

    /** The place to store shapes */
    private Vector<Shape> shapeVector = null;

    /** A stack implements the undo&redo operations */
    private Stack<Shape> shapeStack = null;

    /** The flag whether it is updated */
    private boolean isUpdated;

    /** The constructor of local whiteboard */
    public LocalWhiteboard(){
        shapeVector = new Vector<Shape>();
        shapeStack = new Stack<Shape>();
    }

    /** Add the shape to the whiteboard */
    public void addShape(Shape shape){
        shapeVector.addElement(shape);
        isUpdated = true;
    }

    /** Get the latest inserting shape */
    public Shape getCurrentShape(){
        return shapeVector.elementAt(shapeVector.size()-1);
    }

    /** Get the size of shapes in whiteboard */
    public int getShapeVectorSize(){
        return shapeVector.size();
    }

    /** Get the shape of specific index */
    public Shape getShapeVectorElementAt(int index){
        return shapeVector.elementAt(index);
    }

    /** Add the point to the current shape */
    public void addPointToCurrentShape(int x, int y){
        Point point;
        Shape shape = shapeVector.elementAt(shapeVector.size()-1);
        point = new Point( x, y);
        shape.addPoint(point);
        isUpdated = true;
    }

    /** Check the isUpdated state */
    public boolean isUpdated(int userid){
        if(isUpdated)
        {
            isUpdated = false;
            return true;
        }
        else
            return false;
    }

    /** Append the character to the text area */
    public void textAppend(char text){
        Shape shape = shapeVector.elementAt(shapeVector.size()-1);
        shape.textAppend(text);
        isUpdated = true;
    }

    /** Remove the latest shape added */
    public void removeTheCurrentShape(){
        shapeVector.removeElementAt(shapeVector.size() - 1);
        isUpdated = true;
    }

    /** Delete the last character of text area */
    public void textDeleteTheLastLetter(){
        Shape shape = shapeVector.elementAt(shapeVector.size()-1);
        shape.textDeleteTheLastLetter();
        isUpdated = true;
    }

    /** Create a new whiteboard */
    public void createNewWhiteboard() {
        shapeVector = new Vector<Shape>();
        isUpdated = true;
    }
    /** Set the drawing action finished */
    public void setDrawingFinished(boolean isFinished){
        Shape theLastOneShape = shapeVector.elementAt(shapeVector.size()-1);
        theLastOneShape.setIsFinished(isFinished);
    }
    /** Implement the undo operation */
    public void undo() {
        int n = shapeVector.size();
        if( n != 0) {
            // add the shape to the stack
            shapeStack.push(getCurrentShape());
            removeTheCurrentShape();
        }
    }
    /** Implement the redo operation */
    public void redo() {
        int n = shapeStack.size();
        if( n != 0) {
            // pop the top element of stack,
            // and add it to the whiteboard
            shapeVector.addElement(shapeStack.pop());
            isUpdated = true;
        }
    }
    
    /** the empty methods in order to implement the interface */
    public void grantRightTo(int index){}
    public void withdrawRight() {}
    public int getCurrentRightAt() {return 0;}
    public String isChatInfoUpdated(int userid){return null;}
    public void sendMessages(int userid, String messages) {}
    public int userRegistry(String username, String password){return 0;}
    public void userLeave(int userid){}
    public String[][] getUserlist(){return null;}
    public boolean isUserlistUpdated(int userid){return false;}
    public boolean isAdministratorLeave(){return false;}
}
