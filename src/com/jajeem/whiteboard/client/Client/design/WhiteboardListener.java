package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : WhiteboardListener.java
 * Description  : The WhiteboardListener implements the listener of the remote
 *                whiteboard, and it will check the whiteboard whether it is
 *                changed for a specific internal time.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import javax.swing.*;

import com.jajeem.whiteboard.server.Module.Whiteboard;

import java.util.Observable;

/**
 * Class WhiteboardListener is a listener for the remote whiteboard. If the
 * remote whiteboard is changed, it calls the related components to update
 * its information.
 */
public class WhiteboardListener implements Runnable{

    /** Delcare a reference of PaintPanel class */
    private PaintPanel paintPanel;
    
    /** Delcare a reference of Whiteboard class */
    private Whiteboard whiteboard;

    /** Delcare a reference of UserListPanel class */
    private UserListPanel userlistPanel;

    /** Delcare a reference of MainFrame class */
    private MainFrame mainFrame;

    /** The identity of current user */
    private int userid;

    /**
     * The constructor of this listener
     * to initialize the instance variables
     */
    public WhiteboardListener(Whiteboard whiteboard, PaintPanel paintPanel,
                              UserListPanel userlistPanel, int userid,
                              MainFrame mainFrame){
        this.whiteboard = whiteboard;
        this.paintPanel = paintPanel;
        this.userid = userid;
        this.userlistPanel = userlistPanel;
        this.mainFrame = mainFrame;
    }

    public void run() {
        while(!mainFrame.isUserLeave()) {
            try {
                // the interval time to detect the state of remote whiteboard
                Thread.sleep(100);

                if (whiteboard.isUpdated(userid)) {
                    // if the whiteboard is updated
                    paintPanel.repaint();
                }
                
                // If it is not the whiteboard running in local.
                if(!mainFrame.isLocalWhiteboard()) {
                    if (whiteboard.isUserlistUpdated(userid)) {
                        // if the user information is changed
                        userlistPanel.update(whiteboard.getUserlist());
                    }
                    if (whiteboard.isAdministratorLeave()) {
                        // if the administrator is left
                        if(userid != 0){
                            // if the user is not an administrator,
                            // close this session.
                            JOptionPane.showMessageDialog(null,
                               "The administrator has already left and the "
                               + "whiteboard is closed.",
                               "Error", JOptionPane.ERROR_MESSAGE);
                            mainFrame.window_exit();
                        }
                    }
                    String messages = whiteboard.isChatInfoUpdated(userid);
                    if ( messages != null) {
                        mainFrame.appendChatText(messages);
                    }
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                   "Error happens while detecting the updates " + 
                   "of remote whiteboard",
                   "Error", JOptionPane.ERROR_MESSAGE);
                try {
                    // closing this session 
                    mainFrame.window_exit();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                       "Error happens while closing this session.",
                       "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }
}
