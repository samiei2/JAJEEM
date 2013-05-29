package com.jajeem.whiteboard.client.Client;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.Random;

import javax.swing.JOptionPane;

import com.jajeem.whiteboard.client.Client.design.MainFrame;
import com.jajeem.whiteboard.server.Module.Sessions;
import com.jajeem.whiteboard.server.Module.Whiteboard;

public class StudentWhiteboard {
	
	private String txtServerAddress;
	private String hostname;
	private int sessionID;
	private Sessions sessions;
	private int userid;
	
	public StudentWhiteboard(String host){
		Policy.setPolicy(new MinimalPolicy());
    	System.setProperty("javax.net.ssl.trustStore","cert/client.keystore");
    	System.setProperty("javax.net.ssl.keyStore", "cert/client.keystore");
    	System.setProperty("javax.net.ssl.keyStorePassword", "client");

        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            SecurityManager manager = new SecurityManager();
            System.setSecurityManager(manager);
        }
		hostname = host;
		
		userid = new Random().nextInt(2000);
		/** Connect to the server */
		try {
            if (getTxtServerAddress() == null || getTxtServerAddress().equals("")){
                // if the user specify the address of server
                
            }
            else {
            	hostname = getTxtServerAddress();
            }

            // Make reference to SSL-based registry
            Registry registry = LocateRegistry.getRegistry(
                hostname, WhiteboardClient.SESSIONS_PORT,
                new javax.rmi.ssl.SslRMIClientSocketFactory()
            );


            String serviceName = "RWS";

            // "proxy" is the identifier that refer to
            // the remote object that implements the "Sessions"
            // interface
            Object proxy = registry.lookup(serviceName);
            sessions = (Sessions)proxy;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                   "Connection refused to host, please make "
                   + "sure the host is correct.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void Join(int sessionId){
		try {
			sessions.joinSession(sessionID);
			
			Registry registry = LocateRegistry.getRegistry(
	                hostname, WhiteboardClient.WHITEBOARD_PORT,
	                new javax.rmi.ssl.SslRMIClientSocketFactory()
	                );

	        // the name of service in RMI server
	        String serviceName = "RemoteWhiteboard" + sessionID;
	        
	     // "proxy" is the identifier that refer to
            // the remote object that implements the "Whiteboard"
            // interface
            Object proxy = registry.lookup(serviceName);
            Whiteboard whiteboard = (Whiteboard)proxy;

            int userid = whiteboard.userRegistry(getUserName(), "");
            
            MainFrame mainFrame = new MainFrame(sessions, sessionID,
                        whiteboard,userid,"");
            
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	private String getUserName() {
		return "Test";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getTxtServerAddress() {
		return txtServerAddress;
	}

	public void setTxtServerAddress(String txtServerAddress) {
		this.txtServerAddress = txtServerAddress;
	}

}
