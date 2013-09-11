package com.jajeem.whiteboard.client.Client;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.Random;

import javax.swing.JOptionPane;

import com.jajeem.util.LoadingDialog;
import com.jajeem.whiteboard.client.Client.design.MainFrame;
import com.jajeem.whiteboard.server.Module.Sessions;
import com.jajeem.whiteboard.server.Module.Whiteboard;

public class StudentWhiteboard {
	
	private String txtServerAddress;
	private String hostname;
	private int sessionID;
	private Sessions sessions;
	private int userid;
	private LoadingDialog overlayScreen;
	
	public StudentWhiteboard(String host, String sessionPort){
		overlayScreen = new LoadingDialog();
		overlayScreen.setVisible(true);
//		Policy.setPolicy(new MinimalPolicy());
//    	System.setProperty("javax.net.ssl.trustStore","cert/client.keystore");
//    	System.setProperty("javax.net.ssl.keyStore", "cert/client.keystore");
//    	System.setProperty("javax.net.ssl.keyStorePassword", "client");

        // Create and install a security manager
//        if (System.getSecurityManager() == null) {
//            SecurityManager manager = new SecurityManager();
//            System.setSecurityManager(manager);
//        }
		hostname = host;
		
		userid = new Random().nextInt(2000);
		/** Connect to the server */
		if (getTxtServerAddress() == null || getTxtServerAddress().equals("")){
            // if the user specify the address of server
            
        }
        else {
        	hostname = getTxtServerAddress();
        }
		int numOfTries = 10;
		while (numOfTries > 0) {
			try {
	            // Make reference to SSL-based registry
	//            Registry registry = LocateRegistry.getRegistry(
	//                hostname, WhiteboardClient.SESSIONS_PORT,
	//                new javax.rmi.ssl.SslRMIClientSocketFactory()
	//            );
	            System.out.println("Calling " + host +":"+ sessionPort);
		        Registry registry = LocateRegistry.getRegistry(
		                    hostname, Integer.parseInt(sessionPort)
		                );
		
		
		            String serviceName = "RWS";
		
		            // "proxy" is the identifier that refer to
		            // the remote object that implements the "Sessions"
		            // interface
		            System.out.println("Lookup");
		            Object proxy = registry.lookup(serviceName);
		            sessions = (Sessions)proxy;
		            break;
	        } catch(Exception ex) {
	        	numOfTries--;
	        	System.out.println("Try #"+numOfTries);
	        	try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	            JOptionPane.showMessageDialog(null,
	                   "Connection refused to host, please make "
	                   + "sure the host is correct.",
	                   "Error", JOptionPane.ERROR_MESSAGE);
	        }
			if(numOfTries == 0)
				JOptionPane.showMessageDialog(null, "Unable to connect to whiteboard server.Please contact administrator!");
		}
	}
	
	public void Join(int sessionID,String userName, String whiteboardPort){
		int numOfTries = 10;
		while (numOfTries > 0) {
			try {
				System.out.println("Joining");
				sessions.joinSession(sessionID);
				
	//			Registry registry = LocateRegistry.getRegistry(
	//	                hostname, WhiteboardClient.WHITEBOARD_PORT,
	//	                new javax.rmi.ssl.SslRMIClientSocketFactory()
	//	                );
				Registry registry = LocateRegistry.getRegistry(
		                hostname, Integer.parseInt(whiteboardPort)
		                );
	
		        // the name of service in RMI server
		        String serviceName = "RemoteWhiteboard" + sessionID;
		        
		     // "proxy" is the identifier that refer to
	            // the remote object that implements the "Whiteboard"
	            // interface
	            Object proxy = registry.lookup(serviceName);
	            Whiteboard whiteboard = (Whiteboard)proxy;
	            System.out.println("Joined");
	            int userid = whiteboard.userRegistry(userName, "");
	            System.out.println("Opening MainFrame");
	            MainFrame mainFrame = new MainFrame(sessions, sessionID,
	                        whiteboard,userid,"");
	            System.out.println("Main Frame Opened");
	            overlayScreen.setVisible(false);
	            break;
			} catch (RemoteException | NotBoundException e) {
				numOfTries--;
				System.out.println("Try #"+numOfTries);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				System.out.println("Exception :" + e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					System.out.println(e.getStackTrace()[i]);
				}
			}
		}
		if(numOfTries == 0)
			JOptionPane.showMessageDialog(null, "Unable to connect to whiteboard server.Please contact administrator!");
		overlayScreen.setVisible(false);
	}

//	private String getUserName() {
//		if(Session.getStudent().getFullName() == null || Session.getStudent().getFullName().equals(""))
//			return "Anonymous";
//		else
//			return Session.getStudent().getFullName();
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "";
		String sessionPort = "";
		String whiteboardPort = "";
		String userName = "";
		String sessionID = "";
		try{
			host = args[0];
			sessionPort = args[1];
			whiteboardPort = args[2];
			userName = args[3];
			sessionID = args[4];
			System.out.println("Info : ");
			System.out.println("Host : "+args[0]);
			System.out.println("Session Port : "+args[1]);
			System.out.println("Whiteboard Port : "+args[2]);
			System.out.println("Username : "+args[3]);
			System.out.println("Session Id : "+args[4]);
			StudentWhiteboard student = new StudentWhiteboard(host,sessionPort);
			student.Join(Integer.parseInt(sessionID), userName,whiteboardPort);
			System.out.println("Student Whiteboard Started!");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
	}

	public String getTxtServerAddress() {
		return txtServerAddress;
	}

	public void setTxtServerAddress(String txtServerAddress) {
		this.txtServerAddress = txtServerAddress;
	}

}
