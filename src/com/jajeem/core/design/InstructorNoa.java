package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;
import org.jitsi.service.libjitsi.LibJitsi;
import org.jscroll.JScrollDesktopPane;
import org.jscroll.widgets.RootDesktopPane;

import com.alee.extended.list.WebCheckBoxListModel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.LockCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.util.BackgroundPanel;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.Query;
import com.sun.org.apache.regexp.internal.REProgram;

public class InstructorNoa {

	private WebFrame frame;
	private static JScrollDesktopPane desktopPaneScroll;
	private static WebPanel centerPanel;
	private static WebTable studentListTable;

	private static AVTransmit2 transmitter;
	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static List<Chat> chatList = new ArrayList<Chat>();
	private static ServerService serverService;
	private static List<Group> groups = new ArrayList<Group>();

	static WebButton intercomButton = new WebButton();
	private static WebList groupList = new WebList();
	private static boolean modeling = false;

	WebCheckBoxListModel programModel = new WebCheckBoxListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructorNoa window = new InstructorNoa();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public InstructorNoa() throws Exception {

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();

			// Start LibJitsi for first time
			LibJitsi.start();
//			setTransmitter(new AVTransmit2("5000", "", "10000"));
			// setReceiver(new AVReceiveOnly("10010", "", "5010"));

			InstructorNoaUtil.networkSetup();

			initialize();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initialize() throws IOException {
		frame = new WebFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				InstructorNoa.class.getResource("/icons/noa/teacher.png")));
		frame.setShowMinimizeButton(false);
		frame.setTitle("iCalabo");
		frame.setShowResizeCorner(false);
		frame.getContentPane().setBackground(new Color(56, 107, 170));
		frame.setBounds(10, 0, 1021, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		WebPanel centerPanel = new WebPanel();
		InstructorNoa.setCenterPanel(centerPanel);
		WebPanel centerListPanel = new WebPanel();
		centerPanel.add(centerListPanel);
		centerPanel.setBackground(new Color(255, 255, 255));
		WebPanel centerGroupPanel = new WebPanel();
		centerGroupPanel.setBackground(new Color(255, 255, 255));

		final DefaultTableModel model = new DefaultTableModel();

		model.addColumn("PC IP");
		model.addColumn("Student name");

		WebTable table = new WebTable(model);
		setStudentListTable(table);
		table.setBackground(new Color(237, 246, 253));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		WebScrollPane scrollPanel = new WebScrollPane(table);
		scrollPanel.setDrawBorder(false);

		WebPopupMenu groupPopup = new WebPopupMenu();
		groupPopup.add(new WebMenuItem("Empty"));
		getGroupList().setComponentPopupMenu(groupPopup);

		final DefaultListModel groupListModel = new DefaultListModel();
		getGroupList().setModel(groupListModel);

		getGroupList().setModel(new AbstractListModel() {
			String[] values = new String[] { "Group A", "Group B", "Group C",
					"Group D", "Group E", "Group F", "Group G", "Group H",
					"Group I", "Group J", "Group K", "Group L", "Group M", "Group N", "Group O" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		groups.add(new Group("Group A", 0, "#FF0000"));
		groups.add(new Group("Group B", 1, "#00FFFF"));
		groups.add(new Group("Group C", 2, "#0000FF"));
		groups.add(new Group("Group D", 3, "#0000A0"));
		groups.add(new Group("Group E", 4, "#800080"));
		groups.add(new Group("Group F", 5, "#FFFF00"));
		groups.add(new Group("Group G", 6, "#00FF00"));
		groups.add(new Group("Group H", 7, "#FF00FF"));
		groups.add(new Group("Group I", 8, "#800000"));
		groups.add(new Group("Group J", 9, "#F52887"));
		groups.add(new Group("Group K", 10, "#F52887"));
		groups.add(new Group("Group L", 11, "#F52887"));
		groups.add(new Group("Group M", 12, "#F52887"));
		groups.add(new Group("Group N", 13, "#F52887"));
		groups.add(new Group("Group O", 14, "#F52887"));

		WebScrollPane scrollGroupPanel = new WebScrollPane(getGroupList());
		scrollGroupPanel.setDrawBorder(false);

		WebPanel rightButtonPanel = new WebPanel();
		rightButtonPanel.setBackground(new Color(56, 107, 170));

		WebPanel bottomButtonPanel = new WebPanel();
		bottomButtonPanel.setBackground(new Color(58, 109, 175));

		WebPanel bottomLogoPanel = new WebPanel();
		bottomLogoPanel.setBackground(new Color(58, 109, 175));

		WebPanel topButtonPanel = new WebPanel();
		topButtonPanel.setShadeWidth(20);
		topButtonPanel.setBackground(new Color(56, 107, 170));

		WebButton volumeButton = new WebButton();
		volumeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		volumeButton.setDrawShade(false);
		volumeButton.setRound(10);
		volumeButton.setTopSelectedBgColor(new Color(75, 113, 158));
		volumeButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		volumeButton.setForeground(Color.WHITE);
		volumeButton.putClientProperty("key", "volume");
		volumeButton.setText("Volume Control");
		volumeButton.setBottomBgColor(new Color(235, 105, 11));
		volumeButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(volumeButton);

		WebButton callAllButton = new WebButton();
		callAllButton.putClientProperty("key", "callAll");
		callAllButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		callAllButton.setDrawShade(false);
		callAllButton.setRound(10);
		callAllButton.setTopSelectedBgColor(new Color(75, 113, 158));
		callAllButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		callAllButton.setForeground(Color.WHITE);
		callAllButton.setText("Call All");
		callAllButton.setBottomBgColor(new Color(235, 105, 11));
		callAllButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(callAllButton);

		WebButton viewModeButton = new WebButton();
		viewModeButton.putClientProperty("key", "viewMode");
		viewModeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		viewModeButton.setDrawShade(false);
		viewModeButton.setRound(10);
		viewModeButton.setTopSelectedBgColor(new Color(75, 113, 158));
		viewModeButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		viewModeButton.setForeground(Color.WHITE);
		viewModeButton.setText("View Mode");
		viewModeButton.setBottomBgColor(new Color(235, 105, 11));
		viewModeButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(viewModeButton);

		WebButton languageButton = new WebButton();
		languageButton.putClientProperty("key", "language");
		languageButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		languageButton.setDrawShade(false);
		languageButton.setRound(10);
		languageButton.setTopSelectedBgColor(new Color(75, 113, 158));
		languageButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		languageButton.setForeground(Color.WHITE);
		languageButton.setText("EN/FA");
		languageButton.setBottomBgColor(new Color(235, 105, 11));
		languageButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(languageButton);

		BackgroundPanel topPanel = new BackgroundPanel(Toolkit
				.getDefaultToolkit().getImage(
						InstructorNoa.class
								.getResource("/icons/noa/top_logo.png")));
		topPanel.setUndecorated(false);
		topPanel.setWebColored(false);
		topPanel.setTransparentAdd(false);
		topPanel.setDrawBackground(false);
		topPanel.setBackground(new Color(56, 107, 170));
		topPanel.setShadeWidth(20);
		topPanel.setRound(2);
		topPanel.setBackground(new Color(56, 107, 170));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				bottomButtonPanel,
																				0,
																				0,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED))
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								centerPanel,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								753,
																								Short.MAX_VALUE)
																						.addComponent(
																								topButtonPanel,
																								Alignment.LEADING,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addGroup(
																								Alignment.LEADING,
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												topPanel,
																												GroupLayout.PREFERRED_SIZE,
																												741,
																												Short.MAX_VALUE)
																										.addGap(12)))
																		.addGap(6)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																rightButtonPanel,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																bottomLogoPanel,
																GroupLayout.DEFAULT_SIZE,
																226,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				topPanel,
																				GroupLayout.PREFERRED_SIZE,
																				129,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				topButtonPanel,
																				GroupLayout.PREFERRED_SIZE,
																				37,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				centerPanel,
																				GroupLayout.DEFAULT_SIZE,
																				361,
																				Short.MAX_VALUE))
														.addComponent(
																rightButtonPanel,
																GroupLayout.DEFAULT_SIZE,
																539,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																bottomButtonPanel,
																GroupLayout.PREFERRED_SIZE,
																51,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																bottomLogoPanel,
																GroupLayout.PREFERRED_SIZE,
																42,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(
				Alignment.LEADING).addGap(0, 860, Short.MAX_VALUE));
		gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(
				Alignment.LEADING).addGap(0, 107, Short.MAX_VALUE));
		topPanel.setLayout(gl_topPanel);
		// create the scrollable desktop instance and add it to the JFrame
		JScrollDesktopPane scrollableDesktop = new JScrollDesktopPane();
		setDesktopPaneScroll(scrollableDesktop);

		// desktopPane.setBackground(new Color(237, 246, 253));
		centerPanel.setBackground(new Color(237, 246, 253));
		GroupLayout gl_desktopPane = new GroupLayout(getDesktopPaneScroll());
		gl_desktopPane.setAutoCreateContainerGaps(true);
		gl_desktopPane.setAutoCreateGaps(true);
		gl_desktopPane.setHorizontalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		gl_desktopPane.setVerticalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		centerPanel.setLayout(new CardLayout(0, 0));
		getDesktopPaneScroll().putClientProperty("viewMode", "thumbView");
		centerListPanel.putClientProperty("viewMode", "listView");
		centerGroupPanel.putClientProperty("viewMode", "groupView");
		centerPanel.add(scrollableDesktop, "thumbsView");
		centerPanel.add(centerListPanel, "listView");
		centerPanel.add(centerGroupPanel, "groupView");
		centerListPanel.setLayout(new BorderLayout(0, 0));
		centerGroupPanel.setLayout(new BorderLayout(0, 0));
		centerGroupPanel.add(scrollGroupPanel);

		centerListPanel.add(scrollPanel);

		topButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		topButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		bottomButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		topButtonPanel.setLayout(new GridLayout(0, 4, 0, 0));
		centerListPanel.setBackground(new Color(237, 246, 253));
		centerGroupPanel.setBackground(new Color(237, 246, 253));
		scrollGroupPanel.setBackground(new Color(237, 246, 253));
		getGroupList().setBackground(new Color(237, 246, 253));
		centerPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { getDesktopPaneScroll(), centerListPanel,
						centerGroupPanel }));

		WebButton surveyButton = new WebButton();
		surveyButton.setIconTextGap(30);
		surveyButton.putClientProperty("key", "survey");
		surveyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		surveyButton.setDrawShade(false);
		surveyButton.setRound(10);
		surveyButton.setTopSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setForeground(Color.WHITE);
		surveyButton.setText("Survey");
		surveyButton.setBottomBgColor(new Color(225, 234, 244));
		surveyButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(surveyButton);

		WebButton whiteBoardButton = new WebButton();
		whiteBoardButton.setIconTextGap(30);
		whiteBoardButton.putClientProperty("key", "whiteBoard");
		whiteBoardButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		whiteBoardButton.setDrawShade(false);
		whiteBoardButton.setRound(10);
		whiteBoardButton.setTopSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setForeground(Color.WHITE);
		whiteBoardButton.setText("White board");
		whiteBoardButton.setBottomBgColor(new Color(225, 234, 244));
		whiteBoardButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(whiteBoardButton);

		WebButton powerButton = new WebButton();
		powerButton.setIconTextGap(30);
		powerButton.putClientProperty("key", "pcController");
		powerButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		powerButton.setDrawShade(false);
		powerButton.setRound(10);
		powerButton.setTopSelectedBgColor(new Color(75, 113, 158));
		powerButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		powerButton.setForeground(Color.WHITE);
		powerButton.setText("PC Controller");
		powerButton.setBottomBgColor(new Color(225, 234, 244));
		powerButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(powerButton);

		WebButton powerOffButton = new WebButton("Power Off");
		powerOffButton.putClientProperty("key", "powerOff");
		powerOffButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PowerCommand powerCommand;
				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					if (getDesktopPane().getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) getDesktopPane()
								.getSelectedFrame().getClientProperty("ip");

						try {
							powerCommand = new PowerCommand(InetAddress
									.getLocalHost().getHostAddress(), "",
									Integer.parseInt(Config.getParam("port")),
									"");
							powerCommand.setTo(selectedStudent);
							powerCommand.setType("turnOff");
							serverService.send(powerCommand);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!groupList.isSelectionEmpty()) {
						int groupIndex = groupList.getSelectedIndex();

						Group group = groups.get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							return;
						} else {
							try {
								powerCommand = new PowerCommand(InetAddress
										.getLocalHost().getHostAddress(), "",
										Integer.parseInt(Config
												.getParam("port")), "");
								powerCommand.setType("logOff");

								for (String studentIp : group.getStudentIps()) {
									powerCommand.setTo(studentIp);
									serverService.send(powerCommand);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton logOffButton = new WebButton("Log Off");
		logOffButton.putClientProperty("key", "logOff");
		logOffButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PowerCommand powerCommand;
				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					if (getDesktopPane().getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) getDesktopPane()
								.getSelectedFrame().getClientProperty("ip");

						try {
							powerCommand = new PowerCommand(InetAddress
									.getLocalHost().getHostAddress(), "",
									Integer.parseInt(Config.getParam("port")),
									"");
							powerCommand.setTo(selectedStudent);
							powerCommand.setType("logOff");
							serverService.send(powerCommand);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!groupList.isSelectionEmpty()) {
						int groupIndex = groupList.getSelectedIndex();

						Group group = groups.get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							return;
						} else {
							try {
								powerCommand = new PowerCommand(InetAddress
										.getLocalHost().getHostAddress(), "",
										Integer.parseInt(Config
												.getParam("port")), "");
								powerCommand.setType("logOff");

								for (String studentIp : group.getStudentIps()) {
									powerCommand.setTo(studentIp);
									serverService.send(powerCommand);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		WebButton restartButton = new WebButton("Restart");
		restartButton.putClientProperty("key", "restart");
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PowerCommand powerCommand;
				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					if (getDesktopPane().getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) getDesktopPane()
								.getSelectedFrame().getClientProperty("ip");

						try {
							powerCommand = new PowerCommand(InetAddress
									.getLocalHost().getHostAddress(), "",
									Integer.parseInt(Config.getParam("port")),
									"");
							powerCommand.setTo(selectedStudent);
							powerCommand.setType("logOff");
							serverService.send(powerCommand);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!groupList.isSelectionEmpty()) {
						int groupIndex = groupList.getSelectedIndex();

						Group group = groups.get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							return;
						} else {
							try {
								powerCommand = new PowerCommand(InetAddress
										.getLocalHost().getHostAddress(), "",
										Integer.parseInt(Config
												.getParam("port")), "");
								powerCommand.setType("restart");

								for (String studentIp : group.getStudentIps()) {
									powerCommand.setTo(studentIp);
									serverService.send(powerCommand);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton lockButton = new WebButton("Lock/Unlock");
		lockButton.putClientProperty("key", "lock");
		lockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LockCommand lockCommand;
				if (getDesktopPane().getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) getDesktopPane()
							.getSelectedFrame().getClientProperty("ip");
					try {
						lockCommand = new LockCommand(InetAddress
								.getLocalHost().getHostAddress(),
								selectedStudent, Integer.parseInt(Config
										.getParam("port")));
						serverService.send(lockCommand);
						if (!(boolean) getDesktopPane().getSelectedFrame()
								.getClientProperty("lock")) {
							getDesktopPane().getSelectedFrame()
									.putClientProperty("lock", true);
							getDesktopPane()
									.getSelectedFrame()
									.setFrameIcon(
											new ImageIcon(
													ImageIO.read(InstructorNoa.class
															.getResourceAsStream("/icons/noa/lock.png"))));

						} else {
							getDesktopPane().getSelectedFrame()
									.putClientProperty("lock", false);
							if (getDesktopPane().getSelectedFrame()
									.isSelected()) {
								getDesktopPane()
										.getSelectedFrame()
										.setFrameIcon(
												new ImageIcon(
														ImageIO.read(InstructorNoa.class
																.getResourceAsStream("/icons/menubar/check.png"))));
							} else {
								getDesktopPane()
										.getSelectedFrame()
										.setFrameIcon(
												new ImageIcon(
														ImageIO.read(InstructorNoa.class
																.getResourceAsStream("/icons/menubar/student.png"))));
							}
						}
						// getDesktopPane().getSelectedFrame().updateUI();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		WebButtonPopup pcControllerPopupButton = new WebButtonPopup(
				powerButton, PopupWay.upCenter);

		GroupPanel pcControllerPopupContent = new GroupPanel(5, false,
				powerOffButton, logOffButton, restartButton, lockButton);
		pcControllerPopupContent.setMargin(15);

		pcControllerPopupButton.setContent(pcControllerPopupContent);

		WebButton internetButton = new WebButton();
		internetButton.putClientProperty("key", "internet");
		WebButtonPopup internetPopupButton = new WebButtonPopup(internetButton,
				PopupWay.upCenter);

		WebButton internetSendWebsiteButton = new WebButton("Send Website");
		internetSendWebsiteButton.putClientProperty("key", "internetWebsite");
		internetSendWebsiteButton.setHorizontalAlignment(SwingConstants.CENTER);

		final WebTextField WebsiteTextField = new WebTextField("", 10);
		WebsiteTextField.setHorizontalAlignment(SwingConstants.CENTER);

		WebButton internetBlockButton = new WebButton("Block Internet");
		internetBlockButton.setHorizontalAlignment(SwingConstants.CENTER);
		internetBlockButton.putClientProperty("key", "internetStop");

		GroupPanel InternetPopupContent = new GroupPanel(5, false,
				internetBlockButton, WebsiteTextField,
				internetSendWebsiteButton);
		InternetPopupContent.setMargin(15);

		internetPopupButton.setContent(InternetPopupContent);
		internetPopupButton.setDefaultFocusComponent(internetBlockButton);

		internetButton.setIconTextGap(30);
		internetButton.putClientProperty("key", "internet");
		internetButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		internetButton.setDrawShade(false);
		internetButton.setRound(10);
		internetButton.setTopSelectedBgColor(new Color(75, 113, 158));
		internetButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		internetButton.setForeground(Color.WHITE);
		internetButton.setText("Internet Controller");
		internetButton.setBottomBgColor(new Color(225, 234, 244));
		internetButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(internetButton);

		internetSendWebsiteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					if (getDesktopPane().getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) getDesktopPane()
								.getSelectedFrame().getClientProperty("ip");

						try {
							if (!WebsiteTextField.getText().equals("")) {
								WebsiteCommand wc = new WebsiteCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										selectedStudent, Integer
												.parseInt(Config
														.getParam("port")),
										WebsiteTextField.getText());
								serverService.send(wc);
							} else {
								return;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!groupList.isSelectionEmpty()) {
						int groupIndex = groupList.getSelectedIndex();

						Group group = groups.get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							return;
						} else {
							try {
								if (!WebsiteTextField.getText().equals("")) {
									WebsiteCommand wc = new WebsiteCommand(
											InetAddress.getLocalHost()
													.getHostAddress(), "",
											Integer.parseInt(Config
													.getParam("port")),
											WebsiteTextField.getText());
									for (String studentIp : group
											.getStudentIps()) {
										wc.setTo(studentIp);
										serverService.send(wc);
									}
								} else {
									return;
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				WebsiteTextField.setText("");
			}
		});

		internetBlockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {

				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					if (getDesktopPane().getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) getDesktopPane()
								.getSelectedFrame().getClientProperty("ip");

						try {
							InternetCommand ic = new InternetCommand(
									InetAddress.getLocalHost().getHostAddress(),
									selectedStudent, Integer.parseInt(Config
											.getParam("port")));
							serverService.send(ic);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!groupList.isSelectionEmpty()) {
						int groupIndex = InstructorNoa.getGroupList()
								.getSelectedIndex();

						Group group = groups.get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							return;
						} else {
							try {
								InternetCommand ic = new InternetCommand(
										InetAddress.getLocalHost()
												.getHostAddress(), "", Integer
												.parseInt(Config
														.getParam("port")));
								for (String studentIp : group.getStudentIps()) {
									ic.setTo(studentIp);
									serverService.send(ic);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton programButton = new WebButton();
		WebButtonPopup pogramPopupButton = new WebButtonPopup(programButton,
				PopupWay.upCenter);

		final WebTextField programTextField = new WebTextField("", 10);
		WebButton programAddButton = new WebButton("Add");
		final ProgramListTableModel programTableModel = new ProgramListTableModel();
		final WebTable ptable = new WebTable(programTableModel);
		ptable.setOpaque(false);
		ptable.setPreferredScrollableViewportSize(new Dimension(150, 100));

		programTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
			}
		});

		WebScrollPane programTableScrollPane = new WebScrollPane(ptable);
		programTableScrollPane.setOpaque(false);

		initColumnSizes(ptable);

		GroupPanel programPopupContent = new GroupPanel(5, false,
				programTableScrollPane, new GroupPanel(1, true,
						programTextField, programAddButton));

		programPopupContent.setOpaque(false);
		programPopupContent.setMargin(15);

		pogramPopupButton.setContent(programPopupContent);
		pogramPopupButton.setDefaultFocusComponent(programTextField);

		programButton.setIconTextGap(30);
		programButton.putClientProperty("key", "program");
		programButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programButton.setDrawShade(false);
		programButton.setRound(10);
		programButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programButton.setForeground(Color.WHITE);
		programButton.setText("Allow & Restrict Program");
		programButton.setBottomBgColor(new Color(225, 234, 244));
		programButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(programButton);

		programAddButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (programTextField.getText().equals("")) {
					return;
				}
				Component card = null;
				for (Component comp : InstructorNoa.getCenterPanel()
						.getComponents()) {
					if (comp.isVisible() == true) {
						card = comp;
					}
				}

				if (((JComponent) card).getClientProperty("viewMode").equals(
						"thumbView")) {
					programTableModel.addRow(new Object[] {
							programTextField.getText(), true });
					try {
						WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
								InetAddress.getLocalHost().getHostAddress(),
								Config.getParam("broadcastingIp"), Integer
										.parseInt(Config.getParam("port")),
								(programTextField.getText() + ".exe"), true);
						serverService.send(ic);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (((JComponent) card).getClientProperty("viewMode")
						.equals("groupView")) {
					if (!InstructorNoa.getGroupList().isSelectionEmpty()) {
						int groupIndex = InstructorNoa.getGroupList()
								.getSelectedIndex();

						Group group = InstructorNoa.getGroups().get(groupIndex);
						if (group.getStudentIps().isEmpty()) {
							programTableModel.addRow(new Object[] {
									programTextField.getText(), false });
							return;
						} else {
							programTableModel.addRow(new Object[] {
									programTextField.getText(), true });

							try {
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(), "", Integer
												.parseInt(Config
														.getParam("port")),
										(programTextField.getText() + ".exe"),
										true);
								for (String studentIp : group.getStudentIps()) {
									ic.setTo(studentIp);
									serverService.send(ic);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					} else {
						programTableModel.addRow(new Object[] {
								programTextField.getText(), false });
					}
				}
				programTextField.setText("");
			}
		});

		WebButton programStartButton = new WebButton();
		programStartButton.setIconTextGap(30);
		programStartButton.putClientProperty("key", "programStart");
		programStartButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programStartButton.setDrawShade(false);
		programStartButton.setRound(10);
		programStartButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setForeground(Color.WHITE);
		programStartButton.setText("Program Starter");
		programStartButton.setBottomBgColor(new Color(225, 234, 244));
		programStartButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(programStartButton);

		rightButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		WebButton monitorButton = new WebButton();
		monitorButton.setHorizontalAlignment(SwingConstants.LEADING);
		monitorButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/monitor.png")));
		monitorButton.setIconTextGap(30);
		monitorButton.putClientProperty("key", "monitor");
		monitorButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		monitorButton.setDrawShade(false);
		monitorButton.setRound(10);
		monitorButton.setTopSelectedBgColor(new Color(75, 113, 158));
		monitorButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		monitorButton.setForeground(Color.WHITE);
		monitorButton.setText("Monitoring");
		monitorButton.setBottomBgColor(new Color(225, 234, 244));
		monitorButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(monitorButton);

		intercomButton.setHorizontalAlignment(SwingConstants.LEADING);
		intercomButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/intercom.png")));
		intercomButton.setIconTextGap(30);
		intercomButton.putClientProperty("key", "intercom");
		intercomButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		intercomButton.setDrawShade(false);
		intercomButton.setRound(10);
		intercomButton.setTopSelectedBgColor(new Color(75, 113, 158));
		intercomButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		intercomButton.setForeground(Color.WHITE);
		intercomButton.setText("Intercom");
		intercomButton.setBottomBgColor(new Color(225, 234, 244));
		intercomButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(intercomButton);

		WebButton groupButton = new WebButton();
		groupButton.setHorizontalAlignment(SwingConstants.LEADING);
		groupButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/group.png")));
		groupButton.setIconTextGap(30);
		groupButton.putClientProperty("key", "group");
		groupButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		groupButton.setDrawShade(false);
		groupButton.setRound(10);
		groupButton.setTopSelectedBgColor(new Color(75, 113, 158));
		groupButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		groupButton.setForeground(Color.WHITE);
		groupButton.setText("Groups");
		groupButton.setBottomBgColor(new Color(225, 234, 244));
		groupButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(groupButton);

		WebButton modelButton = new WebButton();
		modelButton.setHorizontalAlignment(SwingConstants.LEADING);
		modelButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/group.png")));
		modelButton.setIconTextGap(30);
		modelButton.putClientProperty("key", "model");
		modelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		modelButton.setDrawShade(false);
		modelButton.setRound(10);
		modelButton.setTopSelectedBgColor(new Color(75, 113, 158));
		modelButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		modelButton.setForeground(Color.WHITE);
		modelButton.setText("Modeling");
		modelButton.setBottomBgColor(new Color(225, 234, 244));
		modelButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(modelButton);

		WebButton recordButton = new WebButton();
		recordButton.setHorizontalAlignment(SwingConstants.LEADING);
		recordButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/record.png")));
		recordButton.setIconTextGap(30);
		recordButton.putClientProperty("key", "record");
		recordButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		recordButton.setDrawShade(false);
		recordButton.setRound(10);
		recordButton.setTopSelectedBgColor(new Color(75, 113, 158));
		recordButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		recordButton.setForeground(Color.WHITE);
		recordButton.setText("Recording");
		recordButton.setBottomBgColor(new Color(225, 234, 244));
		recordButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(recordButton);

		WebButton speechButton = new WebButton();
		speechButton.setHorizontalAlignment(SwingConstants.LEADING);
		speechButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/speech.png")));
		speechButton.setIconTextGap(30);
		speechButton.putClientProperty("key", "speech");
		speechButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		speechButton.setDrawShade(false);
		speechButton.setRound(10);
		speechButton.setTopSelectedBgColor(new Color(75, 113, 158));
		speechButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		speechButton.setForeground(Color.WHITE);
		speechButton.setText("Speech Recognition");
		speechButton.setBottomBgColor(new Color(225, 234, 244));
		speechButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(speechButton);

		WebButton fileButton = new WebButton();
		fileButton.setHorizontalAlignment(SwingConstants.LEADING);
		fileButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/file.png")));
		fileButton.setIconTextGap(30);
		fileButton.putClientProperty("key", "file");
		fileButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		fileButton.setDrawShade(false);
		fileButton.setRound(10);
		fileButton.setTopSelectedBgColor(new Color(75, 113, 158));
		fileButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		fileButton.setForeground(Color.WHITE);
		fileButton.setText("File Sharing");
		fileButton.setBottomBgColor(new Color(225, 234, 244));
		fileButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(fileButton);

		WebButton quizButton = new WebButton();
		quizButton.setHorizontalAlignment(SwingConstants.LEADING);
		quizButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/quiz.png")));
		quizButton.setIconTextGap(30);
		quizButton.putClientProperty("key", "quiz");
		quizButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		quizButton.setDrawShade(false);
		quizButton.setRound(10);
		quizButton.setTopSelectedBgColor(new Color(75, 113, 158));
		quizButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		quizButton.setForeground(Color.WHITE);
		quizButton.setText("Exam");
		quizButton.setBottomBgColor(new Color(225, 234, 244));
		quizButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(quizButton);

		WebButton videoButton = new WebButton();
		videoButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/com/jajeem/images/Media_Player.png")));
		videoButton.setIconTextGap(30);
		videoButton.putClientProperty("key", "movieplayer");
		videoButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		videoButton.setDrawShade(false);
		videoButton.setRound(10);
		videoButton.setTopSelectedBgColor(new Color(75, 113, 158));
		videoButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		videoButton.setForeground(Color.WHITE);
		videoButton.setText("Movie Player");
		videoButton.setBottomBgColor(new Color(225, 234, 244));
		videoButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(videoButton);

		WebButton reportButton = new WebButton();
		reportButton.setHorizontalAlignment(SwingConstants.LEADING);
		reportButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/report.png")));
		reportButton.setIconTextGap(30);
		reportButton.putClientProperty("key", "report");
		reportButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportButton.setDrawShade(false);
		reportButton.setRound(10);
		reportButton.setTopSelectedBgColor(new Color(75, 113, 158));
		reportButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		reportButton.setForeground(Color.WHITE);
		reportButton.setText("Reports");
		reportButton.setBottomBgColor(new Color(225, 234, 244));
		reportButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(reportButton);

		final WebButtonPopup reportPopupButton = new WebButtonPopup(reportButton,
				PopupWay.downCenter);

		
		///////////////////// Report Combo
		
		final GroupPanel reportPopupContent = new GroupPanel(5, false);
		reportPopupContent.setMargin(15);
		
		final WebComboBox reportStudentComboBox = new WebComboBox();
		reportStudentComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		
		final WebComboBox reportQuizComboBox = new WebComboBox();
		reportQuizComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		
		String[] items = { "SummaryofStudents", "StudentResult", "PointChart" ,"AnswerRate"};
		final WebComboBox reportComboBox = new WebComboBox(new String[]{"Summary of Students","Student Result","Point Chart","Answer Rate"});
		
		final WebButton reportGoButton = new WebButton("Go");
		reportGoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object selectedItem = reportComboBox.getSelectedItem();
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
						.format(Calendar.getInstance().getTime());
				JasperReport.generate(selectedItem.toString(),
						(selectedItem.toString()+ "_"+ timeStamp), Query.SummaryOfStudents(1));
			}
		});
		
		reportComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println(reportComboBox.getSelectedItem().toString());
				if(reportComboBox.getSelectedItem().toString().equals("Summary of Students")){
					reportPopupContent.removeAll();
					reportPopupContent.add(reportComboBox);
					reportPopupContent.add(reportQuizComboBox);
					reportPopupContent.add(reportGoButton);	
					reportPopupContent.setSize(reportPopupContent.getWidth(), reportPopupContent.getHeight() + reportQuizComboBox.getHeight());
					reportPopupButton.packPopup();
					reportPopupButton.revalidate();
					reportPopupButton.repaint();
				}
				else if(reportComboBox.getSelectedItem().toString().equals("Student Result")){
					reportPopupContent.removeAll();
					reportPopupContent.add(reportComboBox);
					reportPopupContent.add(reportStudentComboBox);
					reportPopupContent.add(reportQuizComboBox);
					reportPopupContent.add(reportGoButton);
					reportPopupContent.setSize(reportPopupContent.getWidth(), reportPopupContent.getHeight() + reportQuizComboBox.getHeight() + reportStudentComboBox.getHeight());
					reportPopupButton.packPopup();
					reportPopupButton.revalidate();
					reportPopupButton.repaint();
				}
				else if(reportComboBox.getSelectedItem().toString().equals("Point Chart")){
					reportPopupContent.removeAll();
					reportPopupContent.add(reportComboBox);
					reportPopupContent.add(reportStudentComboBox);
					reportPopupContent.add(reportQuizComboBox);
					reportPopupContent.add(reportGoButton);
					reportPopupContent.setSize(reportPopupContent.getWidth(), reportPopupContent.getHeight() + reportQuizComboBox.getHeight() + reportStudentComboBox.getHeight());
					reportPopupButton.packPopup();
					reportPopupButton.revalidate();
					reportPopupButton.repaint();
				}
				else if(reportComboBox.getSelectedItem().toString().equals("Answer Rate")){
					reportPopupContent.removeAll();
					reportPopupContent.add(reportComboBox);
					reportPopupContent.add(reportQuizComboBox);
					reportPopupContent.add(reportGoButton);
					reportPopupContent.setSize(reportPopupContent.getWidth(), reportPopupContent.getHeight() + reportQuizComboBox.getHeight());
					reportPopupButton.packPopup();
					reportPopupButton.revalidate();
					reportPopupButton.repaint();
				}
			}
		});

		reportPopupContent.add(reportComboBox);
		reportPopupContent.add(reportQuizComboBox);
		reportPopupContent.add(reportGoButton);

		reportPopupButton.setContent(reportPopupContent);

		WebButton accountButton = new WebButton();
		accountButton.setHorizontalAlignment(SwingConstants.LEADING);
		accountButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/account.png")));
		accountButton.setIconTextGap(30);
		accountButton.putClientProperty("key", "account");
		accountButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		accountButton.setDrawShade(false);
		accountButton.setRound(10);
		accountButton.setTopSelectedBgColor(new Color(75, 113, 158));
		accountButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		accountButton.setForeground(Color.WHITE);
		accountButton.setText("Accounts");
		accountButton.setBottomBgColor(new Color(225, 234, 244));
		accountButton.setTopBgColor(new Color(116, 166, 219));
//		rightButtonPanel.add(accountButton);

		WebButton chatButton = new WebButton();
		chatButton.setHorizontalAlignment(SwingConstants.LEADING);
		chatButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/chat.png")));
		chatButton.setIconTextGap(30);
		chatButton.putClientProperty("key", "chat");
		chatButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		chatButton.setDrawShade(false);
		chatButton.setRound(10);
		chatButton.setTopSelectedBgColor(new Color(75, 113, 158));
		chatButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		chatButton.setForeground(Color.WHITE);
		chatButton.setText("Chat & Messaging");
		chatButton.setBottomBgColor(new Color(225, 234, 244));
		chatButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(chatButton);

		WebLabel bottomLogoLabel = new WebLabel("LOGO");
		bottomLogoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		bottomLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoLabel.setForeground(new Color(255, 127, 80));

		WebLabel copyRightLabel = new WebLabel("Copy right 2013");
		copyRightLabel.setForeground(Color.WHITE);
		copyRightLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		copyRightLabel.setText("Copy Right \u00A9 2013");
		copyRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		bottomLogoPanel.add(bottomLogoLabel);
		bottomLogoPanel.add(copyRightLabel);
		frame.getContentPane().setLayout(groupLayout);

		// Add Event Handlers
		InstructorNoaUtil instructorNoaUtil = new InstructorNoaUtil();
		instructorNoaUtil.addEventsRightPanel(rightButtonPanel);
		instructorNoaUtil.addEventsBottomPanel(bottomButtonPanel, frame);
		instructorNoaUtil.addEventsTopPanel(topButtonPanel);

		getGroupList().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				WebList list = (WebList) e.getSource();
				int index = list.locationToIndex(e.getPoint());

				if (e.getClickCount() == 2
						&& SwingUtilities.isLeftMouseButton(e)) {
					return;
				} else if (SwingUtilities.isRightMouseButton(e)) {
					WebPopupMenu popup = (WebPopupMenu) list
							.getComponentPopupMenu();
					popup.removeAll();
					List<String> studentNames = getGroups().get(index)
							.getStudentNames();
					if (studentNames.isEmpty()) {
						popup.add(new WebMenuItem("Empty"));
						return;
					} else {
						for (String studentName : studentNames) {
							popup.add(new WebMenuItem(studentName));
						}
					}
				}
			}
		});

	}

	public static AVTransmit2 getTransmitter() {
		return transmitter;
	}

	public static void setTransmitter(AVTransmit2 transmitter) {
		InstructorNoa.transmitter = transmitter;
	}

	public static List<Chat> getChatList() {
		return chatList;
	}

	public static void setChatList(List<Chat> chatList) {
		InstructorNoa.chatList = chatList;
	}

	public static ServerService getServerService() {
		return serverService;
	}

	public static void setServerService(ServerService serverService) {
		InstructorNoa.serverService = serverService;
	}

	public static WebPanel getCenterPanel() {
		return centerPanel;
	}

	public static void setCenterPanel(WebPanel centerPanel) {
		InstructorNoa.centerPanel = centerPanel;
	}

	public static WebTable getStudentListTable() {
		return studentListTable;
	}

	public static void setStudentListTable(WebTable studentListTable) {
		InstructorNoa.studentListTable = studentListTable;
	}

	public static void setIntercomText(String text) {
		intercomButton.setText(text);
	}

	private void initColumnSizes(JTable table) {
		ProgramListTableModel model = (ProgramListTableModel) table.getModel();
		TableColumn column;
		Component comp;
		int headerWidth;
		int cellWidth;
		Object[] longValues = model.longValues;
		TableCellRenderer headerRenderer = table.getTableHeader()
				.getDefaultRenderer();

		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);

			comp = headerRenderer.getTableCellRendererComponent(null,
					column.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;

			comp = table.getDefaultRenderer(model.getColumnClass(i))
					.getTableCellRendererComponent(table, longValues[i], false,
							false, 0, i);
			cellWidth = comp.getPreferredSize().width;

			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}

	public static WebList getGroupList() {
		return groupList;
	}

	public static void setGroupList(WebList groupList) {
		InstructorNoa.groupList = groupList;
	}

	public static List<Group> getGroups() {
		return groups;
	}

	public static void setGroups(List<Group> groups) {
		InstructorNoa.groups = groups;
	}

	private class ProgramListTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3816337549406126398L;
		private String[] columnNames = { "Name", "Status" };
		private Object[][] data = { { "Chrome", false }, { "Firefox", false },
				{ "IExplore", false }, { "WMplayer", false } };

		public final Object[] longValues = { "Jane", Boolean.TRUE };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
			return longValues[c].getClass();
		}

		public boolean isCellEditable(int row, int col) {
			return col >= 1;
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
			if (value instanceof Boolean) {
				if ((boolean) value) {
					try {

						Component card = null;
						for (Component comp : InstructorNoa.getCenterPanel()
								.getComponents()) {
							if (comp.isVisible() == true) {
								card = comp;
							}
						}

						if (((JComponent) card).getClientProperty("viewMode")
								.equals("thumbView")) {

							try {
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										Config.getParam("broadcastingIp"),
										Integer.parseInt(Config
												.getParam("port")),
										(data[row][col - 1] + ".exe"), true);
								serverService.send(ic);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (((JComponent) card).getClientProperty(
								"viewMode").equals("groupView")) {
							if (!groupList.isSelectionEmpty()) {
								int groupIndex = InstructorNoa.getGroupList()
										.getSelectedIndex();

								Group group = groups.get(groupIndex);
								if (group.getStudentIps().isEmpty()) {
									return;
								} else {
									try {
										WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
												InetAddress.getLocalHost()
														.getHostAddress(), "",
												Integer.parseInt(Config
														.getParam("port")),
												(data[row][col - 1] + ".exe"),
												true);

										for (String studentIp : group
												.getStudentIps()) {
											ic.setTo(studentIp);
											serverService.send(ic);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {

						Component card = null;
						for (Component comp : InstructorNoa.getCenterPanel()
								.getComponents()) {
							if (comp.isVisible() == true) {
								card = comp;
							}
						}

						if (((JComponent) card).getClientProperty("viewMode")
								.equals("thumbView")) {

							try {
								WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
										InetAddress.getLocalHost()
												.getHostAddress(),
										Config.getParam("broadcastingIp"),
										Integer.parseInt(Config
												.getParam("port")),
										(data[row][col - 1] + ".exe"), false);
								serverService.send(ic);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (((JComponent) card).getClientProperty(
								"viewMode").equals("groupView")) {
							if (!groupList.isSelectionEmpty()) {
								int groupIndex = InstructorNoa.getGroupList()
										.getSelectedIndex();

								Group group = groups.get(groupIndex);
								if (group.getStudentIps().isEmpty()) {
									return;
								} else {
									try {
										WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
												InetAddress.getLocalHost()
														.getHostAddress(), "",
												Integer.parseInt(Config
														.getParam("port")),
												(data[row][col - 1] + ".exe"),
												false);

										for (String studentIp : group
												.getStudentIps()) {
											ic.setTo(studentIp);
											serverService.send(ic);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void addRow(Object[] row) {
			Object[][] newData = new Object[getRowCount() + 1][getColumnCount()];

			for (int x = 0; x < data.length; x++) {
				newData[x] = data[x];
			}

			newData[getRowCount()] = row;
			data = newData;
			fireTableDataChanged();
		}
	}

	public static ArrayList<String> getAllStudentIPs() {
		JInternalFrame[] frames = getDesktopPane().getAllFrames();
		List<String> listOfStudents = new ArrayList<String>();
		for (JInternalFrame frame : frames) {
			listOfStudents.add((String) frame.getClientProperty("ip"));
		}
		return new ArrayList<>(listOfStudents);
	}

	public static AVReceiveOnly getReceiverOnly() {
		return receiverOnly;
	}

	public static void setReceiverOnly(AVReceiveOnly receiverOnly) {
		InstructorNoa.receiverOnly = receiverOnly;
	}

	public static AVSendOnly getSendOnly() {
		return sendOnly;
	}

	public static void setSendOnly(AVSendOnly sendOnly) {
		InstructorNoa.sendOnly = sendOnly;
	}

	public static RootDesktopPane getDesktopPane() {
		return getDesktopPaneScroll().getDesktopMediator()
				.getDesktopScrollpane().getDesktopPane();
	}

	public static JScrollDesktopPane getDesktopPaneScroll() {
		return desktopPaneScroll;
	}

	public static void setDesktopPaneScroll(JScrollDesktopPane desktopPaneScroll) {
		InstructorNoa.desktopPaneScroll = desktopPaneScroll;
	}

	public static boolean isModeling() {
		return modeling;
	}

	public static void setModeling(boolean modeling) {
		InstructorNoa.modeling = modeling;
	}
}
