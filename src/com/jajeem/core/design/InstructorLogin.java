package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebPasswordField;
import com.jajeem.core.service.InstructorService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.StartUp;
//import org.apache.log4j.PropertyConfigurator;

public class InstructorLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9010296991450692526L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
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
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

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
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					InstructorLogin.class.getResource("/icons/menubar/jajeem.jpg")));
			try {
				new Config();

			} catch (Throwable e) {
			}

			// get list of instructors
			InstructorService instructorService = new InstructorService();
			ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
					.list();

			DefaultListModel listModel1 = new DefaultListModel();

			for (com.jajeem.core.model.Instructor instructorItem : instructorList) {
				listModel1.addElement(instructorItem.getFirstName() + " "
						+ instructorItem.getLastName() + " ("
						+ instructorItem.getUsername() + ")");
			}

			final WebList list1 = new WebList(listModel1);
			WebScrollPane sPanel1 = new WebScrollPane(list1);
			sPanel1.setDrawBackground(true);
			sPanel1.setShadeWidth(0);
			sPanel1.setRound(0);
			sPanel1.setViewportBorder(new TitledBorder(null, "Instructors",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			//

			// get list of courses
			RoomService rs = new RoomService();
			ArrayList<Course> courseList = rs.getCourseDAO().list();
			DefaultListModel listModel2 = new DefaultListModel();

			for (Course courseItem : courseList) {
				listModel2.addElement(courseItem.getName());
			}

			final WebList list2 = new WebList(listModel2);
			WebScrollPane sPanel2 = new WebScrollPane(list2);
			sPanel2.setDrawBackground(true);
			sPanel2.setShadeWidth(0);
			sPanel2.setRound(0);
			sPanel2.setViewportBorder(new TitledBorder(null, "Courses",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			//

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
					JButton okButton = new JButton("Login");
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
					gl_buttonPane.setVerticalGroup(gl_buttonPane
							.createParallelGroup(Alignment.LEADING).addGroup(
									gl_buttonPane
											.createSequentialGroup()
											.addComponent(okButton)
											.addContainerGap(
													GroupLayout.DEFAULT_SIZE,
													Short.MAX_VALUE)));
					buttonPane.setLayout(gl_buttonPane);

					ActionListener listener = new ActionListener() {
						@SuppressWarnings("static-access")
						public void actionPerformed(ActionEvent e) {
							try {
								boolean grant = false;
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
									setVisible(false);
									InstructorNoa instructorNoa = new InstructorNoa();
									instructorNoa
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
					okButton.addActionListener(listener);
				}

			}
		}
	}
}
