package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.WebPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.quiz.design.QuizMain;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;

public class InstructorRight {

	public static String iconsPath = "";
	final static WebPopup popup = new WebPopup();

	/**
	 * @wbp.parser.entryPoint
	 */
	public static WebPanel createPanel(WebPanel panel2) throws Exception {

		new Config();

		final WebPanel panel = new WebPanel();

		ImageIcon imgToolTip = new ImageIcon(ImageIO.read(InstructorRight.class
				.getResourceAsStream("/icons/menubar/tooltip.png")));
		TooltipManager.setDefaultDelay(1000);

		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);
		panel.setUndecorated(true);

		final ImageIcon imgIntercom = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/intercom_text.png")));
		final ImageIcon imgStopIntercom = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/stop_intercom.png")));
		final WebButton intercomButton = new WebButton(imgIntercom);
		intercomButton.setRound(0);
		TooltipManager.setTooltip(intercomButton, imgToolTip,
				"Start talking to selected student", TooltipWay.left);
		panel.add(intercomButton);

		ImageIcon imgVNC = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/vnc_text.png")));
		WebButton VNCButton = new WebButton(imgVNC);
		VNCButton.setRound(0);
		TooltipManager.setTooltip(VNCButton, imgToolTip,
				"Remote access to selected student", TooltipWay.left);
		panel.add(VNCButton);

		ImageIcon imgChat = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/chat_text.png")));
		WebButton chatButton = new WebButton(imgChat);
		TooltipManager.setTooltip(chatButton, imgToolTip,
				"Start chating to selected student", TooltipWay.left);
		chatButton.setRound(0);
		panel.add(chatButton);

		ImageIcon imgStopInternet = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/stopInternet_text.png")));
		WebButton stopInternetButton = new WebButton(imgStopInternet);
		stopInternetButton.setRound(0);
		TooltipManager.setTooltip(stopInternetButton, imgToolTip,
				"Stop all browsers", TooltipWay.left);
		panel.add(stopInternetButton);

		ImageIcon imgGroupwork = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/groupwork_text.png")));
		WebButton groupworkButton = new WebButton(imgGroupwork);
		groupworkButton.setRound(0);
		TooltipManager.setTooltip(groupworkButton, imgToolTip,
				"Start a group with selected students", TooltipWay.left);
		// panel.add(groupworkButton);

		ImageIcon imgBlockApplication = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/blockApplication_text.png")));
		WebButton blockApplicationButton = new WebButton(imgBlockApplication);
		blockApplicationButton.setRound(0);
		TooltipManager.setTooltip(blockApplicationButton, imgToolTip,
				"Restricts students access to specific application",
				TooltipWay.left);
		panel.add(blockApplicationButton);

		ImageIcon imgBlackout = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/blackout_text.png")));
		WebButton blackoutButton = new WebButton(imgBlackout);
		blackoutButton.setRound(0);
		TooltipManager.setTooltip(blackoutButton, imgToolTip,
				"Locks student's mouse and keyboard with blacking his screen",
				TooltipWay.left);
		// panel.add(blackoutButton);

		ImageIcon imgQuiz = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/quiz_text.png")));
		WebButton quizButton = new WebButton(imgQuiz);
		quizButton.setRound(0);
		TooltipManager.setTooltip(quizButton, imgToolTip,
				"Start quiz for the class", TooltipWay.left);
		panel.add(quizButton);

		ImageIcon imgSurvey = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/survey_text.png")));
		WebButton surveyButton = new WebButton(imgSurvey);
		surveyButton.setRound(0);
		TooltipManager.setTooltip(surveyButton, imgToolTip,
				"Start survey for the class", TooltipWay.left);
		panel.add(surveyButton);

		ImageIcon imgWhiteBoard = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/whiteboard_text.png")));
		WebButton whiteboardButton = new WebButton(imgWhiteBoard);
		whiteboardButton.setRound(0);
		TooltipManager.setTooltip(whiteboardButton, imgToolTip,
				"Start Whiteboard for the class", TooltipWay.left);
		panel.add(whiteboardButton);

		ImageIcon imgVideoPlayer = new ImageIcon(
				InstructorRight.class
						.getResource("/com/jajeem/images/Video-icon.png"));
		WebButton videoPlayerButton = new WebButton(imgVideoPlayer);
		videoPlayerButton.setRound(0);
		TooltipManager.setTooltip(videoPlayerButton, imgToolTip,
				"Start video player for the class", TooltipWay.left);
		panel.add(videoPlayerButton);

		ImageIcon imgSendWebsite = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/sendWebsite_text.png")));
		WebButton sendWebsiteButton = new WebButton(imgSendWebsite);
		sendWebsiteButton.setRound(0);
		TooltipManager.setTooltip(sendWebsiteButton, imgToolTip,
				"Send website", TooltipWay.left);
		panel.add(sendWebsiteButton);

		ImageIcon imgMessage = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/message_text.png")));
		WebButton messageButton = new WebButton(imgMessage);
		messageButton.setRound(0);
		TooltipManager.setTooltip(messageButton, imgToolTip, "Message",
				TooltipWay.left);
		panel.add(messageButton);

