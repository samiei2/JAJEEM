package com.jajeem.core.design;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.command.model.RequestCourseListCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileManagerMain;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.recorder.design.RecorderStudent;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButtonStudent;
import com.jajeem.util.CustomStudentFrame;
import com.jajeem.util.i18n;

public class Student {
	CustomStudentFrame frm;
	static CustomStudentFrame jajeemProject;
	Font font = new Font("Arial", Font.BOLD, 20);
	private CustomButtonStudent buttonFileManager;
	private CustomButtonStudent buttonMoviePlayer;
	private CustomButtonStudent buttonRecording;
	private CustomButtonStudent buttonContactInstructor;
	private static CustomButtonStudent buttonContactInstructorTemp;
	private CustomButtonStudent buttonAccountManager;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;

	private static VNCCaptureService vncViewer;

	private static com.jajeem.core.model.Student studentModel;
	private static boolean granted = false;
	private static CustomButtonStudent recordButtonStatic;
	private Timer timer;
	private static int countdown = 30000;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Student frame = new Student();
					frame.getMainFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Student() throws Exception {
		try {
			new Config();
			new i18n();

			setTransmitter(new AVTransmit2("10000", "", "5000"));
			setReceiverOnly(new AVReceiveOnly("10010", "", "5010"));

		} catch (Throwable e) {
		}

		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				countdown -= 1000;
				if (countdown <= 0) {
					Student.getFrmJajeemProject().setVisible(false);
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start();

		CustomStudentFrame mainFram = new CustomStudentFrame();
		mainFram.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Student.class.getResource("/icons/noa_en/logo.png")));
		jajeemProject = mainFram;
		setMainFrame(mainFram);
		mainFram.setResizable(false);
		// mainFram.setAlwaysOnTop(true);
		try {
			mainFram.setTitle(i18n.getParam("Classmate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFram.setSize(441, 436);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - mainFram.getWidth();
		int y = (int) ((rect.getMaxY() - mainFram.getHeight()));
		mainFram.setLocation(x, y);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(mainFram.getMainContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		ImageIcon imgToolTip = null;
		try {
			imgToolTip = new ImageIcon(ImageIO.read(Student.class
					.getResourceAsStream("/icons/menubar/tooltip.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TooltipManager.setDefaultDelay(500);
		ImageIcon imgMessage = null;
		try {
			imgMessage = new ImageIcon(ImageIO.read(Student.class
					.getResourceAsStream(("/icons/noa_en/chatStudent.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon imgFile = null;
		try {
			imgFile = new ImageIcon(
					ImageIO.read(Student.class
							.getResourceAsStream(("/icons/noa_en/filesharingStudent.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon imgVideo = null;
		try {
			imgVideo = new ImageIcon(
					ImageIO.read(Student.class
							.getResourceAsStream(("/icons/noa_en/movieplayerStudent.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon imgRecord = null;
		try {
			imgRecord = new ImageIcon(
					ImageIO.read(Student.class
							.getResourceAsStream(("/icons/noa_en/recordingStudent.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon imgAccount = null;
		try {
			imgAccount = new ImageIcon(ImageIO.read(Student.class
					.getResourceAsStream(("/icons/noa_en/accountStudent.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		buttonContactInstructor = new CustomButtonStudent(imgMessage);
		buttonContactInstructor.setUndecorated(true);
		buttonContactInstructor.setHorizontalAlignment(SwingConstants.LEADING);
		buttonContactInstructor.setMargin(0, 10, 0, 0);
		buttonContactInstructor.setIconTextGap(20);
		buttonContactInstructor.setFont(font);
		buttonContactInstructor.setForeground(Color.WHITE);
		try {
			buttonContactInstructor.setText(i18n.getParam("Chat"));
			TooltipManager.setTooltip(buttonContactInstructor, imgToolTip,
					i18n.getParam("tooltipChatInstructor"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}
		buttonContactInstructorTemp = buttonContactInstructor;

		buttonFileManager = new CustomButtonStudent(imgFile);
		buttonFileManager.setUndecorated(true);
		buttonFileManager.setHorizontalAlignment(SwingConstants.LEADING);
		buttonFileManager.setMargin(0, 10, 0, 0);
		buttonFileManager.setIconTextGap(20);
		buttonFileManager.setFont(font);
		buttonFileManager.setForeground(Color.WHITE);
		try {
			buttonFileManager.setText(i18n.getParam("File Manager"));
			TooltipManager.setTooltip(buttonFileManager, imgToolTip,
					i18n.getParam("File Manager to send or receive files"),
					TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}

		buttonMoviePlayer = new CustomButtonStudent(imgVideo);
		buttonMoviePlayer.setUndecorated(true);
		buttonMoviePlayer.setHorizontalAlignment(SwingConstants.LEADING);
		buttonMoviePlayer.setMargin(0, 10, 0, 0);
		buttonMoviePlayer.setIconTextGap(20);
		buttonMoviePlayer.setFont(font);
		buttonMoviePlayer.setForeground(Color.WHITE);
		buttonMoviePlayer.setText(i18n.getParam("Movie Player"));
		try {
			TooltipManager.setTooltip(buttonMoviePlayer, imgToolTip,
					i18n.getParam("Movie Player"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}

		buttonRecording = new CustomButtonStudent(imgRecord);
		buttonRecording.setUndecorated(true);
		buttonRecording.setHorizontalAlignment(SwingConstants.LEADING);
		buttonRecording.setMargin(0, 10, 0, 0);
		buttonRecording.setIconTextGap(20);
		buttonRecording.setFont(font);
		buttonRecording.setForeground(Color.WHITE);
		buttonRecording.setText(i18n.getParam("Recorder"));
		try {
			TooltipManager.setTooltip(buttonRecording, imgToolTip,
					i18n.getParam("Record voice or desktop"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}
		recordButtonStatic = buttonRecording;

		buttonAccountManager = new CustomButtonStudent(imgAccount);
		buttonAccountManager.setUndecorated(true);
		buttonAccountManager.setHorizontalAlignment(SwingConstants.LEADING);
		buttonAccountManager.setMargin(0, 10, 0, 0);
		buttonAccountManager.setIconTextGap(20);
		buttonAccountManager.setFont(font);
		buttonAccountManager.setForeground(Color.WHITE);
		buttonAccountManager.setText(i18n.getParam("Account"));
		try {
			TooltipManager.setTooltip(buttonAccountManager, imgToolTip,
					i18n.getParam("Record voice or desktop"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING, false)
												.addComponent(
														buttonFileManager,
														GroupLayout.PREFERRED_SIZE,
														308,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														buttonMoviePlayer,
														GroupLayout.PREFERRED_SIZE,
														308,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														buttonRecording,
														GroupLayout.PREFERRED_SIZE,
														308,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														buttonAccountManager,
														GroupLayout.PREFERRED_SIZE,
														308,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														buttonContactInstructor,
														GroupLayout.PREFERRED_SIZE,
														308,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(35, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(buttonContactInstructor,
								GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonFileManager,
								GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonMoviePlayer,
								GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonRecording,
								GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAccountManager,
								GroupLayout.PREFERRED_SIZE, 41,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(15, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		mainFram.getMainContentPane().setLayout(groupLayout);

		initEvents();

	}

	public static Window getFrmJajeemProject() {
		return jajeemProject;
	}

	private void initEvents() {
		buttonFileManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClientFileManagerMain main = new ClientFileManagerMain();
					main.setVisible(true);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});

		buttonMoviePlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final Process proc;
					System.out.println(new File("util/", "videoplayer.jar")
							.exists());
					proc = Runtime.getRuntime().exec(
							"java -jar videoplayer.jar \"\" true", null,
							new File("util/"));
					// Then retrieve the process output
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								BufferedReader in = new BufferedReader(
										new InputStreamReader(proc
												.getInputStream()));
								String line = null;
								while ((line = in.readLine()) != null) {
									System.out.println(line);
								}
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
							}
						}
					}).start();
				} catch (IOException ex) {
					JajeemExcetionHandler.logError(ex);
					ex.printStackTrace();
				}
			}
		});

		buttonContactInstructor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MessageSend.main(new String[] { StudentLogin.getServerIp(),
							Config.getParam("serverPort") });
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});

		buttonRecording.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Enabling dialog decoration
//				boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
//				WebLookAndFeel.setDecorateDialogs(true);

				RecorderStudent recorder = new RecorderStudent(
						new ArrayList<String>(), false, false);
				recorder.setLocationRelativeTo(getMainFrame());
				recorder.setVisible(true);
				buttonRecording.setEnabled(false);
				// Restoring frame decoration option
//				WebLookAndFeel.setDecorateDialogs(decorateFrames);
			}
		});

		buttonAccountManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RequestCourseListCommand gcc;
				try {
					gcc = new RequestCourseListCommand(InetAddress
							.getLocalHost().getHostAddress(), StudentLogin
							.getServerIp(), Integer.parseInt(Config
							.getParam("serverPort")), studentModel);
					StudentLogin.getServerService().send(gcc);
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});
	}

	public CustomStudentFrame getMainFrame() {
		return frm;
	}

	private void setMainFrame(CustomStudentFrame std) {
		this.frm = std;
	}

	public static List<Chat> getChatList() {
		return chatList;
	}

	public static void setChatList(List<Chat> chatList) {
		Student.chatList = chatList;
	}

	public static AVTransmit2 getTransmitter() throws Exception {
		if (transmitter == null) {
			transmitter = new AVTransmit2("10000", "", "5000");
		}
		return transmitter;
	}

	public static void setTransmitter(AVTransmit2 transmitter) throws Exception {
		Student.transmitter = transmitter;
	}

	public static VNCCaptureService getVncViewer() {
		return vncViewer;
	}

	public static void setVncViewer(VNCCaptureService vncViewer) {
		Student.vncViewer = vncViewer;
	}

	public static AVReceiveOnly getReceiverOnly() {
		return receiverOnly;
	}

	public static void setReceiverOnly(AVReceiveOnly receiverOnly) {
		Student.receiverOnly = receiverOnly;
	}

	public static AVSendOnly getSendOnly() {
		return sendOnly;
	}

	public static void setSendOnly(AVSendOnly sendOnly) {
		Student.sendOnly = sendOnly;
	}

	public static com.jajeem.core.model.Student getStudentModel() {
		return studentModel;
	}

	public static void setStudentModel(
			com.jajeem.core.model.Student studentModel) {
		Student.studentModel = studentModel;
	}

	public static boolean isGranted() {
		return granted;
	}

	public static void setGranted(boolean granted) {
		Student.granted = granted;
	}

	public static CustomButtonStudent getRecordButtonStatic() {
		return recordButtonStatic;
	}

	public static void setRecordButtonStatic(
			CustomButtonStudent recordButtonStatic) {
		Student.recordButtonStatic = recordButtonStatic;
	}

	public static int getCountdown() {
		return countdown;
	}

	public static void setCountdown(int countdown) {
		Student.countdown = countdown;
	}

	public static CustomButtonStudent getButtonContactInstructor() {
		return buttonContactInstructorTemp;
	}
}
