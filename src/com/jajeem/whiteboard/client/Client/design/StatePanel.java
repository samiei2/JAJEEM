package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : StatePanel.java
 * Description  : The StatePanel implements a state bar, which displays
 *                the current cursor's position, drawing type of shape
 *                and the user.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import javax.swing.*;
import java.awt.*;

/**
 * Class StatePanel displays the information of cursor's position, drawing
 * type, and the name of current user.
 */
public class StatePanel extends JPanel {

    /** The label displays the state of current user information */
    private JLabel labelState;

    /** The label displays the current type of shape */
    private JLabel labelType;

    /** The constructor of StatePanel to initialize the variables  */
    public StatePanel(String username) {
        // create a label display the name of user
        JLabel labelUsername;
        JPanel usernameAndTypePanel;
        // set the layout type of this panel
		this.setLayout(new BorderLayout());

        // initializes the components
		labelState = new JLabel();
		labelState.setPreferredSize(new Dimension(200,23));
		labelType = new JLabel("Current Shape: Cursor");
        labelType.setPreferredSize(new Dimension(200,23));
        labelUsername = new JLabel("Username: "+ username);
        labelUsername.setPreferredSize(new Dimension(200,23));

        usernameAndTypePanel = new JPanel();
        usernameAndTypePanel.setLayout(new BorderLayout());
        usernameAndTypePanel.add("East",labelUsername);
        usernameAndTypePanel.add("West",labelType);

        this.add("Center",usernameAndTypePanel);
		this.add("West",labelState);
	}

    /** Set the state of mouse */
    public void setMouseState(String mouseState) {
		labelState.setText(" " + mouseState);
	}

    /** Set the type of current shape */
    public void setShapeType(String shapeType) {
		labelType.setText("Current Shape: " + shapeType + " ");
	}
}