		ImageIcon imgPower = new ImageIcon(
				ImageIO.read(InstructorRight.class
						.getResourceAsStream("/icons/applications_style1/power_text.png")));
		final WebButton powerButton = new WebButton(imgPower);
		powerButton.setRound(0);
		TooltipManager.setTooltip(powerButton, imgToolTip,
				"Turn off, Log off, Restart student's computer",
				TooltipWay.left);
		panel.add(powerButton);

		// talk
		intercomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {

				ServerService ss = null;
				try {
					ss = new ServerService();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) InstructorCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					try {
						// "--remote-host=127.0.0.1 --remote-port-base=10000"

						if (Instructor.getTransmitter().isTransmitting()) {

							intercomButton.setIcon(imgIntercom);

							// Stop transmitting to prev student and sent stop
							// command to him
							Instructor.getTransmitter().stop();
							Instructor.getReceiver().close();
							Instructor.getTransmitter().getRemoteAddr()
									.getHostAddress();
							StopIntercomCommand si = new StopIntercomCommand(
									InetAddress.getLocalHost().getHostAddress(),
									Instructor.getTransmitter().getRemoteAddr()
											.getHostAddress(), Integer
											.parseInt(Config.getParam("port")));
							ss.send(si);

							// if selected new student, start talking to him
							if (!Instructor.getTransmitter().getRemoteAddr()
									.getHostAddress().equals(selectedStudent)) {

								intercomButton.setIcon(imgStopIntercom);

								// Start transmitting to new student
								Instructor.getTransmitter().setRemoteAddr(
										InetAddress.getByName(selectedStudent));
								Instructor.getReceiver().setRemoteAddr(
										InetAddress.getByName(selectedStudent));
								Instructor.getTransmitter().start();
								Instructor.getReceiver().initialize();
								//
							}
						} else {

							intercomButton.setIcon(imgIntercom);

							// Send start receiver to selected student and start
							// transmitter
							StartIntercomCommand si = new StartIntercomCommand(
									InetAddress.getLocalHost().getHostAddress(),
									selectedStudent, Integer.parseInt(Config
											.getParam("port")));
							ss.send(si);

							Instructor.getTransmitter().setRemoteAddr(
									InetAddress.getByName(selectedStudent));
							Instructor.getReceiver().setRemoteAddr(
									InetAddress.getByName(selectedStudent));
							Instructor.getTransmitter().start();
							Instructor.getReceiver().initialize();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					// if no students selected and is transmitting to someone
					if (Instructor.getTransmitter().isTransmitting()) {

						intercomButton.setIcon(imgIntercom);

						// Stop transmitting to prev student and sent stop
						// command to him
						Instructor.getTransmitter().stop();
						Instructor.getReceiver().close();
						StopIntercomCommand si;
						try {
							si = new StopIntercomCommand(InetAddress
									.getLocalHost().getHostAddress(),
									Instructor.getTransmitter().getRemoteAddr()
											.getHostAddress(), Integer
											.parseInt(Config.getParam("port")));
							ss.send(si);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						return;
					}
				}
			}

		});

		// blacks student's screen
		blackoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				boolean currentBalckState = false;
				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					JInternalFrame selectedFrame = InstructorCenter.desktopPane
							.getSelectedFrame();
					selectedStudent = (String) selectedFrame
							.getClientProperty("ip");
					if (selectedFrame.getClientProperty("balck") == null) {
						currentBalckState = false;
						selectedFrame.putClientProperty("black", "true");
					} else if ((boolean) selectedFrame
							.getClientProperty("balck")) {
						currentBalckState = true;
						selectedFrame.putClientProperty("black", "false");
					} else {
						currentBalckState = false;
						selectedFrame.putClientProperty("black", "true");
					}

					try {
						ServerService ss = new ServerService();
						BlackoutCommand bc = new BlackoutCommand(InetAddress
								.getLocalHost().getHostAddress(),
								selectedStudent, Integer.parseInt(Config
										.getParam("port")), currentBalckState);
						ss.send(bc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// closes all student's browsers
		stopInternetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) InstructorCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					try {
						ServerService ss = new ServerService();
						InternetCommand ic = new InternetCommand(InetAddress
								.getLocalHost().getHostAddress(),
								selectedStudent, Integer.parseInt(Config
										.getParam("port")));
						ss.send(ic);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});

		// block a student's application by adding it to microsoft's registry
		blockApplicationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
					throws NumberFormatException {
				// Enabling dialog decoration
				boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
				WebLookAndFeel.setDecorateDialogs(true);

				// Opening dialog
				Dialog dialog = new Dialog(panel, "application",
						"Application name", "Block");
				dialog.pack();
				dialog.setLocationRelativeTo(panel);
				dialog.setVisible(true);

				// Restoring frame decoration option
				WebLookAndFeel.setDecorateDialogs(decorateFrames);
			}
		});

		// send a website to student's
		sendWebsiteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
					throws NumberFormatException {
				// Enabling dialog decoration
				boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
				WebLookAndFeel.setDecorateDialogs(true);

				// Opening dialog
				Dialog dialog = new Dialog(panel, "website", "Website", "Send");
				dialog.pack();
				dialog.setLocationRelativeTo(panel);
				dialog.setVisible(true);

				// Restoring frame decoration option
				WebLookAndFeel.setDecorateDialogs(decorateFrames);
			}
		});

		quizButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				QuizMain quiz = new QuizMain();
				quiz.setVisible(true);
			}
		});

		surveyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				com.jajeem.survey.design.SurveyMain survey = new com.jajeem.survey.design.SurveyMain();
