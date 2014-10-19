package com.jajeem.filemanager.client;

import java.awt.Component;
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
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.design.account.CustomAccountButton;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.filemanager.design.CustomFileButton;
import com.jajeem.util.Threading.ThreadManager;
import com.jajeem.util.Threading.ThreadPoolConstants;

public class ClientFileSendTab extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable webTable;
	private WebButton wbtnBrowse;
	private WebButton wbtnSend;
	private WebButton wbtnClear;
	private ClientFileSendTab currentPanel;
	private ArrayList<String> fileNames = new ArrayList<>();
	private FileTransferEvent fileTransferEvent = new FileTransferEvent();
	private int currentIndex;

	/**
	 * Create the panel.
	 */
	public ClientFileSendTab() {
		currentPanel = this;
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		wbtnBrowse = new CustomFileButton("/icons/noa_en/filebrowsebutton.png");
		wbtnBrowse.setUndecorated(true);
//		wbtnBrowse.setText("Browse");

		wbtnClear = new CustomFileButton("/icons/noa_en/fileclearbutton.png");
		wbtnClear.setUndecorated(true);
		wbtnClear.setEnabled(false);
//		wbtnClear.setText("Clear");

		wbtnSend = new CustomFileButton("/icons/noa_en/fileacceptbutton.png");
		wbtnSend.setUndecorated(true);
		wbtnSend.setEnabled(false);
//		wbtnSend.setText("Send");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
							.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "File Name", "Status" }) {
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
				JFileChooser chooser = new JFileChooser();
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
					model.addRow(new Object[] {
							webTable.getRowCount() == 0 ? 1 : webTable
									.getRowCount() + 1, fileNames.get(i),
							"Idle" });
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
				if (t != ClientFileSendTab.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.setValueAt("Success", currentIndex, 2);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void progress(FileTransferObject evt, Class t) {
				// if(t!=ClientFileSendTab.class)
				// return;
				// DefaultTableModel model =
				// (DefaultTableModel)webTable.getModel();
				// model.setValueAt(String.format("%." + 2 + "f\n",
				// evt.getProgressValue())+" %", currentIndex, 2);
			}

			@Override
			public void fail(FileTransferObject evt, Class t) {
				if (t != ClientFileSendTab.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					model.setValueAt("Failed", currentIndex, 2);
				} catch (Exception e) {
				}
			}

			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {

			}

			@Override
			public void fileAcceptRequest(FileTransferObject evt, Class t) {
				//

			}

			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				//

			}
		}, ClientFileSendTab.class);
	}

	protected void SendFile(final File file) {
		try {
			// JOptionPane dialog = new
			// JOptionPane("File transfer in progress,please wait ...",
			// JOptionPane.WARNING_MESSAGE, JOptionPane.CANCEL_OPTION,null , new
			// Object[]{"Cancel"}, null);
			// final JDialog confirmationDialog = dialog.createDialog(this,
			// "File Transfer");
			final ClientSendFileProgressWindow progwin = new ClientSendFileProgressWindow();
			ThreadManager.getInstance(ThreadPoolConstants.FILEPOOL).run(new Runnable() {

				@Override
				public void run() {
					// ArrayList<String> ips =
					// InstructorNoa.getSelectedStudentIPs();

					try {
						// for (int i = 0; i < ips.size(); i++) { // send for
						// all selected clients
						// System.out.println("Sending file to : "+ips.get(0));
						Socket clientSocket = new Socket(StudentLogin
								.getServerIp(), 54321);
						// Socket clientSocket=new Socket("127.0.0.1",54321);
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
									ClientSendFileProgressWindow.class);
						}
						out.flush();
						out.close();
						fis.close();
						// }
						progwin.dispose();
						// confirmationDialog.dispose();
						new FileTransferEvent().fireSuccess(null,
								ClientFileSendTab.class);

					} catch (Exception e) {
						progwin.dispose();
						// confirmationDialog.dispose();
						JajeemExceptionHandler.logError(e,
								ClientFileSendTab.class);
						new FileTransferEvent().fireFailure(null,
								ClientFileSendTab.class);
					}
				}
			});
			// confirmationDialog.setVisible(true);
			progwin.setVisible(true);
			// System.out.println(dialog.getValue().toString());
			// int command = dialog.getValue() instanceof String &&
			// dialog.getValue().toString().equals("Cancel") ? 0 : -1;
			// if(command==0 && !fileSender.isInterrupted())
			// fileSender.interrupt();
			// System.out.println(fileSender.isAlive()+":"+command);
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
			new FileTransferEvent().fireFailure(null, ClientFileSendTab.class);
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
