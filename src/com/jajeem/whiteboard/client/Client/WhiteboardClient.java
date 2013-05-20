package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : WhiteboardClient.java
 * Description  : The WhiteboardClient is the first
 *                frame to be provided for users, in
 *                which users can connect to the server,
 *                gets the session information, creates
 *                session and join other sessions.
 * Author       : Ruxin Hou
 * Team         : TheThreeBytes
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;
import com.jajeem.whiteboard.client.Client.design.MainFrame;
import com.jajeem.whiteboard.client.Module.Graphics.Image;
import com.jajeem.whiteboard.server.Module.Sessions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketPermission;
import java.nio.file.DirectoryIteratorException;
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

/**
 * Class WhiteboardClient implements a client, in
 * which the session information is displayed in
 * the list. User can choose the session to join,
 * or create a own session.
 */
public class WhiteboardClient extends JFrame {

    /** The default server port */
    public static final int SESSIONS_PORT = 2019;
    public static final int WHITEBOARD_PORT = 2020;

    /** The related components */
    private JPanel sessionsPanel;
    private JTable sessionsList;
    private JScrollPane scrollPane;
    private TableModel tableModel;
    private JLabel chooseSessionsLabel;
    private JButton btnCreate;
    private JButton btnJoin;
    private JButton btnCancel;
    private JButton btnConnect;

    /** The name of columns */
    private final String[] columnNames = {"Session ID","Session Name","Administrator",
            "Password Needs","Users Number"};
    
    /** Declare a reference of the Session class */
    private Sessions sessions;

    /** The place to store the information of users */
    private Object[][] data = null;

    /** The flag whether is runing a session listener */
    private boolean isRunSessionsListener = false;

    /** Declare a reference of the WhiteboardChooseServer class */
    private WhiteboardChooseServer whiteboardChooseServer;

    /** The address of host */
    private String hostname;

