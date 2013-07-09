package com.jajeem.whiteboard.server.Server;
/**
 * File Name    : SessionsImpl.java
 * Description  : This is the implementation of sessions interface. 
 * Author       : Asset Issabayev
 * Team         : TheThreeBytes
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.jajeem.whiteboard.server.Module.Sessions;

/**
 * Class SessionsImpl is the implementation of the Sessions interface in
 * the server-end. And it implements the methods defined in the
 * interface. This class provides the method to get the information
 * of sessions.
 */
public class SessionsImpl extends UnicastRemoteObject
        implements Sessions {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1960919987230605311L;

	/** The place to store whiteboards */
    private WhiteboardImpl sessions[];

    /** The maximum number of whiteboards */
    private int sessionsMaxNum;

    /** The number of current whiteboards */
    private int sessionsNum;

    /** The maximum users number of per whiteboard */
    private final int oneSessionMaxNum = 10;

    /** The name of host*/
    private String hostname;

    /** The place to store the information of whiteboards */
    private String[][] sessionList;

    /** The current users of each whiteboard */
    private int[] usersNumofEachSession;

    /** The flags whether the sessions information has been changed */
    private String[] isSessionListUpdated;

    /** The maximum of connecting users */
    private int maxNumofPeople;

    /** The registry of rmi server */
    Registry registry;

    /** The constructor to initialize the instance variables */
    public SessionsImpl(String hostname, int sessionsMaxNum) throws RemoteException {
        // use the standard SSL-based custom socket factory classes
        // to creates the Remote Object
//        super(WhiteboardServer.SESSIONS_PORT,
//                new javax.rmi.ssl.SslRMIClientSocketFactory(),
//                new javax.rmi.ssl.SslRMIServerSocketFactory());
    	super(WhiteboardServer.SESSIONS_PORT);
        this.hostname = hostname;
        this.sessionsMaxNum = sessionsMaxNum;
        this.sessionsNum = 0;
        sessions = new WhiteboardImpl[sessionsMaxNum];
        usersNumofEachSession = new int[sessionsMaxNum];
        // the list of sessions has five columns 
        sessionList = new String[sessionsMaxNum][5];
        // the maximum users of connecting the server
        maxNumofPeople = sessionsMaxNum*oneSessionMaxNum;
        
        isSessionListUpdated = new String[maxNumofPeople];
        System.out.println("Sessions Create");

        // Create SSL-based registry
//        registry = LocateRegistry.createRegistry(WhiteboardServer.WHITEBOARD_PORT,
//            new javax.rmi.ssl.SslRMIClientSocketFactory(),
//            new javax.rmi.ssl.SslRMIServerSocketFactory());
        registry = LocateRegistry.createRegistry(WhiteboardServer.WHITEBOARD_PORT);
    }

    /** Create a new session (or whiteboard) */
    public int createSessions(String sessionsName, String adminName,
                                 String password) throws RemoteException {
        for(int i=0; i < sessionsMaxNum; i++){
            if(sessions[i] == null) {
                // if the sessions has a vacancy
                // the name of service consist of a fixed name
                // plus a identity of this session
                String serviceName = "RemoteWhiteboard" + i;

                try {                    
                    sessions[i] = new WhiteboardImpl(sessionsName,adminName,password);
                    // Bind this object instance to the service name
                    registry.rebind(serviceName,sessions[i]);

                    System.out.println("Create a new session. Session id: " + i);
                } catch(Exception ex) {
                    System.out.println("Error starting a whiteboard: " +
                                    ex.getMessage());
                    // something wrong with starting a whiteboard
                    return -2;
                }
                sessionList[i][0] = Integer.toString(i);
                sessionList[i][1] = sessionsName;
                sessionList[i][2] = adminName;
                if ( !password.equals("") ){
                    // if the session has a password
                    sessionList[i][3] = "¡Ì";
                }
                usersNumofEachSession[i] = 1;
                sessionsNum++;
                // change the state of server to notify
                // all the users that the information of
                // sesssions has been changed 
                notifyAllSessionListUpdated();
                return i;
            }
        }
        // The current number of sessions is max number
        // it can't create a session in the server.
        return -1;
    }

    /** Get the list of sessions information */
    public String[][] getSessionList() throws RemoteException{
        for(int i=0; i < sessionsMaxNum; i++){
            if(sessions[i] != null) {
                // if the session exists
                sessionList[i][4] = usersNumofEachSession[i] + "/" + oneSessionMaxNum;
            }
        }
        return sessionList;
    }

    /** Close the specific session */
    public void closeSession(int sessionID) throws RemoteException{
        sessionsNum--;
        sessions[sessionID] = null;
        usersNumofEachSession[sessionID] = 0;
        String serviceName = "RemoteWhiteboard" + sessionID;

        try {
            // unbind the session from the rmi server
            registry.unbind(serviceName);
            
            System.out.println("One session is closed. Session id: " + sessionID);
        } catch(Exception ex) {
            
            // something wrong with closing a whiteboard
            System.out.println("Error closing a whiteboard: " +
                            ex.getMessage());            
        }
        // clean the data of this session
        for(int i=0; i<5; i++) {
            sessionList[sessionID][i] = null;
        }
        // notify all users that the session
        // information has been changed 
        notifyAllSessionListUpdated();
    }

    /**
     * The connection registry which contributes to record
     * the number of current users
     */
    public int connectionRegistry() throws RemoteException {
        int n = maxNumofPeople;
        for(int i=0; i < n; i++) {
            if(isSessionListUpdated[i] == null) {
                // initialize the specific position in connection list
                isSessionListUpdated[i] = "";
                return i;
            }
        }
        return -1;
    }

    /**
     * Check the state of session list whether it has been
     * changed according the specific connection identity.
     */
    public boolean isSessionlistUpdated(int connectID)
        throws RemoteException {
        if(isSessionListUpdated[connectID].equals("T"))
        {
            isSessionListUpdated[connectID] = "F";
            return true;
        }
        else
            return false;
    }

    /** Alter all the flag to notify users */
    private void notifyAllSessionListUpdated(){
        for(int i=0;i < maxNumofPeople;i++) {
            if(isSessionListUpdated[i] != null)
                isSessionListUpdated[i] = "T";
        }
    }

    /**
     * One user joins a specific session and the number
     * of users in this session increases. 
     */
    public void joinSession(int sessionID) throws RemoteException{
        usersNumofEachSession[sessionID]++;
        notifyAllSessionListUpdated();
    }

    /**
     * One user leaves a specific session and the number
     * of users in this session decreases.
     */
    public void leaveSession(int sessionID) throws RemoteException{
        usersNumofEachSession[sessionID]--;
        notifyAllSessionListUpdated();
    }

    /** Get the name of specific session */
    public String getSessionName(int sessionID) throws RemoteException{
        return sessionList[sessionID][1];
    }
}
