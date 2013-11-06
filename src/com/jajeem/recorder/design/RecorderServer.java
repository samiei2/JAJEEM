package com.jajeem.recorder.design;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.StyleConstants;
import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.InstructorProgressWindow;
import com.jajeem.filemanager.client.ClientProgressWindow;
import com.jajeem.filemanager.design.FileCollect;
import com.jajeem.filemanager.design.FileInbox;
import com.jajeem.util.FileUtil;
import com.jajeem.util.Session;
import com.jajeem.util.i18n;

public class RecorderServer {
	
	static long requestNumber = 0;
	FileTransferEvent fileEvents = new FileTransferEvent();
	public void Startup(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				StartFileListenerServer();
			}
		}).start(); 
	
	}

	private void StartFileListenerServer() {
		try {
			ServerSocket internalsocket=new ServerSocket(54333);
			while(true){
				final Socket client = internalsocket.accept();
				if(client.isConnected() && client.isClosed()){
					return;
				}
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						final ClientProgressWindow progwin = new ClientProgressWindow();
						try{
							File outputFile;
							WebDirectoryChooser directoryChooser = new WebDirectoryChooser(null,
									"Choose directory to save students recording");

							directoryChooser.setVisible(true);

							if (directoryChooser.getResult() == StyleConstants.OK_OPTION) {
								File filetmp = directoryChooser.getSelectedFolder();
								outputFile = new File(filetmp.getPath());
								if (!outputFile.exists())
									outputFile.mkdir();
							} else {
								int resp = 1;

								try {
									resp = WebOptionPane
											.showConfirmDialog(
													null,
													i18n.getParam("Do you want to save the recording is the default folder (\\Recordings) or discard recording?"),
													i18n.getParam("Confirm"),
													WebOptionPane.YES_NO_OPTION,
													WebOptionPane.QUESTION_MESSAGE);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								if (resp == 0) {
									outputFile = new File(FileUtil.getRecorderPath());
								} else {
									return;
								}
							}
							progwin.setVisible(true);
							FileTransferObject evt = new FileTransferObject(
									this);
							InputStream in = client.getInputStream();
						    
						    byte[] path = new byte[2048];
						    in.read(path, 0, 2048);
						    @SuppressWarnings("unused")
							String pathStr = new String(path);
						    byte[] name = new byte[2048];
						    in.read(name, 0, 2048);
						    String nameStr = new String(name);
						    byte[] filelen = new byte[2048];
						    in.read(filelen, 0, 2048);
						    String temp = new String(filelen).trim();
						    int fileLength = Integer.parseInt(temp);
						    
							String inboxPath = outputFile.getPath();
						    File inbox = new File(inboxPath);
						    inbox.mkdirs();
						    File file = new File(inbox,client.getInetAddress().getHostAddress());
						    file.mkdir();
						    File output = new File(file,nameStr);
						    FileOutputStream fos = new FileOutputStream(output);
						    
						    int x=0;
						    byte[] b = new byte[4194304];
						    long bytesRead = 0;
						    while((x = in.read(b)) > 0)
						    {
						        fos.write(b, 0, x);
						        bytesRead += x;
						        evt.setProgressValue(((double) bytesRead / (double) fileLength) * 100.0);
								new FileTransferEvent().fireProgress(evt,
										RecorderProgressWindow.class);
						    }
						    fos.flush();
						    fos.close();
						    in.close();
						    client.close();
						    progwin.dispose();
						}
						catch(Exception e){
							progwin.dispose();
//							confirmationDialog.dispose();
							JajeemExcetionHandler.logError(e,RecorderServer.class);
							new FileTransferEvent().fireFailure(null,FileInbox.class);
						}
					}
				}).start();
			}
		} catch (Exception e) {
			
			JajeemExcetionHandler.logError(e,RecorderServer.class);
			WebOptionPane.showMessageDialog(null, "An error has occured in recorder server.Recording services might not work correctly,please restart the application!");
		}
	}
	
	public static void main(String[] list){
		new RecorderServer().Startup();
	}
}
