package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jrdesktop.viewer.ScreenPlayer;
import jrdesktop.viewer.Viewer;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jscroll.widgets.RootDesktopPane;

import com.alee.extended.filechooser.SelectionMode;
import com.alee.extended.filechooser.WebFileChooser;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.table.WebTable;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.alee.managers.popup.WebPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.FileUtils;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.command.model.StartSpeechCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartVideoChatCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopCallAllCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.StopModelCommand;
import com.jajeem.command.model.StopVideoChatCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.command.service.ServerServiceTimer;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.design.FileManagerMain;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.design.alt.Quiz_Main;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.survey.design.alt.Survey_Main;
import com.jajeem.util.Config;
import com.jajeem.util.FileUtil;
import com.jajeem.util.LnkParser;
import com.jajeem.util.Session;
import com.jajeem.util.WinRegistry;
import com.jajeem.util.i18n;

public class InstructorNoaUtil {

	private static ServerServiceTimer serverServiceTimer;
	final static WebPopup popup = new WebPopup();
	final static List<String> fileListModel = new ArrayList<String>();

	static Quiz_Main quiz = null;
	static Survey_Main survey = null;
	static Quiz_Main[] groupsQuizWindows = new Quiz_Main[15];
	static Survey_Main[] groupsSurveyWindows = new Survey_Main[15];
	public static ArrayList<String> recordingsList = new ArrayList<>();
	private Thread _videoChat;

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

				final JButton button = ((JButton) c);

