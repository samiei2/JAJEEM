package com.jajeem.whiteboard.server.Server;
/**
 * File Name    : WhiteboardServer.java
 * Description  : This is the main entry of server-end.
 * Author       : Asset Issabayev
 * Team         : TheThreeBytes
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketPermission;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.PropertyPermission;

import javax.swing.JOptionPane;

/**
 * Class WhiteboardServer provides an implementation of the registry
 * of the SessionsImpl. 
 */
public class WhiteboardServer {
    
    /** the default server port */
    public static int SESSIONS_PORT ;
    public static int WHITEBOARD_PORT;

    /** The maximum number of sessions */
    public static final int SESSIONS_MAX_NUMBER = 100;

    /** The main entry of the Server */
    public static void main(String[] args) {
    	try {
    		SESSIONS_PORT = Integer.parseInt(args[0]);
        	WHITEBOARD_PORT = Integer.parseInt(args[1]);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Port Number!");
			System.exit(1);
		}
    	Policy.setPolicy(new MinimalPolicy());
//    	try {
//			com.google.common.io.Files.copy(new File("cert/server.keystore"), new File("c:/server.keystore"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	System.setProperty("javax.net.ssl.trustStore","cert/server.keystore");
//    	System.setProperty("javax.net.ssl.keyStore", "cert/server.keystore");
//    	System.setProperty("javax.net.ssl.keyStorePassword", "server");
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            SecurityManager manager = new SecurityManager();
            System.setSecurityManager(manager);
        }
        
        int sessionsNum = SESSIONS_MAX_NUMBER;
        if( args.length == 1) {
            // if the number of arguments 
            try {
                // get the number which user input
                sessionsNum = Integer.parseInt(args[0]);
                if(sessionsNum < 1 & sessionsNum > SESSIONS_MAX_NUMBER) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException ex) {
                System.out.println("Error:" + args[0] + " is not a valid number!");
                WhiteboardServer.usage();
                return;
            }
        }

        String serviceName = "RWS";

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostAddress();

            // Create SSL-based registry
//            Registry registry = LocateRegistry.createRegistry(SESSIONS_PORT,
//                    new javax.rmi.ssl.SslRMIClientSocketFactory(),
//                    new javax.rmi.ssl.SslRMIServerSocketFactory()); 
            Registry registry = LocateRegistry.createRegistry(SESSIONS_PORT);

            SessionsImpl sessions = new SessionsImpl(hostname, sessionsNum);

            // Bind this object instance to the service name
            registry.rebind(serviceName, sessions);
            
            //Naming.rebind("rmi://" + hostname + "/" + serviceName, sessions);
            System.out.println(serviceName + " bound in registry");
        } catch(Exception ex) {
            System.out.println("Error starting the server: " +
                 ex.getMessage());
        }
        
        // creates a new thread to receive the user's input
        // which is used to end the program
        new Thread( new Runnable()
        {
            public void run()
            {
                // creates a buffer reader to read the input
                BufferedReader localReader = null;
                String msg = null;
                System.out.print(">");
                try
                {
                    // get a reference of the input stream
                    localReader = new BufferedReader(new InputStreamReader(System.in));
                    while((msg = localReader.readLine()) != null)
                    {
                        // if the user input the quit command
                        // then exit the program
                        if(msg.equals("quit"))
                        	;
//                            System.exit(0);;
                        System.out.print(">");
                    }
                }
                catch(IOException ioe)
                {
                    // this exception might be generated by the reading of
                    // the command from the user
                    System.out.println("Error while reading the command from users: " + ioe.getMessage());
                }
            }
        }).start();
    }
    
    
    public static void Start(int sessionsPort,int whiteboardPort){
    	SESSIONS_PORT = sessionsPort;
    	WHITEBOARD_PORT = whiteboardPort;
    	Policy.setPolicy(new MinimalPolicy());
//    	try {
//			com.google.common.io.Files.copy(new File("cert/server.keystore"), new File("c:/server.keystore"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	System.setProperty("javax.net.ssl.trustStore","cert/server.keystore");
//    	System.setProperty("javax.net.ssl.keyStore", "cert/server.keystore");
//    	System.setProperty("javax.net.ssl.keyStorePassword", "server");
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            SecurityManager manager = new SecurityManager();
            System.setSecurityManager(manager);
        }
        
        int sessionsNum = SESSIONS_MAX_NUMBER;
        
        String serviceName = "RWS";

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostAddress();

            // Create SSL-based registry
