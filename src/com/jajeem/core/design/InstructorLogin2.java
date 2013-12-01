package com.jajeem.core.design;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.StartUp;
import com.jajeem.util.i18n;

public class InstructorLogin2 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginRoundedTextBox textField;
	private LoginRoundedPasswordBox passwordField;
	int posX = 0, posY = 0;
	protected JTextComponent usernameTF;
	protected JPasswordField passwordTF;
	protected static Window frame;
	RoomService roomService = new RoomService();
	private JComboBox courseCombo;
	protected ArrayList<Course> courseList;
	private CustomLoginButton webButtonLogin;

	public InstructorLogin2() {
		frame = this;
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		getContentPane().setBackground(new Color(0, 0, 0, 0));
		
		
		new Config();
		new i18n();
		
		LoginRoundedPanel mainPanel = new LoginRoundedPanel();
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		
		JLabel label = new JLabel("Courses : ");
		label.setForeground(new Color(63, 63, 63));
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JComboBox comboBox = new JComboBox();
		courseCombo = comboBox;
		
		CustomLoginButton customLoginButton = new CustomLoginButton();
		customLoginButton.addActionListener(new ActionListener() {
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
		customLoginButton.setUndecorated(true);
		customLoginButton.setText("Select");
		customLoginButton.setIconTextGap(13);
		customLoginButton.setForeground(new Color(63, 63, 63));
		customLoginButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(64)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(165)
							.addComponent(customLoginButton, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(2)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(customLoginButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		panel_3.setLayout(gl_panel_3);
		
		textField = new LoginRoundedTextBox();
		textField.setForeground(new Color(63,63,63,255));
		textField.setFont(new Font("Tahoma", Font.BOLD, 15));
		textField.setWaterMarkText("Username");
		textField.setColumns(10);
		usernameTF = textField;
		textField.addKeyListener(new KeyAdapter() {
        	@SuppressWarnings("static-access")
			@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER)
        			PerformLogin();
        		super.keyPressed(e);
        	}
		});
		
		passwordField = new LoginRoundedPasswordBox();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 15));
		passwordField.setWaterMarkText("Password");
		passwordField.setForeground(new Color(63,63,63,255));
		passwordTF = passwordField;
		passwordTF.addKeyListener(new KeyAdapter() {
        	@SuppressWarnings("static-access")
			@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER)
        			PerformLogin();
        		super.keyPressed(e);
        	}
		});
		
		
		webButtonLogin = new CustomLoginButton();
		webButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PerformLogin();
			}
		});
		webButtonLogin.setMargin(new Insets(0, 0, 0, 15));
		webButtonLogin.setIconTextGap(13);
		webButtonLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		webButtonLogin.setForeground(new Color(63,63,63,255));
		ImageIcon lockIcon = new ImageIcon(InstructorLogin2.class.getResource("/icons/noa_en/teacherloginbuttonicon.png"));
		ImageIcon lockIconScaled = new ImageIcon(lockIcon.getImage()
				.getScaledInstance(14,
						18, Image.SCALE_SMOOTH));
		webButtonLogin.setIcon(lockIconScaled);
		webButtonLogin.setText("Log In");
		webButtonLogin.setUndecorated(true);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButtonLogin, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(webButtonLogin, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(159)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(185, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		ImageIcon logo = new ImageIcon(InstructorLogin2.class.getResource("/icons/noa_en/New/logo.png"));
		ImageIcon logoScaled = new ImageIcon(logo.getImage()
				.getScaledInstance(120,
						91, Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(logoScaled);
		panel_1.setLayout(gl_panel_1);
		getContentPane().add(mainPanel);
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		mainPanel.setLayout(gl_mainPanel);
//		setLocationRelativeTo(this);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen()
						- posY);

			}
		});
		
		setSize(495, 174);
//		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
//		pack();
//		panel.add(new RoundedPanel());
	}

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

				frame.setSize(505, 273);

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
					frame = new InstructorLogin2();
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

