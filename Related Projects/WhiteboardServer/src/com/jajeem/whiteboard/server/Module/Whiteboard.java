package com.jajeem.whiteboard.server.Module;
/**
 * File Name    : Whiteboard.java
 * Description  : This file contains a Whiteboard interface, which
 *                is the interface between the client and server.
 * Author       : Asset Issabayev
 * Team         : TheThreeBytes
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface Whiteboard is the proxy of the WhiteboardImpl in the client.
 * A client can call the remote method by using this interface.
 */
public interface Whiteboard extends Remote{

    /** Add the shape to the whiteboard */
    public void addShape(Shape shape) throws RemoteException;

    /** Get the latest inserting shape */
    public Shape getCurrentShape() throws RemoteException;

    /** Get the size of shapes in whiteboard */
    public int getShapeVectorSize() throws RemoteException;

    /** Get the shape of specific index */
    public Shape getShapeVectorElementAt(int index) throws RemoteException;

    /** Add the point to the current shape */
    public void addPointToCurrentShape(int x, int y) throws RemoteException;

    /** Check the isUpdated state */
    public boolean isUpdated(int userid) throws RemoteException;

    /** The registry of one user */
    public int userRegistry(String username, String password) throws RemoteException;

    /** Clean the user information when a user leaves */
    public void userLeave(int userid) throws RemoteException;

    /** Get the list of user information */
    public String[][] getUserlist() throws RemoteException;

    /** Check the user list whether has been changed */
    public boolean isUserlistUpdated(int userid) throws RemoteException;

    /** Check whether the administrator leaves */
    public boolean isAdministratorLeave() throws RemoteException;

    /** Append the character to the text area */
    public void textAppend(char text) throws RemoteException;

    /** Remove the latest shape added */
    public void removeTheCurrentShape() throws RemoteException;

    /** Delete the last character of text area */
    public void textDeleteTheLastLetter() throws RemoteException;

    /** Grant the paint right */
    public void grantRightTo(int index) throws RemoteException;

    /** Withdraw the paint right */
    public void withdrawRight() throws RemoteException;

    /** Get the index who has the current right to draw */
    public int getCurrentRightAt() throws RemoteException;

    /** Create a new whiteboard */
    public void createNewWhiteboard() throws RemoteException;

    /** Set the drawing action finished */
    public void setDrawingFinished(boolean isFinished) throws RemoteException;

    /** Implement the undo operation */
    public void undo() throws RemoteException;

    /** Implement the redo operation */
    public void redo() throws RemoteException;

    /** Check the chat information whether has been changed */
    public String isChatInfoUpdated(int userid) throws RemoteException;

    /** Send a message to users */
    public void sendMessages(int userid, String messages) throws RemoteException;
}