//            Registry registry = LocateRegistry.createRegistry(SESSIONS_PORT,
//                    new javax.rmi.ssl.SslRMIClientSocketFactory(),
//                    new javax.rmi.ssl.SslRMIServerSocketFactory()); 
            Registry registry = LocateRegistry.createRegistry(SESSIONS_PORT);

            SessionsImpl sessions = new SessionsImpl(hostname, sessionsNum);

            // Bind this object instance to the service name
            registry.rebind(serviceName, sessions);
            
            //Naming.rebind("rmi://" + hostname + "/" + serviceName, sessions);
            System.out.println(serviceName + " bound in registry");
        } catch(Exception ex) {
            System.out.println("Error starting the server: " +
                 ex.getMessage());
        }
        
        // creates a new thread to receive the user's input
        // which is used to end the program
        new Thread( new Runnable()
        {
            public void run()
            {
                // creates a buffer reader to read the input
                BufferedReader localReader = null;
                String msg = null;
                System.out.print(">");
                try
                {
                    // get a reference of the input stream
                    localReader = new BufferedReader(new InputStreamReader(System.in));
                    while((msg = localReader.readLine()) != null)
                    {
                        // if the user input the quit command
                        // then exit the program
                        if(msg.equals("quit"))
                        	;
//                            System.exit(0);;
                        System.out.print(">");
                    }
                }
                catch(IOException ioe)
                {
                    // this exception might be generated by the reading of
                    // the command from the user
                    System.out.println("Error while reading the command from users: " + ioe.getMessage());
                }
            }
        }).start();
    }
    /**
	 * Displays a simple usage message that specifies
	 * how to run the program.
	 */
	public static void usage() {

		System.out.println("usage: java WhiteboardServer [numberofsessions]");
		System.out.println("the number of sessions: not greater than the maximum which is 100.");
		System.out.println();
	}
}

class MinimalPolicy extends Policy {

    private static PermissionCollection perms;

    public MinimalPolicy() {
        super();
        if (perms == null) {
            perms = new MyPermissionCollection();
            addPermissions();
        }
    }

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        return perms;
    }

    private void addPermissions() {
    	AllPermission allPermission = new AllPermission();
        SocketPermission socketPermission = new SocketPermission("*:1024-65535", "connect,accept,resolve");
        PropertyPermission propertyPermission = new PropertyPermission("*", "read, write");
        FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read, write");
        RuntimePermission runtimePermission = new RuntimePermission("accessClassInPackage.sun.jdbc.odbc", "read");

        perms.add(socketPermission);
        perms.add(propertyPermission);
        perms.add(filePermission);
        perms.add(runtimePermission);
        perms.add(allPermission);
    }

}

class MyPermissionCollection extends PermissionCollection {

    private static final long serialVersionUID = 614300921365729272L;

    ArrayList<Permission> perms = new ArrayList<Permission>();

    public void add(Permission p) {
        perms.add(p);
    }

    public boolean implies(Permission p) {
        for (Iterator<Permission> i = perms.iterator(); i.hasNext();) {
            if (((Permission) i.next()).implies(p)) {
                return true;
            }
        }
        return false;
    }

    public Enumeration<Permission> elements() {
        return Collections.enumeration(perms);
    }

    public boolean isReadOnly() {
        return false;
    }
}