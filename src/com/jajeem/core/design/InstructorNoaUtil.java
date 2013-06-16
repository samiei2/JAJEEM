package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import jrdesktop.viewer.Viewer;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.WebPopup;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.command.service.ServerServiceTimer;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.design.QuizMain;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.videoplayer.design.VideoPlayer;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;

public class InstructorNoaUtil {

	private static ServerServiceTimer serverServiceTimer;
	final static WebPopup popup = new WebPopup();

	/*
	 * ***************** Right Panel Events **************************
	 */
	Component intl;
	public void addEventsRightPanel(final WebPanel rightButtonPanel) {

		String key = "";
		
		for (Component c : rightButtonPanel.getComponents()) {
			intl = c;
			if (c instanceof JButton) {

				key = (String) ((JButton) c).getClientProperty("key");

				key = (String) ((JButton) c).getClientProperty("key");
				if (key == null) {
					return;
				}

				switch (key) {

				case "monitor":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0)
								throws NumberFormatException {
							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								String selectedStudent = "";
								selectedStudent = (String) InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip");
								jrdesktop.Config conf = null;
								System.out.println(selectedStudent);
								try {
									conf = new jrdesktop.Config(false, "",
											selectedStudent,
											Integer.parseInt(Config
													.getParam("vncPort")),
											"admin", "admin", false, false);
								} catch (Exception e) {
									e.printStackTrace();
								}
								VNCCaptureService vnc = new VNCCaptureService();
								vnc.startClient(conf);
							}
						}
					});

