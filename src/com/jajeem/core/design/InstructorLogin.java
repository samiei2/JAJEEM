package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebPasswordField;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.room.model.Session;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.StartUp;

public class InstructorLogin extends JDialog {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 9010296991450692526L;
	static ArrayList<Course> courseList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JajeemExcetionHandler.logMessage("Application Started!",
					InstructorLogin.class);
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			@SuppressWarnings("unused")
			InstructorLogin dialog = new InstructorLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public InstructorLogin() throws SQLException {
		@SuppressWarnings("unused")
		StartUp start = new StartUp();
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		loginDialog loginDialog = new loginDialog(this);
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);
		loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		loginDialog.setBounds(400, 200, 550, 400);
		loginDialog.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {

			}
		});

		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	private static class loginDialog extends WebDialog {

		private static final long serialVersionUID = -5121321476236877112L;
		private final JPanel contentPanel = new JPanel();
		private final WebPasswordField password = new WebPasswordField(15);

		public loginDialog(Window owner) throws SQLException {
			super(owner, "Welcome to iCalabo");
			// setIconImage(Toolkit.getDefaultToolkit().getImage(
			// InstructorLogin.class
			// .getResource("/icons/menubar/jajeem.jpg")));
			try {
				new Config();

			} catch (Throwable e) {
			}

			// get list of instructors
			InstructorService instructorService = new InstructorService();
			final ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
					.list();
			EventList<com.jajeem.core.model.Instructor> insList = new BasicEventList<com.jajeem.core.model.Instructor>();
			insList.addAll(instructorList);
			FilterList<Instructor> filterList = new FilterList<Instructor>(
					insList);
			SortedList<Instructor> sortedIns = new SortedList<Instructor>(
					filterList, null);

			DefaultListModel listModel1 = new DefaultListModel();

			// we should always have admin user
			if (instructorService.get("admin") == null) {
				Instructor admin = new Instructor();
				admin.setFirstName("admin");
				admin.setLastName("admin");
				admin.setUsername("admin");
				admin.setPassword("admin");
				instructorService.create(admin);
			}

			for (com.jajeem.core.model.Instructor instructorItem : sortedIns) {
				if (!instructorItem.getUsername().equals("admin")) {
					listModel1.addElement(instructorItem.getFirstName() + " "
							+ instructorItem.getLastName() + " ("
							+ instructorItem.getUsername() + ")");
				}
			}

			final WebList list1 = new WebList(listModel1);
			WebScrollPane sPanel1 = new WebScrollPane(list1);
			sPanel1.setDrawBackground(true);
			sPanel1.setShadeWidth(0);
			sPanel1.setRound(0);
			sPanel1.setViewportBorder(new TitledBorder(null, "Instructors",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));

			final DefaultListModel listModel2 = new DefaultListModel();
			final WebList list2 = new WebList(listModel2);
			WebScrollPane sPanel2 = new WebScrollPane(list2);
			sPanel2.setDrawBackground(true);
			sPanel2.setShadeWidth(0);
			sPanel2.setRound(0);
			sPanel2.setViewportBorder(new TitledBorder(null, "Courses",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));

			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
			{
				JSplitPane splitPane = new JSplitPane();
				splitPane.setResizeWeight(0.5);
				contentPanel.add(splitPane, BorderLayout.CENTER);
				{
					splitPane.setLeftComponent(sPanel1);
				}
				{
					splitPane.setRightComponent(sPanel2);
				}
			}
			{
				JPanel panel = new JPanel();
				contentPanel.add(panel, BorderLayout.SOUTH);
				{
					JLabel lblNewLabel = new JLabel("Password");
					panel.add(lblNewLabel);
				}
				{
					password.setShadeWidth(0);
					password.setRound(0);
					panel.add(password);

				}
			}
			{
				JPanel buttonPane = new JPanel();
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					WebButton okButton = new WebButton("Login");
					final WebButton adminButton = new WebButton(
							"Login as Administrator");
					adminButton.setVisible(false);
					WebButton moreButton = new WebButton("More");
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
					GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
					gl_buttonPane
							.setHorizontalGroup(gl_buttonPane
									.createParallelGroup(Alignment.LEADING)
									.addGroup(
											Alignment.TRAILING,
											gl_buttonPane
													.createSequentialGroup()
													.addContainerGap(197,
															Short.MAX_VALUE)
													.addComponent(okButton)
													.addGap(190)));
					gl_buttonPane.setHorizontalGroup(gl_buttonPane
							.createParallelGroup(Alignment.TRAILING).addGroup(
									Alignment.TRAILING,
									gl_buttonPane
											.createSequentialGroup()
											.addContainerGap(197,
													Short.MAX_VALUE)
											.addComponent(adminButton)
											.addGap(190)));

					gl_buttonPane.setHorizontalGroup(gl_buttonPane
							.createParallelGroup(Alignment.TRAILING).addGroup(
									Alignment.TRAILING,
									gl_buttonPane
											.createSequentialGroup()
											.addContainerGap(197,
													Short.MAX_VALUE)
											.addComponent(moreButton)
											.addGap(190)));

					gl_buttonPane.setVerticalGroup(gl_buttonPane
							.createParallelGroup(Alignment.LEADING).addGroup(
									gl_buttonPane
											.createSequentialGroup()
											.addComponent(okButton)
											.addContainerGap(
													GroupLayout.DEFAULT_SIZE,
													Short.MAX_VALUE)));
					gl_buttonPane.setVerticalGroup(gl_buttonPane
							.createParallelGroup(Alignment.LEADING).addGroup(
									gl_buttonPane
											.createSequentialGroup()
											.addComponent(adminButton)
											.addContainerGap(
													GroupLayout.DEFAULT_SIZE,
													Short.MAX_VALUE)));
					gl_buttonPane.setVerticalGroup(gl_buttonPane
							.createParallelGroup(Alignment.LEADING).addGroup(
									gl_buttonPane
											.createSequentialGroup()
											.addComponent(moreButton)
											.addContainerGap(
													GroupLayout.DEFAULT_SIZE,
													Short.MAX_VALUE)));

					list1.getSelectionModel().setSelectionMode(
							ListSelectionModel.SINGLE_SELECTION);

					list1.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							JList list = (JList) evt.getSource();
							if (evt.getClickCount() == 2) {
								int index = list.locationToIndex(evt.getPoint());
								ArrayList<Course> courseList1 = null;
								try {
									RoomService rs = new RoomService();

									courseList1 = rs.getCourseDAO()
											.getCoursesByInstructorId(
													instructorList.get(index)
															.getId());
									listModel2.clear();
									for (Course courseItem : courseList1) {
										Date startDate = new Date(courseItem
												.getStartDate());
										SimpleDateFormat dt = new SimpleDateFormat(
												"yyyy-MM-dd");
										listModel2.addElement(courseItem
												.getName()
												+ "-"
												+ courseItem.getLevel()
												+ " ("
												+ courseItem.getClassType()
												+ ", "
												+ dt.format(startDate)
												+ ")");
									}
									
									if (courseList1.size() == 0) {
										listModel2.addElement("No courses available");
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								courseList = courseList1;
							}
						}
					});

					ActionListener okButtonListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								boolean grant = false;
								if (list1.getSelectedValue() == null
										|| list2.getSelectedValue() == null) {
									return;
								}

								String user = list1.getSelectedValue()
										.toString();
								InstructorService instructorService = new InstructorService();
								int indexOfOpenBracket = user.indexOf("(");
								int indexOfLastBracket = user.lastIndexOf(")");
								grant = instructorService.authenticate(user
										.substring(indexOfOpenBracket + 1,
												indexOfLastBracket), password
										.getPassword());
								if (grant) {
									Instructor instructor = instructorService
											.get(user.substring(
													indexOfOpenBracket + 1,
													indexOfLastBracket));
									com.jajeem.util.Session
											.setInstructor(instructor);

									// TODO Session initialization which is set
									// to default
									Session session = new Session();
									session.setInstructor(instructor);
									session.setCourse(courseList.get(list2
											.getSelectedIndex()));
									com.jajeem.util.Session.setSession(session);
									// TODO Save Session in database

									// //////////////////////////////

									setVisible(false);
									InstructorNoa
											.main(new String[] {
													(String) list1
															.getSelectedValue(),
													(String) list2
															.getSelectedValue() });

								} else {
									password.setBackground(Color
											.decode("#FAD9D9"));
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					};

					ActionListener adminButtonListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								boolean grant = false;
								String user = "admin";
								InstructorService instructorService = new InstructorService();
								grant = instructorService.authenticate(user,
										password.getPassword());
								if (grant) {
									AdminPanel frame = new AdminPanel();
									frame.setVisible(true);
									setVisible(false);
								} else {
									password.setBackground(Color
											.decode("#FAD9D9"));
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					};

					ActionListener moreButtonListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (adminButton.isVisible()) {
								adminButton.setVisible(false);
							} else {
								adminButton.setVisible(true);
							}
						}
					};

					okButton.addActionListener(okButtonListener);
					adminButton.addActionListener(adminButtonListener);
					moreButton.addActionListener(moreButtonListener);
				}

			}
		}
	}
}
