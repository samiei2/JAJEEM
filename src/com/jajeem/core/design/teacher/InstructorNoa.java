package com.jajeem.core.design.teacher;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

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
import com.alee.laf.checkbox.WebCheckBox;
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
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.ui.CustomTeacherFrame;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.model.Group;
import com.jajeem.message.design.Chat;
import com.jajeem.quiz.model.Run;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;
import com.jajeem.util.CustomBottomButton;
import com.jajeem.util.CustomButton;
import com.jajeem.util.CustomCallAllButton;
import com.jajeem.util.CustomCellRenderer;
import com.jajeem.util.CustomJList;
import com.jajeem.util.CustomPanel;
import com.jajeem.util.CustomPowerButton;
import com.jajeem.util.CustomPowerPanel;
import com.jajeem.util.CustomTopButton;
import com.jajeem.util.JasperReport;
import com.jajeem.util.Query;
import com.jajeem.util.i18n;

public class InstructorNoa {

	private static CustomTeacherFrame frame;
	public static Map<String, com.jajeem.core.model.Student> studentList;
	private static ArrayList<String> conversationPairs;
	private static ArrayList<String> conversationIps;
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
	private static CustomJList groupList = new CustomJList();
	private static boolean modeling = false;
	private static DefaultListModel langListModel = new DefaultListModel();
	private static WebList langList = new WebList(langListModel);

	ArrayList<Run> reportRunList = new ArrayList<>();
	ArrayList<com.jajeem.core.model.Student> reportStudentList = new ArrayList<>();

	WebCheckBoxListModel programModel = new WebCheckBoxListModel();

	static WebPanel rightButtonPanel;

	Font font = new Font("Arial", Font.BOLD, 20);
	private JLabel lblHour1;
	private JLabel lblHour2;
	private JLabel lblMin1;
	private JLabel lblMin2;
	private JLabel lblLogo;
	private JLabel lblLogoText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					new InstructorNoa();
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

			String ip = InetAddress.getLocalHost().getHostAddress().toString();
			ip = ip.substring(0, ip.lastIndexOf(".")) + ".255";
			sendOnly = new AVSendOnly("5010", ip, "10010");

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

