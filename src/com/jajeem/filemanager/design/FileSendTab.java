package com.jajeem.filemanager.design;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.ListOfArrayDataSource;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.Packet;

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
	
	/**
	 * Create the panel.
	 */
	public FileSendTab() {
		currentPanel = this;
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		wbtnBrowse = new WebButton();
		wbtnBrowse.setText("Browse");
		
		wbtnClear = new WebButton();
		wbtnClear.setEnabled(false);
		wbtnClear.setText("Clear");
		
		wbtnSend = new WebButton();
		wbtnSend.setEnabled(false);
		wbtnSend.setText("Send");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
							.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbtnClear, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnSend, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnBrowse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "File Name", "Status" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

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
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = chooser.showOpenDialog(currentPanel);
				if(result == JFileChooser.APPROVE_OPTION){
					File[] files = chooser.getSelectedFiles();
					for (int i = 0; i < files.length; i++) {
						if(files[i].isDirectory())
							fileNames.addAll(getPath(getDirectoryContent(files[i])));
						else
							fileNames.add(files[i].getAbsolutePath());
					}
					if(fileNames.size() != 0){
						wbtnSend.setEnabled(true);
						wbtnClear.setEnabled(true);
					}
					Collections.sort(fileNames);
				}
				
				
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				for (int i = 0; i < fileNames.size(); i++) {
					model.addRow(new Object[]{
							webTable.getRowCount() == 0 ? 1 : webTable.getRowCount() + 1,
							fileNames.get(i),
							"Idle"
					});
				}
			}
		});
		
		wbtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileNames.clear();
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.getDataVector().clear();
				model.fireTableDataChanged();
				webTable.repaint();
				webTable.updateUI();
				wbtnSend.setEnabled(false);
				wbtnClear.setEnabled(false);
			}
		});
		
		wbtnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> listOfFiles = new ArrayList<>();
				int[] list = webTable.getSelectedRows();
				for (int i = 0; i < list.length; i++) {
					listOfFiles.add(fileNames.get(list[i]));
				}
				for (int i = 0; i < listOfFiles.size(); i++) {
					File file = new File(listOfFiles.get(i));
					if(file.exists()){
						currentIndex = list[i];
						SendFile(file);
					}
					else{
						//Set Status in table to Not Exists
					}
				}
			}
		});
		
		fileTransferEvent.addEventListener(new FileTransferEventListener() {
			
			@Override
			public void success(FileTransferObject evt, Class t) {
				if(t!=FileSendTab.class)
					return;
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.setValueAt("Success", currentIndex, 2);
			}
			
			@Override
			public void progress(FileTransferObject evt, Class t) {
				if(t!=FileSendTab.class)
					return;
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.setValueAt(String.format("%." + 2 + "f\n", evt.getProgressValue())+" %", currentIndex, 2);
			}
			
			@Override
			public void fail(FileTransferObject evt, Class t) {
				if(t!=FileSendTab.class)
					return;
				DefaultTableModel model = (DefaultTableModel)webTable.getModel();
				model.setValueAt("Failed", currentIndex, 2);
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
		try{
			JOptionPane dialog = new JOptionPane("File transfer in progress,please wait ...", JOptionPane.WARNING_MESSAGE, JOptionPane.CANCEL_OPTION,null , new Object[]{"Cancel"}, null);
			final JDialog confirmationDialog = dialog.createDialog(this, "File Transfer");
			Thread fileSender = new Thread(new Runnable() {
				
				@Override
				public void run() {
					ArrayList<String> ips = InstructorNoa.getSelectedStudentIPs();
					System.out.println("Ips Count : "+ips.size());
					try {
						for (int i = 0; i < ips.size(); i++) { // send for all selected clients
							System.out.println("Ip : "+ips.get(i));
							Socket clientSocket=new Socket(ips.get(i),12345);
//							Socket clientSocket=new Socket("127.0.0.1",12345);
							OutputStream out=clientSocket.getOutputStream();
						    FileInputStream fis=new FileInputStream(file);
						    byte[] info = new byte[2048];
						    byte[] temp = file.getPath().getBytes();
						    int len = file.getPath().length();
						    for (int k=0; k < len; k++) info[k]=temp[k];
						    for (int k=len; k < 2048; k++) info[k]=0x00;
						    out.write(info, 0, 2048);
						    
						    len = file.getName().length();
						    temp = file.getName().getBytes();
						    for (int k=0; k < len; k++) info[k]=temp[k];
						    for (int k=len; k < 2048; k++) info[k]=0x00;
						    out.write(info, 0, 2048);
						    
						    FileInputStream inp = new FileInputStream(file);
						    long fileLength = inp.available();
						    len = String.valueOf(inp.available()).length();
						    temp = String.valueOf(inp.available()).getBytes();
						    for (int k=0; k < len; k++) info[k]=temp[k];
						    for (int k=len; k < 2048; k++) info[k]=0x00;
						    out.write(info, 0, 2048);
						    inp.close();
						    
						    int x;
						    byte[] b = new byte[4194304];
						    long bytesRead = 0;
						    while((x=fis.read(b)) > 0)
						    {
						    	out.write(b, 0, x);
						    	bytesRead += x;
						    	FileTransferObject evt = new FileTransferObject(this);
						        evt.setProgressValue(((double)bytesRead*100/(double)fileLength)*100.0);
						        new FileTransferEvent().fireProgress(evt,FileSendTab.class);
						    }
						    out.close();
						    fis.close();
						}
						new FileTransferEvent().fireSuccess(null, FileSendTab.class);
						confirmationDialog.dispose();
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e,FileSendTab.class);
						new FileTransferEvent().fireFailure(null, FileSendTab.class);
						confirmationDialog.dispose();
					}
				}
			});
			fileSender.start();
			confirmationDialog.setVisible(true);
			System.out.println(dialog.getValue().toString());
			int command = dialog.getValue() instanceof String && dialog.getValue().toString().equals("Cancel") ? 0 : -1;
			if(command==0 && !fileSender.isInterrupted())
				fileSender.interrupt();
			System.out.println(fileSender.isAlive()+":"+command);
		}
		catch(Exception e){
			JajeemExcetionHandler.logError(e);
			new FileTransferEvent().fireFailure(null, FileSendTab.class);
			e.printStackTrace();
		}
	}

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if(files.get(i).isDirectory()){
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

