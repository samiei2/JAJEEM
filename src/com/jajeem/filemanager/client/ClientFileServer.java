package com.jajeem.filemanager.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.Packet;
import com.jajeem.util.StartUp;

public class ClientFileServer {
	public void Startup(){
		File file = new File("Outbox");
		if(!file.exists())
			file.mkdir();
		file = new File("Inbox");
		if(!file.exists())
			file.mkdir();
		StartServer();
	}

	private void StartServer() {
		try {
			ServerSocket ss = new ServerSocket(12345);
			while(true){
				final Socket client = ss.accept();
				final ClientProgressWindow progwin = new ClientProgressWindow();
		        new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							InputStream in = client.getInputStream();
						    
						    byte[] path = new byte[2048];
						    in.read(path, 0, 2048);
						    String pathStr = new String(path);
						    byte[] name = new byte[2048];
						    in.read(name, 0, 2048);
						    byte[] filelen = new byte[2048];
						    in.read(filelen, 0, 2048);
						    String temp = new String(filelen).trim();
						    int fileLength = Integer.parseInt(temp.trim());
						    String nameStr = new String(name);
						    File inbox = new File("Inbox");
						    if(!inbox.exists())
						    	inbox.mkdir();
						    File output = new File(inbox, nameStr);
						    FileOutputStream fos = new FileOutputStream(output);
						    
						    int x=0;
						    byte[] b = new byte[4194304];
						    long bytesRead = 0;
						    while((x = in.read(b)) > 0)
						    {
						        fos.write(b, 0, x);
						        bytesRead += x;
						        FileTransferObject evt = new FileTransferObject(this);
						        evt.setProgressValue(((double)bytesRead/(double)fileLength)*100.0);
						        new FileTransferEvent().fireProgress(evt, ClientProgressWindow.class);
						    }
						    fos.flush();
						    fos.close();
						    in.close();
						    client.close();
						    //TODO add filetransferobject object 
						    progwin.dispose();
						    FileTransferObject obj = new FileTransferObject(this);
						    obj.setFileName(output.getAbsolutePath());
						    new FileTransferEvent().fireSuccess(obj, ClientFileInbox.class);
						    JOptionPane.showMessageDialog(null, "Teacher has sent you a file.you can find it in your inbox!");
						}
						catch(Exception e){
							progwin.dispose();
							JajeemExcetionHandler.logError(e,ClientFileServer.class);
							new FileTransferEvent().fireFailure(null, ClientFileInbox.class);
						}
					}
				}).start();
		        progwin.setVisible(true);
			}
	        
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e,ClientFileServer.class);
		}
	}
	
	public static void main(String[] args){
		new ClientFileServer().Startup();
	}
}