//				survey.setVisible(true);
			}
		});

		videoPlayerButton.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
//				VideoPlayer player = new VideoPlayer("", false);
			}
		});

		VNCButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) InstructorCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					jrdesktop.Config conf = null;
					try {
						conf = new jrdesktop.Config(false, "", selectedStudent,
								Integer.parseInt(Config.getParam("vncPort")),
								"admin", "admin", false, false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					VNCCaptureService vnc = new VNCCaptureService();
					vnc.startClient(conf);
				}
			}
		});

		powerButton.addActionListener(new ActionListener() {

			private PowerCommand powerCommand;
			private String selectedStudent = "";
			private ServerService serverService = new ServerService();

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				try {
					powerCommand = new PowerCommand(InetAddress.getLocalHost()
							.getHostAddress(), "", Integer.parseInt(Config
							.getParam("port")), "");
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {

					selectedStudent = (String) InstructorCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");

					powerCommand.setTo(selectedStudent);

					WebButton turnOffButton = new WebButton("Turn off");
					WebButton logOffButton = new WebButton("Log off");
					WebButton restartButton = new WebButton("Restart");

					WebButtonGroup buttonGroup = new WebButtonGroup(true,
							turnOffButton, logOffButton, restartButton);
					buttonGroup.setButtonsDrawFocus(false);

					popup.setPopupStyle(PopupStyle.lightSmall);
					popup.setMargin(5);
					popup.add(buttonGroup);
					popup.setRound(0);
					if (popup.isShowing()) {
						popup.hidePopup();
					} else {
						popup.showPopup(powerButton);
					}

					turnOffButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							popup.hidePopup();
							powerCommand.setType("turnOff");
							serverService.send(powerCommand);
						}
					});

					logOffButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							popup.hidePopup();
							powerCommand.setType("logOff");
							serverService.send(powerCommand);
						}
					});

					restartButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							popup.hidePopup();
							powerCommand.setType("restart");
							serverService.send(powerCommand);
						}
					});
				}
			}
		});

		whiteboardButton.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// WebFileChooser file = new WebFileChooser(new
				// java.awt.Frame());
				// file.showDialog();
//				WhiteboardClient whiteboard = new WhiteboardClient();
				// whiteboard.main(null);
			}
		});

		messageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) InstructorCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					try {
						MessageSend.main(new String[] { selectedStudent,
								Config.getParam("port") });
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

				}

			}
		});

		chatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.CENTER);

		panel2.setUndecorated(true);
		panel2.setOpaque(true);
		panel.setOpaque(true);

		return panel2;
	}

	private static class Dialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4762888417428594490L;

		public Dialog(WebPanel owner, String type, String labelText,
				String buttonText) {
			super(owner);
			// setIconImages(WebLookAndFeel.getImages());
			setRound(0);
			setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setModal(true);

			TableLayout layout = new TableLayout(new double[][] {
					{ TableLayout.PREFERRED, TableLayout.FILL },
					{ TableLayout.PREFERRED, TableLayout.PREFERRED,
							TableLayout.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(new WebLabel(labelText, WebLabel.TRAILING), "0,0");
			final WebTextField text = new WebTextField(15);
			content.add(text, "1,0");

			WebButton ok = new WebButton(buttonText);
			WebButton cancel = new WebButton("Cancel");
			if (type == "application") {
				WebButton unBlock = new WebButton("Unblock");
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorCenter.desktopPane
									.getSelectedFrame().getClientProperty("ip");
							try {
								ServerService ss = new ServerService();
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText(), true);
								ss.send(ic);
							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}

					}
				};

				ActionListener listenerUnblock = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorCenter.desktopPane
									.getSelectedFrame().getClientProperty("ip");
							try {
								ServerService ss = new ServerService();
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText(), false);
								ss.send(ic);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					}
				};
				unBlock.addActionListener(listenerUnblock);
				ok.addActionListener(listener);
				content.add(new CenterPanel(new GroupPanel(5, ok, unBlock,
						cancel)), "0,2,1,2");
			} else if (type == "website") {
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						if (InstructorCenter.desktopPane.getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorCenter.desktopPane
									.getSelectedFrame().getClientProperty("ip");
							try {
								ServerService ss = new ServerService();
								WebsiteCommand wc = new WebsiteCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText());
								ss.send(wc);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					}
				};
				ok.addActionListener(listener);
				content.add(new CenterPanel(new GroupPanel(5, ok, cancel)),
						"0,2,1,2");
			}

			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});

			SwingUtils.equalizeComponentsWidths(ok, cancel);

			add(content);

			HotkeyManager.registerHotkey(this, ok, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, ok, Hotkey.ENTER);
		}
	}

}