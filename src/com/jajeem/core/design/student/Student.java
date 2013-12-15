package com.jajeem.core.design.student;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.command.model.RequestCourseListCommand;
import com.jajeem.core.design.ui.BaseStudentFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jajeem.core.design.ui.CustomMainButton;
import com.jajeem.core.design.ui.CustomStudentButton;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileManagerMain;

import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Font;
import javax.swing.SwingConstants;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;
import org.jitsi.impl.neomedia.codec.audio.silk.InitEncoderFLP;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.recorder.design.RecorderStudent;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.CustomCloseButton;
import com.jajeem.util.i18n;

public class Student {

	private JPanel contentPane;
	private JLabel lblNewLabel_1;
	private CustomCloseButton buttonMin;
	private CustomCloseButton buttonClose;
	
	BaseStudentFrame frm;
	static BaseStudentFrame jajeemProject;
	Font font = new Font("Arial", Font.BOLD, 20);
	private CustomStudentButton buttonFileManager;
	private CustomStudentButton buttonMoviePlayer;
	private CustomStudentButton buttonRecording;
	private CustomStudentButton buttonContactInstructor;
	private static CustomStudentButton buttonContactInstructorTemp;
	private CustomStudentButton buttonAccountManager;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;
	private static AVTransmit2 conversationtransmitter;

	private static VNCCaptureService vncViewer;

	private static com.jajeem.core.model.Student studentModel;
	private static boolean granted = false;
	private static CustomStudentButton recordButtonStatic;
	private Timer timer;
	private static int countdown = 30000;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student frame = new Student();
//					frame.getMainFrame().setSize(400, 500);
//					frame.getMainFrame().pack();
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
			setConversationtransmitter(new AVTransmit2("5000", "", "10000"));

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
		
		buttonContactInstructor = new CustomStudentButton(imgMessage);
		buttonContactInstructor.setUndecorated(true);
		buttonContactInstructor.setHorizontalAlignment(SwingConstants.LEADING);
		buttonContactInstructor.setMargin(0, 10, 0, 0);
		buttonContactInstructor.setIconTextGap(20);
		buttonContactInstructor.setFont(font);
		buttonContactInstructor.setForeground(Color.WHITE);
		try {
			buttonContactInstructor.setText(i18n.getParam("Chat"));
			TooltipManager.setTooltip(buttonContactInstructor, imgToolTip,
					i18n.getParam("Chat with Instructor"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}
		buttonContactInstructorTemp = buttonContactInstructor;
		
		buttonFileManager = new CustomStudentButton(imgFile);
		buttonFileManager.setUndecorated(true);
		buttonFileManager.setHorizontalAlignment(SwingConstants.LEADING);
		buttonFileManager.setMargin(0, 10, 0, 0);
		buttonFileManager.setIconTextGap(20);
		buttonFileManager.setFont(font);
		buttonFileManager.setForeground(Color.WHITE);
		try {
			buttonFileManager.setText(i18n.getParam("File Manager"));
			TooltipManager.setTooltip(buttonFileManager, imgToolTip,
					i18n.getParam("Use File Manager to send or receive files"),
					TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		buttonMoviePlayer = new CustomStudentButton(imgVideo);
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
		
		buttonRecording = new CustomStudentButton(imgRecord);
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
		
		buttonAccountManager = new CustomStudentButton(imgAccount);
		buttonAccountManager.setUndecorated(true);
		buttonAccountManager.setHorizontalAlignment(SwingConstants.LEADING);
		buttonAccountManager.setMargin(0, 10, 0, 0);
		buttonAccountManager.setIconTextGap(20);
		buttonAccountManager.setFont(font);
		buttonAccountManager.setForeground(Color.WHITE);
		buttonAccountManager.setText(i18n.getParam("Account"));
		try {
			TooltipManager.setTooltip(buttonAccountManager, imgToolTip,
					i18n.getParam("Account Manager"), TooltipWay.down);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BaseStudentFrame frame = new BaseStudentFrame();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPanel());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonContactInstructor, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(buttonFileManager, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(buttonMoviePlayer, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(buttonRecording, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(buttonAccountManager, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonContactInstructor, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttonFileManager, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttonMoviePlayer, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttonRecording, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buttonAccountManager, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		frame.getContentPanel().setLayout(groupLayout);
		
		JLabel lblNewLabel = new JLabel("ClassMate");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century", Font.PLAIN, 30));
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout_1 = new GroupLayout(frame.getLogoPanel());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, 80, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, 20, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		frame.getLogoPanel().setLayout(groupLayout_1);
		
		frame.setPreferredSize(new Dimension(300, 500));
		
		buttonClose = new CustomCloseButton("/icons/noa_en/close.png");
		buttonClose.setUndecorated(true);
		
		buttonMin = new CustomCloseButton("/icons/noa_en/min.png");
		buttonMin.setUndecorated(true);
		GroupLayout groupLayout_2 = new GroupLayout(frame.getClosePanel());
		groupLayout_2.setHorizontalGroup(
			groupLayout_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonMin, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(buttonClose, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout_2.setVerticalGroup(
			groupLayout_2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addGroup(groupLayout_2.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(6)
							.addComponent(buttonClose, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout_2.createSequentialGroup()
							.addGap(12)
							.addComponent(buttonMin, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getClosePanel().setLayout(groupLayout_2);
		
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(new Dimension(300,500));
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle rect = ge.getMaximumWindowBounds();
		int x = (int) rect.getMaxX() - frame.getWidth();
		int y = (int) ((rect.getMaxY() - frame.getHeight()));
		frame.setLocation(x, y);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Student.class.getResource("/icons/noa_en/logo.png")));
		frame.setResizable(false);
		jajeemProject = frame;
		setMainFrame(frame);
		
		InitEvents();
	}

	private void InitEvents() {
		getMainFrame().addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent e) {
				lblNewLabel_1.setIcon(
						new ImageIcon(
								new ImageIcon(Student.class.getResource("/icons/noa_en/New/loginlogo.png"))
								.getImage()
								.getScaledInstance(lblNewLabel_1.getHeight(), lblNewLabel_1.getHeight(), Image.SCALE_SMOOTH)));
				super.componentShown(e);
			}
		});
		
		buttonClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getMainFrame().setVisible(false);
			}
		});
		
		buttonMin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getMainFrame().setExtendedState(JFrame.ICONIFIED);
			}
		});
		
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
					if(StudentLogin.getServerService()!=null)
						StudentLogin.getServerService().send(gcc);
					else
						WebOptionPane.showMessageDialog(null, "You are not logged in!\nPlease log in first!", "Error", WebOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static Window getFrmJajeemProject() {
		return jajeemProject;
	}
	
	public BaseStudentFrame getMainFrame() {
		return frm;
	}

	private void setMainFrame(BaseStudentFrame std) {
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

	public static CustomStudentButton getRecordButtonStatic() {
		return recordButtonStatic;
	}

	public static void setRecordButtonStatic(
			CustomStudentButton recordButtonStatic) {
		Student.recordButtonStatic = recordButtonStatic;
	}

	public static int getCountdown() {
		return countdown;
	}

	public static void setCountdown(int countdown) {
		Student.countdown = countdown;
	}

	public static CustomStudentButton getButtonContactInstructor() {
		return buttonContactInstructorTemp;
	}

	public static AVTransmit2 getConversationtransmitter() {
		return conversationtransmitter;
	}

	public static void setConversationtransmitter(
			AVTransmit2 conversationtransmitter) {
		Student.conversationtransmitter = conversationtransmitter;
	}
}
