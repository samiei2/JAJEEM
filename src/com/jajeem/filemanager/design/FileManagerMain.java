package com.jajeem.filemanager.design;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.alee.laf.tabbedpane.WebTabbedPane;
import com.jajeem.exception.JajeemExceptionHandler;

public class FileManagerMain extends FileManagerBase{
	private FileSendTab fileSendTab;
	private FileCollect fileCollectTab;
	private FileInbox fileInboxTab;
	private FileAssignmentTab fileAssignmentTab;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.alee.laf.panel.WebPanel webPanel1;
	private WebTabbedPane webTabbedPane;
	private ArrayList<String> receivingIps;
	
	static FileManagerMain internalaccessor = new FileManagerMain();
	
	public FileManagerMain() {
		if(internalaccessor != null){
			if(internalaccessor.isVisible()){
				internalaccessor.toFront();
				return;
			}
			else{
				internalaccessor.setVisible(true);
				return;
			}
		}
		
		setLookAndFeel();
		setBackground(new Color(0,0,0,0));
		try {
			fileSendTab = new FileSendTab(this);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		try {
			fileCollectTab = new FileCollect();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			fileInboxTab = new FileInbox();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			fileAssignmentTab = new FileAssignmentTab(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		webTabbedPane = new WebTabbedPane();
//		webTabbedPane.setOpaque(false);
		
		try{
			webTabbedPane.addTab("File Send", fileSendTab);
			webTabbedPane.addTab("File Assignments", fileAssignmentTab);
			webTabbedPane.addTab("File Collect", fileCollectTab);
			webTabbedPane.addTab("File Inbox", fileInboxTab);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webTabbedPane, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		getMainContentPane().setLayout(groupLayout);
		
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		initEvents();
		internalaccessor = this;
		setVisible(true);
	}
	
	public void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				fileInboxTab.SaveRequests();
			}
		});
		
		getCloseButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
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

	public void invokeFileCollect() {
		fileCollectTab.CollectFiles();
	}

	public void setReceivingIps(ArrayList<String> arrayList) {
		receivingIps = arrayList;
	}

	public ArrayList<String> getReceivingIps() {
		return receivingIps;
	}
	
	public static void main(String[] args) {
		FileManagerMain main = new FileManagerMain();
		main.setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
