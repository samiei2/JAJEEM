package com.jajeem.core.design;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;

import jrdesktop.viewer.Viewer;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.list.WebList;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.slider.WebSlider;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.alee.managers.popup.WebPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.command.service.ServerServiceTimer;
import com.jajeem.filemanager.design.FileManagerMain;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.design.alt.Quiz_Main;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.survey.design.alt.Survey_Main;
import com.jajeem.util.Config;
import com.jajeem.util.FileUtil;
import com.jajeem.util.Session;
import com.jajeem.util.WinRegistry;

public class InstructorNoaUtil {

	private static ServerServiceTimer serverServiceTimer;
	final static WebPopup popup = new WebPopup();

	static Quiz_Main quiz = null;
	static Survey_Main survey = null;
	static Quiz_Main[] groupsQuizWindows = new Quiz_Main[10];
	static Survey_Main[] groupsSurveyWindows = new Survey_Main[10];

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

									// if some one is selected and we were
									// talking to him,
									// stop transmitting and sent a stop command
									// to him
									if (InstructorNoa.getTransmitter()
											.getRemoteAddr().getHostAddress()
											.equals(selectedStudent)) {
										if (InstructorNoa.getTransmitter()
												.isTransmitting()) {
											InstructorNoa.getTransmitter()
													.stop();
											StopIntercomCommand si;
											try {
												si = new StopIntercomCommand(
														InetAddress
																.getLocalHost()
																.getHostAddress(),
														InstructorNoa
																.getTransmitter()
																.getRemoteAddr()
																.getHostAddress(),
														Integer.parseInt(Config
																.getParam("port")));
												InstructorNoa
														.getServerService()
														.send(si);

												InstructorNoa
														.setIntercomText("Intercom");
											} catch (Exception e) {
												e.printStackTrace();
											}
										} else {
											StartIntercomCommand si = new StartIntercomCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													selectedStudent,
													Integer.parseInt(Config
															.getParam("port")));
											InstructorNoa.getServerService()
													.send(si);

											InstructorNoa
													.getTransmitter()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa.getTransmitter()
													.start();
											InstructorNoa
													.setIntercomText("Stop");
										}

									} else {
										if (InstructorNoa.getTransmitter()
												.isTransmitting()) {
											InstructorNoa.getTransmitter()
													.stop();
											StopIntercomCommand si;
											si = new StopIntercomCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													InstructorNoa
															.getTransmitter()
															.getRemoteAddr()
															.getHostAddress(),
													Integer.parseInt(Config
															.getParam("port")));
											InstructorNoa.getServerService()
													.send(si);
											StartIntercomCommand startI = new StartIntercomCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													selectedStudent,
													Integer.parseInt(Config
															.getParam("port")));
											InstructorNoa.getServerService()
													.send(startI);

											InstructorNoa
													.getTransmitter()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa.getTransmitter()
													.start();
											InstructorNoa
													.setIntercomText("Stop");

										} else {
											// Send start receiver to selected
											// student and start transmitter
											StartIntercomCommand si = new StartIntercomCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													selectedStudent,
													Integer.parseInt(Config
															.getParam("port")));
											InstructorNoa.getServerService()
													.send(si);

											InstructorNoa
													.getTransmitter()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa.getTransmitter()
													.start();
											InstructorNoa
													.setIntercomText("Stop");
										}
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
									StopIntercomCommand si;
									try {
										si = new StopIntercomCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												InstructorNoa.getTransmitter()
														.getRemoteAddr()
														.getHostAddress(),
												Integer.parseInt(Config
														.getParam("port")));
										InstructorNoa.getServerService().send(
												si);

										InstructorNoa
												.setIntercomText("Intercom");
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									InstructorNoa.setIntercomText("Intercom");
									return;
								}
							}
						}

					});

					break;
				case "group":
					((JButton) c).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								comp.setVisible(false);
							}
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (((JComponent) comp).getClientProperty(
										"viewMode").equals("groupView")) {
									comp.setVisible(true);
								}
							}
						}
					});

					break;
				case "model":
					((JButton) c).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("thumbView")) {

								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null) {
									String selectedStudent = "";
									selectedStudent = (String) InstructorNoa
											.getDesktopPane()
											.getSelectedFrame()
											.getClientProperty("ip");
									try {
										StartModelCommand sm = new StartModelCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												Config.getParam("broadcastingIp"),
												Integer.parseInt(Config
														.getParam("port")),
												selectedStudent);
										InstructorNoa.getServerService().send(
												sm);

										if (InstructorNoa.getReceiverOnly() == null) {
											AVReceiveOnly ar = new AVReceiveOnly(
													"10010", selectedStudent,
													"5010");
											ar.initialize();
											InstructorNoa.setReceiverOnly(ar);
										} else {
											InstructorNoa
													.getReceiverOnly()
													.setRemoteAddr(
															InetAddress
																	.getByName(selectedStudent));
											InstructorNoa.getReceiverOnly()
													.initialize();
										}

										jrdesktop.Config conf = null;
										conf = new jrdesktop.Config(false, "",
												selectedStudent,
												Integer.parseInt(Config
														.getParam("vncPort")),
												"admin", "admin", false, false);

										VNCCaptureService vnc = new VNCCaptureService();
										vnc.startClient(conf);

									} catch (Exception e) {
										e.printStackTrace();
									}

								} else {
									return;
								}
							} else if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
							}
						}
					});
					break;
				case "record":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						// recorder action
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
							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								if (!InstructorNoa.getGroupList()
										.isSelectionEmpty()) {
									int groupIndex = InstructorNoa
											.getGroupList().getSelectedIndex();

									Group group = InstructorNoa.getGroups()
											.get(groupIndex);
									if (group.getStudentIps().isEmpty()) {
										return;
									} else {
										try {
											FileManagerMain main = new FileManagerMain();
											main.setReceivingIps(new ArrayList<>(group.getStudentIps()));
											main.setVisible(true);
										} catch (Exception e) {
										}
									}
								}
							} else {
								FileManagerMain main = new FileManagerMain();
								main.setReceivingIps(InstructorNoa.getAllStudentIPs());
								main.setVisible(true);
							}
						}
					});

					break;
				case "quiz":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								if (!InstructorNoa.getGroupList()
										.isSelectionEmpty()) {
									int groupIndex = InstructorNoa
											.getGroupList().getSelectedIndex();

									Group group = InstructorNoa.getGroups()
											.get(groupIndex);
									if (group.getStudentIps().isEmpty()) {
										return;
									} else {
										try {
											if (Session.getIsQuizWindowsOpen()[groupIndex] == true) {
												if (groupsQuizWindows[groupIndex] == null) {
													groupsQuizWindows[groupIndex] = new Quiz_Main(
															groupIndex,
															group.getStudentIps());
													groupsQuizWindows[groupIndex]
															.setVisible(true);
												} else {
													groupsQuizWindows[groupIndex]
															.toFront();
													groupsQuizWindows[groupIndex]
															.repaint();
												}
											} else {
												groupsQuizWindows[groupIndex] = new Quiz_Main(
														groupIndex,
														group.getStudentIps());
												groupsQuizWindows[groupIndex]
														.setVisible(true);
											}

										} catch (Exception e) {
										}
									}
								}
							} else {
								if (Session.isQuizWindowOpen()) {
									if (quiz == null) {
										quiz = new Quiz_Main(-1, null);
										quiz.setVisible(true);
									} else {
										quiz.toFront();
										quiz.repaint();
									}
								} else {
									quiz = new Quiz_Main(-1, null);
									quiz.setVisible(true);
								}
							}
						}
					});
					break;
				case "movieplayer":
					((JButton) c).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// Run a java app in a separate system process
							try {
								final Process proc;
								System.out.println(new File("util/",
										"videoplayer.jar").exists());
								proc = Runtime.getRuntime().exec(
										"java -jar videoplayer.jar", null,
										new File("util/"));
								// Then retrieve the process output
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										try {
											BufferedReader in = new BufferedReader(  
						                            new InputStreamReader(proc.getInputStream()));  
										        String line = null;  
										        while ((line = in.readLine()) != null) {  
										            System.out.println(line);  
										        }  
										} catch (Exception e) {
										}
									}
								}).start();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					});
					break;
				case "report":

					break;
				case "account":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							
						}
					});
					break;
				case "chat":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("thumbView")) {

								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null) {
									String selectedStudent = "";
									selectedStudent = (String) InstructorNoa
											.getDesktopPane()
											.getSelectedFrame()
											.getClientProperty("ip");
									Chat currentChat = null;
									if (!InstructorNoa.getChatList().isEmpty()) {
										for (Chat chat : InstructorNoa
												.getChatList()) {
											if (chat.getTo().equals(
													selectedStudent)) {
												currentChat = chat;
												currentChat.setVisible(true);
												break;
											}
										}
										if (currentChat == null) {
											try {
												currentChat = new Chat(
														selectedStudent,
														Integer.parseInt(Config
																.getParam("port")),
														false, -1,
														selectedStudent);
												InstructorNoa.getChatList()
														.add(currentChat);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									} else {
										if (currentChat == null) {
											try {
												currentChat = new Chat(
														selectedStudent,
														Integer.parseInt(Config
																.getParam("port")),
														false, -1,
														selectedStudent);
												InstructorNoa.getChatList()
														.add(currentChat);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							} else if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								if (!InstructorNoa.getGroupList()
										.isSelectionEmpty()) {
									int groupIndex = InstructorNoa
											.getGroupList().getSelectedIndex();

									Group group = InstructorNoa.getGroups()
											.get(groupIndex);
									if (group.getStudentIps().isEmpty()) {
										return;
									} else {
										try {
											Chat currentChat = null;
											if (!InstructorNoa.getChatList()
													.isEmpty()) {
												for (Chat chat : InstructorNoa
														.getChatList()) {
													if (chat.getTo()
															.equals(String
																	.valueOf(groupIndex))) {
														currentChat = chat;
														currentChat
																.setVisible(true);
														break;
													}
												}
												if (currentChat == null) {
													try {
														currentChat = new Chat(
																"",
																Integer.parseInt(Config
																		.getParam("port")),
																true,
																groupIndex,
																group.getName());
														currentChat.setTo(String
																.valueOf(groupIndex));
														InstructorNoa
																.getChatList()
																.add(currentChat);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											} else {
												if (currentChat == null) {
													try {
														currentChat = new Chat(
																"",
																Integer.parseInt(Config
																		.getParam("port")),
																true,
																groupIndex,
																group.getName());
														currentChat.setTo(String
																.valueOf(groupIndex));
														InstructorNoa
																.getChatList()
																.add(currentChat);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
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

	public void addEventsBottomPanel(final WebPanel bottomButtonPanel,
			final WebFrame mainFrame) throws IOException {
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

							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								if (!InstructorNoa.getGroupList()
										.isSelectionEmpty()) {
									int groupIndex = InstructorNoa
											.getGroupList().getSelectedIndex();

									Group group = InstructorNoa.getGroups()
											.get(groupIndex);
									if (group.getStudentIps().isEmpty()) {
										return;
									} else {
										try {
											if (Session
													.getIsSurveyWindowsOpen()[groupIndex] == true) {
												if (groupsSurveyWindows[groupIndex] == null) {
													groupsSurveyWindows[groupIndex] = new Survey_Main(
															groupIndex,
															group.getStudentIps());
													groupsSurveyWindows[groupIndex]
															.setVisible(true);
												} else {
													groupsSurveyWindows[groupIndex]
															.toFront();
													groupsSurveyWindows[groupIndex]
															.repaint();
												}
											} else {
												groupsSurveyWindows[groupIndex] = new Survey_Main(
														groupIndex,
														group.getStudentIps());
												groupsSurveyWindows[groupIndex]
														.setVisible(true);
											}

										} catch (Exception e) {
										}
									}
								}
							} else {
								if (Session.isSurveyWindowOpen()) {
									if (survey == null) {
										survey = new Survey_Main(-1, null);
										survey.setVisible(true);
									} else {
										survey.toFront();
										survey.repaint();
									}
								} else {
									survey = new Survey_Main(-1, null);
									survey.setVisible(true);
								}
							}
						}
					});

					break;

				case "power":
					// in instructor noa!
					break;

				case "whiteBoard":
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {

							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								if (!InstructorNoa.getGroupList()
										.isSelectionEmpty()) {
									int groupIndex = InstructorNoa
											.getGroupList().getSelectedIndex();
									// Starting Whiteboard Server
									String SessionPort = String
											.valueOf(2000 + (groupIndex * 2));
									String WhiteboardPort = String
											.valueOf(2000 + (groupIndex * 2 + 1));
									try {
										final Process proc = Runtime
												.getRuntime()
												.exec("java -jar WhiteboardServer.jar "
														+ SessionPort
														+ " "
														+ WhiteboardPort, null,
														new File("util/"));
										new Thread(new Runnable() {

											@Override
											public void run() {
												try {
													BufferedReader in = new BufferedReader(
															new InputStreamReader(
																	proc.getInputStream()));
													String line = null;
													while ((line = in
															.readLine()) != null) {
														System.out
																.println(line);
													}
												} catch (Exception e) {
													// TODO: handle exception
												}
											}
										}).start();
									} catch (IOException e) {
										e.printStackTrace();
									}

									Group group = InstructorNoa.getGroups()
											.get(groupIndex);
									if (group.getStudentIps().isEmpty()) {
										return;
									} else {
										try {
											// Start Teacher Whiteboard
											try {
												final Process proc = Runtime
														.getRuntime()
														.exec("java -jar WhiteboardTeacher.jar "
																+ SessionPort
																+ " "
																+ WhiteboardPort,
																null,
																new File(
																		"util/"));
												new Thread(new Runnable() {

													@Override
													public void run() {
														try {
															BufferedReader in = new BufferedReader(
																	new InputStreamReader(
																			proc.getInputStream()));
															String line = null;
															while ((line = in
																	.readLine()) != null) {
																System.out
																		.println(line);
															}
														} catch (Exception e) {
														}
													}
												}).start();
											} catch (IOException e) {
												e.printStackTrace();
											}

											StartWhiteBoardCommand vc = new StartWhiteBoardCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													"", Integer.parseInt(Config
															.getParam("port")));

											vc.setSessionPort(SessionPort);
											vc.setWhiteboardPort(WhiteboardPort);

											for (String studentIp : group
													.getStudentIps()) {
												vc.setTo(studentIp);
												InstructorNoa
														.getServerService()
														.send(vc);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
							else{
								try {
									final Process proc = Runtime
											.getRuntime()
											.exec("java -jar WhiteboardServer.jar "
													+ "1998"
													+ " "
													+ "1999", null,
													new File("util/"));
									new Thread(new Runnable() {

										@Override
										public void run() {
											try {
												BufferedReader in = new BufferedReader(
														new InputStreamReader(
																proc.getInputStream()));
												String line = null;
												while ((line = in
														.readLine()) != null) {
													System.out
															.println(line);
												}
											} catch (Exception e) {
												// TODO: handle exception
											}
										}
									}).start();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
								// Start Teacher Whiteboard
								try {
									final Process proc = Runtime
											.getRuntime()
											.exec("java -jar WhiteboardTeacher.jar "
													+ "1998"
													+ " "
													+ "1999",
													null,
													new File(
															"util/"));
									new Thread(new Runnable() {

										@Override
										public void run() {
											try {
												BufferedReader in = new BufferedReader(
														new InputStreamReader(
																proc.getInputStream()));
												String line = null;
												while ((line = in
														.readLine()) != null) {
													System.out
															.println(line);
												}
											} catch (Exception e) {
											}
										}
									}).start();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
								StartWhiteBoardCommand vc;
								try {
									new Config();
									vc = new StartWhiteBoardCommand(
											InetAddress.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"), Integer.parseInt(Config
													.getParam("port")));
									vc.setSessionPort("1998");
									vc.setWhiteboardPort("1999");
									InstructorNoa.getServerService().send(vc);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								
							}

							// if (Session.isWhiteboardWindowOpen()) {
							// if (client == null) {
							// client = new WhiteboardClient();
							// } else {
							// client.toFront();
							// client.repaint();
							// }
							// } else {
							// client = new WhiteboardClient();
							// }

						}
					});
					break;
				case "internet":
					// in instructor noa!
					break;

				case "program":
					// in instructor noa!
					break;
				case "programStart":
					try {
						String pathToStartMenu = WinRegistry
								.readString(
										WinRegistry.HKEY_LOCAL_MACHINE,
										"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
										"Common Start Menu")
								+ "\\Programs";

						List<String> fileListModel = new ArrayList<String>();

						FileUtil fileUtil = new FileUtil();
						final File[] fileList = fileUtil
								.finder(pathToStartMenu);
						for (int i = 0; i < fileList.length; i++) {
							File file = fileList[i];
							if (file.getName().indexOf(".") != -1) {
								String extension = file.getName().substring(
										file.getName().indexOf("."));
								if (extension.equals(".lnk")) {
									fileListModel.add(file.getName().substring(
											0, file.getName().length() - 4));
								}
							}
						}

						WebList programsList = new WebList(
								fileListModel.toArray());
						programsList.setVisibleRowCount(6);
						programsList.setSelectedIndex(0);
						programsList.setEditable(false);

						WebButtonPopup programPopupButton = new WebButtonPopup(
								(WebButton) button, PopupWay.upCenter);
						GroupPanel programPopupContent = new GroupPanel(5,
								false, new WebScrollPane(programsList));
						programPopupContent.setMargin(15);
						programPopupContent.setOpaque(false);
						programsList.setOpaque(false);

						programPopupButton.setContent(programPopupContent);

						programsList.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								WebList list = (WebList) evt.getSource();
								if (evt.getClickCount() == 2) {
									int index = list.locationToIndex(evt
											.getPoint());
									for (int i = 0; i < fileList.length; i++) {
										File file = fileList[i];
										if (file.getName().indexOf(".") != -1) {
											if (file.getName()
													.substring(
															0,
															file.getName()
																	.length() - 4)
													.equals(list
															.getModel()
															.getElementAt(index))) {

												Component card = null;
												for (Component comp : InstructorNoa
														.getCenterPanel()
														.getComponents()) {
													if (comp.isVisible() == true) {
														card = comp;
													}
												}

												if (((JComponent) card)
														.getClientProperty(
																"viewMode")
														.equals("thumbView")) {
													if (InstructorNoa
															.getDesktopPane()
															.getSelectedFrame() != null) {
														String selectedStudent = "";
														selectedStudent = (String) InstructorNoa
																.getDesktopPane()
																.getSelectedFrame()
																.getClientProperty(
																		"ip");

														StartApplicationCommand sa;
														try {
															sa = new StartApplicationCommand(
																	InetAddress
																			.getLocalHost()
																			.getHostAddress(),
																	selectedStudent,
																	Integer.parseInt(Config
																			.getParam("port")),
																	file.getName()
																			.substring(
																					0,
																					file.getName()
																							.length() - 4));
															InstructorNoa
																	.getServerService()
																	.send(sa);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
												} else if (((JComponent) card)
														.getClientProperty(
																"viewMode")
														.equals("groupView")) {
													if (!InstructorNoa
															.getGroupList()
															.isSelectionEmpty()) {
														int groupIndex = InstructorNoa
																.getGroupList()
																.getSelectedIndex();

														Group group = InstructorNoa
																.getGroups()
																.get(groupIndex);
														if (group
																.getStudentIps()
																.isEmpty()) {
															return;
														} else {
															
															StartApplicationCommand sa;
															try {
																sa = new StartApplicationCommand(
																		InetAddress
																				.getLocalHost()
																				.getHostAddress(),
																		"",
																		Integer.parseInt(Config
																				.getParam("port")),
																		file.getName()
																				.substring(
																						0,
																						file.getName()
																								.length() - 4));
																for (String studentIp : group
																		.getStudentIps()) {
																	sa.setTo(studentIp);
																	InstructorNoa
																	.getServerService()
																	.send(sa);
																}
																
															} catch (Exception e) {
																e.printStackTrace();
															}
															
														}
													}
												}
											}

										}
									}
								}
							}
						});

					} catch (IllegalArgumentException | IllegalAccessException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
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

	public void addEventsTopPanel(final WebPanel bottomButtonPanel) {
		String key = "";

		for (Component c : bottomButtonPanel.getComponents()) {
			if (c instanceof JButton) {

				final JButton button = ((JButton) c);

				key = (String) ((JButton) c).getClientProperty("key");
				if (key == null) {
					return;
				}

				switch (key) {

				case "volume":
					final WebSlider slider1 = new WebSlider(
							WebSlider.HORIZONTAL);
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							slider1.setMinimum(0);
							slider1.setMaximum(100);
							slider1.setMinorTickSpacing(10);
							slider1.setMajorTickSpacing(50);
							slider1.setPaintTicks(false);
							slider1.setPaintLabels(false);

							popup.setPopupStyle(PopupStyle.lightSmall);
							popup.setMargin(0);
							popup.add(slider1);
							popup.setRound(0);

							int vol = slider1.getValue();

							Component card = null;
							for (Component comp : InstructorNoa
									.getCenterPanel().getComponents()) {
								if (comp.isVisible() == true) {
									card = comp;
								}
							}

							if (popup.isShowing()) {
								popup.hidePopup();

								if (((JComponent) card).getClientProperty(
										"viewMode").equals("thumbView")) {
									if (InstructorNoa.getDesktopPane()
											.getSelectedFrame() != null) {
										String selectedStudent = "";
										selectedStudent = (String) InstructorNoa
												.getDesktopPane()
												.getSelectedFrame()
												.getClientProperty("ip");
										try {

											VolumeCommand vc = new VolumeCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													selectedStudent,
													Integer.parseInt(Config
															.getParam("port")),
													"set", vol * 650);
											InstructorNoa.getServerService()
													.send(vc);
										} catch (Exception e) {
											e.printStackTrace();
										}

									}
								} else if (((JComponent) card)
										.getClientProperty("viewMode").equals(
												"groupView")) {
									if (!InstructorNoa.getGroupList()
											.isSelectionEmpty()) {
										int groupIndex = InstructorNoa
												.getGroupList()
												.getSelectedIndex();

										Group group = InstructorNoa.getGroups()
												.get(groupIndex);
										if (group.getStudentIps().isEmpty()) {
											return;
										} else {
											try {
												VolumeCommand vc = new VolumeCommand(
														InetAddress
																.getLocalHost()
																.getHostAddress(),
														"",
														Integer.parseInt(Config
																.getParam("port")),
														"set", vol * 650);
												for (String studentIp : group
														.getStudentIps()) {
													vc.setTo(studentIp);
													InstructorNoa
															.getServerService()
															.send(vc);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}

									}

								}
							} else {
								popup.showPopup(button);
							}
						}
					});
					break;
				case "callAll":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								StartCallAllCommand sm = new StartCallAllCommand(
										InetAddress.getLocalHost()
												.getHostAddress(), Config
												.getParam("broadcastingIp"),
										Integer.parseInt(Config
												.getParam("port")));
								InstructorNoa.getServerService().send(sm);

								if (InstructorNoa.getSendOnly() == null) {
									AVSendOnly as;
									as = new AVSendOnly("5010", "224.5.6.7",
											"10010");
									InstructorNoa.setSendOnly(as);
									as.start();
								} else {
									InstructorNoa.getSendOnly().start();
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});

					break;
				case "viewMode":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							CardLayout cl = (CardLayout) InstructorNoa
									.getCenterPanel().getLayout();
							cl.next(InstructorNoa.getCenterPanel());
						}
					});

					break;
				case "language":

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
			final WebDesktopPane desktopPane, final String hostIp,
			final String hostName) throws NumberFormatException, Exception {
		final WebInternalFrame internalFrame = new WebInternalFrame(hostName,
				false, false, false, true);

		// get current list of students, if some one is new, add him/her
		JInternalFrame[] frames = desktopPane.getAllFrames();
		List<String> listOfStudents = new ArrayList<String>();
		for (JInternalFrame frame : frames) {
			listOfStudents.add((String) frame.getClientProperty("ip"));
		}

		for (JInternalFrame frame : frames) {
			if (hostIp.equals((String) frame.getClientProperty("ip"))) {
				if (!((Viewer) frame.getClientProperty("vnc")).isConnected()) {
					jrdesktop.Config con = new jrdesktop.Config(false, "",
							hostIp,
							Integer.parseInt(Config.getParam("vncPort")),
							"admin", "admin", false, false);
					final Viewer vnc = new Viewer(con);
					vnc.StartThumbs((WebInternalFrame) frame);
					frame.putClientProperty("vnc", vnc);
					frame.putClientProperty("live", true);
					if (frame.isSelected()) {
						frame.setFrameIcon(new ImageIcon(
								ImageIO.read(InstructorNoaUtil.class
										.getResourceAsStream("/icons/menubar/check.png"))));
					} else {
						frame.setFrameIcon(new ImageIcon(
								ImageIO.read(InstructorNoaUtil.class
										.getResourceAsStream("/icons/menubar/student.png"))));
					}
					// frame.updateUI();

					return null;
				}
				break;
			}
		}

		if (listOfStudents.contains(hostIp)) {
			return null;
		}

		jrdesktop.Config con = new jrdesktop.Config(false, "", hostIp,
				Integer.parseInt(Config.getParam("vncPort")), "admin", "admin",
				false, false);
		final Viewer vnc = new Viewer(con);

		internalFrame.putClientProperty("vnc", vnc);
		internalFrame.putClientProperty("ip", hostIp);
		internalFrame.putClientProperty("lock", false);
		internalFrame.putClientProperty("username", hostName);
		internalFrame.putClientProperty("live", true);
		internalFrame.putClientProperty("group", -1);

		internalFrame.setFrameIcon(new ImageIcon(ImageIO
				.read(InstructorNoaUtil.class
						.getResourceAsStream("/icons/menubar/student.png"))));
		vnc.StartThumbs(internalFrame);

		internalFrame.open();
		desktopPane.add(internalFrame);

		internalFrame.setBounds(0 + (desktopPane.getComponentCount() * 200), 0,
				200, 200);

		internalFrame.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {

				if (!(boolean) internalFrame.getClientProperty("live")) {
					return;
				}

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorNoaUtil.class
									.getResourceAsStream("/icons/menubar/student.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {

				if (!(boolean) internalFrame.getClientProperty("live")) {
					return;
				}

				try {
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorNoaUtil.class
									.getResourceAsStream("/icons/menubar/check.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		WebPopupMenu popup = new WebPopupMenu();
		((JComponent) internalFrame.getComponent(1))
				.setComponentPopupMenu(popup);
		TooltipManager.setTooltip(((JComponent) internalFrame.getComponent(1)),
				"No Group");

		WebMenuItem menuItemUngroup = new WebMenuItem("Ungroup");
		WebMenuItem menuItemGroupA = new WebMenuItem("Group A");
		WebMenuItem menuItemGroupB = new WebMenuItem("Group B");
		WebMenuItem menuItemGroupC = new WebMenuItem("Group C");
		WebMenuItem menuItemGroupD = new WebMenuItem("Group D");
		WebMenuItem menuItemGroupE = new WebMenuItem("Group E");
		WebMenuItem menuItemGroupF = new WebMenuItem("Group F");
		WebMenuItem menuItemGroupG = new WebMenuItem("Group G");
		WebMenuItem menuItemGroupH = new WebMenuItem("Group H");
		WebMenuItem menuItemGroupI = new WebMenuItem("Group I");
		WebMenuItem menuItemGroupJ = new WebMenuItem("Group J");

		popup.add(menuItemUngroup);
		popup.addSeparator();
		popup.add(menuItemGroupA);
		popup.add(menuItemGroupB);
		popup.add(menuItemGroupC);
		popup.add(menuItemGroupD);
		popup.add(menuItemGroupE);
		popup.add(menuItemGroupF);
		popup.add(menuItemGroupG);
		popup.add(menuItemGroupH);
		popup.add(menuItemGroupI);
		popup.add(menuItemGroupJ);

		menuItemUngroup.putClientProperty("group", "none");
		menuItemUngroup.putClientProperty("group_no", -1);
		menuItemGroupA.putClientProperty("group", "A");
		menuItemGroupA.putClientProperty("group_no", 0);
		menuItemGroupB.putClientProperty("group", "B");
		menuItemGroupB.putClientProperty("group_no", 1);
		menuItemGroupC.putClientProperty("group", "C");
		menuItemGroupC.putClientProperty("group_no", 2);
		menuItemGroupD.putClientProperty("group", "D");
		menuItemGroupD.putClientProperty("group_no", 3);
		menuItemGroupE.putClientProperty("group", "E");
		menuItemGroupE.putClientProperty("group_no", 4);
		menuItemGroupF.putClientProperty("group", "F");
		menuItemGroupF.putClientProperty("group_no", 5);
		menuItemGroupG.putClientProperty("group", "G");
		menuItemGroupG.putClientProperty("group_no", 6);
		menuItemGroupH.putClientProperty("group", "G");
		menuItemGroupH.putClientProperty("group_no", 7);
		menuItemGroupI.putClientProperty("group", "H");
		menuItemGroupI.putClientProperty("group_no", 8);
		menuItemGroupJ.putClientProperty("group", "I");
		menuItemGroupJ.putClientProperty("group_no", 9);

		ActionListener actionListenerGroupMenuItem = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebMenuItem menuItem = (WebMenuItem) e.getSource();
				String group = (String) menuItem.getClientProperty("group");
				int groupIndex = (int) menuItem.getClientProperty("group_no");
				int currentGroup = (int) internalFrame
						.getClientProperty("group");
				if (groupIndex < 0) {
					internalFrame.setBorder(BorderFactory.createEmptyBorder());
					if (currentGroup > -1) {
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentIps().remove(hostIp);
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentNames().remove(hostName);
						TooltipManager.setTooltip(
								((JComponent) internalFrame.getComponent(1)),
								"No Group");
					}

				} else {
					internalFrame.putClientProperty("group", groupIndex);
					internalFrame.setBorder(BorderFactory.createMatteBorder(
							4,
							4,
							4,
							4,
							Color.decode(InstructorNoa.getGroups()
									.get(groupIndex).getColor())));
					if (currentGroup < 0 && groupIndex > -1) {
						InstructorNoa.getGroups().get(groupIndex)
								.getStudentIps().add(hostIp);
						InstructorNoa.getGroups().get(groupIndex)
								.getStudentNames().add(hostName);
						TooltipManager.setTooltip(
								((JComponent) internalFrame.getComponent(1)),
								"Group " + group);
					} else if (currentGroup > -1 && groupIndex > -1) {
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentIps().remove(hostIp);
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentNames().remove(hostName);

						InstructorNoa.getGroups().get(groupIndex)
								.getStudentIps().add(hostIp);
						InstructorNoa.getGroups().get(groupIndex)
								.getStudentNames().add(hostName);
						TooltipManager.setTooltip(
								((JComponent) internalFrame.getComponent(1)),
								"Group " + group);
					}
				}
			}
		};

		menuItemUngroup.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupA.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupB.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupC.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupD.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupE.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupF.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupG.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupH.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupI.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupJ.addActionListener(actionListenerGroupMenuItem);

		// Add new student to student's table (List View)
		DefaultTableModel model = (DefaultTableModel) InstructorNoa
				.getStudentListTable().getModel();
		model.addRow(new Object[] { hostIp, hostName });

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
}
