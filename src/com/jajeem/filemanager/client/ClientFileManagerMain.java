package com.jajeem.filemanager.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.filemanager.design.FileManagerBase;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.tabbedpane.WebTabbedPane;

public class ClientFileManagerMain extends FileManagerBase {

	private static final long serialVersionUID = 1L;
	private ClientFileSendTab fileSendTab;
	private ClientFileInbox fileInboxTab;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private WebTabbedPane webTabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFileManagerMain frame = new ClientFileManagerMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientFileManagerMain() {
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		fileSendTab = new ClientFileSendTab();
		fileInboxTab = new ClientFileInbox();
		
		webTabbedPane = new WebTabbedPane();
		webTabbedPane.setOpaque(true);
		
		webTabbedPane.addTab("File Send", fileSendTab);
		webTabbedPane.addTab("File Inbox", fileInboxTab);
		
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webTabbedPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webTabbedPane, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		getMainContentPane().setLayout(groupLayout);
		
		getCloseButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
	}
	
	protected static final void setLookAndFeel() {
		String lookAndFeelClassName = null;
		LookAndFeelInfo[] lookAndFeelInfos = UIManager
				.getInstalledLookAndFeels();
		for (LookAndFeelInfo lookAndFeel : lookAndFeelInfos) {
			if ("Windows".equals(lookAndFeel.getName())) {
				lookAndFeelClassName = lookAndFeel.getClassName();
			}
		}
		if (lookAndFeelClassName == null) {
			lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
		}
		try {
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
		}
	}
}
