package com.jajeem.filemanager.design;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
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

public class FileCollect extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebButton wbtnCollectFiles;
	private WebButton wbtnDeleteCollectedFiles;
	private WebTable webTable;
	private FileTransferEvent fileEvent = new FileTransferEvent();
	private ArrayList<String> files = new ArrayList<>();
	private WebButton wbtnOpen;

	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	@SuppressWarnings("serial")
	public FileCollect() throws Exception {
//		new i18n();
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		wbtnCollectFiles = new CustomFileButton("/icons/noa_en/filecollectbutton.png");
		wbtnCollectFiles.setUndecorated(true);
//		wbtnCollectFiles.setText(i18n.getParam("Collect Files"));

		wbtnDeleteCollectedFiles = new CustomFileButton("/icons/noa_en/fileclearbutton.png");
		wbtnDeleteCollectedFiles.setUndecorated(true);
		wbtnDeleteCollectedFiles.setEnabled(false);
//		wbtnDeleteCollectedFiles.setText(i18n.getParam("Delete Collected Files"));

		wbtnOpen = new CustomFileButton("/icons/noa_en/fileopenbutton.png");
		wbtnOpen.setUndecorated(true);
		wbtnOpen.setEnabled(false);
//		wbtnOpen.setText(i18n.getParam("Open"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 313, Short.MAX_VALUE)
							.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", i18n.getParam("File Name"), i18n.getParam("Status") }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(0).setMinWidth(55);
		webTable.getColumnModel().getColumn(0).setMaxWidth(55);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(2).setMinWidth(105);
		webTable.getColumnModel().getColumn(2).setMaxWidth(105);
		webScrollPane.setViewportView(webTable);
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
						JOptionPane.showMessageDialog(
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
					Desktop.getDesktop().open(
							new File((String) webTable.getValueAt(
									webTable.getSelectedRow(), 1)));
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
				// TODO Auto-generated method stub

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
