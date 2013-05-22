package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : WhiteboardChooseServer.java
 * Description  : The WhiteboardChooseServer provides an input area of
 *                server address. It connects to the server and gets the
 *                information about the sessions.
 * Author       : Ruxin Hou
 * Team         : TheThreeBytes
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jajeem.whiteboard.server.Module.Sessions;

/**
 * Class WhiteboardChooseServer provides an input area to receive the
 * server address. And when the ok button is clicked, it will connect
 * to the server and get the information of sessions.
 */
public class WhiteboardChooseServer extends JDialog {

    /** The input field of server address */
    private JTextField txtServerAddress;

    /** Declare a reference of WhiteboardClient class */
    private WhiteboardClient whiteboardClient;

    /** The constructor to initilize the components */
    public WhiteboardChooseServer(WhiteboardClient whiteboardClient){
        // declare related components
        JPanel userPanel;
        JLabel userLoginLabel;
        JButton btnOk;
        JButton btnCancel;
        JLabel infoLabel;
        JLabel info2Label;
        JLabel serverAddressLabel;

        // get the reference of WhiteboardClient
        this.whiteboardClient = whiteboardClient;
        userPanel = new JPanel();
        userLoginLabel = new JLabel();

        // if this dialog is not closed, the focus of parent
        // component cannot be gotten.
        this.setModal(true);

        // initialize the components
        btnOk = new JButton();
        btnCancel = new JButton();
        infoLabel = new JLabel();
        info2Label = new JLabel();
        serverAddressLabel = new JLabel();
        txtServerAddress = new JTextField();

        // set the size of this dialog
        this.setSize(300,180);
        // make this dialog displayed in the center of screen
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int)(width-this.getWidth())/2,
               (int)(height-this.getHeight())/2);
        // make this dialog fixed
        this.setResizable(false);
        this.setTitle("Choose the Server");
        
        userPanel.setLayout(null);
        userPanel.setBackground(Color.cyan);
        userLoginLabel.setText("Connect to The Server");
        userLoginLabel.setFont(new Font("Calibri",
                Font.BOLD | Font.ITALIC, 14));
        userLoginLabel.setForeground(Color.RED);

        infoLabel.setText("(Note: The default address of the server is ");
        info2Label.setText("localhost, if leave blank.)");
        serverAddressLabel.setText("Server Address: ");
        btnOk.setText("Connect");
        btnCancel.setText("Leave");

        userLoginLabel.setBounds(80,15,250,20);
        infoLabel.setBounds(30,35,300,20);
        info2Label.setBounds(30,55,300,20);
        serverAddressLabel.setBounds(30,80,100,20);
        this.txtServerAddress.setBounds(130,80,120,20);

        btnOk.setBounds(105,120,90,20);
        // Connect to the server
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnOk_ActionPerformed(e);
            }
        });

        btnCancel.setBounds(195,120,80,20);
        // Cancel the dialog
        btnCancel.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                window_Closed();
            }
        }));

        // closing this dialog
        this.addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent event){
            window_Closed();
          }
        });

        // add the components 
        userPanel.add(userLoginLabel);
        userPanel.add(infoLabel);
        userPanel.add(info2Label);
        userPanel.add(serverAddressLabel);
        userPanel.add(txtServerAddress);
        userPanel.add(btnOk);
        userPanel.add(btnCancel);
        this.add(userPanel);
        this.setVisible(true);
    }

    /** Connect to the server */
    private void btnOk_ActionPerformed(ActionEvent e) {

        try {
            // the local address
            InetAddress addr = InetAddress.getLocalHost();
            // get the name of local host
            String hostname = addr.getHostName();
            if (!txtServerAddress.getText().trim().equals("")){
                // if the user specify the address of server
                hostname = txtServerAddress.getText();
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
            String[] list = registry.list();
            Object proxy = registry.lookup(serviceName);
            Sessions sessions = (Sessions)proxy;

            // the identity of connection
            int connectID = sessions.connectionRegistry();
            // update the list of current sessions
            whiteboardClient.updateList(sessions.getSessionList());
            whiteboardClient.setSessions(sessions);

            // set the flag of running session listener
            whiteboardClient.setIsRunSessionsListener(true);
            this.setVisible(false);
            whiteboardClient.setHostName(hostname);

            Thread listener;
            try {
                // create the listener of sessions to update
                // the session information immediately
                listener = new Thread(new SessionsListener(sessions,
                        whiteboardClient, connectID));
                listener.start();

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                   "Error happens while starting the session listener.",
                   "Error", JOptionPane.ERROR_MESSAGE);
                window_Closed();
            }
            // change the title of the button 
            whiteboardClient.btnConnect_SetText("Disconnect");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                   "Connection refused to host, please make "
                   + "true the host is correct.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Closing this session */
    private void window_Closed() {
        this.dispose();
    }  
}
