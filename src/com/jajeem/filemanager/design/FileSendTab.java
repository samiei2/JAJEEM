package com.jajeem.filemanager.design;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.model.Group;
import com.jajeem.util.i18n;

public class FileSendTab extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable webTable;
	private WebButton wbtnBrowse;
	private WebButton wbtnSend;
	private WebButton wbtnClear;
	private FileSendTab currentPanel;
	private ArrayList<String> fileNames = new ArrayList<>();
	private FileTransferEvent fileTransferEvent = new FileTransferEvent();
	private int currentIndex;
	private WebCheckBox chckbxSendToAll;

	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	public FileSendTab(FileManagerMain main) throws Exception {
//		new i18n();
		currentPanel = this;
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		wbtnBrowse = new CustomFileButton("/icons/noa_en/filebrowsebutton.png");
		wbtnBrowse.setUndecorated(true);
//		wbtnBrowse.setText(i18n.getParam("Browse"));

		wbtnClear = new CustomFileButton("/icons/noa_en/fileclearbutton.png");
		wbtnClear.setUndecorated(true);
		wbtnClear.setEnabled(false);
//		wbtnClear.setText(i18n.getParam("Clear"));

		wbtnSend = new CustomFileButton("/icons/noa_en/fileacceptbutton.png");
		wbtnSend.setUndecorated(true);
		wbtnSend.setEnabled(false);
//		wbtnSend.setText(i18n.getParam("Send"));

		chckbxSendToAll = new WebCheckBox(i18n.getParam("Send to all"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(chckbxSendToAll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
							.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxSendToAll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", i18n.getParam("File Name"), i18n.getParam("Status") }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(45);
		webTable.getColumnModel().getColumn(0).setMaxWidth(45);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		webTable.getColumnModel().getColumn(2).setMaxWidth(100);
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);
		initEvents();
	}

	private void initEvents() {
		wbtnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = chooser.showOpenDialog(currentPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
					File[] files = chooser.getSelectedFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].isDirectory()) {
							fileNames
									.addAll(getPath(getDirectoryContent(files[i])));
						} else {
							fileNames.add(files[i].getAbsolutePath());
						}
					}
					if (fileNames.size() != 0) {
						wbtnSend.setEnabled(true);
						wbtnClear.setEnabled(true);
					}
					Collections.sort(fileNames);
				}

				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				for (int i = 0; i < fileNames.size(); i++) {
					try {
						model.addRow(new Object[] {
								webTable.getRowCount() == 0 ? 1 : webTable
										.getRowCount() + 1, fileNames.get(i),
										i18n.getParam("Idle") });
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		wbtnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileNames.clear();
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				wbtnSend.setEnabled(false);
				wbtnClear.setEnabled(false);
			}
		});

		wbtnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> listOfFiles = new ArrayList<>();
				int[] list = webTable.getSelectedRows();
				for (int i = 0; i < list.length; i++) {
					listOfFiles.add(fileNames.get(list[i]));
				}
				for (int i = 0; i < listOfFiles.size(); i++) {
					File file = new File(listOfFiles.get(i));
					if (file.exists()) {
						currentIndex = list[i];
						SendFile(file);
					} else {
						// Set Status in table to Not Exists
					}
				}
			}
		});

		fileTransferEvent.addEventListener(new FileTransferEventListener() {

			@Override
			public void success(FileTransferObject evt, Class t) {
				if (t != FileSendTab.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					System.out.println(currentIndex);
					model.setValueAt(i18n.getParam("Success"), currentIndex, 2);
				} catch (Exception e) {
				}
			}

			@Override
			public void progress(FileTransferObject evt, Class t) {
				// if(t!=FileSendTab.class)
				// return;
				// DefaultTableModel model =
				// (DefaultTableModel)webTable.getModel();
				// model.setValueAt(String.format("%." + 2 + "f\n",
				// evt.getProgressValue())+" %", currentIndex, 2);
			}

			@Override
			public void fail(FileTransferObject evt, Class t) {
				if (t != FileSendTab.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.setValueAt(i18n.getParam("Failed"), currentIndex, 2);
				} catch (Exception e) {

				}
			}

			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				//

			}

			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
				//

			}

			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				//

			}
		}, FileSendTab.class);
	}

	@SuppressWarnings("deprecation")
	protected void SendFile(final File file) {
		try {
			final FileSendProgressWindow progwin = new FileSendProgressWindow();
			Thread fileSender = new Thread(new Runnable() {

				@Override
				public void run() {
					ArrayList<String> ips = null;
					if (chckbxSendToAll.isSelected()) {
						ips = InstructorNoa.getAllStudentIPs();
					} else {
						Component card = null;
						for (Component comp : InstructorNoa.getCenterPanel()
								.getComponents()) {
							if (comp.isVisible() == true) {
								card = comp;
							}
						}

						if (((JComponent) card).getClientProperty("viewMode")
								.equals("groupView")) {
							if (!InstructorNoa.getGroupList()
									.isSelectionEmpty()) {
								int groupIndex = InstructorNoa.getGroupList()
										.getSelectedIndex();

								Group group = InstructorNoa.getGroups().get(
										groupIndex);
								if (group.getStudentIps().isEmpty()) {
									return;
								} else {
									try {
										ips = new ArrayList<>(group
												.getStudentIps());
									} catch (Exception e) {
										JajeemExcetionHandler.logError(e);
									}
								}
							}
						} else if (((JComponent) card).getClientProperty(
								"viewMode").equals("thumbView")) {
							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								ips = new ArrayList<>();
								ips.add(((String) InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip")));
							} else {
								try {
									JOptionPane.showMessageDialog(null,
											i18n.getParam("No student is selected!"));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								ips = null;
							}
						}
					}
					if (ips == null) {
						try {
							JOptionPane.showMessageDialog(null,
									i18n.getParam("No student is selected!"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
					System.out.println("Ips Count : " + ips.size());
					try {
						for (int i = 0; i < ips.size(); i++) { // send for all
																// selected
																// clients
							System.out.println("Ip : " + ips.get(i));
							Socket clientSocket = new Socket(ips.get(i), 12345);
							OutputStream out = clientSocket.getOutputStream();
							FileInputStream fis = new FileInputStream(file);
							byte[] info = new byte[2048];
							byte[] temp = file.getPath().getBytes();
							int len = file.getPath().length();
							for (int k = 0; k < len; k++) {
								info[k] = temp[k];
							}
							for (int k = len; k < 2048; k++) {
								info[k] = 0x00;
							}
							out.write(info, 0, 2048);

							len = file.getName().length();
							temp = file.getName().getBytes();
							for (int k = 0; k < len; k++) {
								info[k] = temp[k];
							}
							for (int k = len; k < 2048; k++) {
								info[k] = 0x00;
							}
							out.write(info, 0, 2048);

							FileInputStream inp = new FileInputStream(file);
							long fileLength = inp.available();
							len = String.valueOf(inp.available()).length();
							temp = String.valueOf(inp.available()).getBytes();
							for (int k = 0; k < len; k++) {
								info[k] = temp[k];
							}
							for (int k = len; k < 2048; k++) {
								info[k] = 0x00;
							}
							out.write(info, 0, 2048);
							inp.close();

							int x;
							byte[] b = new byte[4194304];
							long bytesRead = 0;
							while ((x = fis.read(b)) > 0) {
								out.write(b, 0, x);
								bytesRead += x;
								FileTransferObject evt = new FileTransferObject(
										this);
								evt.setProgressValue(((double) bytesRead / (double) fileLength) * 100.0);
								new FileTransferEvent().fireProgress(evt,
										FileSendProgressWindow.class);
							}
							out.flush();
							out.close();
							fis.close();
							progwin.reset();
						}
						progwin.dispose();
						// confirmationDialog.dispose();
						if (ips.size() != 0) {
							new FileTransferEvent().fireSuccess(null,
									FileSendTab.class);
						} else {
							new FileTransferEvent().fireFailure(null,
									FileSendTab.class);
						}
					} catch (Exception e) {
						progwin.dispose();
						// confirmationDialog.dispose();
						System.out.println(e.getMessage());
						JajeemExcetionHandler.logError(e, FileSendTab.class);
						new FileTransferEvent().fireFailure(null,
								FileSendTab.class);

					}
				}
			});
			fileSender.start();
			progwin.setVisible(true);
			// confirmationDialog.setVisible(true);
			// System.out.println(dialog.getValue().toString());
			// int command = dialog.getValue() instanceof String &&
			// dialog.getValue().toString().equals("Cancel") ? 0 : -1;
			// if(command==0 && !fileSender.isInterrupted())
			// fileSender.interrupt();
			// System.out.println(fileSender.isAlive()+":"+command);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JajeemExcetionHandler.logError(e);
			new FileTransferEvent().fireFailure(null, FileSendTab.class);
			e.printStackTrace();
		}
	}

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected Collection<? extends String> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i).getPath());
		}
		return list;
	}
}
