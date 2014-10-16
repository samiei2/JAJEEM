package com.jajeem.whiteboard.client.Client.design;
/**
 * File Name    : UserListPanel.java
 * Description  : The UserListPanel implements a display list of the current
 *                peers.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import com.jajeem.whiteboard.server.Module.Whiteboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class UserListPanel is the list to display the information of peers,
 * including the name, type and who has the drawing power. 
 */
public class UserListPanel extends JPanel {
    /** The name of columns */
    private final String[] columnNames = {"Username","Type","Right"};

    /** The list of user information */
    private JTable userlist;

    /** The scroll pane which provides a movable display area */
    private JScrollPane scrollPane;

    /** The model of table which implements the modify of table */
    private TableModel tableModel;

    /** The place to store the data */
    private Object[][] data;

    /** Declare a reference of the Whiteboard */
    private Whiteboard whiteboard;

    /** The constructor to initialize */
    public UserListPanel(int userid, Whiteboard whiteboard) {
        
        JButton btnGrantRight;
        JButton btnWithdrawRight;
        JPanel rightManagePanel;
        JPanel btnPanel;
        
        this.whiteboard = whiteboard;
        try {
            // get the list of current peers information
            // from the whiteboard
            data = whiteboard.getUserlist();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error happens while reading the list of user information.",
                "Error", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
        }
        tableModel = new DefaultTableModel(data,columnNames);
        // create the table and change it editable to disable
        userlist = new JTable(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rightManagePanel = new JPanel();
        rightManagePanel.setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.setLayout(new BorderLayout()); 

        btnGrantRight = new JButton("Grant");
        btnWithdrawRight = new JButton("Withdraw");

        btnGrantRight.setPreferredSize(new Dimension(100,20));
        // listen the event of button clicked
        btnGrantRight.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnGrantRight_ActionPerformed(e);
            }
        });

        btnWithdrawRight.setPreferredSize(new Dimension(100,20));
        // listen the event of button clicked 
        btnWithdrawRight.addActionListener((new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                btnWithdrawRight_ActionPerformed(e);
            }
        }));

        // sets the table model to the JTable, which
        // is used for updating the JTable.
        userlist.setModel(tableModel);
        // sets that the table only choose one line
        userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userlist.setPreferredScrollableViewportSize(new Dimension(200, 160));
        userlist.getTableHeader().setResizingAllowed(false);
        
        scrollPane = new JScrollPane(userlist);
        if(userid != 0)
        {
            // if the user is not an administrator of
            // this session, he/she will not have the
            // view of those buttons
            btnGrantRight.setVisible(false);
            btnWithdrawRight.setVisible(false);
        }
        btnPanel.add("West", btnGrantRight);
        btnPanel.add("Center", btnWithdrawRight);

        rightManagePanel.add("North",scrollPane);
        rightManagePanel.add("South",btnPanel);

        this.add(rightManagePanel);
    }

    /** Updates the list of the user information */
    public void update(Object[][] data) {
        // create a model of table according to the new data
        tableModel = new DefaultTableModel(data,columnNames);
        // set the model of the userlist
        userlist.setModel(tableModel);
        // set the displayed component in scroll pane
        scrollPane.setViewportView(userlist);
    }

    /** Grant the drawing right to the specific user */
    private void btnGrantRight_ActionPerformed(ActionEvent e) {
        int selectedRow = userlist.getSelectedRow();
        
        if (selectedRow >= 0){
            try {
                // grant the right to the selected row 
                this.whiteboard.grantRightTo(selectedRow);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                    "Error happens while withdrawing the drawing right.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }       
    }

    /** Withdraw the drawing right */
    private void btnWithdrawRight_ActionPerformed(ActionEvent e) {
        try {
            this.whiteboard.withdrawRight();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error happens while withdrawing the drawing right.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
