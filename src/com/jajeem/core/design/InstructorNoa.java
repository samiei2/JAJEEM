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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
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
import com.alee.managers.popup.PopupListener;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.LockCommand;
import com.jajeem.command.model.PowerCommand;
import com.jajeem.command.model.WebsiteCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.dao.h2.RunDAO;
import com.jajeem.quiz.model.Run;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.room.model.Course;
import com.jajeem.util.BackgroundPanel;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.Query;
import com.jajeem.util.i18n;

public class InstructorNoa {

	private static WebFrame frame;
	public static Map<String, com.jajeem.core.model.Student> studentList;
	private static JScrollDesktopPane desktopPaneScroll;
	private static WebPanel centerPanel;
	private static WebTable studentListTable;
	private static Instructor instructorModel;
	private static Course courseModel;

	private static AVTransmit2 transmitter;
	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	@SuppressWarnings("unused")
	private static boolean transmitting;
	private static String transmittingType;

	private static List<Chat> chatList = new ArrayList<Chat>();
	private static ServerService serverService;
	private static List<Group> groups = new ArrayList<Group>();

	static WebButton intercomButton = new WebButton();
	private static WebList groupList = new WebList();
	private static boolean modeling = false;
	private static DefaultListModel langListModel = new DefaultListModel();
	private static WebList langList = new WebList(langListModel);

	ArrayList<Run> reportRunList = new ArrayList<>();
	ArrayList<com.jajeem.core.model.Student> reportStudentList = new ArrayList<>();

	WebCheckBoxListModel programModel = new WebCheckBoxListModel();
	
	static WebPanel rightButtonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					new InstructorNoa();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
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
			new i18n();

			// Start LibJitsi for first time
			LibJitsi.start();
			transmitter = new AVTransmit2("5000", "", "10000");
			sendOnly = new AVSendOnly("5010", "", "10010");

			InstructorNoaUtil.networkSetup();