	@SuppressWarnings({ "unchecked", "rawtypes", "serial", "static-access" })
	private void initialize() throws Exception {

		studentList = new HashMap<String, com.jajeem.core.model.Student>();

		conversationIps = new ArrayList<String>();
		conversationPairs = new ArrayList<String>();

		frame = new CustomTeacherFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				InstructorNoa.class.getResource("/icons/noa/teacher.png")));
		frame.setTitle(i18n.getParam("Classmate"));
		
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				BufferedImage logo = null;
				BufferedImage logoText = null;
				try {
					logo = ImageIO.read(getClass().getResource("/icons/noa_en/logoteacher.png"));
					logoText = ImageIO.read(getClass().getResource("/icons/noa_en/classmatelogo.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ImageIcon scaledLogo = new ImageIcon(logo.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH));
				lblLogo.setIcon(scaledLogo);
				ImageIcon scaledLogoText = new ImageIcon(logoText.getScaledInstance(lblLogoText.getWidth(), lblLogoText.getHeight(), Image.SCALE_SMOOTH));
				lblLogoText.setIcon(scaledLogoText);
				super.componentShown(e);
			}
		});

		langListModel.addElement("English");
		langListModel.addElement("�?ارسی");
		langList.addMouseListener(new MouseAdapter() {
			@Override
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
		CustomPanel centerListPanel = new CustomPanel(
				"/icons/noa_en/new/maingridpanel.png");
		centerPanel.add(centerListPanel);
		centerPanel.setBackground(new Color(255, 255, 255));
		WebPanel centerGroupPanel = new WebPanel();
		centerGroupPanel.setBackground(new Color(255, 255, 255));

		final DefaultTableModel model = new DefaultTableModel();

		model.addColumn(i18n.getParam("PC IP"));
		model.addColumn(i18n.getParam("Student name"));

		WebTable table = new WebTable(model);
		table.setOpaque(false);
		setStudentListTable(table);
		table.setBackground(new Color(237, 246, 253));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		WebScrollPane scrollPanel = new WebScrollPane(table);
		scrollPanel.setOpaque(false);
		scrollPanel.setDrawBorder(false);

		WebPopupMenu groupPopup = new WebPopupMenu();
		groupPopup.add(new WebMenuItem(i18n.getParam(("Empty"))));
		getGroupList().setComponentPopupMenu(groupPopup);

		final DefaultListModel groupListModel = new DefaultListModel();
		getGroupList().setModel(groupListModel);

		getGroupList().addMouseListener(new MouseAdapter() {

			private WebFrame frame;

			@Override
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Group group = getGroups().get(index);
					List<String> studentNames = group.getStudentNames();
					frame = new WebFrame();
					WebList studentList = new WebList();
					studentList.setListData(studentNames.toArray());
					frame.getContentPane().add(new WebScrollPane(studentList));
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		});

		getGroupList().setModel(new AbstractListModel() {
			String[] values = new String[] { i18n.getParam("Group A"),
					i18n.getParam("Group B"), i18n.getParam("Group C"), i18n.getParam("Group D"), i18n.getParam("Group E"), i18n.getParam("Group F"),
					i18n.getParam("Group G"), i18n.getParam("Group H"), i18n.getParam("Group I"), i18n.getParam("Group J"), i18n.getParam("Group K"),
					i18n.getParam("Group L"), i18n.getParam("Group M"), i18n.getParam("Group N"), i18n.getParam("Group O") };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(int index) {
				return values[index];
			}
		});

		getGroupList().setCellRenderer(new CustomCellRenderer());

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
		scrollGroupPanel.setOpaque(false);
		scrollGroupPanel.getViewport().setOpaque(false);

		rightButtonPanel = new WebPanel();
		rightButtonPanel.setOpaque(false);

		WebPanel bottomButtonPanel = new WebPanel();
		bottomButtonPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		bottomButtonPanel.setOpaque(false);

		// create the scrollable desktop instance and add it to the JFrame
		JScrollDesktopPane scrollableDesktop = new JScrollDesktopPane();
		setDesktopPaneScroll(scrollableDesktop);

		/// @Armin codes Clean Up actions and move to proper place
		WebPopupMenu popup = new WebPopupMenu();
		WebMenuItem menuItemThumbView = new WebMenuItem("Thumbnail View");
		WebMenuItem menuItemGroupView = new WebMenuItem("Group View");
		WebMenuItem menuItemListView = new WebMenuItem("List View");

		popup.add(menuItemThumbView);
		popup.add(menuItemGroupView);
		popup.add(menuItemListView);

		scrollableDesktop.getDesktopMediator().getDesktopScrollpane()
				.getDesktopPane().setComponentPopupMenu(popup);
		// getGroupList().setComponentPopupMenu(popup);
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
		// /////////////////////////////////////////////////////////////////

		// desktopPane.setBackground(new Color(237, 246, 253));
		centerPanel.setBackground(new Color(237, 246, 253));
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
		bottomButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));

		centerListPanel.setBackground(new Color(237, 246, 253));
		centerGroupPanel.setBackground(new Color(237, 246, 253));
		scrollGroupPanel.setBackground(new Color(237, 246, 253));
		getGroupList().setBackground(new Color(0, 0, 0, 0));
		getGroupList().setOpaque(false);
		centerPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { getDesktopPaneScroll(), centerListPanel,
						centerGroupPanel }));

		CustomBottomButton surveyButton = new CustomBottomButton();
		surveyButton.setIconTextGap(30);
		surveyButton.putClientProperty("key", "survey");
		surveyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		surveyButton.setDrawShade(false);
		surveyButton.setRound(10);
		surveyButton.setTopSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setForeground(Color.LIGHT_GRAY);
		surveyButton.setText(i18n.getParam("Survey"));
		surveyButton.setBottomBgColor(new Color(225, 234, 244));
		surveyButton.setTopBgColor(new Color(116, 166, 219));
		surveyButton.setUndecorated(true);
		bottomButtonPanel.add(surveyButton);

		CustomBottomButton whiteBoardButton = new CustomBottomButton();
		whiteBoardButton.setIconTextGap(30);
		whiteBoardButton.putClientProperty("key", "whiteBoard");
		whiteBoardButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		whiteBoardButton.setDrawShade(false);
		whiteBoardButton.setRound(10);
		whiteBoardButton.setTopSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setForeground(Color.LIGHT_GRAY);
		whiteBoardButton.setText(i18n.getParam("White board"));
		whiteBoardButton.setBottomBgColor(new Color(225, 234, 244));
		whiteBoardButton.setTopBgColor(new Color(116, 166, 219));
		whiteBoardButton.setUndecorated(true);
		bottomButtonPanel.add(whiteBoardButton);

		CustomBottomButton powerButton = new CustomBottomButton();
		powerButton.setIconTextGap(30);
		powerButton.putClientProperty("key", "pcController");
		powerButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		powerButton.setDrawShade(false);
		powerButton.setRound(10);
		powerButton.setTopSelectedBgColor(new Color(75, 113, 158));
		powerButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		powerButton.setForeground(Color.LIGHT_GRAY);
		powerButton.setText(i18n.getParam("PC Controller"));
		powerButton.setBottomBgColor(new Color(225, 234, 244));
		powerButton.setTopBgColor(new Color(116, 166, 219));
		powerButton.setUndecorated(true);
		bottomButtonPanel.add(powerButton);

		CustomPowerButton powerOffButton = new CustomPowerButton(
				i18n.getParam("Power Off"), new ImageIcon(
						InstructorNoa.class
								.getResource("/icons/noa_en/poweroff.png")));
		powerOffButton.putClientProperty("key", "powerOff");
		powerOffButton.setUndecorated(false);
		powerOffButton.setHorizontalAlignment(SwingConstants.LEADING);
		powerOffButton.setHorizontalTextPosition(SwingConstants.TRAILING);
		powerOffButton.setIconTextGap(20);
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

		CustomPowerButton logOffButton = new CustomPowerButton(
				i18n.getParam("Log Off"), new ImageIcon(
						InstructorNoa.class
								.getResource("/icons/noa_en/logoff.png")));
		logOffButton.putClientProperty("key", "logOff");
		logOffButton.setUndecorated(false);
		logOffButton.setHorizontalAlignment(SwingConstants.LEADING);
		logOffButton.setHorizontalTextPosition(SwingConstants.TRAILING);
		logOffButton.setIconTextGap(20);
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

		CustomPowerButton restartButton = new CustomPowerButton(
				i18n.getParam("Restart"), new ImageIcon(
						InstructorNoa.class
								.getResource("/icons/noa_en/restart.png")));
		restartButton.putClientProperty("key", "restart");
		restartButton.setUndecorated(false);
		restartButton.setHorizontalAlignment(SwingConstants.LEADING);
		restartButton.setHorizontalTextPosition(SwingConstants.TRAILING);
		restartButton.setIconTextGap(20);
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

		CustomPowerButton lockButton = new CustomPowerButton(
				i18n.getParam("Lock/Unlock"), new ImageIcon(
						InstructorNoa.class
								.getResource("/icons/noa_en/lock.png")));
		lockButton.putClientProperty("key", "lock");
		lockButton.setUndecorated(false);
		// lockButton.setMargin(0, 10, 0, 0);
		lockButton.setHorizontalAlignment(SwingConstants.LEADING);
		lockButton.setHorizontalTextPosition(SwingConstants.TRAILING);
		lockButton.setIconTextGap(20);
		lockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LockAction();
			}
		});

		WebButtonPopup pcControllerPopupButton = new WebButtonPopup(
				powerButton, PopupWay.upCenter);

		GroupPanel pcControllerPopupContent = new GroupPanel(3, false,
				powerOffButton, logOffButton, restartButton, lockButton);
		pcControllerPopupContent.setMargin(10);

		CustomPowerPanel panel = new CustomPowerPanel();
		panel.setPreferredSize(new Dimension(160, 210));
		panel.add(pcControllerPopupContent);
		pcControllerPopupButton.setMargin(5);
		pcControllerPopupButton.setContent(panel);

		CustomBottomButton internetButton = new CustomBottomButton();
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

		final WebCheckBox sendToAllWebCheckBox = new WebCheckBox();
		sendToAllWebCheckBox.setText("Send to All");

		CustomPowerPanel internetContentPanel = new CustomPowerPanel();
		GroupPanel InternetPopupContent = new GroupPanel(5, false,
				internetBlockButton, WebsiteTextField,
				internetSendWebsiteButton, sendToAllWebCheckBox);
		InternetPopupContent.setMargin(15);

		internetContentPanel.add(InternetPopupContent);
		internetPopupButton.setContent(internetContentPanel);
		internetPopupButton.setMargin(5);
		internetPopupButton.setDefaultFocusComponent(internetBlockButton);

		internetButton.setIconTextGap(30);
		internetButton.putClientProperty("key", "internet");
		internetButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		internetButton.setDrawShade(false);
		internetButton.setRound(10);
		internetButton.setTopSelectedBgColor(new Color(75, 113, 158));
		internetButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		internetButton.setForeground(Color.LIGHT_GRAY);
		internetButton.setText(i18n.getParam("Internet Controller"));
		internetButton.setBottomBgColor(new Color(225, 234, 244));
		internetButton.setTopBgColor(new Color(116, 166, 219));
		internetButton.setUndecorated(true);
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

				if (sendToAllWebCheckBox.isSelected()) {
					try {
						if (!WebsiteTextField.getText().equals("")) {
							WebsiteCommand wc = new WebsiteCommand(InetAddress
									.getLocalHost().getHostAddress(), Config
									.getParam("broadcastingIp"), Integer
									.parseInt(Config.getParam("port")),
									WebsiteTextField.getText());
							serverService.send(wc);
						} else {
							return;
						}
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e);
						e.printStackTrace();
					}
				} else {
					if (((JComponent) card).getClientProperty("viewMode")
							.equals("thumbView")) {
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
					} else if (((JComponent) card)
							.getClientProperty("viewMode").equals("groupView")) {
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

		CustomBottomButton programButton = new CustomBottomButton();

		programButton.setIconTextGap(30);
		programButton.putClientProperty("key", "program");
		programButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programButton.setDrawShade(false);
		programButton.setRound(10);
		programButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programButton.setForeground(Color.LIGHT_GRAY);
		programButton.setText(i18n.getParam("Allow & Restrict Program"));
		programButton.setBottomBgColor(new Color(225, 234, 244));
		programButton.setTopBgColor(new Color(116, 166, 219));
		programButton.setUndecorated(true);
		bottomButtonPanel.add(programButton);

		CustomBottomButton programStartButton = new CustomBottomButton();
		programStartButton.setIconTextGap(30);
		programStartButton.putClientProperty("key", "programStart");
		programStartButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programStartButton.setDrawShade(false);
		programStartButton.setRound(10);
		programStartButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setForeground(Color.LIGHT_GRAY);
		programStartButton.setText(i18n.getParam("Program Starter"));
		programStartButton.setBottomBgColor(new Color(225, 234, 244));
		programStartButton.setTopBgColor(new Color(116, 166, 219));
		programStartButton.setUndecorated(true);
		bottomButtonPanel.add(programStartButton);

		CustomBottomButton conversationButton = new CustomBottomButton();
		conversationButton.setIconTextGap(30);
		conversationButton.putClientProperty("key", "conversation");
		conversationButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		conversationButton.setDrawShade(false);
		conversationButton.setRound(10);
		conversationButton.setTopSelectedBgColor(new Color(75, 113, 158));
		conversationButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		conversationButton.setForeground(Color.LIGHT_GRAY);
		conversationButton.setText(i18n.getParam("Conversations"));
		conversationButton.setBottomBgColor(new Color(225, 234, 244));
		conversationButton.setTopBgColor(new Color(116, 166, 219));
		conversationButton.setUndecorated(true);
		bottomButtonPanel.add(conversationButton);

		rightButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		ImageIcon monitorIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/monitor.png"));
		ImageIcon monitorIconScaled = new ImageIcon(monitorIcon.getImage()
				.getScaledInstance(monitorIcon.getIconWidth() - 10,
						monitorIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton monitorButton = new CustomButton(monitorIconScaled);
		monitorButton.setUndecorated(true);
		monitorButton.setHorizontalAlignment(SwingConstants.LEADING);
		monitorButton.setIconTextGap(20);
		monitorButton.putClientProperty("key", "monitor");
		monitorButton.setFont(font);
		monitorButton.setForeground(Color.WHITE);
		monitorButton.setText(i18n.getParam("Monitoring"));
		monitorButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(monitorButton);

		ImageIcon intercomIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/intercom.png"));
		ImageIcon intercomIconScaled = new ImageIcon(intercomIcon.getImage()
				.getScaledInstance(intercomIcon.getIconWidth() - 10,
						intercomIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton intercomButton = new CustomButton(intercomIconScaled);
		this.intercomButton = intercomButton;
		intercomButton.setUndecorated(true);
		intercomButton.setHorizontalAlignment(SwingConstants.LEADING);
		intercomButton.setIconTextGap(20);
		intercomButton.putClientProperty("key", "intercom");
		intercomButton.setFont(font);
		intercomButton.setForeground(Color.WHITE);
		intercomButton.setText(i18n.getParam("Intercom"));
		intercomButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(intercomButton);

		ImageIcon videochatIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/videochat.png"));
		ImageIcon videochatIconScaled = new ImageIcon(videochatIcon.getImage()
				.getScaledInstance(videochatIcon.getIconWidth() - 10,
						videochatIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton videoChatButton = new CustomButton(videochatIconScaled);
		videoChatButton.setUndecorated(true);
		videoChatButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoChatButton.setIconTextGap(20);
		videoChatButton.putClientProperty("key", "videoChat");
		videoChatButton.setFont(font);
		videoChatButton.setForeground(Color.WHITE);
		videoChatButton.setText(i18n.getParam("Video Chat"));
		videoChatButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(videoChatButton);

		ImageIcon groupIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/group.png"));
		ImageIcon groupIconScaled = new ImageIcon(groupIcon.getImage()
				.getScaledInstance(groupIcon.getIconWidth() - 10,
						groupIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton groupButton = new CustomButton(groupIconScaled);
		groupButton.setUndecorated(true);
		groupButton.setHorizontalAlignment(SwingConstants.LEADING);
		groupButton.setIconTextGap(20);
		groupButton.putClientProperty("key", "group");
		groupButton.setFont(font);
		groupButton.setForeground(Color.WHITE);
		groupButton.setText(i18n.getParam("Groups"));
		groupButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(groupButton);

		ImageIcon modelIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/modeling.png"));
		ImageIcon modelIconScaled = new ImageIcon(modelIcon.getImage()
				.getScaledInstance(modelIcon.getIconWidth() - 10,
						modelIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton modelButton = new CustomButton(modelIconScaled);
		modelButton.setUndecorated(true);
		modelButton.setHorizontalAlignment(SwingConstants.LEADING);
		modelButton.setIconTextGap(20);
		modelButton.putClientProperty("key", "model");
		modelButton.setFont(font);
		modelButton.setForeground(Color.WHITE);
		modelButton.setText(i18n.getParam("Modeling"));
		modelButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(modelButton);

		ImageIcon recordIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/recording.png"));
		ImageIcon recordIconScaled = new ImageIcon(recordIcon.getImage()
				.getScaledInstance(recordIcon.getIconWidth() - 10,
						recordIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton recordButton = new CustomButton(recordIconScaled);
		recordButton.setUndecorated(true);
		recordButton.setHorizontalAlignment(SwingConstants.LEADING);
		recordButton.setIconTextGap(20);
		recordButton.putClientProperty("key", "record");
		recordButton.setFont(font);
		recordButton.setForeground(Color.WHITE);
		recordButton.setText(i18n.getParam("Recording"));
		recordButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(recordButton);

		ImageIcon speechIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/speech.png"));
		ImageIcon speechIconScaled = new ImageIcon(speechIcon.getImage()
				.getScaledInstance(speechIcon.getIconWidth() - 10,
						speechIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton speechButton = new CustomButton(speechIconScaled);
		speechButton.setUndecorated(true);
		speechButton.setHorizontalAlignment(SwingConstants.LEADING);
		speechButton.setIconTextGap(10);
		speechButton.putClientProperty("key", "speech");
		speechButton.setFont(font);
		speechButton.setForeground(Color.WHITE);
		speechButton.setText(i18n.getParam("Speech Recognition"));
		speechButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(speechButton);

		ImageIcon fileIcon = new ImageIcon(
				InstructorNoa.class
						.getResource("/icons/noa_en/filesharing.png"));
		ImageIcon fileIconScaled = new ImageIcon(fileIcon.getImage()
				.getScaledInstance(fileIcon.getIconWidth() - 10,
						fileIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton fileButton = new CustomButton(fileIconScaled);
		fileButton.setUndecorated(true);
		fileButton.setHorizontalAlignment(SwingConstants.LEADING);
		fileButton.setIconTextGap(20);
		fileButton.putClientProperty("key", "file");
		fileButton.setFont(font);
		fileButton.setForeground(Color.WHITE);
		fileButton.setText(i18n.getParam("File Sharing"));
		fileButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(fileButton);

		ImageIcon quizIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/exam.png"));
		ImageIcon quizIconScaled = new ImageIcon(quizIcon.getImage()
				.getScaledInstance(quizIcon.getIconWidth() - 10,
						quizIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton quizButton = new CustomButton(quizIconScaled);
		quizButton.setUndecorated(true);
		quizButton.setHorizontalAlignment(SwingConstants.LEADING);
		quizButton.setIconTextGap(20);
		quizButton.putClientProperty("key", "quiz");
		quizButton.setFont(font);
		quizButton.setForeground(Color.WHITE);
		quizButton.setText(i18n.getParam("Exam"));
		quizButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(quizButton);

		ImageIcon videoIcon = new ImageIcon(
				InstructorNoa.class
						.getResource("/icons/noa_en/movieplayer.png"));
		ImageIcon videoIconScaled = new ImageIcon(videoIcon.getImage()
				.getScaledInstance(videoIcon.getIconWidth() - 10,
						videoIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton videoButton = new CustomButton(videoIconScaled);
		videoButton.setUndecorated(true);
		videoButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoButton.setIconTextGap(20);
		videoButton.putClientProperty("key", "movieplayer");
		videoButton.setFont(font);
		videoButton.setForeground(Color.WHITE);
		videoButton.setText(i18n.getParam("Movie Player"));
		videoButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(videoButton);

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

		// be careful with this list index, we work with index
		final String[] jasperFileNames = { "AnswerRate", "StudentResult",
				"SummaryofStudents" };
		final WebComboBox reportComboBox = new WebComboBox(new String[] {
				i18n.getParam("Answer rate"), i18n.getParam("Student result"), i18n.getParam("Summary of students") });

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

		ImageIcon accountIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/account.png"));
		ImageIcon accountIconScaled = new ImageIcon(accountIcon.getImage()
				.getScaledInstance(accountIcon.getIconWidth() - 10,
						accountIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton accountButton = new CustomButton(accountIconScaled);
		accountButton.setUndecorated(true);
		accountButton.setHorizontalAlignment(SwingConstants.LEADING);
		accountButton.setIconTextGap(20);
		accountButton.putClientProperty("key", "account");
		accountButton.setFont(font);
		accountButton.setForeground(Color.WHITE);
		accountButton.setText(i18n.getParam("My account"));
		accountButton.setMargin(new Insets(5, 10, 0, 0));
		rightButtonPanel.add(accountButton);

		ImageIcon chatIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/chat.png"));
		ImageIcon chatIconScaled = new ImageIcon(chatIcon.getImage()
				.getScaledInstance(chatIcon.getIconWidth() - 10,
						chatIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		CustomButton chatButton = new CustomButton(chatIconScaled);
		chatButton.setUndecorated(true);
		chatButton.setHorizontalAlignment(SwingConstants.LEADING);
		chatButton.setIconTextGap(20);
		chatButton.putClientProperty("key", "chat");
		chatButton.setFont(font);
		chatButton.setForeground(Color.WHITE);
		chatButton.setText(i18n.getParam("Chat"));
		chatButton.setMargin(new Insets(5, 10, 0, 0));
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

		GroupLayout groupLayout = new GroupLayout(frame.getMainContentPane());
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
														.addComponent(
																bottomButtonPanel,
																Alignment.TRAILING,
																956, 956,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				centerPanel,
																				759,
																				759,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				rightButtonPanel,
																				187,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(rightButtonPanel,
												GroupLayout.PREFERRED_SIZE,
												297, Short.MAX_VALUE)
										.addComponent(centerPanel,
												GroupLayout.DEFAULT_SIZE, 307,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(bottomButtonPanel,
								GroupLayout.PREFERRED_SIZE, 47,
								GroupLayout.PREFERRED_SIZE)));

		frame.getMainContentPane().setLayout(groupLayout);

		// Add Event Handlers
		InstructorNoaUtil instructorNoaUtil = new InstructorNoaUtil();
		instructorNoaUtil.addEventsRightPanel(rightButtonPanel);
		instructorNoaUtil.addEventsBottomPanel(bottomButtonPanel, frame);
		frame.getTopContentPane().setLayout(
				new BoxLayout(frame.getTopContentPane(), BoxLayout.X_AXIS));

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		frame.getTopContentPane().add(panel_3);

		WebPanel topButtonPanel = new WebPanel();
		topButtonPanel.setOpaque(false);
		
		WebPanel logoPanel = new WebPanel();
		logoPanel.setOpaque(false);

		CustomTopButton volumeButton = new CustomTopButton();
		volumeButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa_en/volume.png")));
		volumeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		volumeButton.setForeground(Color.DARK_GRAY);
		volumeButton.putClientProperty("key", "volume");
		volumeButton.setText(i18n.getParam("Volume Control"));
		volumeButton.setUndecorated(true);
		volumeButton.setOpaque(false);

		CustomCallAllButton callAllButton = new CustomCallAllButton();
		callAllButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa_en/callall.png")));
		callAllButton.putClientProperty("key", "callAll");
		callAllButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		callAllButton.setForeground(Color.DARK_GRAY);
		callAllButton.setText(i18n.getParam("Call All"));
		callAllButton.setOpaque(false);
		callAllButton.setUndecorated(true);

		CustomTopButton viewModeButton = new CustomTopButton();
		viewModeButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa_en/viewmode.png")));
		viewModeButton.putClientProperty("key", "viewMode");
		viewModeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		viewModeButton.setForeground(Color.DARK_GRAY);
		viewModeButton.setText(i18n.getParam("View Mode"));
		viewModeButton.setUndecorated(true);
		viewModeButton.setOpaque(false);

		CustomTopButton languageButton = new CustomTopButton();
		languageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		languageButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa_en/lang.png")));
		languageButton.putClientProperty("key", "language");
		languageButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		languageButton.setForeground(Color.DARK_GRAY);
		languageButton.setText(i18n.getParam("Language"));
		languageButton.setUndecorated(true);
		languageButton.setOpaque(false);

		WebButtonPopup langPopupButton = new WebButtonPopup(languageButton,
				PopupWay.downCenter);

		GroupPanel langPopupContent = new GroupPanel(5, false, langList);
		langPopupContent.setMargin(15);
		langPopupButton.setContent(langPopupContent);

		CustomPanel panelClock = new CustomPanel("/icons/noa_en/clockPanel.png");
		panelClock.setOpaque(false);
		GroupLayout gl_topPanel = new GroupLayout(topButtonPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(volumeButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(viewModeButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(callAllButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(languageButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelClock, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelClock, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
						.addGroup(gl_topPanel.createSequentialGroup()
							.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(viewModeButton, 33, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(languageButton, 33, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(volumeButton, 33, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(callAllButton, 33, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GroupLayout gl_panelClock = new GroupLayout(panelClock);
		gl_panelClock.setHorizontalGroup(gl_panelClock.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelClock
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 176,
								Short.MAX_VALUE).addContainerGap()));
		gl_panelClock.setVerticalGroup(gl_panelClock.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panelClock
						.createSequentialGroup()
						.addGap(4)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 64,
								Short.MAX_VALUE)));

		final DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		ImageIcon _dIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/clockdd.png"));
		new ImageIcon(_dIcon.getImage().getScaledInstance(15, 15,
				Image.SCALE_SMOOTH));

		ImageIcon _0Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/0.png"));

		ImageIcon _1Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/1.png"));

		ImageIcon _2Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/2.png"));

		ImageIcon _3Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/3.png"));

		ImageIcon _4Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/4.png"));

		ImageIcon _5Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/5.png"));

		ImageIcon _6Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/6.png"));

		ImageIcon _7Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/7.png"));

		ImageIcon _8Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/8.png"));

		ImageIcon _9Icon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/9.png"));
		ImageIcon _ddIcon = new ImageIcon(
				InstructorNoa.class.getResource("/icons/noa_en/clockdd.png"));
		ImageIcon _ddIconScaled = new ImageIcon(_ddIcon.getImage()
				.getScaledInstance(10, 25, Image.SCALE_SMOOTH));

		JLabel lblDate = new JLabel("00/00/0000");
		lblDate.setText(dateFormat.format(cal.getTime()).split(" ")[0]);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDate.setForeground(Color.WHITE);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				lblDate))
														.addComponent(
																panel_2,
																GroupLayout.PREFERRED_SIZE,
																169,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 38,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblDate).addGap(6)));

		lblHour1 = new JLabel();
		lblHour1.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));

		lblHour2 = new JLabel();
		lblHour2.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));

		lblMin1 = new JLabel();
		lblMin1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin1.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));

		lblMin2 = new JLabel();
		lblMin2.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin2.setIcon(new ImageIcon(_0Icon.getImage().getScaledInstance(25,
				25, Image.SCALE_SMOOTH)));

		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(_ddIconScaled);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(6)
					.addComponent(lblHour1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHour2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMin1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMin2)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblMin2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
						.addComponent(lblMin1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
						.addComponent(label, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE)
						.addComponent(lblHour2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
						.addComponent(lblHour1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);

		final HashMap<String, ImageIcon> clockIcons = new HashMap<String, ImageIcon>();
		clockIcons.put("0", _0Icon);
		clockIcons.put("1", _1Icon);
		clockIcons.put("2", _2Icon);
		clockIcons.put("3", _3Icon);
		clockIcons.put("4", _4Icon);
		clockIcons.put("5", _5Icon);
		clockIcons.put("6", _6Icon);
		clockIcons.put("7", _7Icon);
		clockIcons.put("8", _8Icon);
		clockIcons.put("9", _9Icon);

		Thread _clockAndTime = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Calendar cal = Calendar.getInstance();
					String CurrentTime = dateFormat.format(cal.getTime());
					String time = CurrentTime.split(" ")[1];
					final String Hour = time.split(":")[0];
					final String Minute = time.split(":")[1];
					if (lblHour1.getText() == String.valueOf(Hour.charAt(0))
							&& lblHour2.getText() == String.valueOf(Hour
									.charAt(1))
							&& lblMin1.getText() == String.valueOf(Minute
									.charAt(0))
							&& lblMin2.getText() == String.valueOf(Minute
									.charAt(0))) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					try {
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								try {
									lblHour1.setIcon(new ImageIcon(
											clockIcons
													.get(String.valueOf(Hour
															.charAt(0)))
													.getImage()
													.getScaledInstance(
															lblHour1.getWidth(),
															lblHour1.getHeight(),
															Image.SCALE_SMOOTH)));
									lblHour2.setIcon(new ImageIcon(
											clockIcons
													.get(String.valueOf(Hour
															.charAt(1)))
													.getImage()
													.getScaledInstance(
															lblHour2.getWidth(),
															lblHour2.getHeight(),
															Image.SCALE_SMOOTH)));
									lblMin1.setIcon(new ImageIcon(clockIcons
											.get(String.valueOf(Minute
													.charAt(0)))
											.getImage()
											.getScaledInstance(
													lblMin1.getWidth(),
													lblMin1.getHeight(),
													Image.SCALE_SMOOTH)));
									lblMin2.setIcon(new ImageIcon(clockIcons
											.get(String.valueOf(Minute
													.charAt(1)))
											.getImage()
											.getScaledInstance(
													lblMin2.getWidth(),
													lblMin2.getHeight(),
													Image.SCALE_SMOOTH)));
									lblHour1.repaint();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		_clockAndTime.start();

		panel_1.setLayout(gl_panel_1);
		panelClock.setLayout(gl_panelClock);
		topButtonPanel.setLayout(gl_topPanel);
		instructorNoaUtil.addEventsTopPanel(topButtonPanel);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_3
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(logoPanel,GroupLayout.PREFERRED_SIZE,300,Short.MAX_VALUE)
						.addComponent(topButtonPanel,
								GroupLayout.PREFERRED_SIZE, 519,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(
				Alignment.LEADING)
				.addComponent(logoPanel,GroupLayout.PREFERRED_SIZE,100,Short.MAX_VALUE)
				.addComponent(topButtonPanel,GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE));
		
		lblLogo = new JLabel("");
		
		lblLogoText = new JLabel("");
		GroupLayout gl_logoPanel = new GroupLayout(logoPanel);
		gl_logoPanel.setHorizontalGroup(
			gl_logoPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_logoPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLogoText, 166, 433, Short.MAX_VALUE)
					.addGap(378))
		);
		gl_logoPanel.setVerticalGroup(
			gl_logoPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_logoPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_logoPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_logoPanel.createSequentialGroup()
							.addGap(46)
							.addComponent(lblLogoText, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
						.addComponent(lblLogo, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
					.addContainerGap())
		);
		logoPanel.setLayout(gl_logoPanel);
		panel_3.setLayout(gl_panel_3);


		getGroupList().addMouseListener(new MouseAdapter() {
			@Override
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
			selectedStudent = (String) getDesktopPane().getSelectedFrame()
					.getClientProperty("ip");
			try {
				lockCommand = new LockCommand(InetAddress.getLocalHost()
						.getHostAddress(), selectedStudent,
						Integer.parseInt(Config.getParam("port")));
				serverService.send(lockCommand);
				if (!(boolean) getDesktopPane().getSelectedFrame()
						.getClientProperty("lock")) {
					getDesktopPane().getSelectedFrame().putClientProperty(
							"lock", true);
					getDesktopPane()
							.getSelectedFrame()
							.setFrameIcon(
									new ImageIcon(
											ImageIO.read(InstructorNoa.class
													.getResourceAsStream("/icons/noa/lock.png"))));

				} else {
					getDesktopPane().getSelectedFrame().putClientProperty(
							"lock", false);
					if (getDesktopPane().getSelectedFrame().isSelected()) {
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
		CardLayout cl = (CardLayout) InstructorNoa.getCenterPanel().getLayout();
		cl.show(centerPanel, "listView");
	}

	protected void GroupViewActionListener() {
		CardLayout cl = (CardLayout) InstructorNoa.getCenterPanel().getLayout();
		cl.show(centerPanel, "groupView");
	}

	protected void ThumbViewActionListener() {
		CardLayout cl = (CardLayout) InstructorNoa.getCenterPanel().getLayout();
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

	public static void setGroupList(CustomJList groupList) {
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

	public static void changeModelButtonText(String text) {
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

	public static ArrayList<String> getConversationPairs() {
		return conversationPairs;
	}

	public static void setConversationPairs(ArrayList<String> conversationPairs) {
		InstructorNoa.conversationPairs = conversationPairs;
	}

	public static ArrayList<String> getConversationIps() {
		return conversationIps;
	}

	public static void setConversationIps(ArrayList<String> conversationIps) {
		InstructorNoa.conversationIps = conversationIps;
	}
}

class GradientPanel extends WebPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final int N = 32;

	public GradientPanel() {
		// this.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));
		// this.add(new WebLabel("Test:", WebLabel.CENTER));
		// this.add(new WebTextField("This is a test."));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// Color color1 = getBackground();
		Color color1 = new Color(43, 106, 89);
		Color color2 = new Color(77, 178, 184);
		int w = getWidth();
		int h = getHeight();
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}
}