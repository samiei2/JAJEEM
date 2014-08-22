package com.jajeem.whiteboard.server.Module;
/**
 * File Name    : Sessions.java
 * Description  : This file contains a Sessions interface, which
 *                is the interface between the client and server. 
 * Author       : Asset Issabayev
 * Team         : TheThreeBytes
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface Sessions is the proxy of the SessionsImpl in the client.
 * A client can call the remote method by using this interface. 
 */
public interface Sessions extends Remote {

    /** Create a new session (or whiteboard) */
    public int createSessions(String sessionsName, String adminName,
                                 String password) throws RemoteException;

    /** Get the list of sessions information */
    public String[][] getSessionList() throws RemoteException;

    /** Close the specific session */
    public void closeSession(int sessionID) throws RemoteException;

    /**
     * The connection registry which contributes to record
     * the number of current users
     */
    public int connectionRegistry() throws RemoteException;

    /**
     * Check the state of session list whether it has been
     * changed according the specific connection identity.
     */
    public boolean isSessionlistUpdated(int connectID) throws RemoteException;

    /**
     * One user joins a specific session and the number
     * of users in this session increases.
     */
    public void joinSession(int sessionID) throws RemoteException;

    /**
     * One user leaves a specific session and the number
     * of users in this session decreases.
     */
    public void leaveSession(int sessionID) throws RemoteException;

    /** Get the name of specific session */
    public String getSessionName(int sessionID) throws RemoteException;
}
