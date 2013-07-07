package com.jajeem.filemanager.client;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import com.alee.laf.button.WebButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.table.WebTable;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;

import java.io.File;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ClientFileInbox extends WebPanel {
	private WebTable webTable;
	private WebButton wbtnOpen;
	private FileTransferEvent fileEvents;
	private ArrayList<String> fileList = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public ClientFileInbox() {
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		wbtnOpen = new WebButton();
		wbtnOpen.setText("Open");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
						.addComponent(wbtnOpen, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "File Name","Status"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(0).setMinWidth(55);
		webTable.getColumnModel().getColumn(0).setMaxWidth(55);
		webTable.getColumnModel().getColumn(2).setMinWidth(55);
		webTable.getColumnModel().getColumn(2).setMaxWidth(55);
		
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);
		initEvents();
	}

	private void initEvents() {
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Runtime.getRuntime().exec(new String[]
					        {"rundll32 url.dll,FileProtocolHandler",
					        	new File(fileList.get(webTable.getSelectedRow())).getAbsolutePath()});
				} catch (IOException e1) {
					JajeemExcetionHandler.logError(e1,ClientFileInbox.class);
					e1.printStackTrace();
				}
			}
		});
		
		fileEvents = new FileTransferEvent();
		fileEvents.addEventListener(new FileTransferEventListener() {
			
			@Override
			public void success(FileTransferObject evt, Class t) {
				if(t!=ClientFileInbox.class)
					return;
				fileList.add(evt.getFileName());
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.addRow(new Object[]{
						webTable.getRowCount() == 0 ? 1 : webTable.getRowCount() + 1,
						evt.getFileName(),
						"Received"
				});
			}
			
			@Override
			public void progress(FileTransferObject evt, Class t) {
				
			}
			
			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				// 
				
			}
			
			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				// 
				
			}
			
			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
				// 
				
			}
			
			@Override
			public void fail(FileTransferObject evt, Class t) {
				// 
				
			}
		}, ClientFileInbox.class);
	}
}
