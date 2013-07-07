/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.filemanager.design;
import com.alee.laf.tabbedpane.WebTabbedPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeListener;
import javax.swing.GroupLayout;
import javax.swing.SingleSelectionModel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import com.alee.laf.button.WebButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.table.WebTable;
import com.jajeem.filemanager.InstructorServer;

/**
 *
 * @author Armin
 */
public class FileManagerMain extends javax.swing.JFrame {

    private FileSendTab fileSendTab;
	private FileCollect fileCollectTab;
	private FileInbox fileInboxTab;
	private FileAssignmentTab fileAssignmentTab;
	/**
     * Creates new form FileManagerMain
     */
    public FileManagerMain() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        webPanel1 = new com.alee.laf.panel.WebPanel();
        fileSendTab = new FileSendTab();
        fileCollectTab = new FileCollect();
        fileInboxTab = new FileInbox();
        fileAssignmentTab = new FileAssignmentTab();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        webTabbedPane = new WebTabbedPane();
        webTabbedPane.addTab("File Send", fileSendTab);
        webTabbedPane.addTab("File Assignments", fileAssignmentTab);
        webTabbedPane.addTab("File Collect", fileCollectTab);
        webTabbedPane.addTab("File Inbox", fileInboxTab);
        

        javax.swing.GroupLayout webPanel1Layout = new javax.swing.GroupLayout(webPanel1);
        webPanel1Layout.setHorizontalGroup(
        	webPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(webPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(webTabbedPane, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        			.addContainerGap())
        );
        webPanel1Layout.setVerticalGroup(
        	webPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(webPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(webTabbedPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        			.addContainerGap())
        );
        webPanel1.setLayout(webPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(webPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(webPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FileManagerMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.panel.WebPanel webPanel1;
    private WebTabbedPane webTabbedPane;
}
