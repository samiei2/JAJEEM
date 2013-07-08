package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : SessionsListener.java
 * Description  : The SessionsListener implements the listener of the remote
 *                server, which implements the session management.
 * Author       : Ruxin Hou
 * Team         : TheThreeBytes
 */
import com.jajeem.whiteboard.server.Module.Sessions;

import javax.swing.*;

/**
 * Class SessionsListener provides an implementation of listener
 * to listen the remote server, and updates the session list in
 * the wthieboard client. 
 */
public class SessionsListener implements Runnable{

    /** Declare a reference of the session*/
    private Sessions sessions;

    /** Declare a reference of the WhiteboardClient */
    private WhiteboardClient whiteboardClient;

    /** The identity of connection */
    private int connectID;

    /** The constructor of Session listener */
    public SessionsListener(Sessions sessions,
                            WhiteboardClient whiteboardClient,
                            int connectID){
        this.sessions = sessions;
        this.whiteboardClient = whiteboardClient;
        this.connectID = connectID;
    }

    public void run() {
        while(whiteboardClient.getIsRunSessionsListener()) {
            try {
                // the interval time to detect the state of server
                Thread.sleep(1000);
                // if the session list updated, then
                // updates the local sessions list.
                if (sessions.isSessionlistUpdated(connectID)) {
                    whiteboardClient.updateList(sessions.getSessionList());
                }              
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                       "Error happens while updating the session list.",
                       "Error", JOptionPane.ERROR_MESSAGE);
//                System.exit(0);
            }
        }
    }
}
