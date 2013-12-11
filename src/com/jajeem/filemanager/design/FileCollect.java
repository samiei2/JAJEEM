package com.jajeem.filemanager.design;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.table.WebTable;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.InstructorServer;
import com.jajeem.util.Audio;
import com.jajeem.util.Config;
import com.jajeem.util.i18n;
import java.awt.Component;

public class FileCollect extends JPanel {
	private CustomFileButton wbtnOpen;
	private CustomFileButton wbtnDeleteCollectedFiles;
	private CustomFileButton wbtnCollectFiles;
	
	private FileTransferEvent fileEvent = new FileTransferEvent();
	private ArrayList<String> files = new ArrayList<>();
	private WebTable webTable;

	/**
	 * Create the panel.
	 */
	public FileCollect() {
		
		JScrollPane scrollPane = new JScrollPane();
		
		wbtnCollectFiles = new CustomFileButton("/icons/noa_en/filecollectbutton.png");
		wbtnCollectFiles.setToolTipText("Collect Files");
		wbtnCollectFiles.setUndecorated(true);
		wbtnCollectFiles.setText("");
		
		wbtnDeleteCollectedFiles = new CustomFileButton("/icons/noa_en/fileclearbutton.png");
		wbtnDeleteCollectedFiles.setToolTipText("Delete Collected Files");
		wbtnDeleteCollectedFiles.setUndecorated(true);
		wbtnDeleteCollectedFiles.setText("");
		
		wbtnOpen = new CustomFileButton("/icons/noa_en/fileopenbutton.png");
		wbtnOpen.setToolTipText("Open File");
		wbtnOpen.setUndecorated(true);
		wbtnOpen.setText("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
							.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		
		webTable = new WebTable();
		try {
			webTable.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "#", i18n.getParam("File Name"), i18n.getParam("Status") }) {
				boolean[] columnEditables = new boolean[] { false, false, false };

				@Override
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		webTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(0).setMinWidth(55);
		webTable.getColumnModel().getColumn(0).setMaxWidth(55);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(2).setMinWidth(105);
		webTable.getColumnModel().getColumn(2).setMaxWidth(105);
		scrollPane.setViewportView(webTable);
		setLayout(groupLayout);

		initEvents();
	}
	
	private void initEvents() {
		wbtnCollectFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					CollectFiles();
				} catch (Exception e) {

				}
			}
		});

		wbtnDeleteCollectedFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < files.size(); i++) {
					File file = new File(files.get(i));
					try {
						file.delete();
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e, FileCollect.class);
						WebOptionPane.showMessageDialog(
								null,
								file.getAbsolutePath()
										+ " Can't be deleted!\n Please check your permissions.");
					}
				}
				files.clear();
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				if (webTable.getRowCount() != 0) {
					wbtnDeleteCollectedFiles.setEnabled(false);
					wbtnOpen.setEnabled(false);
				}
			}
		});

		wbtnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(webTable.getRowCount() != 0)
						if(webTable.getSelectedRow()!=-1){
							Desktop.getDesktop().open(
									new File((String) webTable.getValueAt(
											webTable.getSelectedRow(), 1)));
						}
						else{
							WebOptionPane.showMessageDialog(null, "No row is selected!\nPlease select a row first!");
						}
					else{
						WebOptionPane.showMessageDialog(null, "Table is empty!");
					}
					// Desktop.getDesktop().open(new
					// File(files.get(webTable.getSelectedRow())));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		fileEvent.addEventListener(new FileTransferEventListener() {

			@Override
			public void success(FileTransferObject evt, Class t) {
				if (t != FileCollect.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.addRow(new Object[] {
							webTable.getRowCount() == 0 ? 1 : webTable
									.getRowCount() + 1, evt.getFileName(),
									i18n.getParam("Received") });
					files.add(evt.getFileName());
					if (webTable.getRowCount() != 0) {
						wbtnDeleteCollectedFiles.setEnabled(true);
						wbtnOpen.setEnabled(true);
					}
					Audio.playSound("util/Ding.aiff");
				} catch (Exception e) {
				}
			}

			@Override
			public void progress(FileTransferObject evt, Class t) {
			}

			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				if (t != FileCollect.class) {
					return;
				}
				try {
					new FileTransferEvent().fireAcceptFileRequest(evt,
							InstructorServer.class);
				} catch (Exception e) {
				}
			}

			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
			}

			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
			}

			@Override
			public void fail(FileTransferObject evt, Class t) {
			}
		}, FileCollect.class);
	}

	public void CollectFiles() {
		DefaultTableModel model = (DefaultTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webTable.repaint();
		webTable.updateUI();

		try {
			new Config();
			ServerService service;
			if (InstructorNoa.getServerService() == null) {
				service = new ServerService();
			} else {
				service = InstructorNoa.getServerService();
			}
			ArrayList<String> ips = InstructorNoa.getAllStudentIPs();
			for (int i = 0; i < ips.size(); i++) {
				SendFileCollectCommand cmd = new SendFileCollectCommand(
						InetAddress.getLocalHost().getHostAddress(),
						ips.get(i), Integer.parseInt(Config.getParam("port")));
				service.send(cmd);
			}
		} catch (Exception e) {

		}
	}
}
