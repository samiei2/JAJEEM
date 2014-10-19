package com.jajeem.filemanager.client;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.FileUtil;
import com.jajeem.util.Threading.ThreadManager;
import com.jajeem.util.Threading.ThreadPoolConstants;

public class ClientFileServer {
	public void Startup() {

		String inboxPath = FileUtil.getInboxPath();
		String outboxPath = FileUtil.getOutboxPath();
		File file = new File(outboxPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(inboxPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		StartServer();
	}

	private void StartServer() {
		try {
			ServerSocket ss = new ServerSocket(12345);
			while (true) {
				final Socket client = ss.accept();
				final ClientProgressWindow progwin = new ClientProgressWindow();
				ThreadManager.getInstance(ThreadPoolConstants.FILEPOOL).runSingle(new Runnable() {
					@Override
					public void run() {
						try {
							InputStream in = client.getInputStream();
							FileTransferObject evt = new FileTransferObject(
									this);
							byte[] path = new byte[2048];
							in.read(path, 0, 2048);
							new String(path);
							byte[] name = new byte[2048];
							in.read(name, 0, 2048);
							byte[] filelen = new byte[2048];
							in.read(filelen, 0, 2048);
							String temp = new String(filelen).trim();
							int fileLength = Integer.parseInt(temp.trim());

							String inboxPath = FileUtil.getInboxPath();
							String nameStr = new String(name);
							File inbox = new File(inboxPath);
							if (!inbox.exists()) {
								inbox.mkdirs();
							}
							
							Date now = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm");
							String datetime = format.format(now);
							
							String filname = "(" + datetime + ")" + nameStr;
							File output = new File(inbox, filname);
							FileOutputStream fos = new FileOutputStream(output);

							int x = 0;
							byte[] b = new byte[4194304];
							long bytesRead = 0;
							while ((x = in.read(b)) > 0) {
								fos.write(b, 0, x);
								bytesRead += x;

								evt.setProgressValue(((double) bytesRead / (double) fileLength) * 100.0);
								new FileTransferEvent().fireProgress(evt,
										ClientProgressWindow.class);
							}
							fos.flush();
							fos.close();
							in.close();
							client.close();
							// TODO add filetransferobject object
							progwin.dispose();
							FileTransferObject obj = new FileTransferObject(
									this);
							obj.setFileName(output.getAbsolutePath());
							new FileTransferEvent().fireSuccess(obj,
									ClientFileInbox.class);

							JOptionPane
									.showConfirmDialog(null,
											"A new file has been received from the teacher!");

							File foler = new File(FileUtil.getInboxPath());
							Desktop desktop = null;
							if (Desktop.isDesktopSupported()) {
								desktop = Desktop.getDesktop();
							}
							try {
								desktop.open(foler);
							} catch (IOException e) {
							}
						} catch (Exception e) {
							progwin.dispose();
							JajeemExceptionHandler.logError(e,
									ClientFileServer.class);
							new FileTransferEvent().fireFailure(null,
									ClientFileInbox.class);
						}
					}
				});
				progwin.setVisible(true);
			}

		} catch (Exception e) {
			JajeemExceptionHandler.logError(e, ClientFileServer.class);
		}
	}

	public static void main(String[] args) {
		new ClientFileServer().Startup();
	}
}
