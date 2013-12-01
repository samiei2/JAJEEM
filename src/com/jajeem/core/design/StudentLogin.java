package com.jajeem.core.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;

import org.jitsi.service.libjitsi.LibJitsi;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileServer;
import com.jajeem.util.Config;
import com.jajeem.util.CustomLoginFrame;
import com.jajeem.util.CustomPasswordField;
import com.jajeem.util.CustomTextField;
import com.jajeem.util.KeyHook;
import com.jajeem.util.MouseHook;
import com.jajeem.util.i18n;

public class StudentLogin extends CustomLoginFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5121321476236877112L;

	private static String serverIp;
	private static CustomLoginFrame loginDialog;
	private static KeyHook keyHook;
	private static MouseHook mouseHook;
	public static String name = "";

	private static Student student;

	private static ServerService serverService;

	static JTextField username = new WebTextField(15);
	static JPasswordField password = new WebPasswordField(15);
	private JTextField textField;
	private JPasswordField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();
			new i18n();

			new Student();
			new StudentLogin();

		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public StudentLogin() throws Exception {

		initilization();

		WebButton wbtnLogin = new WebButton();
		wbtnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					if (username.getText().equals("")
							|| password.getPassword().equals("")) {
						WebOptionPane.showMessageDialog(getRootPane(),
								i18n.getParam("Please fill in all fields."), i18n.getParam("Information"),
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					if (serverIp == null) {
						WebOptionPane
								.showMessageDialog(
										getRootPane(),
										i18n.getParam("No instructor found, wait for your instructor."),
										i18n.getParam("Information"),
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					AuthenticateCommand authenticateCommand = new AuthenticateCommand(
							InetAddress.getLocalHost().getHostAddress(),
							serverIp, Integer.parseInt(Config
									.getParam("serverPort")), username
									.getText(), password.getPassword());
					name = username.getText();
					getServerService().send(authenticateCommand);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		wbtnLogin.setBorder(new MatteBorder(5, 3, 5, 3, Color.GRAY));
		wbtnLogin.setBackground(Color.GRAY);
		wbtnLogin.setRound(10);
		wbtnLogin.setText(i18n.getParam("Login"));

		JLabel lblUsername = new JLabel(i18n.getParam("Username"));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsername.setForeground(SystemColor.controlDkShadow);

		JLabel lblPassword = new JLabel(i18n.getParam("Password"));
		lblPassword.setForeground(SystemColor.controlDkShadow);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));

		textField = new CustomTextField("/icons/noa_en/new/logintextbox.png");
		textField.setMargin(new Insets(0, 10, 0, 0));
		username = textField;
		textField.setColumns(10);

		textField_1 = new CustomPasswordField(
				"/icons/noa_en/new/logintextbox.png");
		textField_1.setMargin(new Insets(0, 10, 0, 0));
		password = textField_1;
		textField_1.setColumns(10);

		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				lblUsername,
																				GroupLayout.PREFERRED_SIZE,
																				107,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				textField,
																				GroupLayout.PREFERRED_SIZE,
																				264,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				lblPassword,
																				GroupLayout.PREFERRED_SIZE,
																				107,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(10)
																										.addComponent(
																												wbtnLogin,
																												100,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								textField_1,
																								GroupLayout.PREFERRED_SIZE,
																								264,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(34, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING,
												false)
										.addComponent(textField)
										.addComponent(lblUsername,
												GroupLayout.DEFAULT_SIZE, 35,
												Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPassword,
												GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_1,
												GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(wbtnLogin, 28,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(46, Short.MAX_VALUE)));
		getMainContentPane().setLayout(groupLayout);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);

		HotkeyManager.registerHotkey(this, wbtnLogin, Hotkey.ENTER);

		setLoginDialog(this);
		setSize(450, 275);
		// super.pack();
		getLoginDialog().setLocationRelativeTo(this);

	}

	private void initilization() throws Exception {
		LibJitsi.start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				new ClientFileServer().Startup();
			}
		}).start();

		if (!new File("util").exists()) {
			JOptionPane
					.showMessageDialog(
							null,
							i18n.getParam("util folder does not exist.Please call your administrator!\nShutting Down!"));
			System.exit(1);
		}

		LibJitsi.start();

		setServerService(new ServerService());

		ClientService clientServiceTimer = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("startUpPort")));
		clientServiceTimer.start();

		ClientService clientService = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("port")));
		clientService.start();
	}

	public static KeyHook getKeyHook() {
		return keyHook;
	}

	public static void setKeyHook(KeyHook keyHook) {
		StudentLogin.keyHook = keyHook;
	}

	public static ServerService getServerService() {
		return serverService;
	}

	public static void setServerService(ServerService serverService) {
		StudentLogin.serverService = serverService;
	}

	public static MouseHook getMouseHook() {
		return mouseHook;
	}

	public static void setMouseHook(MouseHook mouseHook) {
		StudentLogin.mouseHook = mouseHook;
	}

	public static Student getStudent() {
		return student;
	}

	public static void setStudent(Student student) {
		StudentLogin.student = student;
	}

	public static void setLoginDialogVisible(boolean flag) {
		getLoginDialog().setVisible(flag);
	}

	public static void setFieldsColor(Color color) {
		username.setBackground(color);
		password.setBackground(color);
	}

	public static String getUsername() {
		return username.getText();
	}

	public static String getServerIp() {
		return serverIp;
	}

	public static void setServerIp(String serverIp) {
		StudentLogin.serverIp = serverIp;
	}

	public static CustomLoginFrame getLoginDialog() {
		return loginDialog;
	}

	public static void setLoginDialog(CustomLoginFrame loginDialog) {
		StudentLogin.loginDialog = loginDialog;
	}
}