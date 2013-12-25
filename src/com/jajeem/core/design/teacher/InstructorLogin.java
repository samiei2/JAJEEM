package com.jajeem.core.design.teacher;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.ui.combobox.JajeemComboBox;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.CustomLoginFrame;
import com.jajeem.util.CustomPasswordField;
import com.jajeem.util.CustomTextField;
import com.jajeem.util.StartUp;
import com.jajeem.util.i18n;
import com.jajeem.ui.textbox.JajeemPasswordTextField;
import com.jajeem.ui.textbox.JajeemTextField;

public class InstructorLogin {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginRoundedTextBox textField;
	private LoginRoundedPasswordBox passwordField;
	private JajeemComboBox comboBox_lang;
	int posX = 0, posY = 0;
	protected JTextComponent usernameTF;
	protected JPasswordField passwordTF;
	protected static Window frame;
	RoomService roomService = new RoomService();
	@SuppressWarnings("rawtypes")
	private JComboBox courseCombo;
	protected ArrayList<Course> courseList;
	private CustomLoginButton webButtonLogin;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InstructorLogin() {
		new Config();
//		new i18n();
		
		final CustomLoginFrame mainPanel = new CustomLoginFrame();
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(mainPanel.getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		JLabel label = new JLabel("Courses : ");
		label.setForeground(new Color(63, 63, 63));
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JajeemComboBox comboBox = new JajeemComboBox();
		courseCombo = comboBox;
		
		CustomLoginButton customLoginButton_1 = new CustomLoginButton();
		customLoginButton_1.setUndecorated(true);
		customLoginButton_1.setText("Select");
		customLoginButton_1.setIconTextGap(13);
		customLoginButton_1.setForeground(new Color(63, 63, 63));
		customLoginButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		customLoginButton_1.addActionListener(new ActionListener() {
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
					} catch (Exception e1) {
						JajeemExcetionHandler.logError(e1);
						e1.printStackTrace();
					}

					if (grant) {
						Instructor instructor = instructorService
								.get(usernameTF.getText());
						com.jajeem.util.Session.setInstructor(instructor);
						InstructorNoa.setInstructorModel(instructor);

						if (courseCombo.getItemCount() != 0) {
							Course selectedCourse = courseList.get(courseCombo
									.getSelectedIndex());

							InstructorNoa.setCourseModel(selectedCourse);

							com.jajeem.util.Session.setCourse(selectedCourse);
							InstructorNoa.main(null);
							frame.dispose();
						} else if (usernameTF.getText().equals("admin")) {
							int resp = -1;
							try {
								resp = JOptionPane
										.showConfirmDialog(
												null,
												i18n.getParam("You are about to login as adminstrator without a course, are you sure?"),
												i18n.getParam("Confirm"),
												JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
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
		
		JajeemComboBox jajeemComboBox = new JajeemComboBox();
		jajeemComboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Farsi"}));
		comboBox_lang = jajeemComboBox;
		jajeemComboBox.setForeground(Color.DARK_GRAY);
		jajeemComboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		jajeemComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBox_lang.getSelectedIndex();
				try {
					if (index == 0) {
						Config.setParam("lang", "en");
					} else if (index == 1) {
						Config.setParam("lang", "fa");
					}
				} catch (Exception ex) {
					JajeemExcetionHandler.logError(ex);
					ex.printStackTrace();
				}
			}
		});

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addGap(156)
							.addComponent(customLoginButton_1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addGap(51)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(jajeemComboBox, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(comboBox, Alignment.TRAILING, 0, 148, Short.MAX_VALUE))))
					.addContainerGap(151, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(2)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jajeemComboBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(customLoginButton_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Username : ");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.DARK_GRAY);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 18));
		lblPassword.setForeground(Color.DARK_GRAY);
		
		CustomTextField jajeemTextField = new CustomTextField("/icons/noa_en/new/logintextbox.png");
		usernameTF = jajeemTextField;
		jajeemTextField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER)
        			PerformLogin();
        		super.keyPressed(e);
        	}
		});
		
		CustomPasswordField jajeemTextField_1 = new CustomPasswordField("/icons/noa_en/new/logintextbox.png");
		passwordTF = jajeemTextField_1;
		jajeemTextField_1.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER)
        			PerformLogin();
        		super.keyPressed(e);
        	}
		});
		
		CustomLoginButton customLoginButton = new CustomLoginButton();
		customLoginButton.setUndecorated(true);
		customLoginButton.setText("Log In");
		customLoginButton.setMargin(new Insets(0, 0, 0, 15));
		customLoginButton.setIconTextGap(13);
		customLoginButton.setForeground(new Color(63, 63, 63));
		customLoginButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		webButtonLogin = customLoginButton;
		ImageIcon lockIcon = new ImageIcon(InstructorLogin.class.getResource("/icons/noa_en/teacherloginbuttonicon.png"));
		ImageIcon lockIconScaled = new ImageIcon(lockIcon.getImage()
				.getScaledInstance(14,
						18, Image.SCALE_SMOOTH));
		customLoginButton.setIcon(lockIconScaled);
		customLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PerformLogin();
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jajeemTextField, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblPassword)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jajeemTextField_1, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)))
					.addGap(9))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(155)
					.addComponent(customLoginButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(168, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblNewLabel)
						.addComponent(jajeemTextField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPassword)
						.addComponent(jajeemTextField_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(customLoginButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		mainPanel.getMainContentPane().setLayout(groupLayout);
		
		mainPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});

		mainPanel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				mainPanel.setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen()
						- posY);

			}
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame = mainPanel;
		mainPanel.setResizable(false);
		mainPanel.setUndecorated(true);
		mainPanel.setBackground(new Color(0, 255, 0, 0));
		mainPanel.getContentPane().setBackground(new Color(0, 0, 0, 0));
		mainPanel.setIconImage(new ImageIcon(InstructorLogin.class.getResource("/icons/noa_en/logo.png")).getImage());
		mainPanel.setSize(500, 277);
		mainPanel.setLocation(dim.width / 2 - mainPanel.getSize().width / 2, dim.height / 2
				- mainPanel.getSize().height / 2);
