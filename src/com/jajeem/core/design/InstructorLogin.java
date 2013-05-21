package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.PropertyConfigurator;

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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

public class InstructorLogin extends WebDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5121321476236877112L;
	private final JPanel contentPanel = new JPanel();
	private final WebPasswordField password = new WebPasswordField(15);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InstructorLogin dialog = new InstructorLogin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
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

		setResizable(false);
		setModal(true);
		
		setTitle("Login to iCalabo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Instructor.class.getResource("/menubar/jajeem.jpg")));
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		try {
			// Setting up WebLookAndFeel style
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();
			PropertyConfigurator.configure("conf/log4j.conf");

		} catch (Throwable e) {
		}

		// get list of instructors
		InstructorService instructorService = new InstructorService();
		ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
				.list();

		DefaultListModel listModel1 = new DefaultListModel();

		for (com.jajeem.core.model.Instructor instructorItem : instructorList) {
			listModel1.addElement(instructorItem.getUsername());
		}

		final WebList list1 = new WebList(listModel1);
		WebScrollPane sPanel1 = new WebScrollPane(list1);
		sPanel1.setDrawBackground(true);
		sPanel1.setShadeWidth(0);
		sPanel1.setRound(0);
		sPanel1.setViewportBorder(new TitledBorder(null, "Instructors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		sPanel2.setViewportBorder(new TitledBorder(null, "Courses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//

		setBounds(100, 100, 450, 300);
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
				gl_buttonPane.setHorizontalGroup(
					gl_buttonPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_buttonPane.createSequentialGroup()
							.addContainerGap(197, Short.MAX_VALUE)
							.addComponent(okButton)
							.addGap(190))
				);
				gl_buttonPane.setVerticalGroup(
					gl_buttonPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPane.createSequentialGroup()
							.addComponent(okButton)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				buttonPane.setLayout(gl_buttonPane);

				ActionListener listener = new ActionListener() {
					@SuppressWarnings("static-access")
					public void actionPerformed(ActionEvent e) {
						try {
							boolean grant = false;
							InstructorService instructorService = new InstructorService();
							grant = instructorService.authenticate(
									(String) list1.getSelectedValue(),
									password.getPassword());
							if (grant) {
								setVisible(false);
								Instructor instructor = new Instructor();
								instructor.main(new String[] { (String) list1.getSelectedValue(),
										(String) list2.getSelectedValue() });
								dispose();
							} else {
								password.setBackground(Color.decode("#FAD9D9"));
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				};
				okButton.addActionListener(listener);
			}

		}
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

}
