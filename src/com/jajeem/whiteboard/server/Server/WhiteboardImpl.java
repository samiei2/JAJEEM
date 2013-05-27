package com.jajeem.whiteboard.server.Server;
/**
 * File Name    : WhiteboardImpl.java
 * Description  : This is the implementation of Whiteboard interface.
 * Author       : Asset Issabayev
 * Team         : TheThreeBytes
 */
import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;
import java.util.Vector;

import com.jajeem.whiteboard.server.Module.Shape;
import com.jajeem.whiteboard.server.Module.Whiteboard;

/**
 * Class WhiteboardImpl is the implementation of the Whiteboard interface in
 * the server-end. It provides the method to change the whiteboard data, which
 * is the main information of the current paint. The whiteboardImpl stores a
 * vector which contains the shapes which are drawn by users.
 */
public class WhiteboardImpl extends UnicastRemoteObject
        implements Whiteboard {

    /** The place to store shapes */
    private Vector<Shape> shapeVector = null;

    /** A stack implements the undo&redo operations */
    private Stack<Shape> shapeStack = null;

    /** The number of current users */
    private int numUsers;

    /** Declare a refence of one shape */
    private Shape shape;

    /** The flags whether the whiteboard is updated */
    private boolean[] isUpdated;
    
    /** The flags whether the users' information is updated */
    private boolean[] isUserlistUpdated;

    /** The list of user information */
    private String[][] userList;

    /** The flag indicate whether the administrator left */
    private boolean isAdministratorLeave;

    /** The maximum of user number in per session */
    private final int MaxUserPerSessions = 10;

    /** The name of one session */
    private String sessionsName;

    /** The password of this session */
    private String password;

    /** The chat information */
    private String[] chatInfo;

    /** The flag whether the chat information has been changed */
    private boolean[] isChatInfoUpdated;

    /** The index who has the current right to draw */
    private int currentRightAt;
    
    /** The constructor to initialize variables */
    public WhiteboardImpl(String sessionsName, String adminName,
                                 String password) throws RemoteException {
        // use the standard SSL-based custom socket factory classes
        // to creates the Remote Object
        super(WhiteboardServer.WHITEBOARD_PORT,                
                new javax.rmi.ssl.SslRMIClientSocketFactory(),
                new javax.rmi.ssl.SslRMIServerSocketFactory());       
        shapeVector = new Vector<Shape>();
        shapeStack = new Stack<Shape>();
        this.sessionsName = sessionsName;
        // initialize the session information
        numUsers = 0;
        isUpdated = new boolean[10];
        isUserlistUpdated = new boolean[10];
        userList = new String[10][3];
        chatInfo = new String[10];
        isChatInfoUpdated = new boolean[10];

        // initialize the information of the administrator
        userList[0][0] = adminName;
        userList[0][1] = "Administrator";
        this.password = password;
        userList[0][2] = "¡Ì";
        currentRightAt = 0;
        isAdministratorLeave = false;
        numUsers++;        
    }
    
    /** Add the shape to the whiteboard */
    public void addShape(Shape shape) throws RemoteException{
        shapeVector.addElement(shape);
        notifyAllWhiteboardUpdated();
    }

    /** Get the latest inserting shape */
    public Shape getCurrentShape() throws RemoteException{
        return shapeVector.elementAt(shapeVector.size()-1);
    }
    
    /** Get the size of shapes in whiteboard */
    public int getShapeVectorSize() throws RemoteException{
        return shapeVector.size();
    }

    /** Get the shape of specific index */
    public Shape getShapeVectorElementAt(int index)
        throws RemoteException{
        return shapeVector.elementAt(index);
    }
    
    /** Add the point to the current shape */
    public void addPointToCurrentShape(int x, int y)
        throws RemoteException{
        Point point;
        shape = shapeVector.elementAt(shapeVector.size()-1);
        point = new Point( x, y);
        shape.addPoint(point);
        notifyAllWhiteboardUpdated();
    }
    
    /** Check the isUpdated state */
    public boolean isUpdated(int userid) throws RemoteException{
        if(isUpdated[userid])
        {
            isUpdated[userid] = false;
            return true;
        }
        else
            return false;
    }

    /** Check the user list whether has been changed */
    public boolean isUserlistUpdated(int userid) throws RemoteException{
        if(isUserlistUpdated[userid])
        {
            isUserlistUpdated[userid] = false;
            return true;
        }
        else
            return false;
    }

    /** Check the chat information whether has been changed */
    public String isChatInfoUpdated(int userid) throws RemoteException{
        if(isChatInfoUpdated[userid])
        {
            isChatInfoUpdated[userid] = false;
            return chatInfo[userid];
        }
        return null;
    }

    /** Check whether the administrator leaves */
    public boolean isAdministratorLeave() throws RemoteException{
        return isAdministratorLeave;
    }

    /** The registry of one user */
    public int userRegistry(String username, String password) throws RemoteException{
        for(int i=0; i < 10; i++){
            if(userList[i][0] == null) {
                // if there is a vacancy
                if (i != 0) {
                    // if it is not the administrator
//                    if (!this.password.equals(password)) {
//                        // the password is incorrect
//                        return -2;
//                    }
                    // initialize the user information
                    userList[i][0] = username;
                    userList[i][1] = "User";
                    userList[i][2] = "";
                }
                // notify all the users that the user
                // information has been changed 
                notifyAllUserListUpdated();
                return i;
            }
        }
        // the whiteboard has reached the maximum users
        return -1;
    }

    /** Get the list of user information */
    public String[][] getUserlist() throws RemoteException{
        return userList;
    }

    /** Clean the user information when a user leaves */
    public void userLeave(int userid) throws RemoteException{
        userList[userid][0] = null;
        userList[userid][1] = null;
        if(userid == 0) {
            isAdministratorLeave = true;
        }
        else{
            notifyAllUserListUpdated();
        }
    }
    
    /** Append the character to the text area */
    public void textAppend(char text)
        throws RemoteException{     
        shape = shapeVector.elementAt(shapeVector.size()-1);
        shape.textAppend(text);
        notifyAllWhiteboardUpdated();
    }
    
    /** Remove the latest shape added */
    public void removeTheCurrentShape() throws RemoteException{
        shapeVector.removeElementAt(shapeVector.size() - 1);
        notifyAllWhiteboardUpdated();
    }

    /** Delete the last character of text area */
    public void textDeleteTheLastLetter()
        throws RemoteException{

        shape = shapeVector.elementAt(shapeVector.size()-1);
        shape.textDeleteTheLastLetter();
        notifyAllWhiteboardUpdated();
    }

    /** Grant the paint right */
    public void grantRightTo(int index) throws RemoteException {
        if(index < MaxUserPerSessions) {
            userList[currentRightAt][2] = "";
            // set the current right to the specific user
            currentRightAt = index;
            userList[currentRightAt][2] = "¡Ì";
            notifyAllUserListUpdated();
        }
    }

    /** Withdraw the paint right */
    public void withdrawRight() throws RemoteException {
        // if the right has already in the administrator,
        // it will do no modification.
        if (currentRightAt != 0) {
            userList[currentRightAt][2] = "";
            // set the current right to the administrator
            currentRightAt = 0;
            userList[currentRightAt][2] = "¡Ì";
            notifyAllUserListUpdated();
        }
    }

    /** Get the index who has the current right to draw */
    public int getCurrentRightAt() throws RemoteException {
        return this.currentRightAt;
    }

    /** Create a new whiteboard */
    public void createNewWhiteboard() throws RemoteException {
        shapeVector = new Vector<Shape>();
        notifyAllWhiteboardUpdated();
    }

    /** Alter all the flags whether the whiteboard has been updated */
    private void notifyAllWhiteboardUpdated(){
        for(int i=0; i < MaxUserPerSessions; i++){
            if(userList[i][0] != null) {
                isUpdated[i] = true;
            }
        }
    }

    /** Alter all the flags whether the list of users has been updated */
    private void notifyAllUserListUpdated(){
        for(int i=0; i < MaxUserPerSessions; i++){
            if(userList[i][0] != null) {
                isUserlistUpdated[i] = true;
            }
        }
    }

    /** Alter all the flags whether the chat information has been updated */
    private void notifyAllChatInfoUpdated(){
        for(int i=0; i < MaxUserPerSessions; i++){
            if(userList[i][0] != null) {
                isChatInfoUpdated[i] = true;
            }
        }
    }

    /** Set the drawing action finished */
    public void setDrawingFinished(boolean isFinished)
            throws RemoteException{
        Shape theLastOneShape = shapeVector.elementAt(shapeVector.size()-1);
        theLastOneShape.setIsFinished(isFinished);
    }

    /** Implement the undo operation */
    public void undo() throws RemoteException{
        int n = shapeVector.size();
        if( n != 0) {
            // add the shape to the stack
            shapeStack.push(getCurrentShape());
            removeTheCurrentShape();
        }
    }

    /** Implement the redo operation */
    public void redo() throws RemoteException{
        int n = shapeStack.size();
        if( n != 0) {
            // pop the top element of stack,
            // and add it to the whiteboard
            shapeVector.addElement(shapeStack.pop());
            notifyAllWhiteboardUpdated();
        }
    }

    /** Send a message to users */
    public void sendMessages(int userid, String messages) throws RemoteException{
        String username = userList[userid][0];
        int indexOfBlank = messages.indexOf(" ");
        if (messages.charAt(0) == '/' && indexOfBlank != -1) {
            // send a private message to peers 
            // get the string of command
            String commandStr = messages.substring(1,indexOfBlank);
            // get the string of message 
            String messagesStr = messages.substring(indexOfBlank+1);
            // get the names of user that will be sent  
            String[] receiveUsersname = commandStr.split(",");

            for(String name: receiveUsersname) {
                for(int i=0;i<MaxUserPerSessions;i++){
                    // find the user that the message will be sent
                    if(userList[i][0] != null
                            && userList[i][0].equals(name)) {
                        // change the chat information of that user
                        chatInfo[i] = "[private msg from '" + username + "'] " + messagesStr;
                        isChatInfoUpdated[i] = true;
                        break;
                    }
                }
            }
            // updates the chat text of the sender
            chatInfo[userid] = "[private msg to '" + commandStr + "'] " + messagesStr;
            isChatInfoUpdated[userid] = true;
        }
        else {
            // send message to public
            for(int i=0;i<MaxUserPerSessions;i++) {
                // change all the users' chat information
                chatInfo[i] = username +":" + messages;
                notifyAllChatInfoUpdated();
            }
        }
    }
}
