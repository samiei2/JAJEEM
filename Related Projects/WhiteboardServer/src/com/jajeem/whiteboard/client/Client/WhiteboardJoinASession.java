package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : WhiteboardJoinASession.java
 * Description  : The WhiteboardJoinASession is the interface
 *                to be provided for users to join one session.
 * Author       : Ruxin Hou
 * Team         : TheThreeBytes
 */
import com.jajeem.whiteboard.client.Client.design.MainFrame;
import com.jajeem.whiteboard.server.Module.Sessions;
import com.jajeem.whiteboard.server.Module.Whiteboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class WhiteboardJoinASession provides an interface,
 * in which users can input the basic information to
 * join a session.
 */
public class WhiteboardJoinASession extends JFrame {

    /** The input field of user name */
    private JTextField txtName;

    /** The input field of session password  */
    private JPasswordField txtPwd;

    /** Declare a reference of the Sessions */
    private Sessions sessions;

    /** The identity of session */
    private int sessionID;

    /** Declare a reference of the WhiteboardClient */
    private WhiteboardClient whiteboardClient;

    /** The constructor to initialize the components */
    public WhiteboardJoinASession(Sessions sessions, int sessionID,
                                  WhiteboardClient whiteboardClient) {
        // declare the related components
        JPanel userPanel;
        JLabel userLoginLabel;
        JLabel userNameLabel;
        JLabel userPasswordLabel;
        JButton btnOk;
        JButton btnCancel;

        // initialize the instance variables
        this.sessionID = sessionID;
        this.sessions = sessions;
        this.whiteboardClient = whiteboardClient;

        // create the object of components
        userPanel = new JPanel();
        userLoginLabel = new JLabel();
        userNameLabel = new JLabel();
        userPasswordLabel = new JLabel();
        txtName = new JTextField(20);
        txtPwd = new JPasswordField(20);
        btnOk = new JButton();
        btnCancel = new JButton();

        // set the size
        this.setSize(300,250);
        // set the position to the center of screen
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int)(width-this.getWidth())/2,
               (int)(height-this.getHeight())/2);

        this.setResizable(false);
        this.setTitle("Join A Session");

        // initialize the components 
        userPanel.setLayout(null);
        userPanel.setBackground(Color.cyan);
        userLoginLabel.setText("Join a session");
        userLoginLabel.setFont(new Font("Calibri",
                Font.BOLD | Font.ITALIC, 14));
        userLoginLabel.setForeground(Color.RED);
        userNameLabel.setText("Username: ");
        userPasswordLabel.setText("Password: ");
        
        btnOk.setText("Login");
        btnCancel.setText("Leave");

        userLoginLabel.setBounds(100,15,120,20);
        userNameLabel.setBounds(30,85,80,25);
        userPasswordLabel.setBounds(30,115,80,25);

        this.txtName.setBounds(110,85,120,20);
        this.txtPwd.setBounds(110,115,120,20);

        btnOk.setBounds(125,175,80,20);
        // add a listener to the button
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnOk_ActionPerformed(e);
            }
        });

        btnCancel.setBounds(195,175,80,20);
        // add a listener to the button
        btnCancel.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                window_Closed();
            }
        }));

        // add the components
        userPanel.add(userLoginLabel);
        userPanel.add(userNameLabel);
        userPanel.add(userPasswordLabel);
        userPanel.add(txtName);
        userPanel.add(txtPwd);
        userPanel.add(btnOk);
        userPanel.add(btnCancel);
        this.add(userPanel);
        this.setVisible(true);

        // add a listener of closing window
        this.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               window_Closed();
           }
    });
    }

    /** Implement the operation to join a session */
    private void btnOk_ActionPerformed(ActionEvent e) {
        String username = txtName.getText().trim();
        String password = new String(txtPwd.getPassword());

        if(username.equals("")) {
            // if the input text is empty
            JOptionPane.showMessageDialog(null,
               "Make true the name of user is not empty!",
               "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // call the method of sessions and join the specific
            // session
            sessions.joinSession(sessionID);

            // get the host address
            String hostname = whiteboardClient.getHostName();

            // Make reference to SSL-based registry
//            Registry registry = LocateRegistry.getRegistry(
//                hostname, WhiteboardClient.WHITEBOARD_PORT,
//                new javax.rmi.ssl.SslRMIClientSocketFactory()
//                );
            Registry registry = LocateRegistry.getRegistry(
                    hostname, WhiteboardClient.WHITEBOARD_PORT
                    );

            // the name of service in RMI server
            String serviceName = "RemoteWhiteboard" + sessionID;

            // "proxy" is the identifier that refer to
            // the remote object that implements the "Whiteboard"
            // interface
            Object proxy = registry.lookup(serviceName);
            Whiteboard whiteboard = (Whiteboard)proxy;

            // register to the whiteboard and get the identity of user
            int userid = whiteboard.userRegistry(username, password);
            
            if(userid >= 0) {
                // if the situation is normal
                MainFrame mainFrame = new MainFrame(sessions, sessionID,
                        whiteboard,userid,username);
                this.dispose();
            }
            else if( userid == -1){
                // the number of users reach the upper bound
                JOptionPane.showMessageDialog(null,
                       "This number of users in this session reaches the upper bound.",
                       "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if( userid == -2){
                // the password is incorrect
                JOptionPane.showMessageDialog(null,
                       "The enter password is incorrect.",
                       "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                   "Error happens while joining a session.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Closing the window */
    private void window_Closed(){
        this.dispose();
    }
}