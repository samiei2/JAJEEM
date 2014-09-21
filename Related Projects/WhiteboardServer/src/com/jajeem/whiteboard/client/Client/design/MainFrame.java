package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : MainFrame.java
 * Description  : The MainFrame presents the interface of the whole whiteboard
 *                It contains the major components, such as paintpanel, toolpanel
 *                and etc.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import com.jajeem.whiteboard.server.Module.Sessions;
import com.jajeem.whiteboard.server.Module.Whiteboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

/**
 * Class MainFrame is the main frame to provide an interface
 * to users. And it contains the paintpanel and other components.
 */
public class MainFrame extends JFrame {
    /** the default background color of tools */
    public static final Color TOOL_BACKGROUNDCOLOR = new Color(240,240,240);
    
    /** The a main menu which is under the title of the mainframe */
    private MainMenuBar mainMenuBar;

    /** Declare a whiteboard */ 
    private Whiteboard whiteboard;

    /** The components of main frame */
    private ColorToolBar colorToolBar;
    private PaintPanel paintPanel;
    private ToolPanel toolPanel;
    private ChatPanel chatPanel;
    private StatePanel statePanel;

    /** The current session of this main frame */
    private Sessions sessions;
    /** The identity of session */
    private int sessionID;
    /** The identity of user */
    private int userid;
    /** A flag to decide whether user leaves */
    private boolean isUserLeave;
    /** The width and height of the main frame */
    public static int Width = 860, Height = 550;

    /**
     * The constructor of the main frame to initialize all the
     * components and private fields. 
     */
    public MainFrame(Sessions sessions, int sessionID, Whiteboard whiteboard,
                     int userid, String username) {

        // initialize the private fields. 
        this.isUserLeave = false;
        this.sessions = sessions;
        this.sessionID = sessionID;
        this.whiteboard = whiteboard;
        this.userid = userid;

        if(!isLocalWhiteboard()) {
            // if this session is not a local whiteboard
            try {
                // sets the title of this session
//                this.setTitle("Session Name: "+ sessions.getSessionName(sessionID));
            	this.setTitle("Whiteboard");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                       "Error happens while getting the name of the session.",
                       "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // if this session is a local whiteboard
            this.setTitle("Local Whiteboard");
        }

        // it is the container of the paint and color panel
        JPanel paintAndColorPanel;
        // the panel contains the list of user and chat module
        JPanel userlistAndChatPanel;
        // the list of user panel
        UserListPanel userlistPanel;


        // sets the layout of the frame
        this.setLayout(new BorderLayout());

        // create the object of those components
        paintAndColorPanel = new JPanel(new BorderLayout());
        userlistAndChatPanel = new JPanel(new BorderLayout());
        mainMenuBar = new MainMenuBar(this, whiteboard);
        colorToolBar = new ColorToolBar();
        paintPanel = new PaintPanel(this,whiteboard, 
                colorToolBar.getFontData(), colorToolBar.getColorData());
        toolPanel = new ToolPanel(paintPanel,this,colorToolBar.getColorData());
        statePanel = new StatePanel(username);
        userlistPanel = new UserListPanel(userid,whiteboard);
        chatPanel = new ChatPanel(userid, whiteboard);

        
        // get the size of the current screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        if(isLocalWhiteboard()) {
            // if this session is a local whiteboard,
            // which is smaller than distributed one.
            this.setBounds(
                (int)((screen.getWidth() - Width)/2),
                (int)((screen.getHeight()- Height)/2),
                Width-100, Height);
        }
        else {
            // if it is not a local whiteboard, 
            // set the normal size of this frame
            this.setBounds(
                (int)((screen.getWidth() - Width)/2),
                (int)((screen.getHeight()- Height)/2),
                Width, Height);
        }

        this.setVisible(true);

        Thread listener;
        try {
            // creates a thread to check the state of server 
            listener = new Thread(new WhiteboardListener(whiteboard,
                    paintPanel,userlistPanel,userid,this));
            // start the thread
            listener.start();

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                       "Error happens while starting the listener thread," +
                       "this frame will be closed.",
                       "Error", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
        }

        // add the method to listen the event of closing
        // window in order to clean the information in 
        // the server.
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent event){
                try{
                    window_exit();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // add the corresponding components to
        // each panel
        paintAndColorPanel.add("Center", paintPanel);
        paintAndColorPanel.add("South", colorToolBar);
        paintAndColorPanel.add("West",toolPanel);

        userlistAndChatPanel.add("North", userlistPanel);
        userlistAndChatPanel.add("South", chatPanel);

        if(!isLocalWhiteboard()) {
            this.add("East", userlistAndChatPanel);
        }

        this.add("Center", paintAndColorPanel);
        this.add("South", statePanel);
        this.setJMenuBar(mainMenuBar);
    }

    /** The use closes this session */
    public void window_exit() throws RemoteException{
        userLeave();
        this.dispose();
    }

    /** Get the paint panel*/
    public PaintPanel getPaintPanel(){
        return this.paintPanel;
    }

    /** Get the identity of user */
    public int getUserid(){
        return this.userid;
    }

    /** Get the stroke used */
    public float getCurrentStroke(){
        return this.toolPanel.getCurrentStroke();
    }

    /** Get the flag whether the drawing shape is square */
    public boolean getIsSquare(){
        return this.toolPanel.getIsSquare();
    }

    /** Set the state informaiton of mouse */
    public void setMouseState(String mouseState) {
        statePanel.setMouseState(mouseState);
    }

    /** Set the type of shape */
    public void setShapeType(String shapeType) {
        statePanel.setShapeType(shapeType);
    }

    /** Set the visible of font panel */
    public void setFontPanelVisble(boolean isVisible){
        colorToolBar.setFontPanelVisble(isVisible);
    }

    /** Do the operations to clean information */
    public void userLeave() throws RemoteException {
        whiteboard.userLeave(this.userid);
        if(sessions != null){
            if(userid == 0) {
                // if user is the administrator
                sessions.closeSession(sessionID);
            }
            else{
                sessions.leaveSession(sessionID);
            }
        }
        // set the flag to stop the listener thread.
        isUserLeave = true;
    }

    /** Get the flag to indicate whether user leaves.*/
    public boolean isUserLeave() {
        return this.isUserLeave;
    }

    /** Get the drawing shape whether is transparent. */
    public boolean isTransparent() {
        return this.mainMenuBar.isTransparent();
    }

    /** Get the path of image file */
    public String getImageFilePath() {
        return toolPanel.getImageFilePath();
    }

    /** Get the flag whether is drawing an image */
    public boolean isDrawImage(){
        return toolPanel.isDrawImage();
    }

    /** Reset the selected index of tool panel */
    public void resetToolPanel(){
        toolPanel.resetCursor();
    }

    /** Append the message to the chat panel */
    public void appendChatText(String messages) {
        this.chatPanel.appendChatText(messages);
    }

    /** Return the result whether this session is a local whiteboard */
    public boolean isLocalWhiteboard(){
        return sessions == null;
    }
}