class LoginRoundedPanel extends JPanel {
	public LoginRoundedPanel() {
		setOpaque(false);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Stroke size. it is recommended to set it to 1 for better view */
	protected int strokeSize = 6;
	/** Color of shadow */
	protected Color shadowColor = Color.black;
	/** Sets if it drops shadow */
	protected boolean shady = false;
	/** Sets if it has an High Quality view */
	protected boolean highQuality = true;
	/** Double values for Horizontal and Vertical radius of corner arcs */
	protected Dimension arcs = new Dimension(30, 30);
	/** Distance between shadow border and opaque panel border */
	protected int shadowGap = 5;
	/** The offset of shadow. */
	protected int shadowOffset = 4;
	/** The transparency value of shadow. ( 0 - 255) */
	protected int shadowAlpha = 90;

	// FOLLOWING CODES GOES HERE

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		int shadowGap = this.shadowGap;
		Color shadowColorA = new Color(shadowColor.getRed(),
				shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
		Graphics2D graphics = (Graphics2D) g;

		// Sets antialiasing if HQ.
		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Draws the rounded opaque panel with borders.
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.setStroke(new BasicStroke(strokeSize));
		graphics.drawRoundRect(2, 2, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);

		// Sets strokes to default, is better.
		graphics.setStroke(new BasicStroke());
	}
}

class LoginRoundedTextBox extends JTextField{
	
	private Shape shape;
	private String watermarkText;
    public LoginRoundedTextBox() {
        super();
        setOpaque(false); // As suggested by @AVD in comment.
        setMargin(new Insets(0, 10, 0, 0));
        addFocusListener(new FocusAdapter(){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if(getText().equals(watermarkText))
        			setText("");
        		super.focusGained(e);
        	}
        	
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		if(getText().equals(""))
        			setText(watermarkText);
        		super.focusLost(arg0);
        	}
        });
    }
    public void setWaterMarkText(String string) {
    	watermarkText = string;
		setText(string);
	}
    
    protected void paintComponent(Graphics g) {
//    	Graphics2D g2 = (Graphics2D)g;
//    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//				RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(getBackground());
//        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}

class LoginRoundedPasswordBox extends JPasswordField{
	
	private Shape shape;
	private String watermarkText;
	private String realText;
    public LoginRoundedPasswordBox() {
        super();
        setOpaque(false); // As suggested by @AVD in comment.
        setMargin(new Insets(0, 10, 0, 0));
        setEchoChar((char) 0);
        addFocusListener(new FocusAdapter(){
        	@Override
        	public void focusGained(FocusEvent e) {
        		setEchoChar('●');
        		if(getText().equals(watermarkText))
        			setText("");
        		super.focusGained(e);
        	}
        	
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		if(getText().equals("")){
        			setText(watermarkText);
        			setEchoChar((char) 0);
        		}
        		super.focusLost(arg0); 
        	}
        });       
    }
    public void setWaterMarkText(String string) {
    	watermarkText = string;
		setText(string);
	}
    
    protected void paintComponent(Graphics g) {
//    	Graphics2D g2 = (Graphics2D)g;
//    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//				RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(getBackground());
//        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
	public String getRealText() {
		return realText;
	}
	public void setRealText(String realText) {
		this.realText = realText;
	}
}

class CustomLoginButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	protected boolean isRollOver;
	protected boolean isPressed;

	public CustomLoginButton() {
		init();
	}

	public CustomLoginButton(ImageIcon img) {
		super(img);
		init();
	}

	public CustomLoginButton(String label, ImageIcon img) {
		super(label, img);
		init();
	}

	public CustomLoginButton(String label) {
		super(label);
		init();
	}

	public void init() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
			rollover = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
			selected = ImageIO.read(inp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getModel().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if (model.isRollover()) {
					isRollOver = true;
				} else {
					isRollOver = false;
				}
				if (model.isPressed()) {
					isPressed = true;
				} else {
					isPressed = false;
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			if (isPressed) {
				g2.drawImage(selected, 0, 0, getWidth(), getHeight(), this);
			} else {
				if (isRollOver) {
					g2.drawImage(rollover, 0, 0, getWidth(), getHeight(), this);
				} else {
					g2.drawImage(background, 0, 0, getWidth(), getHeight(),
							this);
				}
			}
		}
		g2.dispose();
		super.paintComponent(g);
	}
}