//		mainPanel.pack();
		
		mainPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				try {
					if (Config.getParam("lang").equals("en")){
						comboBox_lang.setSelectedIndex(0);
					}
					else if (Config.getParam("lang").equals("fa")){
						comboBox_lang.setSelectedIndex(1);
					}
				} catch (Exception e2) {
					JajeemExcetionHandler.logError(e2);
					e2.printStackTrace();
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	protected void PerformLogin() {
		if (usernameTF.getText() != null
				&& !usernameTF.getText().equals("")
				&& passwordTF.getPassword() != null
				&& !passwordTF.getPassword().toString().equals("")) {
			InstructorService instructorService = new InstructorService();
			boolean grant = false;
			try {
				webButtonLogin.setEnabled(false);
				grant = instructorService.authenticate(
						usernameTF.getText(), passwordTF.getPassword());
				webButtonLogin.setEnabled(true);
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
				webButtonLogin.setEnabled(true);
			}

			if (grant) {
				Instructor instructor = instructorService
						.get(usernameTF.getText());
				com.jajeem.util.Session.setInstructor(instructor);
				InstructorNoa.setInstructorModel(instructor);

				frame.setSize(505, 383);

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

//				frame.pack();

			} else {
				usernameTF.setBackground(Color.decode("#FAD9D9"));
				passwordTF.setBackground(Color.decode("#FAD9D9"));
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					UIManager.setLookAndFeel(WebLookAndFeel.class
							.getCanonicalName());

					Thread loading = new Thread(new Runnable() {

						@Override
						public void run() {
							final LoadingDialog2 load = new LoadingDialog2();
							load.setVisible(true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {

								@Override
								public void run() {
									load.setVisible(false);
								}
							}, 6000);
						}
					});
					loading.start();
					new StartUp();
					frame = new InstructorLogin().frame;
//					frame.pack();

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
}