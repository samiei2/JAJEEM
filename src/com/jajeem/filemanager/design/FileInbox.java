package com.jajeem.filemanager.design;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.InstructorServer;
import com.jajeem.util.Audio;
import com.jajeem.util.FileUtil;
import com.jajeem.util.Session;
import com.jajeem.util.i18n;

public class FileInbox extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable webTable;
	private WebButton wbtnDismissAll;
	private WebButton wbtnRejectFile;
	private WebButton wbtnAccept;
	private FileTransferEvent fileEvent = new FileTransferEvent();
	private ArrayList<FileTransferObject> fileSendRequestList = new ArrayList<>();
	private WebButton wbtnRefresh;

	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	public FileInbox() throws Exception {
//		new i18n();
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		wbtnAccept = new CustomFileButton("/icons/noa_en/fileacceptbutton.png");
		wbtnAccept.setUndecorated(true);
		wbtnAccept.setEnabled(false);
//		wbtnAccept.setText(i18n.getParam("Accept File"));

		wbtnRejectFile = new CustomFileButton("/icons/noa_en/filerejectbutton.png");
		wbtnRejectFile.setUndecorated(true);
		wbtnRejectFile.setEnabled(false);
//		wbtnRejectFile.setText(i18n.getParam("Reject File"));

		wbtnDismissAll = new CustomFileButton("/icons/noa_en/fileclearbutton.png");
		wbtnDismissAll.setUndecorated(true);
		wbtnDismissAll.setEnabled(false);
//		wbtnDismissAll.setText(i18n.getParam("Clear"));

		wbtnRefresh = new CustomFileButton("/icons/noa_en/filebrowsebutton.png");
		wbtnRefresh.setUndecorated(true);
//		wbtnRefresh.setText(i18n.getParam("Refresh"));
		wbtnRefresh.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnAccept, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnRejectFile, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDismissAll, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
							.addComponent(wbtnRefresh, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(wbtnAccept, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnRefresh, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnDismissAll, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(wbtnRejectFile, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", i18n.getParam("File Name"), i18n.getParam("From"), i18n.getParam("Status") }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			@Override
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
		PopulateInbox();
	}

	private void PopulateInbox() throws Exception {

		String inboxPath = FileUtil.getInboxPath();
		File inbox = new File(inboxPath);
		if (!inbox.exists()) {
			inbox.mkdirs();
		}
		if (inbox.exists()) {
			File[] list = inbox.listFiles();
			DefaultTableModel model = (DefaultTableModel) webTable.getModel();
			for (int i = 0; i < list.length; i++) {
				fileSendRequestList.add(null);
				// files.add(list[i]);
				model.addRow(new Object[] {
						webTable.getRowCount() == 0 ? 1 : webTable
								.getRowCount() + 1, list[i].getAbsolutePath(),
								i18n.getParam("N/A"), i18n.getParam("Received") });
			}
			for (int i = 0; i < Session.getFileRequestList().size(); i++) {
				fileSendRequestList.add((FileTransferObject) Session
						.getFileRequestList().get(i));
				model.addRow(new Object[] {
						webTable.getRowCount() == 0 ? 1 : webTable
								.getRowCount() + 1,
								i18n.getParam("Cannot show file name until accept"),
						((FileTransferObject) Session.getFileRequestList().get(
								i)).getClientSocket().getInetAddress()
								.getHostAddress(), i18n.getParam("Pending") });
				wbtnAccept.setEnabled(true);
				wbtnRejectFile.setEnabled(true);
				wbtnDismissAll.setEnabled(true);
			}
			Session.getFileRequestList().clear();
		}
	}

	private void initEvents() {
		wbtnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FileTransferEvent().fireAcceptFileRequest(
							fileSendRequestList.get(webTable.getSelectedRow()),
							InstructorServer.class);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
				}
			}
		});

		wbtnRejectFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileTransferEvent().fireRejectFileRequest(
						fileSendRequestList.get(webTable.getSelectedRow()),
						InstructorServer.class);
				fileSendRequestList.remove(webTable.getSelectedRow());
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.removeRow(webTable.getSelectedRow());
				model.fireTableDataChanged();
				webTable.updateUI();

				if (webTable.getRowCount() == 0) {
					wbtnAccept.setEnabled(false);
					wbtnDismissAll.setEnabled(false);
					wbtnRejectFile.setEnabled(false);
				}
			}
		});

		wbtnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				try {
					PopulateInbox();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		wbtnDismissAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < fileSendRequestList.size(); i++) {
					if (fileSendRequestList.get(i) != null) {
						new FileTransferEvent().fireRejectFileRequest(
								fileSendRequestList.get(i),
								InstructorServer.class);
					}
				}

				fileSendRequestList.clear();
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				wbtnAccept.setEnabled(false);
				wbtnDismissAll.setEnabled(false);
				wbtnRejectFile.setEnabled(false);
			}
		});

		webTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = webTable.getSelectedRow();
						if (selectedRow != -1) {
							if (fileSendRequestList.get(selectedRow) == null) {
								wbtnAccept.setEnabled(false);
								wbtnRejectFile.setEnabled(false);
							} else {
								wbtnAccept.setEnabled(true);
								wbtnRejectFile.setEnabled(true);
							}
						}
					}
				});

		fileEvent.addEventListener(new FileTransferEventListener() {

			@Override
			public void success(FileTransferObject evt, Class t) {
				if (t != FileInbox.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.setValueAt(evt.getFileName(),
							webTable.getSelectedRow(), 1);
					model.setValueAt(i18n.getParam("Success"), webTable.getSelectedRow(), 3);
					Desktop.getDesktop()
							.open(new File(FileUtil.getInboxPath()));
					Audio.playSound("util/Ding.aiff");
				} catch (Exception e) {
				}
			}

			@Override
			public void progress(FileTransferObject evt, Class t) {

			}

			@Override
			public void fail(FileTransferObject evt, Class t) {
				if (t != FileInbox.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.setValueAt(i18n.getParam("N/A"), webTable.getSelectedRow(), 1);
					System.out.println(webTable.getSelectedRow());
					model.setValueAt(i18n.getParam("Failed"), webTable.getSelectedRow(), 3);
				} catch (Exception e) {
				}
			}

			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				if (t != FileInbox.class) {
					return;
				}
				try {
					fileSendRequestList.add(evt);
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.addRow(new Object[] {
							webTable.getRowCount() == 0 ? 1 : webTable
									.getRowCount() + 1,
									i18n.getParam("Cannot show file name until accept"),
							evt.getClientSocket().getInetAddress()
									.getHostAddress(), i18n.getParam("Pending") });
					wbtnAccept.setEnabled(true);
					wbtnRejectFile.setEnabled(true);
					wbtnDismissAll.setEnabled(true);
					Session.getFileRequestList().clear();
				} catch (Exception e) {
				}
			}

			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {

			}

			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {

			}
		}, FileInbox.class);
	}

	public void SaveRequests() {
		for (int i = 0; i < fileSendRequestList.size(); i++) {
			if (fileSendRequestList.get(i) != null) {
				Session.getFileRequestList().add(fileSendRequestList.get(i));
			}
		}
	}
}
