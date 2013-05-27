package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : ChatPanel.java
 * Description  : This is the panel to provide the implementation
 *                of the chat interface.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;


import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.whiteboard.server.Module.Whiteboard;

/**
 * Class ChatPanel provides the implementation of the chat interface,
 * which receives the input from users and displays the messages passed
 * by other users.
 */
public class ChatPanel extends WebPanel {
    /** The display area */
    private WebTextArea textArea;

    /** The input area */
    private WebTextField textInput;

    /** Declare a whiteboard object */
    private Whiteboard whiteboard;

    /** The identity of user */
    private int userid;

    /** The constructor */
    public ChatPanel(int userid, Whiteboard whiteboard){
        // Declare a scroll pane which can change the size
        // of display area
        WebScrollPane scrollPane;

        // The label of title
        WebLabel chatLabel;

        // initialize instance variables
        this.userid = userid;
        this.whiteboard = whiteboard;
        // sets the layout and size of this panel
        this.setLayout(null);
        this.setPreferredSize(new Dimension(205, 250));
        
        // creates the title label
        chatLabel = new WebLabel("Chat Channel");
        chatLabel.setBounds(50,0,130,20);
        chatLabel.setFont(new Font("Calibri",
                Font.BOLD, 20));

        // initialize the display and input area
        textArea = new WebTextArea();
        textArea.setBounds(5,20,200,200);
        textArea.setEditable(false);
        textInput = new WebTextField();
        textInput.setBounds(5,220,200,20);

        // initialize the scroll pane
        scrollPane = new WebScrollPane(textArea);
        scrollPane.setVisible(true);
        scrollPane.setBounds(5,20,200,200);

        // add the method to deal with the key event
        textInput.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent keyEvent) {
                // get the code of key pressed
                int keycode = keyEvent.getKeyCode();

                // press the "enter" key, when
                // finish the input
                if(keycode == 10) {
                    try {
                        sendMessages();
                    } catch(Exception ex) {
                        WebOptionPane.showMessageDialog(null,
                            "Error happens while sending the message.",
                            "Error", WebOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        // add the components to this panel
        this.add(chatLabel);
        this.add(scrollPane);
        this.add(textInput);
    }

    /**
     * The method to send message.
     * @throws RemoteException Error happens while sending the message.
     */
    private void sendMessages() throws RemoteException {
        String messages = textInput.getText();
        if(messages.length() != 0) {
            // send message
            whiteboard.sendMessages(userid, messages);
            // clear the input area
            textInput.setText("");
        }
    }

    /**
     * Append the message to the display area
     * @param messages The message to be presented
     */
    public void appendChatText(String messages) {
        // creates a time format
        DateFormat timeFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,DateFormat.MEDIUM);
        
        // get the local time
        String currentTime = timeFormat.format(new Date());

        // append the message
        this.textArea.append(currentTime + "\n");
        this.textArea.append(messages + "\n");

        // set the focus of the display text and always display
        // the latest information
        this.textArea.setCaretPosition(this.textArea.getText().length());
    }
}
