package com.jajeem.filemanager.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.Packet;
import com.jajeem.util.StartUp;

public class ClientServer {
	public void Startup(){
		StartServer();
	}

	private void StartServer() {
		try {
			ServerSocket ss=new ServerSocket(12345);
			while(true){
				final Socket client = ss.accept();
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
						    String nameStr = new String(name);
						    File inbox = new File("Inbox");
						    File output = new File(inbox, nameStr);
						    FileOutputStream fos = new FileOutputStream(output);
						    
						    int x=0;
						    byte[] b = new byte[4194304];
						    while((x = in.read(b)) > 0)
						    {
						        fos.write(b, 0, x);
						    }
						    fos.close();
						    //TODO add filetransferobject object 
						    FileTransferObject obj = new FileTransferObject(this);
						    obj.setFileName(output.getAbsolutePath());
						    new FileTransferEvent().fireSuccess(obj);
						}
						catch(Exception e){
							JajeemExcetionHandler.logError(e,ClientServer.class);
							new FileTransferEvent().fireFailure(null);
						}
					}
				}).start();
			}
	        
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e,ClientServer.class);
		}
	}
	
	public static void main(String[] args){
		new ClientServer().Startup();
	}
}
