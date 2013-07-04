package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;

import javax.swing.JDialog;
import javax.swing.UIManager;

import org.jitsi.service.libjitsi.LibJitsi;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.filemanager.client.ClientServer;
import com.jajeem.util.Config;
import com.jajeem.util.KeyHook;
import com.jajeem.util.MouseHook;
import com.jajeem.util.Unzipper;

public class StudentLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5121321476236877112L;

	
	private static String serverIp;
	private static LoginDialog loginDialog;
	private static KeyHook keyHook;
	private static MouseHook mouseHook;
	
	private static Student student;

	private static ServerService serverService;

	final static WebTextField username = new WebTextField(15);
	final static WebPasswordField password = new WebPasswordField(15);

	public static void setLoginDialogVisible(boolean flag) {
		loginDialog.setVisible(flag);
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			@SuppressWarnings("unused")
			StudentLogin dialog = new StudentLogin();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public StudentLogin() throws NumberFormatException, Exception {

		new Config();
		LibJitsi.start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ClientServer().Startup();
			}
		}).start();
		
		if(!new File("util").exists())
			Unzipper.unzip("util.zip");
		
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

		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		// Opening dialog
		loginDialog = new LoginDialog(this);
		loginDialog.pack();
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
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


	public static class LoginDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4106820035425545163L;
		public static String name = "";

		public LoginDialog(Window owner) {
			super(owner, "Login to iCalabo");
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					Student.class.getResource("/icons/menubar/jajeem.jpg")));
			setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setModal(true);
			setRound(0);

			TableLayout layout = new TableLayout(new double[][] {
					{ TableLayout.PREFERRED, TableLayout.FILL },
					{ TableLayout.PREFERRED, TableLayout.PREFERRED,
							TableLayout.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(new WebLabel("Name", WebLabel.TRAILING), "0,0");
			content.add(username, "1,0");

			content.add(new WebLabel("Password", WebLabel.TRAILING), "0,1");
			content.add(password, "1,1");

			WebButton login = new WebButton("Login");
			login.setRound(0);
			WebButton cancel = new WebButton("Cancel");
			cancel.setRound(0);
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						com.jajeem.core.model.Student student = new com.jajeem.core.model.Student();
						student.setFullName(username.getText());
						com.jajeem.util.Session.setStudent(student);
						
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
			};
			login.addActionListener(listener);
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//Since the user has not logged in,then the user is anonymous
					com.jajeem.core.model.Student student = new com.jajeem.core.model.Student();
					student.setFullName("Anonymous");
					com.jajeem.util.Session.setStudent(student);
					setVisible(false);
					dispose();
				}
			});
			content.add(new CenterPanel(new GroupPanel(5, login, cancel)),
					"0,2,1,2");
			SwingUtils.equalizeComponentsWidths(login, cancel);

			add(content);

			HotkeyManager.registerHotkey(this, login, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, login, Hotkey.ENTER);
		}
	}
}
