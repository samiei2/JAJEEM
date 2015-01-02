package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.jajeem.command.handler.ChatCommandHanlder;
import com.jajeem.command.handler.FinishedQuizCommandHandler;
import com.jajeem.command.handler.FinishedSurveyCommandHandler;
import com.jajeem.command.handler.GetCourseListCommandHandler;
import com.jajeem.command.handler.GetProgramListCommandHandler;
import com.jajeem.command.handler.IntercomRequestCommandHanlder;
import com.jajeem.command.handler.MessageCommandHanlder;
import com.jajeem.command.handler.OpenWebsiteCommandHandler;
import com.jajeem.command.handler.RequestCourseListCommandHandler;
import com.jajeem.command.handler.RestartStudentProgramCommandHandler;
import com.jajeem.command.handler.SendFileAssignmentCommandHandler;
import com.jajeem.command.handler.SendFileCollectCommandHandler;
import com.jajeem.command.handler.SendProgramListCommandHandler;
import com.jajeem.command.handler.SendQuizResponseCommandHandler;
import com.jajeem.command.handler.SendRecordingErrorCommandHandler;
import com.jajeem.command.handler.SendRecordingSuccessCommandHandler;
import com.jajeem.command.handler.SendSpeechFileCommandHandler;
import com.jajeem.command.handler.SendSurveyResponseCommandHandler;
import com.jajeem.command.handler.SetAuthenticateCommandHanlder;
import com.jajeem.command.handler.SetDuplicateLoginCommandHanlder;
import com.jajeem.command.handler.SetGrantCommandHanlder;
import com.jajeem.command.handler.SetInternetCommandHandler;
import com.jajeem.command.handler.SetLockCommandHandler;
import com.jajeem.command.handler.SetPowerCommandHandler;
import com.jajeem.command.handler.SetStudentLogoutCommandHanlder;
import com.jajeem.command.handler.SetTeacherLogoutCommandHanlder;
import com.jajeem.command.handler.SetVolumeCommandHandler;
import com.jajeem.command.handler.SetWhiteBlackAppCommandHandler;
import com.jajeem.command.handler.StartApplicationCommandHanlder;
import com.jajeem.command.handler.StartCallAllCommandHanlder;
import com.jajeem.command.handler.StartCaptureCommandHandler;
import com.jajeem.command.handler.StartConversationCommandHanlder;
import com.jajeem.command.handler.StartIntercomCommandHandler;
import com.jajeem.command.handler.StartModelCommandHanlder;
import com.jajeem.command.handler.StartMoviePlayerCommandHandler;
import com.jajeem.command.handler.StartQuizCommandHandler;
import com.jajeem.command.handler.StartRecorderCommandHandler;
import com.jajeem.command.handler.StartSpeechCommandHandler;
import com.jajeem.command.handler.StartSurveyCommandHandler;
import com.jajeem.command.handler.StartUpCommandHandler;
import com.jajeem.command.handler.StartVideoChatCommandHandler;
import com.jajeem.command.handler.StartViewerCommandHandler;
import com.jajeem.command.handler.StartWhiteBoardCommandHandler;
import com.jajeem.command.handler.StopCallAllCommandHanlder;
import com.jajeem.command.handler.StopCaptureCommandHandler;
import com.jajeem.command.handler.StopConversationCommandHanlder;
import com.jajeem.command.handler.StopIntercomCommandHandler;
import com.jajeem.command.handler.StopModelCommandHanlder;
import com.jajeem.command.handler.StopMoviePlayerCommandHandler;
import com.jajeem.command.handler.StopQuizCommandHanlder;
import com.jajeem.command.handler.StopRecorderCommandHandler;
import com.jajeem.command.handler.StopSurveyCommandHanlder;
import com.jajeem.command.handler.StopVideoChatCommandHandler;
import com.jajeem.command.handler.StopWhiteBoardCommandHanlder;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.ChatCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.DuplicateLoginCommand;
import com.jajeem.command.model.FinishedQuizCommand;
import com.jajeem.command.model.FinishedSurveyCommand;
import com.jajeem.command.model.GetCourseListCommand;
import com.jajeem.command.model.GetProgramListCommand;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.model.IntercomRequestCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.LockCommand;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.RequestCourseListCommand;
import com.jajeem.command.model.RestartStudentProgramCommand;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.command.model.SendProgramListCommand;
import com.jajeem.command.model.SendQuizResponseCommand;
import com.jajeem.command.model.SendRecordingErrorCommand;
import com.jajeem.command.model.SendRecordingSuccessCommand;
import com.jajeem.command.model.SendSpeechFileCommand;
import com.jajeem.command.model.SendSurveyResponseCommand;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartCaptureCommand;
import com.jajeem.command.model.StartConversationCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.command.model.StartMoviePlayerCommand;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartSpeechCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartVideoChatCommand;
import com.jajeem.command.model.StartViewerCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopCallAllCommand;
import com.jajeem.command.model.StopCaptureCommand;
import com.jajeem.command.model.StopConversationCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.StopModelCommand;
import com.jajeem.command.model.StopMoviePlayerCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.model.StopVideoChatCommand;
import com.jajeem.command.model.StopWhiteBoardCommand;
import com.jajeem.command.model.StudentLogoutCommand;
import com.jajeem.command.model.TeacherLogoutCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.Config;

