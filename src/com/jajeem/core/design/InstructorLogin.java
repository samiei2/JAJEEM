package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.licensing.LicenseValidator;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.StartUp;
import com.jajeem.util.i18n;

public class InstructorLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static WebFrame progressBarFrame;
	static ArrayList<Course> courseList;
	private WebTextField usernameTF;
	private WebPasswordField passwordTF;
	private JPanel bottomPanel;
	private WebComboBox courseCombo;
	private static InstructorLogin frame;
	RoomService roomService = new RoomService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(WebLookAndFeel.class
							.getCanonicalName());

					Thread loading = new Thread(new Runnable() {

						@Override
						public void run() {
							final LoadingDialog load = new LoadingDialog();
							load.setVisible(true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {

								@Override
								public void run() {
									load.setVisible(false);
								}
							}, 5000);
						}
					});
					loading.start();
					new StartUp();
					frame = new InstructorLogin();
					frame.pack();

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							frame.setVisible(true);
						}
					}, 5000);

				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public InstructorLogin() throws Exception {
		setResizable(false);
		setTitle("Welcome to Classmate");

		new Config();
		new i18n();

		WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString(i18n
				.getParam("Classmate is loading, Please wait..."));
		progressBar.setOpaque(false);

		progressBarFrame = new WebFrame();
		progressBarFrame.add(progressBar);
		progressBarFrame.setSize(500, 35);
		progressBarFrame.setLocationRelativeTo(null);
		progressBarFrame.setUndecorated(true);
		progressBarFrame.setAlwaysOnTop(true);
		progressBarFrame.setVisible(true);

		// LicenseValidator.ActiveValidateLicense();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(2, 2, 0, 0));

		JPanel subBottomPanel1 = new JPanel();
		bottomPanel.add(subBottomPanel1);

		courseCombo = new WebComboBox();
		courseCombo.setToolTipText("Select a course to login");
		subBottomPanel1.add(courseCombo);

		JPanel subBottomPanel2 = new JPanel();
		bottomPanel.add(subBottomPanel2);

		WebButton loginButton = new WebButton("Login");
		subBottomPanel2.add(loginButton);

		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel topPanel = new JPanel();
		centerPanel.add(topPanel);
		topPanel.setLayout(new GridLayout(2, 4, 0, 0));

		WebLabel usernameLabel = new WebLabel("Username");
		topPanel.add(usernameLabel);

		usernameTF = new WebTextField();
		topPanel.add(usernameTF);
		usernameTF.setColumns(10);

		WebLabel passwordLabel = new WebLabel("Password");
		topPanel.add(passwordLabel);

		passwordTF = new WebPasswordField();
		passwordTF.setColumns(10);
		topPanel.add(passwordTF);

		JPanel middlePanel = new JPanel();
		centerPanel.add(middlePanel);

		WebButton getCourseButton = new WebButton("Get courses");
		middlePanel.add(getCourseButton);
		setLocationRelativeTo(null);

		bottomPanel.setVisible(false);
		HotkeyManager.registerHotkey(this, getCourseButton, Hotkey.ENTER);

		getCourseButton.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (usernameTF.getText() != null
						&& !usernameTF.getText().equals("")
						&& passwordTF.getPassword() != null
						&& !passwordTF.getPassword().toString().equals("")) {
					InstructorService instructorService = new InstructorService();
					boolean grant = false;
					try {
						grant = instructorService.authenticate(
								usernameTF.getText(), passwordTF.getPassword());
					} catch (SQLException e) {
						JajeemExcetionHandler.logError(e);
						e.printStackTrace();
					}

					if (grant) {
						Instructor instructor = instructorService
								.get(usernameTF.getText());
						com.jajeem.util.Session.setInstructor(instructor);
						InstructorNoa.setInstructorModel(instructor);

						bottomPanel.setVisible(true);

						try {
							courseList = roomService
									.getCoursesByInstructorId(instructor
											.getId());

							courseCombo.removeAllItems();

							for (Course courseItem : courseList) {
								Date startDate = new Date(courseItem
										.getStartDate());
								SimpleDateFormat dt = new SimpleDateFormat(
										"yyyy-MM-dd");
								courseCombo.addItem(courseItem.getName() + "-"
										+ courseItem.getLevel() + " ("
										+ courseItem.getClassType() + ", "
										+ dt.format(startDate) + ")");
							}

							if (courseList.size() == 0) {
								courseCombo.addItem(i18n
										.getParam("No courses available"));
							}

						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}

						frame.pack();

					} else {
						usernameTF.setBackground(Color.decode("#FAD9D9"));
						passwordTF.setBackground(Color.decode("#FAD9D9"));
					}
				}
			}
		});

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (usernameTF.getText() != null
						&& !usernameTF.getText().equals("")
						&& passwordTF.getPassword() != null
						&& !passwordTF.getPassword().toString().equals("")) {
					InstructorService instructorService = new InstructorService();
					boolean grant = false;
					try {
						grant = instructorService.authenticate(
								usernameTF.getText(), passwordTF.getPassword());
					} catch (SQLException e1) {
						JajeemExcetionHandler.logError(e1);
						e1.printStackTrace();
					}

					if (grant) {
						Instructor instructor = instructorService
								.get(usernameTF.getText());
						com.jajeem.util.Session.setInstructor(instructor);
						InstructorNoa.setInstructorModel(instructor);

						if (courseCombo.getItemCount() != 0) {
							progressBarFrame.setVisible(true);
							Course selectedCourse = courseList.get(courseCombo
									.getSelectedIndex());

							InstructorNoa.setCourseModel(selectedCourse);

							com.jajeem.util.Session.setCourse(selectedCourse);
							InstructorNoa.main(null);
							frame.dispose();
							progressBarFrame.setVisible(false);
						} else if (usernameTF.getText().equals("admin")) {
							int resp = WebOptionPane
									.showConfirmDialog(
											null,
											"You are about to login as adminstrator without a course, are you sure?",
											"Confirm",
											WebOptionPane.YES_NO_OPTION,
											WebOptionPane.QUESTION_MESSAGE);
							if (resp == 0) {
								InstructorNoa.main(null);
							} else {
								return;
							}

						}
					}
				}
			}
		});

		setResizable(false);
		setLocationRelativeTo(null);
		progressBarFrame.setVisible(false);
	}
}