    /** The constructor to initialize the components */
    public WhiteboardClient() {
    	
        // create a table model
        tableModel = new DefaultTableModel(data,columnNames);
        // create a table and set its editable to disable
        sessionsList = new JTable(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // initialize the components 
        sessionsPanel = new JPanel();
        btnCreate = new JButton();
        btnJoin = new JButton();
        btnCancel = new JButton();
        btnConnect = new JButton();
        chooseSessionsLabel = new JLabel();

        this.chooseSessionsLabel.setText("Choose a session");
        this.chooseSessionsLabel.setFont(new Font("Calibri",
                Font.BOLD | Font.ITALIC, 14));
        this.chooseSessionsLabel.setForeground(Color.RED);
        this.chooseSessionsLabel.setBounds(250,5,120,20);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,330);
        // set the display position to the center of screen 
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int)(width-this.getWidth())/2,
               (int)(height-this.getHeight())/2);
        // set this frame fixed
        this.setResizable(false);
        
        this.setTitle("Whiteboard Client");
        this.sessionsPanel.setLayout(null);
        this.sessionsPanel.setBackground(Color.cyan);
        this.btnCreate.setText("Create");
        this.btnJoin.setText("Join");
        this.btnCancel.setText("Leave");
        this.btnConnect.setText("Connect");

        this.btnCreate.setBounds(310,250,80,20);

        // create a new session
        this.btnCreate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnCreate_ActionPerformed(e);
            }
        });

        this.btnJoin.setBounds(390,250,80,20);

        // join a session
        this.btnJoin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnJoin_ActionPerformed(e);
            }
        });

        this.btnCancel.setBounds(470,250,80,20);
        
        // leave this application 
        this.btnCancel.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnCancel_ActionPerformed(e);
            }
        }));

        this.btnConnect.setBounds(50,250,100,20);
        
        // connect to the server
        this.btnConnect.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnConnect_ActionPerformed(e);
            }
        }));

        // sets the table model to the JTable, which
        // is used for updating the JTable.
        sessionsList.setModel(tableModel);
        
        // sets that the table only choose one line
        sessionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // set the size of the table
        sessionsList.setPreferredScrollableViewportSize(new Dimension(200, 160));

        // set the header of the table fixed
        sessionsList.getTableHeader().setResizingAllowed(false);
        
        scrollPane = new JScrollPane(sessionsList);
        scrollPane.setVisible(true);
        scrollPane.setBounds(50,30,500,200);

        // add the components
        this.sessionsPanel.add(chooseSessionsLabel);
        this.sessionsPanel.add(scrollPane);
        this.sessionsPanel.add(btnCreate);
        this.sessionsPanel.add(btnJoin);
        this.sessionsPanel.add(btnCancel);
        this.sessionsPanel.add(btnConnect);
        this.add(sessionsPanel);
        this.setVisible(true);
    }

    /** Display a dialog to create a new session */
    private void btnCreate_ActionPerformed(ActionEvent e) {
        if(btnConnect.getText().equals("Disconnect")) {
            // if connect to the server
            WhiteboardCreateASession createASession
                    = new WhiteboardCreateASession(sessions,this);
            
            
        } else {
            // if it does not connect to the server,
            // create a local whiteboard 
            LocalWhiteboard localWhiteboard = new LocalWhiteboard();
            MainFrame mainFrame = new MainFrame(null,
                0,localWhiteboard,0,"localuser");
        }
    }

    /** Display a dialog to join a session */
    private void btnJoin_ActionPerformed(ActionEvent e) {
        if(btnConnect.getText().equals("Disconnect")) {
            // get the index of selected row
            int selectedRow = sessionsList.getSelectedRow();
            
            if (selectedRow >= 0){
                // get the identity of the session
                String strSessionID = sessionsList.getValueAt(selectedRow, 0).toString();
                int sessionID = Integer.parseInt(strSessionID);
                // join a session 
                WhiteboardJoinASession joinASession
                    = new WhiteboardJoinASession(sessions,sessionID,this);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                       "Please connect the server first.",
                       "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Leave the application */
    private void btnCancel_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    public void joinStudent(WhiteboardClient student){
    	int selectedRow = student.sessionsList.getSelectedRow();
        
        if (selectedRow >= 0){
            // get the identity of the session
            String strSessionID = student.sessionsList.getValueAt(selectedRow, 0).toString();
            int sessionID = Integer.parseInt(strSessionID);
            // join a session 
            WhiteboardJoinASession joinASession
                = new WhiteboardJoinASession(student.sessions,sessionID,student);
        }
    }

    /** Update the list of session */
    public void updateList(String[][] data){
        this.data = data;
        tableModel = new DefaultTableModel(data,columnNames);
        sessionsList.setModel(tableModel);
        scrollPane.setViewportView(sessionsList);
    }

    /** Connect or disconnect to the server */
    private void btnConnect_ActionPerformed(ActionEvent e) {
        if(btnConnect.getText().equals("Connect")) {
            // display a dialog to connect the server
            whiteboardChooseServer = new WhiteboardChooseServer(this);
        } else {
            // disconnect the server
            setIsRunSessionsListener(false);
            btnConnect.setText("Connect");
            updateList(null);
            whiteboardChooseServer.dispose();
        }    
    }
    
    /** Set the reference of sessions to update the information */
    public void setSessions(Sessions sessions){
        this.sessions = sessions;
    }

    /** Change the title of the button */
    public void btnConnect_SetText(String text) {
        btnConnect.setText(text);
    }
    
    /** Set the flag whether is running the session listener */
    public void setIsRunSessionsListener(boolean isRun) {
        isRunSessionsListener = isRun;
    }
    
    /** Get the flag whether is running the session listener */
    public boolean getIsRunSessionsListener() {
        return this.isRunSessionsListener;
    }
    
    /** Get the address of host */
    public String getHostName(){
        return this.hostname;
    }

    /** Set the address of host */
    public void setHostName(String hostname){
        this.hostname = hostname;
    }
    
    /** The main entry of the client */
    public static void main(String[] args) {
    	Policy.setPolicy(new MinimalPolicy());
    	try {
			com.google.common.io.Files.copy(new File("cert/client.keystore"), new File("c:/client.keystore"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.setProperty("javax.net.ssl.trustStore","cert/client.keystore");
    	System.setProperty("javax.net.ssl.keyStore", "cert/client.keystore");
    	System.setProperty("javax.net.ssl.keyStorePassword", "client");

        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            SecurityManager manager = new SecurityManager();
            System.setSecurityManager(manager);
        }

        WhiteboardClient whiteboardClient = new WhiteboardClient();
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
        SocketPermission sockperm = new SocketPermission("*:80", "connect");

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