package com.jajeem.recorder.design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.label.WebLabel;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.sun.jna.platform.FileMonitor.FileEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.bouncycastle.crypto.tls.AlwaysValidVerifyer;
import java.awt.Dialog.ModalityType;

public class RecorderProgressWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private WebProgressBar webProgressBar;
	private FileTransferEvent event = new FileTransferEvent();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecorderProgressWindow dialog = new RecorderProgressWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecorderProgressWindow() {
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setModal(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 534, 96);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		webProgressBar = new WebProgressBar(0, 100);
		
		WebLabel wblblFileTransferIn = new WebLabel();
		wblblFileTransferIn.setText("File transfer in progress. Please wait ...");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(webProgressBar, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(159)
							.addComponent(wblblFileTransferIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wblblFileTransferIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(52, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		event.addEventListener(new FileTransferEventListener() {
			
			@Override
			public void success(FileTransferObject evt, Class t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void progress(FileTransferObject evt, Class t) {
				if(t!=RecorderProgressWindow.class)
					return;
				setProgressValue((int)evt.getProgressValue());
			}
			
			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fail(FileTransferObject evt, Class t) {
				// TODO Auto-generated method stub
				
			}
		}, RecorderProgressWindow.class);
	}
	
	public void setProgressValue(int i){
		webProgressBar.setValue(i);
	}

	public void reset() {
		webProgressBar.setValue(0);
	}
}