					break;
				case "intercom":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0)
								throws NumberFormatException {

							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								String selectedStudent = "";
								selectedStudent = (String) InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip");
								try {
									// "--remote-host=127.0.0.1 --remote-port-base=10000"

									if (InstructorNoa.getTransmitter()
											.isTransmitting()) {

										// Stop transmitting to prev student and
										// sent stop
										// command to him
										InstructorNoa.getTransmitter().stop();
										InstructorNoa.getReceiver().close();
										InstructorNoa.getTransmitter()
												.getRemoteAddr()
												.getHostAddress();
										StopIntercomCommand si = new StopIntercomCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												InstructorNoa.getTransmitter()
														.getRemoteAddr()
														.getHostAddress(),
												Integer.parseInt(Config
														.getParam("port")));
										InstructorNoa.getServerService().send(
												si);

										// if selected new student, start
										// talking to him
										if (!InstructorNoa.getTransmitter()
												.getRemoteAddr()
												.getHostAddress()
												.equals(selectedStudent)) {

											// Start transmitting to new student
											InstructorNoa
													.getTransmitter()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa
													.getReceiver()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa.getTransmitter()
													.start();
											InstructorNoa.getReceiver()
													.initialize();
											//
										}
									} else {

										// Send start receiver to selected
										// student and start
										// transmitter
										StartIntercomCommand si = new StartIntercomCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												selectedStudent,
												Integer.parseInt(Config
														.getParam("port")));
										InstructorNoa.getServerService().send(
												si);

										InstructorNoa
												.getTransmitter()
												.setRemoteAddr(
														InetAddress
																.getByName(selectedStudent));
										InstructorNoa
												.getReceiver()
												.setRemoteAddr(
														InetAddress
																.getByName(selectedStudent));
										InstructorNoa.getTransmitter().start();
										InstructorNoa.getReceiver()
												.initialize();
									}

								} catch (Exception e) {
									e.printStackTrace();
								}

							} else {
								// if no students selected and is transmitting
								// to someone
								if (InstructorNoa.getTransmitter()
										.isTransmitting()) {

									// Stop transmitting to prev student and
									// sent stop
									// command to him
									InstructorNoa.getTransmitter().stop();
									InstructorNoa.getReceiver().close();
									StopIntercomCommand si;
									try {
										si = new StopIntercomCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												Instructor.getTransmitter()
														.getRemoteAddr()
														.getHostAddress(),
												Integer.parseInt(Config
														.getParam("port")));
										InstructorNoa.getServerService().send(
												si);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									return;
								}
							}
						}

					});

					break;
				case "group":

					break;
				case "model":

					break;
				case "record":
					((JButton) c).addActionListener(new ActionListener() {
						
						@Override
						//recorder action
						public void actionPerformed(ActionEvent arg0) {
							// Enabling dialog decoration
							boolean decorateFrames = WebLookAndFeel
									.isDecorateDialogs();
							WebLookAndFeel.setDecorateDialogs(true);
							
							Recorder recorder = new Recorder();
							recorder.setLocationRelativeTo(rightButtonPanel);
							recorder.setVisible(true);
							
							// Restoring frame decoration option
							WebLookAndFeel.setDecorateDialogs(decorateFrames);
						}
					});
					
					break;
				case "speech":

					break;
				case "file":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							VideoPlayer player= new VideoPlayer("", false);
						}
					});

					break;
				case "quiz":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							QuizMain quiz = new QuizMain();
							quiz.setVisible(true);
						}
					});
					break;
				case "report":

					break;
				case "account":

					break;
				case "chat":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								String selectedStudent = "";
								selectedStudent = (String) InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip");
								try {
									Chat currentChat = new Chat(
											selectedStudent, Integer
													.parseInt(Config
															.getParam("port")));
									InstructorNoa.getChatList()
											.add(currentChat);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								return;
							}
						}
					});

					break;

				default:
					break;
				}
			}
		}
	}
	/*
	 * ***************** Bottom Panel Events **************************
	 */

	public void addEventsBottomPanel(final WebPanel bottomButtonPanel) {
		String key = "";

		for (Component c : bottomButtonPanel.getComponents()) {
			if (c instanceof JButton) {

				final JButton button = ((JButton) c);

				key = (String) ((JButton) c).getClientProperty("key");
				if (key == null) {
					return;
				}

				switch (key) {

				case "survey":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							com.jajeem.survey.design.SurveyMain survey = new com.jajeem.survey.design.SurveyMain();
							survey.setVisible(true);
						}
					});

					break;

				case "power":
					try {

						button.addActionListener(new ActionListener() {

							private PowerCommand powerCommand;

							@Override
							public void actionPerformed(ActionEvent arg0)
									throws NumberFormatException {
								try {
									powerCommand = new PowerCommand(InetAddress
											.getLocalHost().getHostAddress(),
											"", Integer.parseInt(Config
													.getParam("port")), "");
								} catch (Exception e) {
									e.printStackTrace();
								}

								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null) {
									String selectedStudent = "";
									selectedStudent = (String) InstructorNoa
											.getDesktopPane()
											.getClientProperty("ip");

									powerCommand.setTo(selectedStudent);

									WebButton turnOffButton = new WebButton(
											"Turn off");
									WebButton logOffButton = new WebButton(
											"Log off");
									WebButton restartButton = new WebButton(
											"Restart");

									WebButtonGroup buttonGroup = new WebButtonGroup(
											true, turnOffButton, logOffButton,
											restartButton);
									buttonGroup.setButtonsDrawFocus(false);

									popup.setPopupStyle(PopupStyle.lightSmall);
									popup.setMargin(5);
									popup.add(buttonGroup);
									popup.setRound(0);
									if (popup.isShowing()) {
										popup.hidePopup();
									} else {
										popup.showPopup(button);
									}

									turnOffButton
											.addActionListener(new ActionListener() {
												@Override
												public void actionPerformed(
														ActionEvent arg0) {
													popup.hidePopup();
													powerCommand
															.setType("turnOff");
													InstructorNoa
															.getServerService()
															.send(powerCommand);
												}
											});

									logOffButton
											.addActionListener(new ActionListener() {
												@Override
												public void actionPerformed(
														ActionEvent arg0) {
													popup.hidePopup();
													powerCommand
															.setType("logOff");
													InstructorNoa
															.getServerService()
															.send(powerCommand);
												}
											});

									restartButton
											.addActionListener(new ActionListener() {
												@Override
												public void actionPerformed(
														ActionEvent arg0) {
													popup.hidePopup();
													powerCommand
															.setType("restart");
													InstructorNoa
															.getServerService()
															.send(powerCommand);
												}
											});
								}
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case "whiteBoard":
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							new WhiteboardClient();
						}
					});
					break;
				case "internet":
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0)
								throws NumberFormatException {
							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								String selectedStudent = "";
								selectedStudent = (String) InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip");
								try {

									InternetCommand ic = new InternetCommand(
											InetAddress.getLocalHost()
													.getHostAddress(),
											selectedStudent, Integer
													.parseInt(Config
															.getParam("port")));
									InstructorNoa.getServerService().send(ic);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					});
					break;
				case "programStop":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
								throws NumberFormatException {
							// Enabling dialog decoration
							boolean decorateFrames = WebLookAndFeel
									.isDecorateDialogs();
							WebLookAndFeel.setDecorateDialogs(true);

							// Opening dialog
							Dialog dialog = new Dialog(bottomButtonPanel,
									"application", "Application name", "Block");
							dialog.pack();
							dialog.setLocationRelativeTo(bottomButtonPanel);
							dialog.setVisible(true);

							// Restoring frame decoration option
							WebLookAndFeel.setDecorateDialogs(decorateFrames);
						}
					});
					break;
				case "programStart":
					break;

				default:
					break;
				}
			}
		}

	}

	/**
	 * Adds a new internal frame to desktop panel for a new student
	 * 
	 * @param desktopPane
	 *            WebDesktopPane
	 * @param hostIp
	 *            String
	 * @param hostName
	 *            String
	 */
	public static WebInternalFrame createFrame(
			final WebDesktopPane desktopPane, String hostIp, String hostName)
			throws NumberFormatException, Exception {
		final WebInternalFrame internalFrame = new WebInternalFrame(hostName,
				false, false, false, true);

		jrdesktop.Config con = new jrdesktop.Config(false, "", hostIp,
				Integer.parseInt(Config.getParam("vncPort")), "admin", "admin",
				false, false);
		final Viewer vnc = new Viewer(con);

		// get current list of students, if some one is new, add him/her
		JInternalFrame[] frames = desktopPane.getAllFrames();
		List<String> listOfStudents = new ArrayList<String>();
		for (JInternalFrame frame : frames) {
			listOfStudents.add((String) frame.getClientProperty("ip"));
		}

		if (listOfStudents.contains(hostIp)) {
			return null;
		}

		internalFrame.putClientProperty("ip", hostIp);
		internalFrame.putClientProperty("username", hostName);

		internalFrame.setFrameIcon(new ImageIcon(ImageIO
				.read(InstructorNoaUtil.class
						.getResourceAsStream("/icons/menubar/student.png"))));
		vnc.StartThumbs(internalFrame);

		internalFrame.open();
		desktopPane.add(internalFrame);

		internalFrame.setBounds(0 + (desktopPane.getComponentCount() * 200), 0,
				200, 200);

		final WebToolBar toolBar = new WebToolBar();
		toolBar.setRound(0);
		toolBar.setFloatable(false);
		WebPanel panel = new WebPanel();
		// panel.add(toolBar);

		internalFrame.getContentPane().add(panel, BorderLayout.SOUTH);

		final WebCheckBox checkBox = new WebCheckBox("");
		checkBox.setRolloverDarkBorderOnly(true);
		checkBox.setRound(0);
		checkBox.setShadeWidth(0);
		toolBar.add(checkBox);
		internalFrame.revalidate();
		toolBar.setVisible(true);
		panel.setVisible(true);

		internalFrame.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorNoaUtil.class
									.getResourceAsStream("/icons/menubar/student.png"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkBox.setSelected(false);
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorNoaUtil.class
									.getResourceAsStream("/icons/menubar/check.png"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkBox.setSelected(true);
			}
		});

		return internalFrame;
	}

	/**
	 * Initializes network, broadcasting an start up command to network
	 */
	public static void networkSetup() throws NumberFormatException, Exception {
		// setup a broadcasting service, to find all clients around
		serverServiceTimer = new ServerServiceTimer();
		int port = Integer.parseInt(Config.getParam("startUpPort"));
		String broadcastingIp = Config.getParam("broadcastingIp");

		StartUpCommand cmd = new StartUpCommand(InetAddress.getLocalHost()
				.getHostAddress(), broadcastingIp, port, InetAddress
				.getLocalHost().getHostAddress(),
				System.getProperty("user.name"));
		serverServiceTimer.setCmd(cmd);
		serverServiceTimer.setInterval(5000);
		serverServiceTimer.start();

		// setup a service for sending commands to others
		InstructorNoa.setServerService(new ServerService());

		// setup a service to listen to clients commands
		ClientService clientService = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("serverPort")));
		clientService.start();
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
						if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorNoa
									.getDesktopPane().getSelectedFrame()
									.getClientProperty("ip");
							try {

								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText(), true);
								InstructorNoa.getServerService().send(ic);
							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}

					}
				};

				ActionListener listenerUnblock = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorNoa
									.getDesktopPane().getSelectedFrame()
									.getClientProperty("ip");
							try {
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText(), false);
								InstructorNoa.getServerService().send(ic);
							} catch (Exception e1) {
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
						if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
							String selectedStudent = "";
							selectedStudent = (String) InstructorNoa
									.getDesktopPane().getSelectedFrame()
									.getClientProperty("ip");
							try {
								WebsiteCommand wc = new WebsiteCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent,
										Integer.parseInt(Config
												.getParam("port")),
										text.getText());
								InstructorNoa.getServerService().send(wc);
							} catch (Exception e1) {
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