			initialize();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initialize() throws Exception {

		studentList = new HashMap<String, com.jajeem.core.model.Student>();

		frame = new WebFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				InstructorNoa.class.getResource("/icons/noa/teacher.png")));
		frame.setShowMinimizeButton(false);
		frame.setTitle("Classmate");
		frame.setShowResizeCorner(false);
		frame.getContentPane().setBackground(new Color(56, 107, 170));
		frame.setBounds(10, 0, 1021, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		langListModel.addElement("English");
		langListModel.addElement("فارسی");
		langList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					try {
						if (index == 0) {
							if (!Config.getParam("lang").equals("en")) {
								Config.setParam("lang", "en");
								try {
									ProcessBuilder processBuilder = new ProcessBuilder(
											"run.exe");
									processBuilder.start();
								} catch (IOException e) {
									JajeemExcetionHandler.logError(e);
									e.printStackTrace();
								}

								System.exit(0);
							}
						} else if (index == 1) {
							Config.setParam("lang", "fa");
							try {
								ProcessBuilder processBuilder = new ProcessBuilder(
										"run.exe");
								processBuilder.start();
							} catch (IOException e) {
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}

							System.exit(0);
						}
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e);
						e.printStackTrace();
					}
				}
			}
		});

		WebPanel centerPanel = new WebPanel();
		InstructorNoa.setCenterPanel(centerPanel);
		WebPanel centerListPanel = new WebPanel();
		centerPanel.add(centerListPanel);
		centerPanel.setBackground(new Color(255, 255, 255));
		WebPanel centerGroupPanel = new WebPanel();
		centerGroupPanel.setBackground(new Color(255, 255, 255));

		final DefaultTableModel model = new DefaultTableModel();

		model.addColumn(i18n.getParam("PC IP"));
		model.addColumn(i18n.getParam("Student name"));

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
		groupPopup.add(new WebMenuItem(i18n.getParam(("Empty"))));
		getGroupList().setComponentPopupMenu(groupPopup);

		final DefaultListModel groupListModel = new DefaultListModel();
		getGroupList().setModel(groupListModel);

		getGroupList().addMouseListener(new MouseAdapter() {

			private WebFrame frame;

			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Group group = getGroups().get(index);
					List<String> studentNames = group.getStudentNames();
					frame = new WebFrame();
					WebList studentList = new WebList();
					studentList.setListData(studentNames.toArray());
					frame.add(new WebScrollPane(studentList));
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		});

		getGroupList().setModel(new AbstractListModel() {
			String[] values = new String[] { i18n.getParam("Group A"),
					"Group B", "Group C", "Group D", "Group E", "Group F",
					"Group G", "Group H", "Group I", "Group J", "Group K",
					"Group L", "Group M", "Group N", "Group O" };

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

		rightButtonPanel = new WebPanel();
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
		volumeButton.setText(i18n.getParam("Volume Control"));
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
		callAllButton.setText(i18n.getParam("Call All"));
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
		viewModeButton.setText(i18n.getParam("View Mode"));
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
		languageButton.setText(i18n.getParam("Language"));
		languageButton.setBottomBgColor(new Color(235, 105, 11));
		languageButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(languageButton);

		WebButtonPopup langPopupButton = new WebButtonPopup(languageButton,
				PopupWay.downCenter);

		GroupPanel langPopupContent = new GroupPanel(5, false, langList);
		langPopupContent.setMargin(15);

		langPopupButton.setContent(langPopupContent);

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
		
		
		///Armin codes Clean Up actions and move to proper place
		WebPopupMenu popup = new WebPopupMenu();
		WebMenuItem menuItemThumbView = new WebMenuItem("Thumbnail View");
		WebMenuItem menuItemGroupView = new WebMenuItem("Group View");
		WebMenuItem menuItemListView = new WebMenuItem("List View");
		
		popup.add(menuItemThumbView);
		popup.add(menuItemGroupView);
		popup.add(menuItemListView);
		
		scrollableDesktop.getDesktopMediator().getDesktopScrollpane().getDesktopPane().setComponentPopupMenu(popup);
//		getGroupList().setComponentPopupMenu(popup);
		getStudentListTable().setComponentPopupMenu(popup);
		
		menuItemThumbView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ThumbViewActionListener();
			}
		});
		
		menuItemGroupView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GroupViewActionListener();
			}
		});
		
		menuItemListView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ListViewActionListener();
			}
		});
		///////////////////////////////////////////////////////////////////
		
		
		// desktopPane.setBackground(new Color(237, 246, 253));
		centerPanel.setBackground(new Color(237, 246, 253));
		// GroupLayout gl_desktopPane = new GroupLayout(getDesktopPaneScroll());
		// gl_desktopPane.setAutoCreateContainerGaps(true);
		// gl_desktopPane.setAutoCreateGaps(true);
		// gl_desktopPane.setHorizontalGroup(gl_desktopPane.createParallelGroup(
		// Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		// gl_desktopPane.setVerticalGroup(gl_desktopPane.createParallelGroup(
		// Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
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
		surveyButton.setText(i18n.getParam("Survey"));
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
		whiteBoardButton.setText(i18n.getParam("White board"));
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
		powerButton.setText(i18n.getParam("PC Controller"));
		powerButton.setBottomBgColor(new Color(225, 234, 244));
		powerButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(powerButton);

		WebButton powerOffButton = new WebButton(i18n.getParam("Power Off"));
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
							JajeemExcetionHandler.logError(e);
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
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton logOffButton = new WebButton(i18n.getParam("Log Off"));
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
							JajeemExcetionHandler.logError(e);
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
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		WebButton restartButton = new WebButton(i18n.getParam("Restart"));
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
							JajeemExcetionHandler.logError(e);
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
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton lockButton = new WebButton(i18n.getParam("Lock/Unlock"));
		lockButton.putClientProperty("key", "lock");
		lockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LockAction();
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

		WebButton internetSendWebsiteButton = new WebButton(
				i18n.getParam("Send Website"));
		internetSendWebsiteButton.putClientProperty("key", "internetWebsite");
		internetSendWebsiteButton.setHorizontalAlignment(SwingConstants.CENTER);

		final WebTextField WebsiteTextField = new WebTextField("", 10);
		WebsiteTextField.setHorizontalAlignment(SwingConstants.CENTER);

		WebButton internetBlockButton = new WebButton(
				i18n.getParam("Block Internet"));
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
		internetButton.setText(i18n.getParam("Internet Controller"));
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
							JajeemExcetionHandler.logError(e);
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
								JajeemExcetionHandler.logError(e);
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
							JajeemExcetionHandler.logError(e);
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
								JajeemExcetionHandler.logError(e);
								e.printStackTrace();
							}
						}
					}
				}
			}

		});

		WebButton programButton = new WebButton();
		
		programButton.setIconTextGap(30);
		programButton.putClientProperty("key", "program");
		programButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programButton.setDrawShade(false);
		programButton.setRound(10);
		programButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programButton.setForeground(Color.WHITE);
		programButton.setText(i18n.getParam("Allow & Restrict Program"));
		programButton.setBottomBgColor(new Color(225, 234, 244));
		programButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(programButton);

		WebButton programStartButton = new WebButton();
		programStartButton.setIconTextGap(30);
		programStartButton.putClientProperty("key", "programStart");
		programStartButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programStartButton.setDrawShade(false);
		programStartButton.setRound(10);
		programStartButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setForeground(Color.WHITE);
		programStartButton.setText(i18n.getParam("Program Starter"));
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
		intercomButton.setText(i18n.getParam("Intercom"));
		intercomButton.setBottomBgColor(new Color(225, 234, 244));
		intercomButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(intercomButton);

		WebButton videoChatButton = new WebButton();
		videoChatButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoChatButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/intercom.png")));
		videoChatButton.setIconTextGap(30);
		videoChatButton.putClientProperty("key", "videoChat");
		videoChatButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		videoChatButton.setDrawShade(false);
		videoChatButton.setRound(10);
		videoChatButton.setTopSelectedBgColor(new Color(75, 113, 158));
		videoChatButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		videoChatButton.setForeground(Color.WHITE);
		videoChatButton.setText(i18n.getParam("Video Chat"));
		videoChatButton.setBottomBgColor(new Color(225, 234, 244));
		videoChatButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(videoChatButton);

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
		groupButton.setText(i18n.getParam("Groups"));
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
		modelButton.setText(i18n.getParam("Modelling"));
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
		recordButton.setText(i18n.getParam("Recording"));
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
		speechButton.setText(i18n.getParam("Speech Recognition"));
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
		fileButton.setText(i18n.getParam("File Sharing"));
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
		quizButton.setText(i18n.getParam("Exam"));
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
		videoButton.setText(i18n.getParam("Movie Player"));
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
		reportButton.setText(i18n.getParam("Reports"));
		reportButton.setBottomBgColor(new Color(225, 234, 244));
		reportButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(reportButton);

		final WebButtonPopup reportPopupButton = new WebButtonPopup(
				reportButton, PopupWay.upCenter);

		// /////////////////// Report Combo

		final GroupPanel reportPopupContent = new GroupPanel(5, false);
		reportPopupContent.setMargin(15);

		final WebComboBox reportStudentComboBox = new WebComboBox();
		reportStudentComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

			}
		});

		final WebComboBox listOfStudentsComboBox = new WebComboBox();

		final WebComboBox reportQuizComboBox = new WebComboBox();
		reportQuizComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

			}
		});

		reportPopupButton.addPopupListener(new PopupListener() {

			@Override
			public void popupWillBeOpened() {
			}

			@Override
			public void popupWillBeClosed() {
			}

			@Override
			public void popupOpened() {
				PopulateQuizCombobox();
				// PopulateStudentCombobox();
			}

			/*
			 * private void PopulateStudentCombobox() { StudentService
			 * stuService = new StudentService(); ArrayList<Student>
			 * studentList; try { studentList = stuService.list();
			 * 
			 * List<String> studentListComboModel = new ArrayList<String>(); for
			 * (Student stu : studentList) { String fullname =
			 * stu.getFirstName() + stu.getLastName(); if (fullname == null ||
			 * fullname.equals("")) { studentListComboModel.add(fullname); }
			 * else { studentListComboModel.add(String.valueOf(stu .getId())); }
			 * } listOfStudentsComboBox.addItem((studentListComboModel
			 * .toArray())); } catch (SQLException e) { e.printStackTrace(); } }
			 */

			private void PopulateQuizCombobox() {
				ArrayList<com.jajeem.quiz.model.Run> quizList;
				try {
					quizList = new RunDAO().list(com.jajeem.util.Session
							.getCourse().getId());
					// quizList = new RunDAO().list();
					reportRunList.clear();
					reportQuizComboBox.removeAllItems();
					QuizService quizService = new QuizService();
					for (int i = 0; i < quizList.size(); i++) {
						Run run = quizList.get(i);

						reportQuizComboBox.addItem(quizService.get(
								run.getQuizId()).getTitle()
								+ " (" + new Date(run.getStart()) + ")");
						reportRunList.add(run);
					}

				} catch (SQLException e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}

			@Override
			public void popupClosed() {
			}
		});

		// be careful with this list index, we work with index
		final String[] jasperFileNames = { "AnswerRate", "StudentResult",
				"SummaryofStudents" };
		final WebComboBox reportComboBox = new WebComboBox(new String[] {
				"Answer rate", "Student result", "Summary of students" });

		final WebButton reportGoButton = new WebButton(i18n.getParam("Go"));
		reportGoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
						.format(Calendar.getInstance().getTime());
				if (reportComboBox.getSelectedIndex() == 0) {
					JasperReport.generate(jasperFileNames[0],
							(jasperFileNames[0].toString() + "_" + timeStamp),
							Query.answerRate(reportRunList.get(
									reportQuizComboBox.getSelectedIndex())
									.getId()));
				} else if (reportComboBox.getSelectedIndex() == 1) {
					JasperReport.generate(jasperFileNames[1],
							(jasperFileNames[1] + "_" + timeStamp), Query
									.studentResult(reportRunList.get(
											reportQuizComboBox
													.getSelectedIndex())
											.getId()));
				} else if (reportComboBox.getSelectedIndex() == 2) {
					JasperReport.generate(jasperFileNames[2],
							(jasperFileNames[2].toString() + "_" + timeStamp),
							Query.summaryOfStudents(reportRunList.get(
									reportQuizComboBox.getSelectedIndex())
									.getId()));

				} else if (reportComboBox.getSelectedIndex() == 4) {

				} else if (reportComboBox.getSelectedIndex() == 5) {

				}

			}
		});

		reportComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (reportComboBox.getSelectedIndex() == 0) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(true);
				} else if (reportComboBox.getSelectedIndex() == 1) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(false);
				} else if (reportComboBox.getSelectedIndex() == 2) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(true);
				} else if (reportComboBox.getSelectedIndex() == 3) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(true);
				} else if (reportComboBox.getSelectedIndex() == 4) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(true);
				} else if (reportComboBox.getSelectedIndex() == 5) {
					listOfStudentsComboBox.setVisible(false);
					reportQuizComboBox.setVisible(false);
				}
			}
		});

		reportPopupContent.add(reportComboBox);
		reportPopupContent.add(reportGoButton);
		reportPopupContent.add(reportQuizComboBox);
		reportPopupContent.add(listOfStudentsComboBox);
		listOfStudentsComboBox.setVisible(false);
		reportQuizComboBox.setVisible(true);

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
		accountButton.setText(i18n.getParam("My account"));
		accountButton.setBottomBgColor(new Color(225, 234, 244));
		accountButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(accountButton);

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
		chatButton.setText(i18n.getParam("Chat"));
		chatButton.setBottomBgColor(new Color(225, 234, 244));
		chatButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(chatButton);

		WebLabel bottomLogoLabel = new WebLabel("LOGO");
		bottomLogoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		bottomLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoLabel.setForeground(new Color(255, 127, 80));

		WebLabel copyRightLabel = new WebLabel();
		copyRightLabel.setForeground(Color.WHITE);
		copyRightLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		copyRightLabel.setText(i18n.getParam("Copy Right \u00A9 2013"));
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
						try {
							popup.add(new WebMenuItem(i18n.getParam("Empty")));
						} catch (Exception e1) {
							JajeemExcetionHandler.logError(e1);
							e1.printStackTrace();
						}
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

	public static void LockAction() {
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
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
		}
	}

	protected void ListViewActionListener() {
		CardLayout cl = (CardLayout) InstructorNoa
				.getCenterPanel().getLayout();
		cl.show(centerPanel, "listView");
	}

	protected void GroupViewActionListener() {
		CardLayout cl = (CardLayout) InstructorNoa
				.getCenterPanel().getLayout();
		cl.show(centerPanel, "groupView");
	}

	protected void ThumbViewActionListener() {
		CardLayout cl = (CardLayout) InstructorNoa
				.getCenterPanel().getLayout();
		cl.show(centerPanel, "thumbsView");
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

	public static String getTransmittingType() {
		return transmittingType;
	}

	public static void setTransmittingType(String transmittingType) {
		InstructorNoa.transmittingType = transmittingType;
	}

	public static boolean isTransmitting() {
		return getSendOnly().isTransmitting()
				|| getTransmitter().isTransmitting();
	}

	public static void setTransmitting(boolean transmitting) {
		InstructorNoa.transmitting = transmitting;
	}

	public static Instructor getInstructorModel() {
		return instructorModel;
	}

	public static void setInstructorModel(Instructor instructorModel) {
		InstructorNoa.instructorModel = instructorModel;
	}

	public static Course getCourseModel() {
		return courseModel;
	}

	public static void setCourseModel(Course courseModel) {
		InstructorNoa.courseModel = courseModel;
	}

	public static String getStudentNameByIP(String ip) {
		if (studentList.containsKey(ip)) {
			return studentList.get(ip).getFullName();
		} else {
			JInternalFrame[] frames = getDesktopPane().getAllFrames();
			for (JInternalFrame frame : frames) {
				if (frame.getClientProperty("ip").equals(ip)) {
					return (String) frame.getClientProperty("username");
				}
			}
		}
		return ip;
	}
	
	public static void changeModelButtonText(String text)
	{
		String key = "";

		for (Component c : rightButtonPanel.getComponents()) {
			if (c instanceof WebButton) {

				key = (String) ((WebButton) c).getClientProperty("key");

				if (key == null) {
					return;
				}

				final WebButton button = ((WebButton) c);

				switch (key) {

				case "monitor":
					button.setText(text);
					break;
				}
			}
		}
	}
}
