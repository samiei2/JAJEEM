package com.jajeem.filemanager.design;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.model.Group;
import com.jajeem.util.Config;

public class FileAssignmentTab extends WebPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable webTable;
	private WebButton wbtnBrowse;
	private WebButton wbtnSend;
	private WebButton wbtnClear;
	private FileAssignmentTab currentPanel;
	private ArrayList<String> fileNames = new ArrayList<>();
	private FileTransferEvent fileTransferEvent = new FileTransferEvent();
	private FileManagerMain parentFrame;

	/**
	 * Create the panel.
	 * 
	 * @param fileManagerMain
	 */
	public FileAssignmentTab(FileManagerMain fileManagerMain) {
		parentFrame = fileManagerMain;
		currentPanel = this;
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		wbtnBrowse = new WebButton();
		wbtnBrowse.setText("Browse");

		wbtnClear = new WebButton();
		wbtnClear.setEnabled(false);
		wbtnClear.setText("Clear");

		wbtnSend = new WebButton();
		wbtnSend.setEnabled(false);
		wbtnSend.setText("Send Assignment");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webScrollPane,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																709,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				wbtnClear,
																				GroupLayout.PREFERRED_SIZE,
																				89,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				wbtnSend,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				425,
																				Short.MAX_VALUE)
																		.addComponent(
																				wbtnBrowse,
																				GroupLayout.PREFERRED_SIZE,
																				89,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(webScrollPane,
												GroupLayout.DEFAULT_SIZE, 223,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wbtnClear,
																GroupLayout.PREFERRED_SIZE,
																24,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wbtnSend,
																GroupLayout.PREFERRED_SIZE,
																24,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wbtnBrowse,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		webTable = new WebTable();
		webTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "File Name", "Time" }) {
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
		webTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		webTable.getColumnModel().getColumn(2).setMaxWidth(120);
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);
		initEvents();
	}

	private void initEvents() {
		wbtnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = chooser.showOpenDialog(currentPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
					fileName = chooser.getSelectedFile().getAbsolutePath();
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					String time = "";
					while (true) {
						time = JOptionPane.showInputDialog(
								"Please Set Time : ", 0);
						try {
							Integer.parseInt(time);
							break;
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null,
									"Invalid time.Please enter a correct one!");
						}
					}
					model.addRow(new Object[] {
							webTable.getRowCount() == 0 ? 1 : webTable
									.getRowCount() + 1, fileName, time + " Min" });
					fileNames.add(fileName);
					wbtnSend.setEnabled(true);
					wbtnClear.setEnabled(true);
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
				JOptionPane
						.showMessageDialog(null,
								"Note: Running assignments will keep running until they end!");
			}
		});

		wbtnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = fileNames.get(webTable.getSelectedRow());
				File file = new File(fileName);
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					final String time = model
							.getValueAt(webTable.getSelectedRow(), 2)
							.toString().split(" ")[0];
					Integer.parseInt(time);
					if (file.exists()) {
						if (!file.isDirectory()) {
							SendFile(file);
						}
					}
				} catch (Exception ex) {

				}
			}
		});

		fileTransferEvent.addEventListener(new FileTransferEventListener() {

			@Override
			public void success(FileTransferObject evt, Class t) {
				if (t != FileAssignmentTab.class) {
					return;
				}
				try {
					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					String file = model
							.getValueAt(webTable.getSelectedRow(), 1)
							.toString();

					final String time = model
							.getValueAt(webTable.getSelectedRow(), 2)
							.toString().split(" ")[0];
					SendFileAssignmentCMD(new File(file).getName(), time);
					final int currentrow = webTable.getSelectedRow();
					new Thread(new Runnable() {
						private Timer timer; // Updates the count every second
						private long remaining; // How many milliseconds remain
												// in the countdown.
						private long lastUpdate; // When count was last updated
						String timetemp = time;
						DefaultTableModel model = (DefaultTableModel) webTable
								.getModel();

						@Override
						public void run() {
							ActionListener taskPerformer = new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent evt) {
									updateDisplay();
								}

								private void updateDisplay() {
									NumberFormat format = NumberFormat
											.getInstance();

									long now = System.currentTimeMillis(); // current
																			// time
																			// in
																			// ms
									long elapsed = now - lastUpdate; // ms
																		// elapsed
																		// since
																		// last
																		// update
									remaining -= elapsed; // adjust remaining
															// time
									lastUpdate = now; // remember this update
														// time
									// Convert remaining milliseconds to mm:ss
									// format and
									// display
									if (remaining < 0) {
										remaining = 0;
									}
									int minutes = (int) (remaining / 60000);
									int seconds = (int) ((remaining % 60000) / 1000);
									model.setValueAt(format.format(minutes)
											+ ":" + format.format(seconds),
											currentrow, 2);

									// If we've completed the countdown beep and
									// display new
									// page
									if (remaining == 0) {
										// Stop updating now.
										parentFrame.invokeFileCollect();
										timer.stop();
										model.setValueAt(timetemp + " Min",
												currentrow, 2);
									}
								}
							};

							if (!time.equals("0")) {
								model.setValueAt(time + ":00", currentrow, 2);
								remaining = Integer.parseInt(time) * 60000;
								timer = new Timer(1000, taskPerformer);
								timer.setInitialDelay(0);
								lastUpdate = System.currentTimeMillis();
								timer.start();
							}
						}
					}).start();
				} catch (Exception e) {

				}
			}

			@Override
			public void progress(FileTransferObject evt, Class t) {
				// if(t!=FileAssignmentTab.class)
				// return;
				// DefaultTableModel model =
				// (DefaultTableModel)webTable.getModel();
				// model.setValueAt(String.format("%." + 2 + "f\n",
				// evt.getProgressValue())+" %", currentIndex, 2);
			}

			@Override
			public void fail(FileTransferObject evt, Class t) {
				if (t != FileAssignmentTab.class) {
					return;
				}
				// DefaultTableModel model =
				// (DefaultTableModel)webTable.getModel();
				// model.setValueAt("Failed", currentIndex, 2);
				JOptionPane.showMessageDialog(null,
						"Sending assignment failed!");
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
		}, FileAssignmentTab.class);
	}

	protected void SendFileAssignmentCMD(String file, String time) {
		try {
			new Config();
			ServerService service;
			if (InstructorNoa.getServerService() == null) {
				service = new ServerService();
			} else {
				service = InstructorNoa.getServerService();
			}
			SendFileAssignmentCommand cmd;
			ArrayList<String> ips = InstructorNoa.getAllStudentIPs();
			for (int i = 0; i < ips.size(); i++) {
				cmd = new SendFileAssignmentCommand(InetAddress.getLocalHost()
						.getHostAddress(), ips.get(i), Integer.parseInt(Config
						.getParam("port")));
				cmd.setTime(time);
				cmd.setFile(file);
				service.send(cmd);
			}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("deprecation")
	protected void SendFile(final File file) {
		try {
			// JOptionPane dialog = new
			// JOptionPane("File transfer in progress,please wait ...",
			// JOptionPane.WARNING_MESSAGE, JOptionPane.CANCEL_OPTION,null , new
			// Object[]{"Cancel"}, null);
			// final JDialog confirmationDialog = dialog.createDialog(this,
			// "File Transfer");
			final FileAssignmentProgressWindow progwin = new FileAssignmentProgressWindow();
			Thread fileSender = new Thread(new Runnable() {

				@Override
				public void run() {
					ArrayList<String> ips = null;
					Component card = null;
					for (Component comp : InstructorNoa.getCenterPanel()
							.getComponents()) {
						if (comp.isVisible() == true) {
							card = comp;
						}
					}

					if (((JComponent) card).getClientProperty("viewMode")
							.equals("groupView")) {
						if (!InstructorNoa.getGroupList().isSelectionEmpty()) {
							int groupIndex = InstructorNoa.getGroupList()
									.getSelectedIndex();

							Group group = InstructorNoa.getGroups().get(
									groupIndex);
							if (group.getStudentIps().isEmpty()) {
								return;
							} else {
								try {
									ips = new ArrayList<>(group.getStudentIps());
								} catch (Exception e) {
									JajeemExcetionHandler.logError(e);
								}
							}
						}
					} else if (((JComponent) card)
							.getClientProperty("viewMode").equals("thumbView")) {
						if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
							ips = new ArrayList<>();
							ips.add(((String) InstructorNoa.getDesktopPane()
									.getSelectedFrame().getClientProperty("ip")));
						} else {
							JOptionPane.showMessageDialog(null,
									"No student is selected!");
							ips = null;
						}
					}

					if (ips == null) {
						JOptionPane.showMessageDialog(null,
								"No student is selected!");
						return;
					}
					try {

						for (int i = 0; i < ips.size(); i++) { // send for all
																// selected
																// clients
							Socket clientSocket = new Socket(ips.get(i), 12345);
							// Socket clientSocket=new
							// Socket("127.0.0.1",12345);
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
										FileAssignmentProgressWindow.class);
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
									FileAssignmentTab.class);
						} else {
							new FileTransferEvent().fireFailure(null,
									FileAssignmentTab.class);
						}
					} catch (Exception e) {
						progwin.dispose();
						// confirmationDialog.dispose();
						JajeemExcetionHandler.logError(e,
								FileAssignmentTab.class);
						new FileTransferEvent().fireFailure(null,
								FileAssignmentTab.class);
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
			JajeemExcetionHandler.logError(e);
			new FileTransferEvent().fireFailure(null, FileAssignmentTab.class);
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

	public void setParent(FileManagerMain fileManagerMain) {
		this.parentFrame = fileManagerMain;
	}
}
