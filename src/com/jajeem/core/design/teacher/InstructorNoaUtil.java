package com.jajeem.core.design.teacher;

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
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import jrdesktop.viewer.ScreenPlayer;
import jrdesktop.viewer.Viewer;

import org.jitsi.examples.AVReceiveOnly;
import org.jscroll.widgets.RootDesktopPane;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.desktoppane.WebInternalFrame;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.table.WebTable;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.alee.managers.popup.WebPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.command.model.GetProgramListCommand;
import com.jajeem.command.model.StartApplicationCommand;
import com.jajeem.command.model.StartCallAllCommand;
import com.jajeem.command.model.StartConversationCommand;
import com.jajeem.command.model.StartIntercomCommand;
import com.jajeem.command.model.StartModelCommand;
import com.jajeem.command.model.StartSpeechCommand;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.model.StartVideoChatCommand;
import com.jajeem.command.model.StartWhiteBoardCommand;
import com.jajeem.command.model.StopCallAllCommand;
import com.jajeem.command.model.StopConversationCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.command.model.StopModelCommand;
import com.jajeem.command.model.StopVideoChatCommand;
import com.jajeem.command.model.TeacherLogoutCommand;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.command.service.ServerServiceTimer;
import com.jajeem.core.design.account.AccountPanel;
import com.jajeem.core.design.account.AdminPanel;
import com.jajeem.core.design.ui.CustomTeacherFrame;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.filemanager.design.FileManagerMain;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.design.alt.Quiz_Main;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.survey.design.alt.Survey_Main;
import com.jajeem.util.Config;
import com.jajeem.util.CustomPowerPanel;
import com.jajeem.util.FileUtil;
import com.jajeem.util.LnkParser;
import com.jajeem.util.Session;
import com.jajeem.util.WinRegistry;
import com.jajeem.util.i18n;

public class InstructorNoaUtil {

	private static ServerServiceTimer serverServiceTimer;
	final static WebPopup popup = new WebPopup();

	static Quiz_Main quiz = null;
	static Recorder recorder_frame = null;
	static Survey_Main survey = null;
	static Quiz_Main[] groupsQuizWindows = new Quiz_Main[15];
	static Survey_Main[] groupsSurveyWindows = new Survey_Main[15];
	public static ArrayList<String> recordingsList = new ArrayList<>();
	private static JInternalFrame previousFrame;
	private Thread _videoChat;
	private static Object lock = new Object();
	static WebButton programRestricButton;

