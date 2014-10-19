package com.jajeem.filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.filemanager.design.FileCollect;
import com.jajeem.filemanager.design.FileInbox;
import com.jajeem.util.FileUtil;
import com.jajeem.util.Session;
import com.jajeem.util.Threading.ThreadManager;
import com.jajeem.util.Threading.ThreadPoolConstants;

public class InstructorServer {

	static long requestNumber = 0;
	FileTransferEvent fileEvents = new FileTransferEvent();

	public void Startup() {
		fileEvents.addEventListener(new FileTransferEventListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void success(FileTransferObject evt, Class t) {
				//

			}

			@SuppressWarnings("rawtypes")
			@Override
			public void progress(FileTransferObject evt, Class t) {
				//
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void fileSendRequest(FileTransferObject evt, Class t) {
				//

			}

			@SuppressWarnings("rawtypes")
			@Override
			public void fileAcceptRequest(final FileTransferObject evt, Class t) {
				if (t != InstructorServer.class) {
					return;
				}
				final Socket client = evt.getClientSocket();
				if (client.isConnected() && client.isClosed()) {
					new FileTransferEvent().fireFailure(evt, FileInbox.class);
					return;
				}
				// JOptionPane dialog = new
				// JOptionPane("File transfer in progress,please wait ...",
				// JOptionPane.WARNING_MESSAGE, JOptionPane.CANCEL_OPTION,null ,
				// new Object[]{"Cancel"}, null);
				// final JDialog confirmationDialog =
				// dialog.createDialog("File Transfer");
				final InstructorProgressWindow progwin = new InstructorProgressWindow();
				ThreadManager.getInstance(ThreadPoolConstants.FILEPOOL).run(new Runnable() {
					@Override
					public void run() {
						try {

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

							String inboxPath = FileUtil.getInboxPath();
							File inbox = new File(inboxPath);
							inbox.mkdirs();
							File file;
							String StudentName = InstructorNoa
									.getStudentNameByIP(client.getInetAddress()
											.getHostAddress());
							if (StudentName != null && StudentName != "") {
								file = new File(inbox, StudentName);
							} else {
								file = new File(inbox, client.getInetAddress()
										.getHostAddress());
							}
							file.mkdir();
							
							Date now = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm");
							String datetime = format.format(now);
							
							String filname = "(" + datetime + ")" + nameStr;
							File output = new File(file, filname);
							FileOutputStream fos = new FileOutputStream(output);

							int x = 0;
							byte[] b = new byte[4194304];
							long bytesRead = 0;
							while ((x = in.read(b)) > 0) {
								fos.write(b, 0, x);
								bytesRead += x;
								evt.setProgressValue(((double) bytesRead / (double) fileLength) * 100.0);
								new FileTransferEvent().fireProgress(evt,
										InstructorProgressWindow.class);
							}
							fos.flush();
							fos.close();
							in.close();
							client.close();

							evt.setFileName(output.getAbsolutePath());
							if (evt.getClientSocket().getLocalPort() == 54321) {
								new FileTransferEvent().fireSuccess(evt,
										FileInbox.class);
							}
							if (evt.getClientSocket().getLocalPort() == 54322) {
								new FileTransferEvent().fireSuccess(evt,
										FileCollect.class);
							}
							// confirmationDialog.dispose();
							progwin.dispose();
						} catch (Exception e) {
							progwin.dispose();
							// confirmationDialog.dispose();
							JajeemExceptionHandler.logError(e,
									InstructorServer.class);
							new FileTransferEvent().fireFailure(null,
									FileInbox.class);
						}
					}
				});
				// confirmationDialog.setVisible(true);
				progwin.setVisible(true);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void fail(FileTransferObject evt, Class t) {

			}

			@SuppressWarnings("rawtypes")
			@Override
			public void fileRejectRequest(FileTransferObject evt, Class t) {
				if (t != InstructorServer.class) {
					return;
				}
				try {
					evt.getClientSocket().close();
				} catch (IOException e) {
					JajeemExceptionHandler.logError(e, InstructorServer.class);
					e.printStackTrace();
				}
			}
		}, InstructorServer.class);

		new Thread(new Runnable() {

			@Override
			public void run() {
				StartFileListenerServer();
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				StartFileCollectionListenerServer();
			}
		}).start();
	}

	@SuppressWarnings("resource")
	private void StartFileListenerServer() {
		try {
			ServerSocket internalsocket = new ServerSocket(54321);
			while (true) {
				final Socket client = internalsocket.accept();
				FileTransferObject obj = new FileTransferObject(this);
				obj.setClientSocket(client);
				obj.setRequestNumber(requestNumber++);
				new FileTransferEvent().fireFileSendRequest(obj,
						FileInbox.class);
				JOptionPane
						.showMessageDialog(
								null,
								client.getInetAddress().getHostAddress()
										+ " has sent you a file,you can check it in your File Manager Inbox!");
				Session.getFileRequestList().add(obj);
			}
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e, InstructorServer.class);
			JOptionPane
					.showMessageDialog(
							null,
							"An error has occured in file server.File services might not work correctly,please restart the application!");
		}
	}

	@SuppressWarnings("resource")
	private void StartFileCollectionListenerServer() {
		try {
			ServerSocket internalsocket = new ServerSocket(54322);
			while (true) {
				final Socket client = internalsocket.accept();
				FileTransferObject obj = new FileTransferObject(this);
				obj.setClientSocket(client);
				obj.setRequestNumber(requestNumber++);
				new FileTransferEvent().fireFileSendRequest(obj,
						FileCollect.class);
			}
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e, InstructorServer.class);
			JOptionPane
					.showMessageDialog(
							null,
							"An error has occured in file server.File services might not work correctly,please restart the application!");
		}
	}

	public static void main(String[] list) {
		new InstructorServer().Startup();
	}
}
