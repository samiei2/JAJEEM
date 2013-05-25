package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.command.handler.ChatCommandHanlder;
import com.jajeem.command.handler.MessageCommandHanlder;
import com.jajeem.command.handler.OpenWebsiteCommandHandler;
import com.jajeem.command.handler.SendQuizResponseCommandHandler;
import com.jajeem.command.handler.SendSurveyResponseCommandHandler;
import com.jajeem.command.handler.SetAuthenticateCommandHanlder;
import com.jajeem.command.handler.SetBlackoutCommandHandler;
import com.jajeem.command.handler.SetGrantCommandHanlder;
import com.jajeem.command.handler.SetInternetCommandHandler;
import com.jajeem.command.handler.SetLockCommandHandler;
import com.jajeem.command.handler.SetPowerCommandHandler;
import com.jajeem.command.handler.SetVolumeCommandHandler;
import com.jajeem.command.handler.SetWhiteBlackAppCommandHandler;
import com.jajeem.command.handler.StartCaptureCommandHandler;
import com.jajeem.command.handler.StartQuizCommandHandler;
import com.jajeem.command.handler.StartSurveyCommandHandler;
import com.jajeem.command.handler.StartUpCommandHandler;
import com.jajeem.command.handler.StartViewerCommandHandler;
import com.jajeem.command.handler.StartWhiteBoardCommandHandler;
import com.jajeem.command.handler.StopCaptureCommandHandler;
import com.jajeem.command.handler.StopQuizCommandHanlder;
import com.jajeem.command.handler.StopSurveyCommandHanlder;
import com.jajeem.command.handler.StopWhiteBoardCommandHanlder;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.LockCommand;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.SendQuizResponseCommand;
import com.jajeem.command.model.SendSurveyResponseCommand;
import com.jajeem.command.model.StartCaptureCommand;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartViewerCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopCaptureCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.model.StopWhiteBoardCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;

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

		return packet.getData();
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

				System.out.println("Receiving ----> Command: " + cmd.getClass() + " from: " + cmd.getFrom());
				
				logger.info("Receiving: Message type: " + cmd.getClass()
						+ ", from: " + cmd.getTo());

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

			} catch (Exception ex) {
				System.err.println("Unknown message:" + ex.toString());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public boolean isStopped() {
		return stopped;
	}

	@Override
	public void send(Command cmd) {
		// TODO Auto-generated method stub

	}

}