				switch (key) {

				case "monitor":
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
								jrdesktop.Config conf = null;
								try {
									conf = new jrdesktop.Config(false, "",
											selectedStudent,
											Integer.parseInt(Config
													.getParam("vncPort")),
											"admin", "admin", false, false);
								} catch (Exception e) {
									JajeemExcetionHandler.logError(e);
									e.printStackTrace();
								}
								VNCCaptureService vnc = new VNCCaptureService();
								vnc.startClient(conf);
							}
						}
					});

					break;
				case "intercom":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0)
								throws NumberFormatException {

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
										if (InstructorNoa.getTransmitter()
												.isTransmitting()) {
											if (InstructorNoa.getTransmitter()
													.getRemoteAddr()
													.getHostAddress()
													.equals(selectedStudent)) {
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
															.setIntercomText(i18n
																	.getParam("Intercom"));
												} catch (Exception e) {
													JajeemExcetionHandler
															.logError(e);
													e.printStackTrace();
												}
											} else {
												WebOptionPane.showMessageDialog(
														InstructorNoa
																.getCenterPanel(),
														"Another voice or video function is already running, please stop it first",
														"Information",
														WebOptionPane.INFORMATION_MESSAGE);
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
													.start("audio");
											InstructorNoa
													.setTransmittingType("intercom");
											InstructorNoa.setIntercomText(i18n
													.getParam("Stop"));
										}
									} catch (Exception e) {
										JajeemExcetionHandler.logError(e);
										e.printStackTrace();
									}
								}
							} else if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								return;
							}
						}

					});

					break;

				case "videoChat":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String selectedStudent = getSelectedStudentIp();

							if (selectedStudent != null && selectedStudent != "") {
								try {
									if (!InstructorNoa.isTransmitting()) {
										ServerService serv = InstructorNoa
												.getServerService();
										StartVideoChatCommand cmd = new StartVideoChatCommand(
												Inet4Address.getLocalHost()
														.getHostAddress(),
												Config.getParam("broadcastingIp"),
												Integer.parseInt(Config
														.getParam("port")));
										serv.send(cmd);
										InstructorNoa.setTransmitting(true);
										InstructorNoa
												.getSendOnly()
												.setRemoteAddr(
														InetAddress
																.getByName(selectedStudent));
										_videoChat = new Thread(new Runnable() {
											
											@Override
											public void run() {
												try {
													button.setText("Trying...");
													button.setEnabled(false);
													InstructorNoa.getSendOnly().start(
															"both");
													button.setText("Stop");
													button.setEnabled(true);
													InstructorNoa
															.setTransmittingType("videoChat");
												} catch (Exception e) {
													button.setText("Video Chat");
													button.setEnabled(true);
												}
											}
										});
										_videoChat.start();
									} else {
										if (InstructorNoa.getTransmittingType()
												.equals("videoChat")) {
											ServerService serv = InstructorNoa
													.getServerService();
											StopVideoChatCommand cmd = new StopVideoChatCommand(
													Inet4Address.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
													Integer.parseInt(Config
															.getParam("port")));
											serv.send(cmd);
											InstructorNoa.getSendOnly().stop();
											button.setText(i18n
													.getParam("Video Chat"));
											InstructorNoa
													.setTransmitting(false);
										}
									}
								} catch (Exception e1) {
									JajeemExcetionHandler.logError(e1);
									e1.printStackTrace();
								}
							}
						}
					});
					break;

				case "group":
					button.addActionListener(new ActionListener() {
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
									"viewMode").equals("thumbView")) {

								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null) {
									String selectedStudent = "";
									selectedStudent = (String) InstructorNoa
											.getDesktopPane()
											.getSelectedFrame()
											.getClientProperty("ip");
									try {
										if (!InstructorNoa.isModeling()) {
											StartModelCommand sm = new StartModelCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
													Integer.parseInt(Config
															.getParam("port")),
													selectedStudent);
											InstructorNoa.getServerService()
													.send(sm);

											if (InstructorNoa.getReceiverOnly() == null) {
												AVReceiveOnly ar = new AVReceiveOnly(
														"10010",
														selectedStudent, "5010");
												ar.initialize("audio");
												InstructorNoa
														.setReceiverOnly(ar);
											} else {
												InstructorNoa
														.getReceiverOnly()
														.setRemoteAddr(
																InetAddress
																		.getByName(selectedStudent));
												InstructorNoa.getReceiverOnly()
														.initialize("audio");
											}

											jrdesktop.Config conf = null;
											conf = new jrdesktop.Config(
													false,
													"",
													selectedStudent,
													Integer.parseInt(Config
															.getParam("vncPort")),
													"admin", "admin", false,
													false);

											VNCCaptureService vnc = new VNCCaptureService();
											vnc.startClient(conf);
											button.setText(i18n
													.getParam("Stop"));

											InstructorNoa.setModeling(true);
										} else {
											StopModelCommand sm = new StopModelCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
													Integer.parseInt(Config
															.getParam("port")));
											InstructorNoa.getServerService()
													.send(sm);
											InstructorNoa.getReceiverOnly()
													.close();
											button.setText(i18n
													.getParam("Modeling"));
											InstructorNoa.setModeling(false);
										}
									} catch (Exception e) {
										JajeemExcetionHandler.logError(e);
										e.printStackTrace();
									}

								} else {
									return;
								}
							} else if (((JComponent) card).getClientProperty(
									"viewMode").equals("groupView")) {
								return;
							}
						}
					});
					break;
				case "record":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						// recorder action
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
											// Enabling dialog decoration
											boolean decorateFrames = WebLookAndFeel
													.isDecorateDialogs();
											WebLookAndFeel
													.setDecorateDialogs(true);

											Recorder recorder = new Recorder(
													new ArrayList<>(group
															.getStudentIps()),
													true, true);
											recorder.setLocationRelativeTo(rightButtonPanel);
											recorder.setVisible(true);

											// Restoring frame decoration option
											WebLookAndFeel
													.setDecorateDialogs(decorateFrames);
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
										}
									}
								}
							} else {
								// Enabling dialog decoration
								boolean decorateFrames = WebLookAndFeel
										.isDecorateDialogs();
								WebLookAndFeel.setDecorateDialogs(true);

								ArrayList<String> temp = new ArrayList<>();
								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null)
									temp.add(InstructorNoa.getDesktopPane()
											.getSelectedFrame()
											.getClientProperty("ip").toString());
								Recorder recorder = new Recorder(temp, false,
										true);
								recorder.setLocationRelativeTo(rightButtonPanel);
								recorder.setVisible(true);

								// Restoring frame decoration option
								WebLookAndFeel
										.setDecorateDialogs(decorateFrames);
							}

						}
					});

					break;
				case "speech":
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								// Desktop.getDesktop().open(new
								// File("util/iCalabo/iCalabo.exe"));
								Process proc = Runtime.getRuntime().exec(
										"util/iCalabo/iCalabo.exe");

								new Config();
								ServerService serv = InstructorNoa
										.getServerService();
								StartSpeechCommand cmd = new StartSpeechCommand(
										Inet4Address.getLocalHost()
												.getHostAddress(), Config
												.getParam("broadcastingIp"),
										Integer.parseInt(Config
												.getParam("port")));
								serv.send(cmd);
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					});
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
											main.setReceivingIps(new ArrayList<>(
													group.getStudentIps()));
											main.setVisible(true);
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
										}
									}
								}
							} else {
								String ip = getSelectedStudentIp();
								if (ip != "" && ip != null) {
									FileManagerMain main = new FileManagerMain();
									main.setReceivingIps(new ArrayList<String>(
											Arrays.asList(ip)));
									main.setVisible(true);
								} else {
									FileManagerMain main = new FileManagerMain();
									ArrayList<String> ips = new ArrayList<>();
									for (int i = 0; i < InstructorNoa
											.getDesktopPane().getAllFrames().length; i++) {
										String sip = InstructorNoa
												.getDesktopPane()
												.getAllFrames()[i]
												.getClientProperty("ip")
												.toString();
										ips.add(sip);
									}
									main.setReceivingIps(ips);
									main.setVisible(true);
								}
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
											JajeemExcetionHandler.logError(e);
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
					button.addActionListener(new ActionListener() {

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
					break;
				case "report":

					break;
				case "account":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								if (InstructorNoa.getInstructorModel()
										.getUsername().equals("admin")) {
									new AdminPanel();
								} else {

									new AccountPanel(InstructorNoa
											.getInstructorModel(),
											InstructorNoa.getCourseModel());
								}
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					});
					break;
				case "chat":
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							Component card = getActiveCard();

							if (((JComponent) card).getClientProperty(
									"viewMode").equals("thumbView")) {
								InstructorNoa.getDesktopPaneScroll()
										.getSelectedFrame();
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
												JajeemExcetionHandler
														.logError(e);
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
														false,
														-1,
														InstructorNoa
																.getDesktopPane()
																.getSelectedFrame()
																.getTitle());
												InstructorNoa.getChatList()
														.add(currentChat);
											} catch (Exception e) {
												JajeemExcetionHandler
														.logError(e);
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
														JajeemExcetionHandler
																.logError(e);
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
														JajeemExcetionHandler
																.logError(e);
														e.printStackTrace();
													}
												}
											}
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
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
			final WebFrame mainFrame) throws Exception {
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
											JajeemExcetionHandler.logError(e);
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

							Component card = getActiveCard();

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
													JajeemExcetionHandler
															.logError(e);
													e.printStackTrace();
												}
											}
										}).start();
									} catch (IOException e) {
										JajeemExcetionHandler.logError(e);
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
							} else {
								try {
									final Process proc = Runtime
											.getRuntime()
											.exec("java -jar WhiteboardServer.jar "
													+ "1998" + " " + "1999",
													null, new File("util/"));
									new Thread(new Runnable() {

										@Override
										public void run() {
											try {
												BufferedReader in = new BufferedReader(
														new InputStreamReader(
																proc.getInputStream()));
												String line = null;
												while ((line = in.readLine()) != null) {
													System.out.println(line);
												}
											} catch (Exception e) {
												e.printStackTrace();
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
													+ "1998" + " " + "1999",
													null, new File("util/"));
									new Thread(new Runnable() {

										@Override
										public void run() {
											try {
												BufferedReader in = new BufferedReader(
														new InputStreamReader(
																proc.getInputStream()));
												String line = null;
												while ((line = in.readLine()) != null) {
													System.out.println(line);
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
									vc = new StartWhiteBoardCommand(InetAddress
											.getLocalHost().getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")));
									vc.setSessionPort("1998");
									vc.setWhiteboardPort("1999");
									InstructorNoa.getServerService().send(vc);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}
					});
					break;
				case "internet":
					// in instructor noa!
					break;

				case "program":
					try {
						String pathToStartMenu = WinRegistry
								.readString(
										WinRegistry.HKEY_LOCAL_MACHINE,
										"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
										"Common Start Menu")
								+ "\\Programs";

						FileUtil fileUtil = new FileUtil();
						final File[] tempfileList = fileUtil
								.finder(pathToStartMenu);
						final File[] fileList;
						final ArrayList<File> listOfAllLinks = new ArrayList<>();
						final ArrayList<File> lnkList = new ArrayList<>();
						final ArrayList<File> exeList = new ArrayList<>();
						for (int i = 0; i < tempfileList.length; i++) {
							if (tempfileList[i].isDirectory())
								listOfAllLinks
										.addAll(getPath(getDirectoryContent(tempfileList[i])));
							else
								listOfAllLinks.add(tempfileList[i]);
						}

						Collections.sort(listOfAllLinks);

						for (int i = 0; i < listOfAllLinks.size(); i++) {
							if (listOfAllLinks.get(i).getName().indexOf(".") != -1) {
								String ext = listOfAllLinks
										.get(i)
										.getName()
										.substring(
												listOfAllLinks.get(i).getName()
														.indexOf("."));
								String path = listOfAllLinks.get(i).getParent();
								String fileName = listOfAllLinks.get(i)
										.getName();
								if (ext.equals(".lnk")) {
									try {
										LnkParser parser = new LnkParser(
												listOfAllLinks.get(i));
										if (parser.getRealFilename().contains(
												".exe")) {
											lnkList.add(listOfAllLinks.get(i));
											exeList.add(new File(parser
													.getRealFilename()));
										}
									} catch (Exception e) {

									}
								}
								// System.out.println(new
								// LnkParser(fileList[i]).getRealFilename());
							}
						}

						final DefaultTableModel model = (new DefaultTableModel(
								new Object[][] {}, new String[] { "Name",
										"status" }) {
							private static final long serialVersionUID = 1L;
							Class[] columnTypes = new Class[] { String.class,
									Boolean.class };

							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
						});

						for (int i = 0; i < lnkList.size(); i++) {
							File file = lnkList.get(i);
							if (file.getName().indexOf(".") != -1) {
								String extension = file.getName().substring(
										file.getName().indexOf("."));
								if (extension.equals(".lnk")) {
									fileListModel.add(exeList.get(i).getName());
									model.addRow(new Object[] {
											file.getParentFile().getName()
													+ "\\"
													+ file.getName()
															.substring(
																	0,
																	file.getName()
																			.length() - 4),
											false });
								}
							}
						}

						final WebTable programsList = new WebTable();

						programsList.setTableHeader(null);
						programsList.setModel(model);
						programsList.getColumnModel().getColumn(0)
								.setResizable(false);
						programsList.getColumnModel().getColumn(1)
								.setResizable(false);
						programsList.getColumnModel().getColumn(1)
								.setPreferredWidth(30);
						programsList.getColumnModel().getColumn(1)
								.setMaxWidth(30);

						WebButtonPopup programPopupButton = new WebButtonPopup(
								(WebButton) button, PopupWay.upCenter);

						final int sizeOfProgramModel = model.getRowCount();

						GroupPanel programPopupContent = new GroupPanel(5,
								false, new WebScrollPane(programsList));
						programPopupContent.setMargin(15);
						programPopupContent.setOpaque(false);
						programsList.setOpaque(false);

						programPopupButton.setContent(programPopupContent);

						programsList.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								boolean value = (boolean) programsList
										.getValueAt(
												programsList.getSelectedRow(),
												1);

								if (value == true) {
									Component card = null;
									for (Component comp : InstructorNoa
											.getCenterPanel().getComponents()) {
										if (comp.isVisible() == true) {
											card = comp;
										}
									}

									if (((JComponent) card).getClientProperty(
											"viewMode").equals("thumbView")) {
										try {
											WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
													Integer.parseInt(Config
															.getParam("port")),
													(fileListModel.get(programsList
															.getSelectedRow())),
													true);
											InstructorNoa.getServerService()
													.send(ic);
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
											e.printStackTrace();
										}
									} else if (((JComponent) card)
											.getClientProperty("viewMode")
											.equals("groupView")) {
										if (!InstructorNoa.getGroupList()
												.isSelectionEmpty()) {
											int groupIndex = InstructorNoa
													.getGroupList()
													.getSelectedIndex();

											Group group = InstructorNoa
													.getGroups()
													.get(groupIndex);
											if (group.getStudentIps().isEmpty()) {
												return;
											} else {

												try {
													WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
															InetAddress
																	.getLocalHost()
																	.getHostAddress(),
															"",
															Integer.parseInt(Config
																	.getParam("port")),
															(fileListModel
																	.get(programsList
																			.getSelectedRow())),
															true);
													for (String studentIp : group
															.getStudentIps()) {
														ic.setTo(studentIp);
														InstructorNoa
																.getServerService()
																.send(ic);
													}
												} catch (Exception e) {
													JajeemExcetionHandler
															.logError(e);
													e.printStackTrace();
												}

											}
										}
									}
								} else {
									Component card = null;
									for (Component comp : InstructorNoa
											.getCenterPanel().getComponents()) {
										if (comp.isVisible() == true) {
											card = comp;
										}
									}

									if (((JComponent) card).getClientProperty(
											"viewMode").equals("thumbView")) {
										try {
											WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
													Integer.parseInt(Config
															.getParam("port")),
													(fileListModel.get(programsList
															.getSelectedRow())),
													false);
											InstructorNoa.getServerService()
													.send(ic);
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
											e.printStackTrace();
										}
									} else if (((JComponent) card)
											.getClientProperty("viewMode")
											.equals("groupView")) {
										if (!InstructorNoa.getGroupList()
												.isSelectionEmpty()) {
											int groupIndex = InstructorNoa
													.getGroupList()
													.getSelectedIndex();

											Group group = InstructorNoa
													.getGroups()
													.get(groupIndex);
											if (group.getStudentIps().isEmpty()) {
												return;
											} else {

												try {
													WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
															InetAddress
																	.getLocalHost()
																	.getHostAddress(),
															"",
															Integer.parseInt(Config
																	.getParam("port")),
															(fileListModel
																	.get(programsList
																			.getSelectedRow())),
															false);
													for (String studentIp : group
															.getStudentIps()) {
														ic.setTo(studentIp);
														InstructorNoa
																.getServerService()
																.send(ic);
													}
												} catch (Exception e) {
													JajeemExcetionHandler
															.logError(e);
													e.printStackTrace();
												}

											}
										}
									}
								}
							}
						});
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				case "programStart":
					try {

						// get start menu path from registry and add them to
						// program starter list
						// String pathToStartMenu = WinRegistry
						// .readString(
						// WinRegistry.HKEY_LOCAL_MACHINE,
						// "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
						// "Common Start Menu")
						// + "\\Programs";
						//
						// FileUtil fileUtil = new FileUtil();
						// final File[] tempfileList = fileUtil
						// .finder(pathToStartMenu);
						// final ArrayList<File> listOfAllLinks = new
						// ArrayList<>();
						// for (int i = 0; i < tempfileList.length; i++) {
						// if (tempfileList[i].isDirectory())
						// listOfAllLinks
						// .addAll(getPath(getDirectoryContent(tempfileList[i])));
						// else
						// listOfAllLinks.add(tempfileList[i]);
						// }
						//
						// Collections.sort(listOfAllLinks);
						//
						final DefaultListModel model = new DefaultListModel();
						// for (int i = 0; i < listOfAllLinks.size(); i++) {
						// File file = listOfAllLinks.get(i);
						// if (file.getName().indexOf(".") != -1) {
						// String extension = file.getName().substring(
						// file.getName().indexOf("."));
						// if (extension.equals(".lnk")) {
						// fileListModel.add(file.getName().substring(
						// 0, file.getName().length() - 4));
						// model.addElement(file.getParentFile()
						// .getName()
						// + "\\"
						// + file.getName()
						// .substring(
						// 0,
						// file.getName()
						// .length() - 4));
						// }
						// }
						// }

						final WebList programsList = new WebList(model);
						programsList.setVisibleRowCount(6);
						programsList.setSelectedIndex(0);
						programsList.setEditable(false);

						WebButtonPopup programPopupButton = new WebButtonPopup(
								(WebButton) button, PopupWay.upCenter);
						final WebButton chooseAppButton = new WebButton(
								i18n.getParam("Add"));

						final int sizeOfProgramModel = model.getSize();

						chooseAppButton.addActionListener(new ActionListener() {
							private ProgramList fileChooser = new ProgramList(
									model);

							public void actionPerformed(ActionEvent e) {
								fileChooser.setVisible(true);
							}
						});

						WebButton runButton = new WebButton(
								i18n.getParam("Run"));

						GroupPanel programPopupContent = new GroupPanel(5,
								false, new WebScrollPane(programsList),
								runButton, chooseAppButton);
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

											StartApplicationCommand sa;
											try {
												sa = new StartApplicationCommand(
														InetAddress
																.getLocalHost()
																.getHostAddress(),
														selectedStudent,
														Integer.parseInt(Config
																.getParam("port")),
														programsList
																.getModel()
																.getElementAt(
																		index)
																.toString());
												InstructorNoa
														.getServerService()
														.send(sa);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									} else if (((JComponent) card)
											.getClientProperty("viewMode")
											.equals("groupView")) {
										if (!InstructorNoa.getGroupList()
												.isSelectionEmpty()) {
											int groupIndex = InstructorNoa
													.getGroupList()
													.getSelectedIndex();

											Group group = InstructorNoa
													.getGroups()
													.get(groupIndex);
											if (group.getStudentIps().isEmpty()) {
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
															programsList
																	.getModel()
																	.getElementAt(
																			index)
																	.toString());
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
						});

						runButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								int index = programsList.getSelectedIndex();

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

										StartApplicationCommand sa;
										try {
											sa = new StartApplicationCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													selectedStudent,
													Integer.parseInt(Config
															.getParam("port")),
													programsList
															.getModel()
															.getElementAt(index)
															.toString());
											InstructorNoa.getServerService()
													.send(sa);
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

											StartApplicationCommand sa;
											try {
												sa = new StartApplicationCommand(
														InetAddress
																.getLocalHost()
																.getHostAddress(),
														"",
														Integer.parseInt(Config
																.getParam("port")),
														programsList
																.getModel()
																.getElementAt(
																		index)
																.toString());
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

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected Collection<? extends File> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<File> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i));
		}
		return list;
	}

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

								if (InstructorNoa.getTransmitter()
										.isTransmitting()) {
									WebOptionPane.showMessageDialog(
											InstructorNoa.getCenterPanel(),
											"Another voice or video function is already running, please stop it first",
											"Information",
											WebOptionPane.INFORMATION_MESSAGE);
									return;
								}

								if (InstructorNoa.getSendOnly()
										.isTransmitting()) {
									String ip = InetAddress.getLocalHost()
											.getHostAddress().toString();
									ip = ip.substring(0, ip.lastIndexOf("."))
											+ ".255";
									StopCallAllCommand sm = new StopCallAllCommand(
											InetAddress.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")));
									InstructorNoa.getServerService().send(sm);
									InstructorNoa.getSendOnly().stop();
									button.setText(i18n.getParam("Call All"));
								} else {
									StartCallAllCommand sm = new StartCallAllCommand(
											InetAddress.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")));
									InstructorNoa.getServerService().send(sm);
									InstructorNoa.getSendOnly().start("audio");
									button.setText(i18n.getParam("Stop"));
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
			final RootDesktopPane desktopPane, final String hostIp,
			final String hostName) throws NumberFormatException, Exception {
		final WebInternalFrame internalFrame = new WebInternalFrame(hostName,
				false, false, false, false);

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
		internalFrame.putClientProperty("recording", false);
		internalFrame.putClientProperty("isselected", false);

		internalFrame.setFrameIcon(new ImageIcon(ImageIO
				.read(InstructorNoaUtil.class
						.getResourceAsStream("/icons/menubar/student.png"))));
		internalFrame.setSize(100, 120);
		internalFrame.setMaximizable(false);
		internalFrame.setIconifiable(false);
		internalFrame.setIcon(false);
		internalFrame.pack();
		internalFrame.setResizable(false);

		vnc.StartThumbs(internalFrame);

		internalFrame.open();

		internalFrame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// JOptionPane.showMessageDialog(null, "not");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				internalFrameMouseClicked(e);
			}
		});

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
					if (internalFrame.getClientProperty("group").equals(-1)) {
						internalFrame.setBorder(BorderFactory
								.createEmptyBorder());
					} else {
						internalFrame.setBorder(BorderFactory.createMatteBorder(
								4, 4, 4, 4, Color.decode(InstructorNoa
										.getGroups()
										.get((int) internalFrame
												.getClientProperty("group"))
										.getColor())));
					}
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

					internalFrame.setBorder(BorderFactory.createMatteBorder(4,
							4, 4, 4, Color.decode("#82CAFF")));
					internalFrame.setFrameIcon(new ImageIcon(
							ImageIO.read(InstructorNoaUtil.class
									.getResourceAsStream("/icons/menubar/check.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		WebPopupMenu popup = new WebPopupMenu();
		WebMenu menu = new WebMenu();

		((JComponent) internalFrame.getComponent(1))
				.setComponentPopupMenu(popup);
		((JScrollPane) ((JPanel) ((JLayeredPane) ((JRootPane) internalFrame
				.getComponent(0)).getComponent(1)).getComponent(0))
				.getComponent(0)).setComponentPopupMenu(popup);

		((JComponent) internalFrame.getComponent(1))
				.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// JOptionPane.showMessageDialog(null, "mcv");
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						internalFrameMouseClicked(e);
					}
				});

		((ScreenPlayer) ((JViewport) ((JScrollPane) ((JPanel) ((JLayeredPane) ((JRootPane) internalFrame
				.getComponent(0)).getComponent(1)).getComponent(0))
				.getComponent(0)).getComponent(0)).getComponent(0))
				.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// JOptionPane.showMessageDialog(null, "not");
					}

					@Override
					public void mousePressed(MouseEvent e) {

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// JOptionPane.showMessageDialog(null, "not");
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// JOptionPane.showMessageDialog(null, "not");
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						internalFrameMouseClicked(e);
					}
				});

		TooltipManager.setTooltip(((JComponent) internalFrame.getComponent(1)),
				"No Group");
		JPopupMenu.setDefaultLightWeightPopupEnabled(true);

		WebMenu menuItemgroups = new WebMenu("Grouping");
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
		WebMenuItem menuItemGroupK = new WebMenuItem("Group K");
		WebMenuItem menuItemGroupL = new WebMenuItem("Group L");
		WebMenuItem menuItemGroupM = new WebMenuItem("Group M");
		WebMenuItem menuItemGroupN = new WebMenuItem("Group N");
		WebMenuItem menuItemGroupO = new WebMenuItem("Group O");

		menuItemgroups.add(menuItemUngroup);
		menuItemgroups.addSeparator();
		menuItemgroups.add(menuItemGroupA);
		menuItemgroups.add(menuItemGroupB);
		menuItemgroups.add(menuItemGroupC);
		menuItemgroups.add(menuItemGroupD);
		menuItemgroups.add(menuItemGroupE);
		menuItemgroups.add(menuItemGroupF);
		menuItemgroups.add(menuItemGroupG);
		menuItemgroups.add(menuItemGroupH);
		menuItemgroups.add(menuItemGroupI);
		menuItemgroups.add(menuItemGroupJ);
		menuItemgroups.add(menuItemGroupK);
		menuItemgroups.add(menuItemGroupL);
		menuItemgroups.add(menuItemGroupM);
		menuItemgroups.add(menuItemGroupN);
		menuItemgroups.add(menuItemGroupO);

		WebMenu menuItemActions = new WebMenu("Actions");
		WebMenuItem menuItemSendFile = new WebMenuItem("Send File");
		WebMenuItem menuItemIntercom = new WebMenuItem("Intercom");
		WebMenuItem menuItemMonitor = new WebMenuItem("Monitor");
		WebMenuItem menuItemChat = new WebMenuItem("Chat");
		WebMenuItem menuItemLock = new WebMenuItem("Lock");
		WebMenuItem menuItemRecord = new WebMenuItem("Record");

		menuItemSendFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SendFileActionListener();
			}
		});
		menuItemIntercom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				IntercomActionListener();
			}
		});
		menuItemMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MonitorActionListener();
			}
		});
		menuItemChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChatActionListener();
			}
		});
		menuItemLock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LockActionListener();
			}
		});
		menuItemRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RecordActionListener();
			}
		});

		menuItemActions.add(menuItemSendFile);
		menuItemActions.add(menuItemIntercom);
		menuItemActions.add(menuItemMonitor);
		menuItemActions.add(menuItemChat);
		menuItemActions.add(menuItemLock);
		menuItemActions.add(menuItemRecord);

		popup.add(menuItemgroups);
		popup.add(menuItemActions);

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
		menuItemGroupH.putClientProperty("group", "H");
		menuItemGroupH.putClientProperty("group_no", 7);
		menuItemGroupI.putClientProperty("group", "I");
		menuItemGroupI.putClientProperty("group_no", 8);
		menuItemGroupJ.putClientProperty("group", "J");
		menuItemGroupJ.putClientProperty("group_no", 9);
		menuItemGroupK.putClientProperty("group", "K");
		menuItemGroupK.putClientProperty("group_no", 10);
		menuItemGroupL.putClientProperty("group", "L");
		menuItemGroupL.putClientProperty("group_no", 11);
		menuItemGroupM.putClientProperty("group", "M");
		menuItemGroupM.putClientProperty("group_no", 12);
		menuItemGroupN.putClientProperty("group", "N");
		menuItemGroupN.putClientProperty("group_no", 13);
		menuItemGroupO.putClientProperty("group", "O");
		menuItemGroupO.putClientProperty("group_no", 14);

		ActionListener actionListenerGroupMenuItem = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebMenuItem menuItem = (WebMenuItem) e.getSource();
				String group = (String) menuItem.getClientProperty("group");
				int groupIndex = (int) menuItem.getClientProperty("group_no");
				int currentGroup = (int) internalFrame
						.getClientProperty("group");
				if (groupIndex < 0) {
					internalFrame.setBorder(BorderFactory.createEmptyBorder());
					internalFrame.putClientProperty("group", -1);
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
		menuItemGroupK.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupL.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupM.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupN.addActionListener(actionListenerGroupMenuItem);
		menuItemGroupO.addActionListener(actionListenerGroupMenuItem);

		// Add new student to student's table (List View)
		DefaultTableModel model = (DefaultTableModel) InstructorNoa
				.getStudentListTable().getModel();
		model.addRow(new Object[] { hostIp, hostName });

		desktopPane.add(internalFrame, BorderLayout.CENTER);
		InstructorNoa.getDesktopPaneScroll().getDesktopMediator()
				.tileInternalFrames();

		return internalFrame;
	}

	protected static void internalFrameMouseClicked(MouseEvent e) {
		if (InstructorNoa.getDesktopPane().getSelectedFrame()
				.getClientProperty("isselected").equals(false)) {
			InstructorNoa.getDesktopPane().getSelectedFrame()
					.putClientProperty("isselected", true);
			try {
				InstructorNoa.getDesktopPane().getSelectedFrame()
						.setSelected(true);
			} catch (PropertyVetoException ex) {
				ex.printStackTrace();
			}
		} else {
			InstructorNoa.getDesktopPane().getSelectedFrame()
					.putClientProperty("isselected", false);
			try {
				InstructorNoa.getDesktopPane().getSelectedFrame()
						.setSelected(false);
			} catch (PropertyVetoException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected static void RecordActionListener() {
		ArrayList<String> temp = new ArrayList<>();
		temp.add(InstructorNoa.getDesktopPane().getSelectedFrame()
				.getClientProperty("ip").toString());
		Recorder recorder = new Recorder(temp, false, true);
		recorder.RecordStudent();
	}

	protected static void LockActionListener() {
		InstructorNoa.LockAction();
	}

	protected static void ChatActionListener() {
		Component card = getActiveCard();

		if (((JComponent) card).getClientProperty("viewMode").equals(
				"thumbView")) {
			InstructorNoa.getDesktopPaneScroll().getSelectedFrame();
			if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
				String selectedStudent = "";
				selectedStudent = (String) InstructorNoa.getDesktopPane()
						.getSelectedFrame().getClientProperty("ip");
				Chat currentChat = null;
				if (!InstructorNoa.getChatList().isEmpty()) {
					for (Chat chat : InstructorNoa.getChatList()) {
						if (chat.getTo().equals(selectedStudent)) {
							currentChat = chat;
							currentChat.setVisible(true);
							break;
						}
					}
					if (currentChat == null) {
						try {
							currentChat = new Chat(selectedStudent,
									Integer.parseInt(Config.getParam("port")),
									false, -1, selectedStudent);
							InstructorNoa.getChatList().add(currentChat);
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
					}
				} else {
					if (currentChat == null) {
						try {
							currentChat = new Chat(selectedStudent,
									Integer.parseInt(Config.getParam("port")),
									false, -1, InstructorNoa.getDesktopPane()
											.getSelectedFrame().getTitle());
							InstructorNoa.getChatList().add(currentChat);
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	protected static void MonitorActionListener() {
		if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
			String selectedStudent = "";
			selectedStudent = (String) InstructorNoa.getDesktopPane()
					.getSelectedFrame().getClientProperty("ip");
			jrdesktop.Config conf = null;
			try {
				conf = new jrdesktop.Config(false, "", selectedStudent,
						Integer.parseInt(Config.getParam("vncPort")), "admin",
						"admin", false, false);
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
			VNCCaptureService vnc = new VNCCaptureService();
			vnc.startClient(conf);
		}
	}

	protected static void IntercomActionListener() {
		Component card = null;
		for (Component comp : InstructorNoa.getCenterPanel().getComponents()) {
			if (comp.isVisible() == true) {
				card = comp;
			}
		}

		if (((JComponent) card).getClientProperty("viewMode").equals(
				"thumbView")) {

			if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
				String selectedStudent = "";
				selectedStudent = (String) InstructorNoa.getDesktopPane()
						.getSelectedFrame().getClientProperty("ip");
				try {
					// "--remote-host=127.0.0.1 --remote-port-base=10000"

					// if some one is selected and we were
					// talking to him,
					// stop transmitting and sent a stop
					// command
					// to him
					if (InstructorNoa.getTransmitter().getRemoteAddr()
							.getHostAddress().equals(selectedStudent)) {
						if (InstructorNoa.getTransmitter().isTransmitting()) {
							InstructorNoa.getTransmitter().stop();
							StopIntercomCommand si;
							try {
								si = new StopIntercomCommand(InetAddress
										.getLocalHost().getHostAddress(),
										InstructorNoa.getTransmitter()
												.getRemoteAddr()
												.getHostAddress(),
										Integer.parseInt(Config
												.getParam("port")));
								InstructorNoa.getServerService().send(si);

								InstructorNoa.setIntercomText(i18n
										.getParam("Intercom"));
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						} else {
							StartIntercomCommand si = new StartIntercomCommand(
									InetAddress.getLocalHost().getHostAddress(),
									selectedStudent, Integer.parseInt(Config
											.getParam("port")));
							InstructorNoa.getServerService().send(si);

							InstructorNoa.getTransmitter().setRemoteAddr(
									InetAddress.getByName(selectedStudent));
							InstructorNoa.getTransmitter().start("audio");
							InstructorNoa.setTransmittingType("intercom");
							InstructorNoa.setIntercomText("Stop");
						}

					} else {
						if (InstructorNoa.getTransmitter().isTransmitting()) {
							InstructorNoa.getTransmitter().stop();
							StopIntercomCommand si;
							si = new StopIntercomCommand(InetAddress
									.getLocalHost().getHostAddress(),
									InstructorNoa.getTransmitter()
											.getRemoteAddr().getHostAddress(),
									Integer.parseInt(Config.getParam("port")));
							InstructorNoa.getServerService().send(si);
							StartIntercomCommand startI = new StartIntercomCommand(
									InetAddress.getLocalHost().getHostAddress(),
									selectedStudent, Integer.parseInt(Config
											.getParam("port")));
							InstructorNoa.getServerService().send(startI);

							InstructorNoa.getTransmitter().setRemoteAddr(
									InetAddress.getByName(selectedStudent));
							InstructorNoa.getTransmitter().start("audio");
							InstructorNoa.setTransmittingType("intercom");
							InstructorNoa
									.setIntercomText(i18n.getParam("Stop"));

						} else {
							// Send start receiver to
							// selected
							// student and start transmitter
							StartIntercomCommand si = new StartIntercomCommand(
									InetAddress.getLocalHost().getHostAddress(),
									selectedStudent, Integer.parseInt(Config
											.getParam("port")));
							InstructorNoa.getServerService().send(si);

							InstructorNoa.getTransmitter().setRemoteAddr(
									InetAddress.getByName(selectedStudent));
							InstructorNoa.getTransmitter().start("audio");
							InstructorNoa.setTransmittingType("intercom");
							InstructorNoa
									.setIntercomText(i18n.getParam("Stop"));
						}
					}

				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}

			} else {
				// if no students selected and is
				// transmitting
				// to someone
				if (InstructorNoa.getTransmitter() != null)
					if (InstructorNoa.getTransmitter().isTransmitting()) {

						// Stop transmitting to prev student
						// and
						// sent stop
						// command to him
						InstructorNoa.getTransmitter().stop();
						StopIntercomCommand si;
						try {
							si = new StopIntercomCommand(InetAddress
									.getLocalHost().getHostAddress(),
									InstructorNoa.getTransmitter()
											.getRemoteAddr().getHostAddress(),
									Integer.parseInt(Config.getParam("port")));
							InstructorNoa.getServerService().send(si);
							InstructorNoa.setTransmittingType("");

							InstructorNoa.setIntercomText(i18n
									.getParam("Intercom"));
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
					} else {
						try {
							InstructorNoa.setIntercomText(i18n
									.getParam("Intercom"));
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
						return;
					}
			}
		} else if (((JComponent) card).getClientProperty("viewMode").equals(
				"groupView")) {
			if (!InstructorNoa.getGroupList().isSelectionEmpty()) {
				int groupIndex = InstructorNoa.getGroupList()
						.getSelectedIndex();

				Group group = InstructorNoa.getGroups().get(groupIndex);
				if (group.getStudentIps().isEmpty()) {
					return;
				} else {
					if (group.getStudentIps().size() != 2) {
						WebOptionPane
								.showMessageDialog(
										InstructorNoa.getCenterPanel(),
										"Intercom is only available for groups with two students",
										"Information",
										WebOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						StartIntercomCommand si;
						try {
							si = new StartIntercomCommand("", "",
									Integer.parseInt(Config.getParam("port")));

							// from student 0 to 1
							si.setFrom(group.getStudentIps().get(0));
							si.setTo(group.getStudentIps().get(1));
							InstructorNoa.getServerService().send(si);

							// from student 1 to 0
							si.setFrom(group.getStudentIps().get(1));
							si.setTo(group.getStudentIps().get(0));
							InstructorNoa.getServerService().send(si);

						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	protected static void SendFileActionListener() {
		FileManagerMain main = new FileManagerMain();
		main.setReceivingIps(new ArrayList<String>(Arrays
				.asList(getSelectedStudentIp())));
		main.setVisible(true);
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
		serverServiceTimer.setInterval(Integer.parseInt(Config
				.getParam("interval")));
		serverServiceTimer.start();

		// setup a service for sending commands to others
		InstructorNoa.setServerService(new ServerService());

		// setup a service to listen to clients commands
		ClientService clientService = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("serverPort")));
		clientService.start();
	}

	public static Window findWindow(Component c) {
		if (c == null) {
			return JOptionPane.getRootFrame();
		} else if (c instanceof Window) {
			return (Window) c;
		} else {
			return findWindow(c.getParent());
		}
	}

	public static Component getActiveCard() {
		Component card = null;
		for (Component comp : InstructorNoa.getCenterPanel().getComponents()) {
			if (comp.isVisible() == true) {
				card = comp;
			}
		}

		return card;
	}

	protected static String getSelectedStudentIp() {
		Component card = getActiveCard();
		String selectedStudent = null;
		if (((JComponent) card).getClientProperty("viewMode").equals(
				"thumbView")) {
			if (InstructorNoa.getDesktopPane().getSelectedFrame() != null) {
				selectedStudent = (String) InstructorNoa.getDesktopPane()
						.getSelectedFrame().getClientProperty("ip");
			} else
				return "";
		}

		return selectedStudent;
	}

}
