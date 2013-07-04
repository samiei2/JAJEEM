package com.jajeem.filemanager.design;

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
import com.jajeem.filemanager.InstructorServer;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FileInbox extends WebPanel {
	private WebTable webTable;
	private WebButton wbtnDismissAll;
	private WebButton wbtnRejectFile;
	private WebButton wbtnAccept;
	private FileTransferEvent fileEvent = new FileTransferEvent();
	private ArrayList<FileTransferObject> fileSendRequestList = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public FileInbox() {
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		wbtnAccept = new WebButton();
		wbtnAccept.setEnabled(false);
		wbtnAccept.setText("Accept File");
		
		wbtnRejectFile = new WebButton();
		wbtnRejectFile.setEnabled(false);
		wbtnRejectFile.setText("Reject File");
		
		wbtnDismissAll = new WebButton();
		wbtnDismissAll.setEnabled(false);
		wbtnDismissAll.setText("Dismiss All");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnAccept, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnRejectFile, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDismissAll, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbtnDismissAll, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnAccept, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnRejectFile, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#","File Name", "From","Status"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(0).setMinWidth(55);
		webTable.getColumnModel().getColumn(0).setMaxWidth(55);
		webTable.getColumnModel().getColumn(2).setMinWidth(95);
		webTable.getColumnModel().getColumn(2).setMaxWidth(95);
		webTable.getColumnModel().getColumn(3).setMinWidth(95);
		webTable.getColumnModel().getColumn(3).setMaxWidth(95);
		
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);
		initEvents();
		
		
		
	}

	private void initEvents() {
		wbtnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FileTransferEvent().fireAcceptFileRequest(fileSendRequestList.get(webTable.getSelectedRow()), InstructorServer.class);
			}
		});
		
		wbtnRejectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FileTransferEvent().fireRejectFileRequest(fileSendRequestList.get(webTable.getSelectedRow()), InstructorServer.class);
				fileSendRequestList.remove(webTable.getSelectedRow());
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.removeRow(webTable.getSelectedRow());
				model.fireTableDataChanged();
				webTable.updateUI();
				
				if(webTable.getRowCount()==0){
					wbtnAccept.setEnabled(false);
					wbtnDismissAll.setEnabled(false);
					wbtnRejectFile.setEnabled(false);
				}
			}
		});
		
		wbtnDismissAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < fileSendRequestList.size(); i++) {
					new FileTransferEvent().fireRejectFileRequest(fileSendRequestList.get(i), InstructorServer.class);
				}
				
				fileSendRequestList.clear();
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				wbtnAccept.setEnabled(false);
				wbtnDismissAll.setEnabled(false);
				wbtnRejectFile.setEnabled(false);
			}
		});
		
		fileEvent.addEventListener(new FileTransferEventListener() {
			
			@Override
			public void success(FileTransferObject evt, Class t) {
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.setValueAt(evt.getFileName(), webTable.getSelectedRow(), 1);
				model.setValueAt("Success", webTable.getSelectedRow(), 3);
			}
			
			@Override
			public void progress(FileTransferObject evt, Class t) {
				
			}
			
			@Override
			public void fail(FileTransferObject evt, Class t) {
				
			}

			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				if(t!=FileInbox.class)
					return;
				fileSendRequestList.add(evt);
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.addRow(new Object[]{
						webTable.getRowCount() == 0 ? 1 : webTable.getRowCount() + 1,
						"Cannot show file name until accept",
						evt.getClientSocket().getInetAddress().getHostAddress(),
						"Pending"
				});
				wbtnAccept.setEnabled(true);
				wbtnRejectFile.setEnabled(true);
				wbtnDismissAll.setEnabled(true);
			}

			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
				
			}

			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				
			}
		}, FileInbox.class);
	}
}