	/*
	 * ***************** Right Panel Events **************************
	 */
	Component intl;
	private static JButton recorder_window;
	protected static boolean callAllActive = false;
	public static Object loadLock;

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
									JajeemExceptionHandler.logError(e);
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
							if (InstructorNoa.getDesktopPane()
									.getSelectedFrame() != null) {
								if (InstructorNoa.getSendOnly()
										.isTransmitting()
										|| InstructorNoa
												.getConversationIps()
												.contains(
														InstructorNoa
																.getDesktopPane()
																.getSelectedFrame()
																.getClientProperty(
																		"ip"))) {
									JOptionPane.showMessageDialog(
											InstructorNoa.getCenterPanel(),
											"Another voice or video function is already running, please stop it first",
											"Information",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							}

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
													JajeemExceptionHandler
															.logError(e);
													e.printStackTrace();
												}
											} else {
												JOptionPane.showMessageDialog(
														InstructorNoa
																.getCenterPanel(),
														i18n.getParam("Another voice or video function is already running, please stop it first"),
														i18n.getParam("Information"),
														JOptionPane.INFORMATION_MESSAGE);
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
										JajeemExceptionHandler.logError(e);
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

							if (InstructorNoa.getTransmitter().isTransmitting()
									|| InstructorNoa.getConversationIps()
											.contains(
													InstructorNoa
															.getDesktopPane()
															.getSelectedFrame()
															.getClientProperty(
																	"ip"))
									|| (InstructorNoa.getSendOnly()
											.isTransmitting() && InstructorNoa
											.getTransmittingType().equals(
													"intercom"))) {
								JOptionPane.showMessageDialog(
										InstructorNoa.getCenterPanel(),
										"Another voice or video function is already running, please stop it first",
										"Information",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}

							String selectedStudent = getSelectedStudentIp();

							if (selectedStudent != null
									&& selectedStudent != "") {
								try {
									if (!InstructorNoa.getSendOnly()
											.isTransmitting()) {
										ServerService serv = InstructorNoa
												.getServerService();
										StartVideoChatCommand cmd = new StartVideoChatCommand(
												InetAddress.getLocalHost()
														.getHostAddress(),
												Config.getParam("broadcastingIp"),
												Integer.parseInt(Config
														.getParam("port")));
										serv.send(cmd);
										InstructorNoa
												.getSendOnly()
												.setRemoteAddr(
														InetAddress
																.getByName(selectedStudent));
										_videoChat = new Thread(new Runnable() {

											@Override
											public void run() {
												try {
													button.setText(i18n
															.getParam("Trying..."));
													button.setEnabled(false);
													InstructorNoa.getSendOnly()
															.start("both");
													button.setText(i18n
															.getParam("Stop"));
													button.setEnabled(true);
													InstructorNoa
															.setTransmittingType("videoChat");
												} catch (Exception e) {
													try {
														InstructorNoa.getSendOnly().setTransmitting(false);
														button.setText(i18n
																.getParam("Video Chat"));
														button.setEnabled(true);
													} catch (Exception e1) {
														e1.printStackTrace();
													}
												}
											}
										});
										_videoChat.start();
									} else {
										if (InstructorNoa.getSendOnly()
												.getRemoteAddr()
												.getHostAddress()
												.equals(selectedStudent)) {
											if (InstructorNoa
													.getTransmittingType()
													.equals("videoChat")) {
												ServerService serv = InstructorNoa
														.getServerService();
												StopVideoChatCommand cmd = new StopVideoChatCommand(
														InetAddress
																.getLocalHost()
																.getHostAddress(),
														Config.getParam("broadcastingIp"),
														Integer.parseInt(Config
																.getParam("port")));
												serv.send(cmd);
												InstructorNoa.getSendOnly()
														.stop();
												button.setText(i18n
														.getParam("Video Chat"));
											}
										} else {
											JOptionPane.showMessageDialog(
													InstructorNoa
															.getCenterPanel(),
													i18n.getParam("Another voice or video function is already running, please stop it first"),
													i18n.getParam("Information"),
													JOptionPane.INFORMATION_MESSAGE);
										}
									}
								} catch (Exception e1) {
									JajeemExceptionHandler.logError(e1);
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
							jrdesktop.Config conf;
							VNCCaptureService vnc = Session.getModelingWindow();
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

											conf = null;
											conf = new jrdesktop.Config(
													false,
													"",
													selectedStudent,
													Integer.parseInt(Config
															.getParam("vncPort")),
													"admin", "admin", false,
													false);
											vnc = null;
											vnc = new VNCCaptureService();
											vnc.startClient(conf);
											Session.setModelingWindowHandle(vnc);
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
											try{
												vnc.stopClient();
											}catch(Exception e){
												e.printStackTrace();
											}
											button.setText(i18n
													.getParam("Modeling"));
											InstructorNoa.setModeling(false);
										}
									} catch (Exception e) {
										JajeemExceptionHandler.logError(e);
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
					recorder_window = ((JButton) c);
					((JButton) c).addActionListener(new ActionListener() {

						@Override
						// recorder action
						public void actionPerformed(ActionEvent arg0) {
							try {
								// boolean decorateFrames = WebLookAndFeel
								// .isDecorateDialogs();
								// WebLookAndFeel
								// .setDecorateDialogs(true);

								Recorder recorder = new Recorder(null, true,
										true);
								recorder.setLocationRelativeTo(rightButtonPanel);
								recorder.setVisible(true);
								getRecorder_Button().setEnabled(false);
								// Restoring frame decoration option
								// WebLookAndFeel
								// .setDecorateDialogs(decorateFrames);
							} catch (Exception e) {
								JajeemExceptionHandler.logError(e);
							}
						}
					});

					break;
				case "speech":
					((JButton) c).addActionListener(new ActionListener() {

						@SuppressWarnings("unused")
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
										InetAddress.getLocalHost()
												.getHostAddress(), Config
												.getParam("broadcastingIp"),
										Integer.parseInt(Config
												.getParam("port")));
								serv.send(cmd);
							} catch (Exception e) {
								JajeemExceptionHandler.logError(e);
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
											JajeemExceptionHandler.logError(e);
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
										try {
											JOptionPane
													.showMessageDialog(
															null,
															i18n.getParam("Cannot start exam on an empty group!\nPlease add some students to the group first!"));
										} catch (Exception e) {
											e.printStackTrace();
										}
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
											JajeemExceptionHandler.logError(e);
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
											JajeemExceptionHandler.logError(e);
										}
									}
								}).start();
							} catch (IOException ex) {
								JajeemExceptionHandler.logError(ex);
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
								if(InstructorNoa.getInstructorModel()!=null){
									if (InstructorNoa.getInstructorModel()
											.getUsername().equals("admin")) {
										new AdminPanel().setVisible(true);
									} else {
	
										new AccountPanel(InstructorNoa
												.getInstructorModel(),
												InstructorNoa.getCourseModel());
									}
								}
								else{
									WebOptionPane.showMessageDialog(null, "You have not logged in as an autheticated user,this might cause database or program corruptions!");									
								}
							} catch (Exception e) {
								JajeemExceptionHandler.logError(e);
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
												JajeemExceptionHandler
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
												JajeemExceptionHandler
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
														JajeemExceptionHandler
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
														JajeemExceptionHandler
																.logError(e);
														e.printStackTrace();
													}
												}
											}
										} catch (Exception e) {
											JajeemExceptionHandler.logError(e);
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

	@SuppressWarnings("unused")
	public void addEventsBottomPanel(final WebPanel bottomButtonPanel,
			final CustomTeacherFrame mainFrame) throws Exception {
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
													groupsSurveyWindows[groupIndex].
															getFrame().setVisible(true);
												} else {
													groupsSurveyWindows[groupIndex].getFrame()
															.toFront();
													groupsSurveyWindows[groupIndex].getFrame()
															.repaint();
												}
											} else {
												groupsSurveyWindows[groupIndex] = new Survey_Main(
														groupIndex,
														group.getStudentIps());
												groupsSurveyWindows[groupIndex].getFrame()
														.setVisible(true);
											}

										} catch (Exception e) {
											JajeemExceptionHandler.logError(e);
										}
									}
								}
							} else {
								if (Session.isSurveyWindowOpen()) {
									if (survey == null) {
										survey = new Survey_Main(-1, null);
										survey.getFrame().setVisible(true);
									} else {
										survey.getFrame().toFront();
										survey.getFrame().repaint();
									}
								} else {
									survey = new Survey_Main(-1, null);
									survey.getFrame().setVisible(true);
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
						@Override
						public void actionPerformed(ActionEvent arg0) {
							button.setEnabled(false);
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
													JajeemExceptionHandler
															.logError(e);
													e.printStackTrace();
												}
											}
										}).start();
									} catch (IOException e) {
										JajeemExceptionHandler.logError(e);
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
							button.setEnabled(true);
						}
					});
					break;
				case "internet":
					// in instructor noa!
					break;

				case "program":
					ProgramRestrictListInitializer init = new ProgramRestrictListInitializer((WebButton)button);
					button.addActionListener(init);
					programRestricButton = (WebButton)button;
					break;
				case "programStart":
					try {
						final DefaultListModel model = new DefaultListModel();
						final WebList programsList = new WebList(model);
						programsList.setVisibleRowCount(6);
						programsList.setSelectedIndex(0);
						programsList.setEditable(false);

						WebButtonPopup programPopupButton = new WebButtonPopup(
								(WebButton) button, PopupWay.upCenter);
						final WebButton chooseAppButton = new WebButton(
								i18n.getParam("Add"));
						final WebCheckBox sendToAll = new WebCheckBox(
								i18n.getParam("Send To All"));

						final int sizeOfProgramModel = model.getSize();

						chooseAppButton.addActionListener(new ActionListener() {
							private ProgramList fileChooser = new ProgramList(
									model);

							@Override
							public void actionPerformed(ActionEvent e) {
								fileChooser.setVisible(true);
							}
						});

						WebButton runButton = new WebButton(
								i18n.getParam("Run"));

						CustomPowerPanel panel = new CustomPowerPanel();
						WebScrollPane scrollpane = new WebScrollPane(
								programsList);
						scrollpane.setOpaque(true);
						GroupPanel programPopupContent = new GroupPanel(5,
								false, scrollpane, runButton, chooseAppButton,
								sendToAll);
						programPopupContent.setMargin(15);
						programPopupContent.setOpaque(false);
						programsList.setOpaque(false);

						panel.add(programPopupContent);
						programPopupButton.setMargin(5);
						programPopupButton.setContent(panel);

						programsList.addMouseListener(new MouseAdapter() {
							@Override
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
									if (sendToAll.isSelected()) {
										StartApplicationCommand sa;
										try {
											sa = new StartApplicationCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													Config.getParam("broadcastingIp"),
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
									} else {
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

				case "conversation":
					try {

						final DefaultListModel model = new DefaultListModel();
						final WebList programsList = new WebList(model);
						programsList.setVisibleRowCount(6);
						programsList.setSelectedIndex(0);
						programsList.setEditable(false);

						WebButtonPopup programPopupButton = new WebButtonPopup(
								(WebButton) button, PopupWay.upCenter);

						WebButton stopButton = new WebButton(
								i18n.getParam("Stop"));

						GroupPanel programPopupContent = new GroupPanel(5,
								false, new WebScrollPane(programsList),
								stopButton);
						programPopupContent.setMargin(15);
						programPopupContent.setOpaque(false);
						programsList.setOpaque(false);

						programPopupButton.setContent(programPopupContent);

						button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {

								model.clear();

								String stu1 = "";
								String stu2 = "";

								for (String pair : InstructorNoa
										.getConversationPairs()) {

									String[] ips = pair.split("\\|");

									stu1 = InstructorNoa
											.getStudentNameByIP(ips[0]);
									stu2 = InstructorNoa
											.getStudentNameByIP(ips[1]);

									try {
										model.addElement(stu1 + " "
												+ i18n.getParam("and") + " "
												+ stu2);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}

							}
						});

						stopButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								int index = programsList.getSelectedIndex();
								if (index < 0) {
									return;
								}

								String pair = InstructorNoa
										.getConversationPairs().get(index);

								String[] ips = pair.split("\\|");

								try {
									StopConversationCommand si;

									si = new StopConversationCommand(InetAddress
											.getLocalHost().getHostAddress(),
											ips[0], Integer.parseInt(Config
													.getParam("port")));
									si.setConversationTo(ips[1]);
									InstructorNoa.getServerService().send(si);

									InstructorNoa.getConversationPairs()
											.remove(pair);
									InstructorNoa.getConversationIps().remove(
											ips[0]);
									InstructorNoa.getConversationIps().remove(
											ips[1]);
									model.remove(programsList.getSelectedIndex());
									programsList.repaint();
								} catch (Exception e) {
									JajeemExceptionHandler.logError(e);
									e.printStackTrace();
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
							SwingConstants.HORIZONTAL);
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

								if ((InstructorNoa.getTransmitter()
										.isTransmitting() || InstructorNoa
										.getSendOnly().isTransmitting())
										&& !callAllActive) {
									JOptionPane.showMessageDialog(
											InstructorNoa.getCenterPanel(),
											"Another voice or video function is already running, please stop it first",
											"Information",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}

								if (InstructorNoa.getDesktopPane()
										.getSelectedFrame() != null) {
									if (InstructorNoa.getConversationIps()
											.contains(
													InstructorNoa
															.getDesktopPane()
															.getSelectedFrame()
															.getClientProperty(
																	"ip"))) {
										JOptionPane.showMessageDialog(
												InstructorNoa.getCenterPanel(),
												"Another voice or video function is already running, please stop it first",
												"Information",
												JOptionPane.INFORMATION_MESSAGE);
										return;
									}
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
									button.setIcon(new ImageIcon(
											InstructorNoa.class
													.getResource("/icons/noa_en/callall.png")));
									callAllActive = false;
									button.repaint();
								} else {
									StartCallAllCommand sm = new StartCallAllCommand(
											InetAddress.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")));
									InstructorNoa.getServerService().send(sm);
									InstructorNoa.getSendOnly().start("audio");
									button.setIcon(new ImageIcon(
											InstructorNoa.class
													.getResource("/icons/noa_en/callalloff.png")));
									button.setText(i18n.getParam("Stop"));
									callAllActive = true;
									button.repaint();
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
	@SuppressWarnings("unused")
	public static WebInternalFrame createFrame(
			final RootDesktopPane desktopPane, final String hostIp,
			final String hostName) throws NumberFormatException, Exception {
		String frameName = hostName;
		final WebInternalFrame internalFrame = new WebInternalFrame(frameName,
				false, false, false, false);

		// get current list of students, if some one is new, add him/her
		JInternalFrame[] frames = desktopPane.getAllFrames();
		List<String> listOfStudents = new ArrayList<String>();
		synchronized (lock) {
			for (JInternalFrame frame : frames) {
				listOfStudents.add((String) frame.getClientProperty("ip"));
			}
		}

		for (JInternalFrame frame : frames) {
			if (hostIp.equals(frame.getClientProperty("ip"))) {
				if (!((Viewer) frame.getClientProperty("vnc")).isConnected()) {
					jrdesktop.Config con = new jrdesktop.Config(false, "",
							hostIp,
							Integer.parseInt(Config.getParam("vncPort")),
							"admin", "admin", false, false);
					final Viewer vnc = new Viewer(con);
					vnc.StartThumbs(frame);
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

		synchronized (lock) {
			if (listOfStudents.contains(hostIp)) {
				return null;
			}
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

		try{
			vnc.StartThumbs(internalFrame);
		}
		catch(Exception e){
			System.out.println("");
		}

		internalFrame.setVisible(true);
		internalFrame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

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

		internalFrame.addInternalFrameListener(new InternalFrameAdapter() {
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
			public void internalFrameClosed(InternalFrameEvent arg0) {
				try {
					Session.getLoggedInStudents().remove(
							internalFrame.getClientProperty("ip").toString());
				} catch (Exception e) {

				}
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
				.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						internalFrameMouseClicked(e);
					}
				});

		((ScreenPlayer) ((JViewport) ((JScrollPane) ((JPanel) ((JLayeredPane) ((JRootPane) internalFrame
				.getComponent(0)).getComponent(1)).getComponent(0))
				.getComponent(0)).getComponent(0)).getComponent(0))
				.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						internalFrameMouseClicked(e);
					}
				});

		TooltipManager.setTooltip((internalFrame.getComponent(1)), i18n.getParam("No Group"));
		JPopupMenu.setDefaultLightWeightPopupEnabled(true);

		WebMenu menuItemgroups = new WebMenu(i18n.getParam("Grouping"));
		WebMenuItem menuItemUngroup = new WebMenuItem(i18n.getParam("Ungroup"));
		WebMenuItem menuItemGroupA = new WebMenuItem(i18n.getParam("Group A"));
		WebMenuItem menuItemGroupB = new WebMenuItem(i18n.getParam("Group B"));
		WebMenuItem menuItemGroupC = new WebMenuItem(i18n.getParam("Group C"));
		WebMenuItem menuItemGroupD = new WebMenuItem(i18n.getParam("Group D"));
		WebMenuItem menuItemGroupE = new WebMenuItem(i18n.getParam("Group E"));
		WebMenuItem menuItemGroupF = new WebMenuItem(i18n.getParam("Group F"));
		WebMenuItem menuItemGroupG = new WebMenuItem(i18n.getParam("Group G"));
		WebMenuItem menuItemGroupH = new WebMenuItem(i18n.getParam("Group H"));
		WebMenuItem menuItemGroupI = new WebMenuItem(i18n.getParam("Group I"));
		WebMenuItem menuItemGroupJ = new WebMenuItem(i18n.getParam("Group J"));
		WebMenuItem menuItemGroupK = new WebMenuItem(i18n.getParam("Group K"));
		WebMenuItem menuItemGroupL = new WebMenuItem(i18n.getParam("Group L"));
		WebMenuItem menuItemGroupM = new WebMenuItem(i18n.getParam("Group M"));
		WebMenuItem menuItemGroupN = new WebMenuItem(i18n.getParam("Group N"));
		WebMenuItem menuItemGroupO = new WebMenuItem(i18n.getParam("Group O"));

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

		WebMenu menuItemActions = new WebMenu(i18n.getParam("Actions"));
		WebMenuItem menuItemSendFile = new WebMenuItem(i18n.getParam("Send File"));
		WebMenuItem menuItemIntercom = new WebMenuItem(i18n.getParam("Intercom"));
		final WebMenu menuItemConversations = new WebMenu(
				i18n.getParam("Conversation With "));
		WebMenuItem menuItemMonitor = new WebMenuItem(i18n.getParam("Monitor"));
		WebMenuItem menuItemChat = new WebMenuItem(i18n.getParam("Chat"));
		WebMenuItem menuItemLock = new WebMenuItem(i18n.getParam("Lock"));
		WebMenuItem menuItemRecord = new WebMenuItem(i18n.getParam("Record"));
		WebMenuItem menuItemLogout = new WebMenuItem(i18n.getParam("Logout"));
		menuItemActions.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (InstructorNoa.getConversationIps().contains(
						InstructorNoa.getDesktopPane().getSelectedFrame()
								.getClientProperty("ip"))) {
					menuItemConversations.setEnabled(false);
				} else {
					menuItemConversations.setEnabled(true);
				}
			}
		});
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
		menuItemConversations.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				menuItemConversations.removeAll();
				WebMenuItem tempItem = null;
				for (int i = 0; i < InstructorNoa.getDesktopPane()
						.getAllFrames().length; i++) {
					if (InstructorNoa.getDesktopPane()
							.getSelectedFrame() != null && !InstructorNoa.getDesktopPane().getAllFrames()[i]
							.equals(InstructorNoa.getDesktopPane()
									.getSelectedFrame())) {
						if(InstructorNoa.getDesktopPane()
								.getSelectedFrame().getClientProperty("live").equals(true))
							tempItem = new WebMenuItem(InstructorNoa
									.getDesktopPane().getAllFrames()[i]
									.getClientProperty("username").toString());
						final int index = i;
						if(tempItem==null)
							continue;
						tempItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								StartIntercomePair(InstructorNoa
										.getDesktopPane().getSelectedFrame()
										.getClientProperty("ip").toString(),
										InstructorNoa.getDesktopPane()
												.getAllFrames()[index]
												.getClientProperty("ip")
												.toString());
							}
						});
						menuItemConversations.add(tempItem);
					}
				}
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
		menuItemLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LogoutActionListener();
			}
		});

		menuItemActions.add(menuItemSendFile);
		menuItemActions.add(menuItemIntercom);
		menuItemActions.add(menuItemConversations);
		menuItemActions.add(menuItemMonitor);
		menuItemActions.add(menuItemChat);
		menuItemActions.add(menuItemLock);
		menuItemActions.add(menuItemRecord);
		menuItemActions.add(menuItemLogout);

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
			@Override
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
						try {
							TooltipManager.setTooltip(
									(internalFrame.getComponent(1)), i18n.getParam("No Group"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
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
								(internalFrame.getComponent(1)), "Group "
										+ group);
					} else if (currentGroup > -1 && groupIndex > -1) {
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentIps().remove(hostIp);
						InstructorNoa.getGroups().get(currentGroup)
								.getStudentNames().remove(hostName);

						InstructorNoa.getGroups().get(groupIndex)
								.getStudentIps().add(hostIp);
						InstructorNoa.getGroups().get(groupIndex)
								.getStudentNames().add(hostName);
						try {
							TooltipManager.setTooltip(
									(internalFrame.getComponent(1)), i18n.getParam("Group ")
											+ group);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
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
		synchronized (lock) {
			frames = desktopPane.getAllFrames();
			listOfStudents = new ArrayList<String>();
			for (JInternalFrame frame : frames) {
				listOfStudents.add((String) frame.getClientProperty("ip"));
			}
			if (!listOfStudents.contains(hostIp)) {
				DefaultTableModel model = (DefaultTableModel) InstructorNoa
						.getStudentListTable().getModel();
				boolean found = false;
				for (int i = 0; i < model.getRowCount(); i++) {
					String ip = model.getValueAt(i, 0).toString();
					if(ip.equals(hostIp)){
						model.removeRow(i);
						model.addRow(new Object[]{hostIp,hostName});
						found = true;
						break;
					}
				}
				if(!found)
					model.addRow(new Object[] { hostIp, hostName, "Not Logged In" });

				desktopPane.add(internalFrame, BorderLayout.CENTER);
				InstructorNoa.getDesktopPaneScroll().getDesktopMediator()
						.tileInternalFrames();
			}
		}

		return internalFrame;
	}

	protected static void internalFrameMouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			if (previousFrame != null
					&& !previousFrame.equals(InstructorNoa.getDesktopPane()
							.getSelectedFrame())) {
				previousFrame.putClientProperty("isselected", false);
			}
			if (InstructorNoa.getDesktopPane().getSelectedFrame()
					.getClientProperty("isselected").equals(false)) {
				InstructorNoa.getDesktopPane().getSelectedFrame()
						.putClientProperty("isselected", true);
				try {
					InstructorNoa.getDesktopPane().getSelectedFrame()
							.setSelected(true);
					previousFrame = InstructorNoa.getDesktopPane()
							.getSelectedFrame();
				} catch (PropertyVetoException ex) {
					ex.printStackTrace();
				}
			} else {
				InstructorNoa.getDesktopPane().getSelectedFrame()
						.putClientProperty("isselected", false);
				try {
					InstructorNoa.getDesktopPane().getSelectedFrame()
							.setSelected(false);
					previousFrame = InstructorNoa.getDesktopPane()
							.getSelectedFrame();
				} catch (PropertyVetoException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	protected static void LogoutActionListener() {
		try {
			String username = InstructorNoa.getDesktopPane().getSelectedFrame()
					.getClientProperty("ip").toString();
			Session.getLoggedInStudents().remove(username);
		} catch (Exception e) {
		}
		TeacherLogoutCommand cmd;
		try {
			cmd = new TeacherLogoutCommand(InetAddress.getLocalHost()
					.getHostAddress(), InstructorNoa.getDesktopPane()
					.getSelectedFrame().getClientProperty("ip").toString(),
					Integer.parseInt(Config.getParam("port")));
			InstructorNoa.getServerService().send(cmd);
		} catch (Exception e) {
		}
		try {
			InstructorNoa.getDesktopPane().getSelectedFrame().dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
							JajeemExceptionHandler.logError(e);
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
							JajeemExceptionHandler.logError(e);
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
				JajeemExceptionHandler.logError(e);
				e.printStackTrace();
			}
			VNCCaptureService vnc = new VNCCaptureService();
			vnc.startClient(conf);
		}
	}

	protected static void IntercomActionListener() {
		if (InstructorNoa.getSendOnly().isTransmitting()
				|| InstructorNoa.getConversationIps().contains(
						InstructorNoa.getDesktopPane().getSelectedFrame()
								.getClientProperty("ip"))) {
			try {
				WebOptionPane
						.showMessageDialog(
								InstructorNoa.getCenterPanel(),
								i18n.getParam("Another voice or video function is already running, please stop it first"),
								i18n.getParam("Information"), JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

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
					if (InstructorNoa.getTransmitter().isTransmitting()) {
						if (InstructorNoa.getTransmitter().getRemoteAddr()
								.getHostAddress().equals(selectedStudent)) {
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
								JajeemExceptionHandler.logError(e);
								e.printStackTrace();
							}
						} else {
							WebOptionPane
									.showMessageDialog(
											InstructorNoa.getCenterPanel(),
											i18n.getParam("Another voice or video function is already running, please stop it first"),
											i18n.getParam("Information"),
											JOptionPane.INFORMATION_MESSAGE);
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
						InstructorNoa.setIntercomText(i18n.getParam("Stop"));
					}
				} catch (Exception e) {
					JajeemExceptionHandler.logError(e);
					e.printStackTrace();
				}
			}
		} else if (((JComponent) card).getClientProperty("viewMode").equals(
				"groupView")) {
			return;
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
			} else {
				return "";
			}
		}

		return selectedStudent;
	}

	private static void StartIntercomePair(String ipFrom, String ipTo) {

//		StartIntercomCommand si;
//		try {
//			si = new StartIntercomCommand(InetAddress.getLocalHost()
//					.getHostAddress(), ipFrom, Integer.parseInt(Config
//					.getParam("port")));
//			si.setFrom(ipTo);
//			InstructorNoa.getServerService().send(si);
//
//			si.setFrom(ipFrom);
//			si.setTo(ipTo);
//			InstructorNoa.getServerService().send(si);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//


		try {
			StartConversationCommand command = new StartConversationCommand(InetAddress.getLocalHost().getHostAddress(), ipFrom, Integer.parseInt(Config.getParam("port")));
			command.setConversationTo(ipTo);
			InstructorNoa.getServerService().send(command);
			
			InstructorNoa.getConversationIps().add(ipFrom);
			InstructorNoa.getConversationIps().add(ipTo);
			InstructorNoa.getConversationPairs().add(ipFrom + "|" + ipTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean getCallAllActive() {
		return callAllActive;
	}

	public static JButton getRecorder_Button() {
		return recorder_window;
	}
	
	protected static ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected static Collection<? extends File> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<File> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i));
		}
		return list;
	}

	
	public static void loadProgramRestrictList(ArrayList<File> lnkList,
			ArrayList<File> exeList) {
		ProgramRestrictListInitializer.loadProgramList(lnkList,exeList);
	}
}

class ProgramRestrictListInitializer implements ActionListener{
	
	static WebButton button;
	static WebButtonPopup programPopupButton;
	static JPanel cardsPanel = new JPanel();
	private static ArrayList<File> lnkList;
	private static ArrayList<File> exeList;
	static Object lock = new Object(); 
	
	public ProgramRestrictListInitializer(WebButton btn){
		this.button = btn;
		programPopupButton = new WebButtonPopup(
				(WebButton) button,
				PopupWay.upCenter);
		programPopupButton.setMargin(5);
		cardsPanel.setLayout(new CardLayout());
		WebPanel loadingPanel = getLoadingJPanel();
		cardsPanel.removeAll();
		cardsPanel.add(loadingPanel,"Loading");
		programPopupButton.setContent(cardsPanel);
	}
	
	public static void loadProgramList(ArrayList<File> lnkLst,
			ArrayList<File> exeLst) {
		lnkList = lnkLst;
		exeList = exeLst;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		WebPanel loadingPanel = getLoadingJPanel();
		cardsPanel.removeAll();
		cardsPanel.add(loadingPanel,"Loading");
		CardLayout cl = (CardLayout)(cardsPanel.getLayout());
	    cl.show(cardsPanel, "Loading");
		
		Component card = null;
		for (Component comp : InstructorNoa.getCenterPanel()
				.getComponents()) {
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
					GetProgramListCommand proglistCommand = new GetProgramListCommand(InetAddress
							.getLocalHost().getHostAddress(), "",
							Integer.parseInt(Config.getParam("port")));
					proglistCommand.setTo(selectedStudent);
					InstructorNoa.getServerService().send(proglistCommand);
					
					synchronized (lock) {
						lock.wait();
					}
					
					final List<String> fileListModel = new ArrayList<String>();
					final DefaultTableModel model = getDefaultTableModel(fileListModel,
							lnkList, exeList);
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

					CustomPowerPanel panel = new CustomPowerPanel();
					GroupPanel programPopupContent = new GroupPanel(
							5, false, new WebScrollPane(
									programsList));
					programPopupContent.setMargin(5);
					programPopupContent.setOpaque(false);
					programsList.setOpaque(false);

					panel.add(programPopupContent);
					cardsPanel.removeAll();
					cardsPanel.add(panel,"content");
				    cl.show(cardsPanel, "content");
				    programPopupButton.showPopup(button);
				    
					setProgramListClickEvent(fileListModel,
							programsList);
					
				} catch (Exception e) {
				}
			}
			else{
				final List<String> fileListModel = new ArrayList<String>();
				try {
					final DefaultTableModel model = getLocalProgList(fileListModel);

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

					CustomPowerPanel panel = new CustomPowerPanel();
					GroupPanel programPopupContent = new GroupPanel(
							5, false, new WebScrollPane(
									programsList));
					programPopupContent.setMargin(5);
					programPopupContent.setOpaque(false);
					programsList.setOpaque(false);

					panel.add(programPopupContent);
					cardsPanel.removeAll();
					cardsPanel.add(panel,"content");
					cl.show(cardsPanel, "content");

					setProgramListClickEvent(fileListModel,
							programsList);
				} catch (Exception e) {

				}
			}
		}
		else{
			final List<String> fileListModel = new ArrayList<String>();
			try {
				final DefaultTableModel model = getLocalProgList(fileListModel);

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

				CustomPowerPanel panel = new CustomPowerPanel();
				GroupPanel programPopupContent = new GroupPanel(
						5, false, new WebScrollPane(
								programsList));
				programPopupContent.setMargin(5);
				programPopupContent.setOpaque(false);
				programsList.setOpaque(false);

				panel.add(programPopupContent);
//				programPopupButton.removeAll();
				programPopupButton.setContent(panel);
				programPopupButton.doLayout();
				programPopupButton.update(programPopupButton.getGraphics());
				programPopupButton.validate();
				programPopupButton.repaint();

				setProgramListClickEvent(fileListModel,
						programsList);
			} catch (Exception e) {

			}
		}
	}

	private WebPanel getLoadingJPanel() {
		CustomPowerPanel panel1 = new CustomPowerPanel();
		
		JLabel lblNewLabel = new JLabel("Loading Content,Wait!");
		GroupLayout gl_panel = new GroupLayout(panel1);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(85)
					.addComponent(lblNewLabel)
					.addContainerGap(100, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(114)
					.addComponent(lblNewLabel)
					.addContainerGap(124, Short.MAX_VALUE))
		);
		panel1.setLayout(gl_panel);
		return panel1;
	}

	private static void setProgramListClickEvent(
			final List<String> fileListModel,
			final WebTable programsList) {
		programsList
				.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(
							MouseEvent evt) {
						boolean value = (boolean) programsList.getValueAt(
								programsList
										.getSelectedRow(),
								1);

						if (value == true) {
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
								try {
									WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
											InetAddress
													.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")),
											(fileListModel
													.get(programsList
															.getSelectedRow())),
											true);
									InstructorNoa
											.getServerService()
											.send(ic);
								} catch (Exception e) {
									JajeemExceptionHandler
											.logError(e);
									e.printStackTrace();
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
											JajeemExceptionHandler
													.logError(e);
											e.printStackTrace();
										}

									}
								}
							}
						} else {
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
								try {
									WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
											InetAddress
													.getLocalHost()
													.getHostAddress(),
											Config.getParam("broadcastingIp"),
											Integer.parseInt(Config
													.getParam("port")),
											(fileListModel
													.get(programsList
															.getSelectedRow())),
											false);
									InstructorNoa
											.getServerService()
											.send(ic);
								} catch (Exception e) {
									JajeemExceptionHandler
											.logError(e);
									e.printStackTrace();
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
											JajeemExceptionHandler
													.logError(e);
											e.printStackTrace();
										}

									}
								}
							}
						}
					}
				});
	}

	private DefaultTableModel getLocalProgList(
			final List<String> fileListModel)
			throws IllegalAccessException,
			InvocationTargetException {
		String pathToStartMenu = WinRegistry
				.readString(
						WinRegistry.HKEY_LOCAL_MACHINE,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
						"Common Start Menu")
				+ "\\Programs";

		FileUtil fileUtil = new FileUtil();
		final File[] tempfileList = fileUtil
				.finder(pathToStartMenu);
		final ArrayList<File> listOfAllLinks = new ArrayList<>();
		final ArrayList<File> lnkList = new ArrayList<>();
		final ArrayList<File> exeList = new ArrayList<>();
		for (int i = 0; i < tempfileList.length; i++) {
			if (tempfileList[i].isDirectory()) {
				listOfAllLinks
						.addAll(InstructorNoaUtil.getPath(InstructorNoaUtil.getDirectoryContent(tempfileList[i])));
			} else {
				listOfAllLinks.add(tempfileList[i]);
			}
		}

		Collections.sort(listOfAllLinks);

		for (int i = 0; i < listOfAllLinks.size(); i++) {
			if (listOfAllLinks.get(i).getName()
					.indexOf(".") != -1) {
				String ext = listOfAllLinks
						.get(i)
						.getName()
						.substring(
								listOfAllLinks
										.get(i)
										.getName()
										.indexOf(
												"."));
				// String path =
				// listOfAllLinks.get(i).getParent();
				// String fileName =
				// listOfAllLinks.get(i)
				// .getName();
				if (ext.equals(".lnk")) {
					try {
						LnkParser parser = new LnkParser(
								listOfAllLinks
										.get(i));
						if (parser
								.getRealFilename()
								.contains(".exe")) {
							lnkList.add(listOfAllLinks
									.get(i));
							exeList.add(new File(
									parser.getRealFilename()));
						}
					} catch (Exception e) {

					}
				}
				// System.out.println(new
				// LnkParser(fileList[i]).getRealFilename());
			}
		}

		final DefaultTableModel model = getDefaultTableModel(fileListModel,
				lnkList, exeList);
		return model;
	}

	private static DefaultTableModel getDefaultTableModel(
			final List<String> fileListModel, final ArrayList<File> lnkList,
			final ArrayList<File> exeList) {
		final DefaultTableModel model = (new DefaultTableModel(
				new Object[][] {}, new String[] {
						"Name", "status" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					String.class, Boolean.class };

			@Override
			public Class<?> getColumnClass(
					int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		for (int i = 0; i < lnkList.size(); i++) {
			File file = lnkList.get(i);
			if (file.getName().indexOf(".") != -1) {
				String extension = file
						.getName()
						.substring(
								file.getName()
										.indexOf(
												"."));
				if (extension.equals(".lnk")) {
					fileListModel.add(exeList
							.get(i).getName());
					model.addRow(new Object[] {
							file.getParentFile()
									.getName()
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
		return model;
	}
}