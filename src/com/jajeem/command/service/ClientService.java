package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import sun.misc.IOUtils;

import com.jajeem.command.handler.ChatCommandHanlder;
import com.jajeem.command.handler.IntercomRequestCommandHanlder;
import com.jajeem.command.handler.MessageCommandHanlder;
import com.jajeem.command.handler.OpenWebsiteCommandHandler;
import com.jajeem.command.handler.SendFileAssignmentCommandHandler;
import com.jajeem.command.handler.SendFileCollectCommandHandler;
import com.jajeem.command.handler.SendQuizResponseCommandHandler;
import com.jajeem.command.handler.SendSpeechFileCommandHandler;
import com.jajeem.command.handler.SendSurveyResponseCommandHandler;
import com.jajeem.command.handler.SetAuthenticateCommandHanlder;
import com.jajeem.command.handler.SetBlackoutCommandHandler;
import com.jajeem.command.handler.SetGrantCommandHanlder;
import com.jajeem.command.handler.SetInternetCommandHandler;
import com.jajeem.command.handler.SetLockCommandHandler;
import com.jajeem.command.handler.SetPowerCommandHandler;
import com.jajeem.command.handler.SetVolumeCommandHandler;
import com.jajeem.command.handler.SetWhiteBlackAppCommandHandler;
import com.jajeem.command.handler.StartApplicationCommandHanlder;
import com.jajeem.command.handler.StartCallAllCommandHanlder;
import com.jajeem.command.handler.StartCaptureCommandHandler;
import com.jajeem.command.handler.StartIntercomCommandHandler;
import com.jajeem.command.handler.StartModelCommandHanlder;
import com.jajeem.command.handler.StartQuizCommandHandler;
import com.jajeem.command.handler.StartRecorderCommandHandler;
import com.jajeem.command.handler.StartSpeechCommandHandler;
import com.jajeem.command.handler.StartSurveyCommandHandler;
import com.jajeem.command.handler.StartUpCommandHandler;
import com.jajeem.command.handler.StartVideoCommandHandler;
import com.jajeem.command.handler.StartViewerCommandHandler;
import com.jajeem.command.handler.StartWhiteBoardCommandHandler;
import com.jajeem.command.handler.StopCaptureCommandHandler;
import com.jajeem.command.handler.StopModelCommandHanlder;
import com.jajeem.command.handler.StopQuizCommandHanlder;
import com.jajeem.command.handler.StopRecorderCommandHandler;
import com.jajeem.command.handler.StopSurveyCommandHanlder;
import com.jajeem.command.handler.StopWhiteBoardCommandHanlder;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.model.IntercomRequestCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.LockCommand;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.command.model.SendQuizResponseCommand;
import com.jajeem.command.model.SendRecordingErrorCommand;
import com.jajeem.command.model.SendRecordingSuccessCommand;
import com.jajeem.command.model.SendSpeechFileCommand;
import com.jajeem.command.model.SendSurveyResponseCommand;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartCaptureCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartSpeechCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartVideoCommand;
import com.jajeem.command.model.StartViewerCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopCaptureCommand;
import com.jajeem.command.model.StopModelCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.model.StopWhiteBoardCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.design.FileSendProgressWindow;
import com.jajeem.util.Config;

public class ClientService implements IConnectorSevice, Runnable {

	protected MulticastSocket socket;
	protected InetAddress group;
	protected int port;
	protected Thread thread;

	private boolean stopped;

	static Logger logger = Logger.getLogger("ClientService.class");

	public ClientService(String group, int port) throws NumberFormatException,
			Exception {

		stopped = false;

		this.port = port;
		this.group = InetAddress.getByName(group);

		/* setup the multicast control channel */
		socket = new MulticastSocket(port);
		System.out
				.println("Client listening on port: " + socket.getLocalPort());

		// socket.connect(this.group,port);
		socket.joinGroup(this.group);

		thread = new Thread(this);

	}

	@Override
	public void start() {
		thread.start();
	}

	@Override
	public void stop() {
		stopped = true;
	}

