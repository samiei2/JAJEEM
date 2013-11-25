package com.jajeem.core.design;

import java.awt.Insets;

import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileServer;
import com.jajeem.util.Config;
import com.jajeem.util.CustomLoginFrame;
import com.jajeem.util.CustomLogoLabel;
import com.jajeem.util.CustomTextField;
import com.jajeem.util.CustomPasswordField;
import com.jajeem.util.KeyHook;
import com.jajeem.util.MouseHook;
import com.jajeem.util.i18n;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;

import java.awt.Color;
import java.io.File;
import java.net.InetAddress;

import javax.swing.border.MatteBorder;

import org.jitsi.service.libjitsi.LibJitsi;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	static WebTextField username = new WebTextField(15);
	static WebPasswordField password = new WebPasswordField(15);
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
	public StudentLogin() throws Exception{
		
		initilization();
		
		CustomLogoLabel lblNewLabel = new CustomLogoLabel("/icons/noa_en/new/username.png");
		
		CustomLogoLabel label = new CustomLogoLabel("/icons/noa_en/new/password.png");
		
		WebButton wbtnLogin = new WebButton();
		wbtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					if (username.getText().equals("")
							|| password.getPassword().equals("")) {
						WebOptionPane.showMessageDialog(getRootPane(),
								"Please fill in all fields.",
								"Information",
								WebOptionPane.INFORMATION_MESSAGE);
						return;
					}

					if (serverIp == null) {
						WebOptionPane
								.showMessageDialog(
										getRootPane(),
										"No instructor found, wait for your instructor.",
										"Information",
										WebOptionPane.INFORMATION_MESSAGE);
						return;
					}

					AuthenticateCommand authenticateCommand = new AuthenticateCommand(
							InetAddress.getLocalHost().getHostAddress(),
							serverIp, Integer.parseInt(Config
									.getParam("serverPort")),
							username.getText(), password.getPassword());
					name = username.getText();
					getServerService().send(authenticateCommand);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		wbtnLogin.setBorder(new MatteBorder(5, 3, 5, 3, (Color) Color.GRAY));
		wbtnLogin.setBackground(Color.GRAY);
		wbtnLogin.setRound(10);
		wbtnLogin.setUndecorated(true);
		wbtnLogin.setText("Login");
		
		CustomTextField textField = new CustomTextField("/icons/noa_en/new/logintextbox.png");
		textField.setColumns(10);
		textField.setMargin(new Insets(0, 10, 0, 0));
		username = textField;
		
		CustomPasswordField textField_1 = new CustomPasswordField("/icons/noa_en/new/logintextbox.png");
		textField_1.setColumns(10);
		textField_1.setMargin(new Insets(0, 10, 0, 0));
		password = textField_1;
		
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(154)
							.addComponent(wbtnLogin, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(57)
					.addComponent(wbtnLogin, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getMainContentPane().setLayout(groupLayout);
		
		setDefaultCloseOperation(WebDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		
		HotkeyManager.registerHotkey(this, wbtnLogin, Hotkey.ENTER);
		
		setLoginDialog(this);
		getLoginDialog().pack();
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