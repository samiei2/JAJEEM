package com.jajeem.filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.jajeem.events.FileTransferEvent;
import com.jajeem.events.FileTransferEventListener;
import com.jajeem.events.FileTransferObject;
import com.jajeem.exception.JajeemExcetionHandler;

public class InstructorServer {
	static long requestNumber = 0;
	FileTransferEvent fileEvents = new FileTransferEvent();
	public void Startup(){
		fileEvents.addEventListener(new FileTransferEventListener() {
			
			@Override
			public void success(FileTransferObject evt) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void progress(FileTransferObject evt) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fileSendRequest(FileTransferObject evt) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fileAcceptRequest(final FileTransferObject evt) {
				final Socket client = evt.getClientSocket();
				if(client.isConnected() && client.isClosed()){
					new FileTransferEvent().fireFailure(evt);
					return;
				}
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
						    File inbox = new File("inbox");
						    inbox.mkdir();
						    File file = new File(inbox,client.getInetAddress().getHostAddress());
						    file.mkdir();
						    File output = new File(file,nameStr);
						    FileOutputStream fos = new FileOutputStream(output);
						    
						    int x=0;
						    byte[] b = new byte[4194304];
						    while((x = in.read(b)) > 0)
						    {
						        fos.write(b, 0, x);
						    }
						    fos.close();
						    //TODO add filetransferobject object
						    evt.setFileName(output.getAbsolutePath());
						    new FileTransferEvent().fireSuccess(null);
						}
						catch(Exception e){
							JajeemExcetionHandler.logError(e,InstructorServer.class);
							new FileTransferEvent().fireFailure(null);
						}
					}
				}).start();
			}
			
			@Override
			public void fail(FileTransferObject evt) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void fileRejectRequest(FileTransferObject evt) {
				try {
					evt.getClientSocket().close();
				} catch (IOException e) {
					JajeemExcetionHandler.logError(e,InstructorServer.class);
					e.printStackTrace();
				}
			}
		});
		StartFileListenerServer();
	}

	private void StartFileListenerServer() {
		try {
			ServerSocket ss=new ServerSocket(54321);
			while(true){
				final Socket client = ss.accept();
				FileTransferObject obj = new FileTransferObject(this);
				obj.setClientSocket(client);
				obj.setRequestNumber(requestNumber++);
				new FileTransferEvent().fireFileSendRequest(obj);
			}
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e,InstructorServer.class);
		}
	}
	
	public static void main(String[] list){
		new InstructorServer().StartFileListenerServer();
	}
}