	@Override
	public void broadcast() {
	}

	private byte[] getDatagram() throws IOException {
		int size = socket.getReceiveBufferSize();
		byte[] buffer = new byte[size];

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		
		// only for C!
		String message = "";
		message += new String(buffer, "UTF8");
		if (message.contains("fromC!")) {
			message = message.replaceAll("fromC!", "");
			logger.info("From C, file path is: " + message);
			
			SendSpeechFile(message);
			
			return message.getBytes();
		}
		
		return packet.getData();
	}

	private void SendSpeechFile(final String message) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					String fileName= message.substring(message.lastIndexOf("\\")+1, message.length());
					SendFileToAll(new File(message),fileName);
					
//					JOptionPane.showMessageDialog(null, "Speech Recognition started for all users!");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "An error occured in sending speech file to users!");
					JajeemExcetionHandler.logError(e);
				}
			}
		});
		t.start();
	}
	
	
	protected void SendFileToAll(final File file,String fileName) {
		try{
			final ArrayList<String> ips = InstructorNoa.getAllStudentIPs();
			System.out.println("Ips Count : "+ips.size());
			try {
				for (int i = 0; i < ips.size(); i++) { // send for all selected clients
					Runnable r = new MyThread(file,ips.get(i),fileName);
					new Thread(r).start();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] packet = getDatagram();

				ByteArrayInputStream b = new ByteArrayInputStream(packet);
				DataInputStream d = new DataInputStream(b);
				process(d);

			} catch (IOException ex) {
				JajeemExcetionHandler.logError(ex);
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void process(DataInputStream d) {
		try {

			ObjectInputStream oi = new ObjectInputStream(d);

			try {
				Object o = oi.readObject();

				if (!(o instanceof Command))
					throw new ClassNotFoundException("Object is not a message");

				Command cmd = (Command) o;

				System.out.println("Receiving ----> Command: " + cmd.getClass()
						+ " from: " + cmd.getFrom());

				// logger.info("Receiving: Message type: " + cmd.getClass()
				// + ", from: " + cmd.getTo());

				if (cmd instanceof StartCaptureCommand) {
					StartCaptureCommandHandler startCaptureHandler = new StartCaptureCommandHandler();
					startCaptureHandler.run(cmd);

				} else if (cmd instanceof StopCaptureCommand) {
					StopCaptureCommandHandler stopCaptureHandler = new StopCaptureCommandHandler();
					stopCaptureHandler.run(cmd);

				} else if (cmd instanceof StartViewerCommand) {
					StartViewerCommandHandler startViewerHandler = new StartViewerCommandHandler();
					startViewerHandler.run(cmd);

				} else if (cmd instanceof StartUpCommand) {
					StartUpCommandHandler startUpHandler = new StartUpCommandHandler();
					startUpHandler.run(cmd);

				} else if (cmd instanceof StartQuizCommand) {
					StartQuizCommandHandler startQuizHandler = new StartQuizCommandHandler();
					startQuizHandler.run(cmd);
				} else if (cmd instanceof StopQuizCommand) {
					StopQuizCommandHanlder stopQuizHandler = new StopQuizCommandHanlder();
					stopQuizHandler.run(cmd);
				} else if (cmd instanceof SendQuizResponseCommand) {
					SendQuizResponseCommandHandler sendquizresponse = new SendQuizResponseCommandHandler();
					sendquizresponse.run(cmd);

				} else if (cmd instanceof StartSurveyCommand) {
					StartSurveyCommandHandler startSurveyHandler = new StartSurveyCommandHandler();
					startSurveyHandler.run(cmd);
				} else if (cmd instanceof StopSurveyCommand) {
					StopSurveyCommandHanlder stopSurveyHandler = new StopSurveyCommandHanlder();
					stopSurveyHandler.run(cmd);
				} else if (cmd instanceof SendSurveyResponseCommand) {
					SendSurveyResponseCommandHandler sendSurveyresponse = new SendSurveyResponseCommandHandler();
					sendSurveyresponse.run(cmd);

				} else if (cmd instanceof WhiteBlackAppCommand) {
					SetWhiteBlackAppCommandHandler setWhiteBlackAppCommandHandler = new SetWhiteBlackAppCommandHandler();
					setWhiteBlackAppCommandHandler.run(cmd);

				} else if (cmd instanceof BlackoutCommand) {
					SetBlackoutCommandHandler setBlackoutCommandHandler = new SetBlackoutCommandHandler();
					setBlackoutCommandHandler.run(cmd);

				} else if (cmd instanceof InternetCommand) {
					SetInternetCommandHandler setInternetCommandHandler = new SetInternetCommandHandler();
					setInternetCommandHandler.run(cmd);

				} else if (cmd instanceof VolumeCommand) {
					SetVolumeCommandHandler setVolumeCommandHandler = new SetVolumeCommandHandler();
					setVolumeCommandHandler.run(cmd);

				} else if (cmd instanceof LockCommand) {
					SetLockCommandHandler setLockCommandHandler = new SetLockCommandHandler();
					setLockCommandHandler.run(cmd);
				}

				else if (cmd instanceof PowerCommand) {
					SetPowerCommandHandler setPowerCommandHandler = new SetPowerCommandHandler();
					setPowerCommandHandler.run(cmd);
				}

				else if (cmd instanceof VolumeCommand) {
					SetVolumeCommandHandler setVolumeCommandHandler = new SetVolumeCommandHandler();
					setVolumeCommandHandler.run(cmd);

				} else if (cmd instanceof WebsiteCommand) {
					OpenWebsiteCommandHandler openWebsiteCommandHandler = new OpenWebsiteCommandHandler();
					openWebsiteCommandHandler.run(cmd);
				}

				else if (cmd instanceof StartWhiteBoardCommand) {
					StartWhiteBoardCommandHandler whiteboardHandler = new StartWhiteBoardCommandHandler();
					whiteboardHandler.run(cmd);

				} else if (cmd instanceof StopWhiteBoardCommand) {
					StopWhiteBoardCommandHanlder whiteboardHandler = new StopWhiteBoardCommandHanlder();
					whiteboardHandler.run(cmd);
				}

				else if (cmd instanceof AuthenticateCommand) {
					SetAuthenticateCommandHanlder setAuthenticateCommandHanlder = new SetAuthenticateCommandHanlder();
					setAuthenticateCommandHanlder.run(cmd);
				}

				else if (cmd instanceof GrantCommand) {
					SetGrantCommandHanlder setGrantCommandHanlder = new SetGrantCommandHanlder();
					setGrantCommandHanlder.run(cmd);
				}

				else if (cmd instanceof MessageCommand) {
					MessageCommandHanlder messageCommandHanlder = new MessageCommandHanlder();
					messageCommandHanlder.run(cmd);
				}

				else if (cmd instanceof ChatCommand) {
					ChatCommandHanlder chatCommandHanlder = new ChatCommandHanlder();
					chatCommandHanlder.run(cmd);
				}

				else if (cmd instanceof StartVideoCommand) {
					StartVideoCommandHandler videoCommandHandler = new StartVideoCommandHandler();
					videoCommandHandler.run(cmd);
				}

				else if (cmd instanceof StartIntercomCommand) {
					StartIntercomCommandHandler startIntercomCommandHandler = new StartIntercomCommandHandler();
					startIntercomCommandHandler.run(cmd);
				}

				else if (cmd instanceof StartApplicationCommand) {
					StartApplicationCommandHanlder startApplicationCommandHandler = new StartApplicationCommandHanlder();
					startApplicationCommandHandler.run(cmd);
				}

				else if (cmd instanceof SendFileCollectCommand) {
					SendFileCollectCommandHandler sendfileCommandHandler = new SendFileCollectCommandHandler();
					sendfileCommandHandler.run(cmd);
				}

				else if (cmd instanceof SendFileAssignmentCommand) {
					SendFileAssignmentCommandHandler sendfileassignmentCommandHandler = new SendFileAssignmentCommandHandler();
					sendfileassignmentCommandHandler.run(cmd);
				}

				else if (cmd instanceof StartModelCommand) {
					StartModelCommandHanlder startModelCommandHanlder = new StartModelCommandHanlder();
					startModelCommandHanlder.run(cmd);
				}

				else if (cmd instanceof IntercomRequestCommand) {
					IntercomRequestCommandHanlder intercomRequestCommandHanlder = new IntercomRequestCommandHanlder();
					intercomRequestCommandHanlder.run(cmd);
				}

				else if (cmd instanceof StartStudentRecordCommand) {
					StartRecorderCommandHandler startRecorderCommandHanlder = new StartRecorderCommandHandler();
					startRecorderCommandHanlder.run(cmd);
				}

				else if (cmd instanceof StopStudentRecordCommand) {
					StopRecorderCommandHandler stopRecorderCommandHanlder = new StopRecorderCommandHandler();
					stopRecorderCommandHanlder.run(cmd);
				}

				else if (cmd instanceof StopModelCommand) {
					StopModelCommandHanlder stopModelCommandHanlder = new StopModelCommandHanlder();
					stopModelCommandHanlder.run(cmd);
				}

				else if (cmd instanceof SendRecordingErrorCommand) {
					StopModelCommandHanlder stopModelCommandHanlder = new StopModelCommandHanlder();
					stopModelCommandHanlder.run(cmd);
				}

				else if (cmd instanceof SendRecordingSuccessCommand) {
					StopModelCommandHanlder stopModelCommandHanlder = new StopModelCommandHanlder();
					stopModelCommandHanlder.run(cmd);
				}
				
				else if (cmd instanceof StartCallAllCommand) {
					StartCallAllCommandHanlder startCallAllCommandHanlder = new StartCallAllCommandHanlder();
					startCallAllCommandHanlder.run(cmd);
				}
				
				else if(cmd instanceof StartSpeechCommand){
					StartSpeechCommandHandler hnldr = new StartSpeechCommandHandler();
					hnldr.run(cmd);
				}
				
				else if(cmd instanceof SendSpeechFileCommand){
					SendSpeechFileCommandHandler hnldr = new SendSpeechFileCommandHandler();
					hnldr.run(cmd);
				}

			} catch (Exception ex) {
				JajeemExcetionHandler.logError(ex);
				System.err.println("Unknown message: " + ex.toString());
			}

		} catch (IOException ex) {
			JajeemExcetionHandler.logError(ex);
			ex.printStackTrace();
		}

	}

	public boolean isStopped() {
		return stopped;
	}

	@Override
	public void send(Command cmd) {
	}
}

class MyThread implements Runnable {
	
	File file;
	String ip;
	String fileName;
	public MyThread(File fileInp,String inp,String fname) {
		file = fileInp;
		ip = inp;
		fileName = fname;
	}

	public void run() {
		   try {
				System.out.println("Ip : "+ip);
				Socket clientSocket=new Socket(ip,12345);
//				Socket clientSocket=new Socket("127.0.0.1",12345);
				OutputStream out=clientSocket.getOutputStream();
			    FileInputStream fis=new FileInputStream(file);
			    byte[] info = new byte[2048];
			    byte[] temp = file.getPath().trim().getBytes();
			    int len = file.getPath().trim().length();
			    for (int k=0; k < len; k++) info[k]=temp[k];
			    for (int k=len; k < 2048; k++) info[k]=0x00;
			    out.write(info, 0, 2048);
			    
			    len = file.getName().trim().length();
			    temp = file.getName().trim().getBytes();
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
			    }
			    out.flush();
			    out.close();
			    fis.close();
			    
			    new Config();
				ServerService serv = InstructorNoa.getServerService();
				SendSpeechFileCommand cmd = new SendSpeechFileCommand(
						InetAddress.getLocalHost().getHostAddress(), ip, Integer.parseInt(Config.getParam("port")));
				
				cmd.setFile(fileName.trim());
				serv.send(cmd);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
	   }
	}