public class ClientService implements IConnectorSevice, Runnable {

	protected MulticastSocket socket;
	protected InetAddress group;
	protected int port;
	protected Thread thread;
	private ExecutorService pool;

	private boolean stopped;

	static Logger logger = Logger.getLogger("ClientService.class");

	public ClientService(String group, int port) throws NumberFormatException,
			Exception {
		stopped = false;
		pool = Executors.newFixedThreadPool(10);

		this.port = port;
		this.group = InetAddress.getByName(group);
		

		/* setup the multicast control channel */
		socket = new MulticastSocket(port);
		
		// socket.connect(this.group,port);
		socket.joinGroup(this.group);
		socket.setReceiveBufferSize(100000);

		thread = new Thread(this);
		
		try {
			System.out.println("Client listening Info");
			System.out.println("Client listening on local address: "
					+ socket.getLocalAddress());
			System.out.println("Client listening on local socket address: "
					+ socket.getLocalSocketAddress());
			System.out.println("Client listening on interface: "
					+ socket.getInterface());
			System.out.println("Client listening on port: "
					+ socket.getLocalPort());
			System.out.println("Client listening on port: "
					+ socket.getLocalPort());
		} catch (Exception e) {

		}
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
					String fileName = message.substring(
							message.lastIndexOf("\\") + 1, message.length());
					SendFileToAll(new File(message), fileName);

					// JOptionPane.showMessageDialog(null,
					// "Speech Recognition started for all users!");
				} catch (Exception e) {
					JOptionPane
							.showMessageDialog(null,
									"An error occured in sending speech file to users!");
					JajeemExceptionHandler.logError(e);
				}
			}
		});
		t.start();
	}

	protected void SendFileToAll(final File file, String fileName) {
		try {
			final ArrayList<String> ips = InstructorNoa.getAllStudentIPs();
			System.out.println("Ips Count : " + ips.size());
			try {
				for (int i = 0; i < ips.size(); i++) { // send for all selected
														// clients
					Runnable r = new MyThread(file, ips.get(i), fileName);
					new Thread(r).start();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			JajeemExceptionHandler.logError(e);
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
				JajeemExceptionHandler.logError(ex);
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

				if (!(o instanceof Command)) {
					throw new ClassNotFoundException("Object is not a message");
				}

				final Command cmd = (Command) o;
				
//				InetAddress address = InetAddress.getByName(cmd.getFrom());

//				System.out.println("Receiving ----> Command: " + cmd.getClass()
//						+ " from: " + cmd.getFrom());

				// logger.info("Receiving: Message type: " + cmd.getClass()
				// + ", from: " + cmd.getTo());

				if (cmd instanceof StartCaptureCommand) {
					pool.execute(new Runnable() {
						@Override
						public void run() {
							long ts = System.nanoTime();
							StartCaptureCommandHandler startCaptureHandler = new StartCaptureCommandHandler();
							startCaptureHandler.run(cmd);
							System.out.println(System.nanoTime()-ts);
						}
					});
				} else if (cmd instanceof StopCaptureCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopCaptureCommandHandler stopCaptureHandler = new StopCaptureCommandHandler();
							stopCaptureHandler.run(cmd);
						}
					});
				} else if (cmd instanceof StartViewerCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartViewerCommandHandler startViewerHandler = new StartViewerCommandHandler();
							startViewerHandler.run(cmd);
						}
					});
				} else if (cmd instanceof StartUpCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartUpCommandHandler startUpHandler = new StartUpCommandHandler();
							try {
								startUpHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StartQuizCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartQuizCommandHandler startQuizHandler = new StartQuizCommandHandler();
							try {
								startQuizHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StopQuizCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopQuizCommandHanlder stopQuizHandler = new StopQuizCommandHanlder();
							try {
								stopQuizHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof SendQuizResponseCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendQuizResponseCommandHandler sendquizresponse = new SendQuizResponseCommandHandler();
							try {
								sendquizresponse.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StartSurveyCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartSurveyCommandHandler startSurveyHandler = new StartSurveyCommandHandler();
							try {
								startSurveyHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StopSurveyCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopSurveyCommandHanlder stopSurveyHandler = new StopSurveyCommandHanlder();
							try {
								stopSurveyHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof SendSurveyResponseCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendSurveyResponseCommandHandler sendSurveyresponse = new SendSurveyResponseCommandHandler();
							try {
								sendSurveyresponse.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof WhiteBlackAppCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetWhiteBlackAppCommandHandler setWhiteBlackAppCommandHandler = new SetWhiteBlackAppCommandHandler();
							try {
								setWhiteBlackAppCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof InternetCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetInternetCommandHandler setInternetCommandHandler = new SetInternetCommandHandler();
							try {
								setInternetCommandHandler.run(cmd);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof VolumeCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetVolumeCommandHandler setVolumeCommandHandler = new SetVolumeCommandHandler();
							try {
								setVolumeCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof LockCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetLockCommandHandler setLockCommandHandler = new SetLockCommandHandler();
							try {
								setLockCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof PowerCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetPowerCommandHandler setPowerCommandHandler = new SetPowerCommandHandler();
							try {
								setPowerCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof VolumeCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetVolumeCommandHandler setVolumeCommandHandler = new SetVolumeCommandHandler();
							try {
								setVolumeCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof WebsiteCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							OpenWebsiteCommandHandler openWebsiteCommandHandler = new OpenWebsiteCommandHandler();
							try {
								openWebsiteCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartWhiteBoardCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartWhiteBoardCommandHandler whiteboardHandler = new StartWhiteBoardCommandHandler();
							try {
								whiteboardHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StopWhiteBoardCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopWhiteBoardCommandHanlder whiteboardHandler = new StopWhiteBoardCommandHanlder();
							try {
								whiteboardHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof AuthenticateCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetAuthenticateCommandHanlder setAuthenticateCommandHanlder = new SetAuthenticateCommandHanlder();
							try {
								setAuthenticateCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof GrantCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetGrantCommandHanlder setGrantCommandHanlder = new SetGrantCommandHanlder();
							try {
								setGrantCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof MessageCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							MessageCommandHanlder messageCommandHanlder = new MessageCommandHanlder();
							try {
								messageCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof ChatCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							ChatCommandHanlder chatCommandHanlder = new ChatCommandHanlder();
							try {
								chatCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartMoviePlayerCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartMoviePlayerCommandHandler videoCommandHandler = new StartMoviePlayerCommandHandler();
							try {
								videoCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StopMoviePlayerCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopMoviePlayerCommandHandler videoCommandHandler = new StopMoviePlayerCommandHandler();
							try {
								videoCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartIntercomCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartIntercomCommandHandler startIntercomCommandHandler = new StartIntercomCommandHandler();
							startIntercomCommandHandler.run(cmd);
						}
					});

				} else if (cmd instanceof StopIntercomCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopIntercomCommandHandler stopIntercomCommandHandler = new StopIntercomCommandHandler();
							stopIntercomCommandHandler.run(cmd);
						}
					});

				} else if (cmd instanceof StartApplicationCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartApplicationCommandHanlder startApplicationCommandHandler = new StartApplicationCommandHanlder();
							try {
								startApplicationCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof SendFileCollectCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendFileCollectCommandHandler sendfileCommandHandler = new SendFileCollectCommandHandler();
							try {
								sendfileCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof SendFileAssignmentCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendFileAssignmentCommandHandler sendfileassignmentCommandHandler = new SendFileAssignmentCommandHandler();
							try {
								sendfileassignmentCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartModelCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartModelCommandHanlder startModelCommandHanlder = new StartModelCommandHanlder();
							try {
								startModelCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof IntercomRequestCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							IntercomRequestCommandHanlder intercomRequestCommandHanlder = new IntercomRequestCommandHanlder();
							try {
								intercomRequestCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartStudentRecordCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartRecorderCommandHandler startRecorderCommandHanlder = new StartRecorderCommandHandler();
							try {
								startRecorderCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StopStudentRecordCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopRecorderCommandHandler stopRecorderCommandHanlder = new StopRecorderCommandHandler();
							try {
								stopRecorderCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StopModelCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopModelCommandHanlder stopModelCommandHanlder = new StopModelCommandHanlder();
							try {
								stopModelCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof SendRecordingErrorCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendRecordingErrorCommandHandler stopModelCommandHanlder = new SendRecordingErrorCommandHandler();
							try {
								stopModelCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof SendRecordingSuccessCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendRecordingSuccessCommandHandler stopModelCommandHanlder = new SendRecordingSuccessCommandHandler();
							try {
								stopModelCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartCallAllCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartCallAllCommandHanlder startCallAllCommandHanlder = new StartCallAllCommandHanlder();
							try {
								startCallAllCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StopCallAllCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopCallAllCommandHanlder stopCallAllCommandHanlder = new StopCallAllCommandHanlder();
							try {
								stopCallAllCommandHanlder.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartSpeechCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartSpeechCommandHandler hnldr = new StartSpeechCommandHandler();
							try {
								hnldr.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof SendSpeechFileCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendSpeechFileCommandHandler hnldr = new SendSpeechFileCommandHandler();
							try {
								hnldr.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof FinishedQuizCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							FinishedQuizCommandHandler hnldr = new FinishedQuizCommandHandler();
							try {
								hnldr.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof FinishedSurveyCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							FinishedSurveyCommandHandler hnldr = new FinishedSurveyCommandHandler();
							try {
								hnldr.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StartVideoChatCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartVideoChatCommandHandler startVideoChatCommandHandler = new StartVideoChatCommandHandler();
							startVideoChatCommandHandler.run(cmd);
						}
					});

				} else if (cmd instanceof StopVideoChatCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopVideoChatCommandHandler stopVideoChatCommandHandler = new StopVideoChatCommandHandler();
							stopVideoChatCommandHandler.run(cmd);
						}
					});

				} else if (cmd instanceof RequestCourseListCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							RequestCourseListCommandHandler requestCourseListCommandHandler = new RequestCourseListCommandHandler();
							try {
								requestCourseListCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof GetCourseListCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							GetCourseListCommandHandler getCourseListCommandHandler = new GetCourseListCommandHandler();
							try {
								getCourseListCommandHandler.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof DuplicateLoginCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetDuplicateLoginCommandHanlder duplicateLogin = new SetDuplicateLoginCommandHanlder();
							try {
								duplicateLogin.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof TeacherLogoutCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetTeacherLogoutCommandHanlder logout = new SetTeacherLogoutCommandHanlder();
							try {
								logout.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});

				} else if (cmd instanceof StudentLogoutCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SetStudentLogoutCommandHanlder logout = new SetStudentLogoutCommandHanlder();
							try {
								logout.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StartConversationCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StartConversationCommandHanlder startConversationCommand = new StartConversationCommandHanlder();
							try {
								startConversationCommand.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof StopConversationCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							StopConversationCommandHanlder stopConversationCommand = new StopConversationCommandHanlder();
							try {
								stopConversationCommand.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof RestartStudentProgramCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							RestartStudentProgramCommandHandler restartConversationCommand = new RestartStudentProgramCommandHandler();
							try {
								restartConversationCommand.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof GetProgramListCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							GetProgramListCommandHandler getProgramListCommand = new GetProgramListCommandHandler();
							try {
								getProgramListCommand.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				} else if (cmd instanceof SendProgramListCommand) {
					pool.execute(new Runnable() {

						@Override
						public void run() {
							SendProgramListCommandHandler sendProgramListCommand = new SendProgramListCommandHandler();
							try {
								sendProgramListCommand.run(cmd);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
				}
				
			} catch (Exception ex) {
				JajeemExceptionHandler.logError(ex);
				System.err.println("Unknown message: " + ex.toString());
			}

		} catch (IOException ex) {
			JajeemExceptionHandler.logError(ex);
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

	public MyThread(File fileInp, String inp, String fname) {
		file = fileInp;
		ip = inp;
		fileName = fname;
	}

	@Override
	@SuppressWarnings({ "resource", "unused" })
	public void run() {
		try {
			System.out.println("Ip : " + ip);
			Socket clientSocket = new Socket(ip, 12345);
			// Socket clientSocket=new Socket("127.0.0.1",12345);
			OutputStream out = clientSocket.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			byte[] info = new byte[2048];
			byte[] temp = file.getPath().trim().getBytes();
			int len = file.getPath().trim().length();
			for (int k = 0; k < len; k++) {
				info[k] = temp[k];
			}
			for (int k = len; k < 2048; k++) {
				info[k] = 0x00;
			}
			out.write(info, 0, 2048);

			len = file.getName().trim().length();
			temp = file.getName().trim().getBytes();
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
			}
			out.flush();
			out.close();
			fis.close();

			new Config();
			ServerService serv = InstructorNoa.getServerService();
			SendSpeechFileCommand cmd = new SendSpeechFileCommand(InetAddress
					.getLocalHost().getHostAddress(), ip,
					Integer.parseInt(Config.getParam("port")));

			cmd.setFile(fileName.trim());
			serv.send(cmd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}
	}
}
