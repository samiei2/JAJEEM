package com.jajeem.whiteboard.client.Client;
/**
 * File Name    : WhiteboardCreateASession.java
 * Description  : The WhiteboardCreateASession is the interface
 *                to be provided for users to create the new
 *                sessions.
 * Author       : Ruxin Hou
 * Team         : TheThreeBytes
 */
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;
import com.jajeem.whiteboard.client.Client.design.MainFrame;
import com.jajeem.whiteboard.server.Module.Sessions;
import com.jajeem.whiteboard.server.Module.Whiteboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class WhiteboardCreateASession provides an interface,
 * in which users can input the basic information to
 * create a session.
 */
public class WhiteboardCreateASession extends JFrame {

    /** The input field of session name */
    private JTextField txtSessionName;
    
    /** The input field of creator name */
    private JTextField txtAdminName;

    /** The input field of password */
    private JPasswordField txtPwd;

    /** The input field of confirm password */
    private JPasswordField txtVerifyPwd;

    /** Declare a reference of the Sessions */
    private Sessions sessions;

    /** Declare a reference of the WhiteboardClient */
    private WhiteboardClient whiteboardClient;

    public WhiteboardCreateASession(Sessions sessions,
                                    WhiteboardClient whiteboardClient) {

        // get the reference from the passing parameters
        this.sessions = sessions;
        this.whiteboardClient = whiteboardClient;

        // declare the related components
        JPanel displayPanel;
        JLabel createSessionLabel;
        JLabel sessionNameLabel;
        JLabel adminNameLabel;
        JLabel passwordLabel;
        JLabel verificationLabel;        
        JButton btnOk;
        JButton btnCancel;

        // create the object of related components 
        displayPanel = new JPanel();
        createSessionLabel = new JLabel();
        sessionNameLabel = new JLabel();
        adminNameLabel = new JLabel();
        passwordLabel = new JLabel();
        verificationLabel = new JLabel();
        txtSessionName = new JTextField(20);
        txtAdminName = new JTextField(20);
        txtPwd = new JPasswordField(20);
        txtVerifyPwd = new JPasswordField(20);
        btnOk = new JButton();
        btnCancel = new JButton();

        // set the size
        this.setSize(300,250);
        
        // set the display position to the center of the screen
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int)(width-this.getWidth())/2,
               (int)(height-this.getHeight())/2);

        this.setResizable(false);
        this.setTitle("Create A New Session");
        displayPanel.setLayout(null);
        displayPanel.setBackground(Color.cyan);

        // initialize the components
        createSessionLabel.setText("Create a new session.");
        createSessionLabel.setFont(new Font("Calibri",
                Font.BOLD | Font.ITALIC, 14));
        createSessionLabel.setForeground(Color.RED);
        sessionNameLabel.setText("Session Name: ");
        adminNameLabel.setText("Username: ");
        passwordLabel.setText("Password: ");
        verificationLabel.setText("Verify Password: ");

        btnOk.setText("Create");
        btnCancel.setText("Leave");

        createSessionLabel.setBounds(80,15,200,20);
        sessionNameLabel.setBounds(15,55,100,20);
        adminNameLabel.setBounds(40,85,80,25);
        passwordLabel.setBounds(40,115,80,25);
        verificationLabel.setBounds(5,145,100,25);

        this.txtSessionName.setBounds(110,55,120,20);
        this.txtAdminName.setBounds(110,85,120,20);
        this.txtPwd.setBounds(110,115,120,20);
        this.txtVerifyPwd.setBounds(110,145,120,20);

        btnOk.setBounds(125,195,80,20);
        // add a listener to the button
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnOk_ActionPerformed(e);
            }
        });

        btnCancel.setBounds(205,195,80,20);
        // add a listener to the button
        btnCancel.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                window_Closed();
            }
        }));

        // add components to the frame
        displayPanel.add(createSessionLabel);
        displayPanel.add(sessionNameLabel);
        displayPanel.add(adminNameLabel);
        displayPanel.add(passwordLabel);
        displayPanel.add(verificationLabel);
        displayPanel.add(txtSessionName);
        displayPanel.add(txtAdminName);
        displayPanel.add(txtPwd);
        displayPanel.add(txtVerifyPwd);
        displayPanel.add(btnOk);
        displayPanel.add(btnCancel);
        this.add(displayPanel);
        this.setVisible(false);

        // add a listener of closing window
        this.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               window_Closed();
           }
        });
        
        btnOk_ActionPerformed(null);
    }

    /** The operation to create a session */
    private void btnOk_ActionPerformed(ActionEvent e) {
        String sessionName = "Home";//txtSessionName.getText().trim();
        String adminName = getTeacher();//txtAdminName.getText().trim();
        String password = "a";//new String(txtPwd.getPassword());
        String confirmPwd = "a";//new String(txtVerifyPwd.getPassword());
        
        if(sessionName.equals("") || adminName.equals("")) {
            // if the input text is empty
            JOptionPane.showMessageDialog(null,
               "Make true the name of session or administrator is not empty!",
               "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirmPwd)) {
            // if the password does not equals the content
            // of confirm password
            JOptionPane.showMessageDialog(null,
               "Make true the password and the confirm is the same!",
               "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // the identity of user
        int userid = 0;
        try {
            // create a new session and get the identity of session
            int sessionID = sessions.createSessions(sessionName, adminName, password);

            // get the host address
            String hostname = whiteboardClient.getHostName();

            // Make reference to SSL-based registry
            Registry registry = LocateRegistry.getRegistry(
                    hostname, WhiteboardClient.WHITEBOARD_PORT,
                    new javax.rmi.ssl.SslRMIClientSocketFactory()
                    );

            // create the name of service
            String serviceName = "RemoteWhiteboard" + sessionID;

            // "proxy" is the identifier that refer to
            // the remote object that implements the "Whiteboard"
            // interface
            Object proxy = registry.lookup(serviceName);           
            Whiteboard whiteboard = (Whiteboard)proxy;

            // create the mainframe
            MainFrame mainFrame = new MainFrame(sessions,
                    sessionID,whiteboard,userid,adminName);
            
            try{
            	new Config();
            	ServerService server = new ServerService();
            	StartWhiteBoardCommand cmd=  new StartWhiteBoardCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
            	cmd.setSessionID(0);
            	server.send(cmd);
            }
            catch(Exception ex){
            	
            }
            this.dispose();

        } catch(Exception ex) {
            System.out.println("Error during execution:" +
                            ex.getMessage());
        }
    }

    private String getTeacher() {
		// TODO Implement
		return "Admin";
	}

	/** Closing this window */
    private void window_Closed(){
        this.dispose();
    }
}

