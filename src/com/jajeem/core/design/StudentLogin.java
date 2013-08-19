package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jitsi.service.libjitsi.LibJitsi;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
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
import com.jajeem.filemanager.client.ClientFileServer;
import com.jajeem.util.Config;
import com.jajeem.util.KeyHook;
import com.jajeem.util.MouseHook;
import com.jajeem.util.i18n;

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

		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		// Opening dialog
		setLoginDialog(new LoginDialog(this));
		getLoginDialog().pack();
		getLoginDialog().setLocationRelativeTo(this);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	public static class LoginDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4106820035425545163L;
		public static String name = "";

		public LoginDialog(Window owner) throws Exception {
			super(owner, i18n.getParam("Login to iCalabo"));
			// setIconImage(Toolkit.getDefaultToolkit().getImage(
			// Student.class.getResource("/icons/menubar/jajeem.jpg")));
			setDefaultCloseOperation(WebDialog.DO_NOTHING_ON_CLOSE);
			setResizable(false);
			setAlwaysOnTop(true);
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

			content.add(new WebLabel(i18n.getParam("Username"),
					WebLabel.TRAILING), "0,0");
			content.add(username, "1,0");

			content.add(new WebLabel(i18n.getParam("Password"),
					WebLabel.TRAILING), "0,1");
			content.add(password, "1,1");

			WebButton login = new WebButton(i18n.getParam("Login"));
			login.setRound(0);
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
			};
			login.addActionListener(listener);

			WebButton cancel = new WebButton(i18n.getParam("Cancel"));
			cancel.setRound(0);
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			content.add(new CenterPanel(new GroupPanel(5, login)), "0,2,1,2");
			SwingUtils.equalizeComponentsWidths(login, cancel);

			add(content);

			// HotkeyManager.registerHotkey(this, cancel, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, login, Hotkey.ENTER);
		}
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

	public static LoginDialog getLoginDialog() {
		return loginDialog;
	}

	public static void setLoginDialog(LoginDialog loginDialog) {
		StudentLogin.loginDialog = loginDialog;
	}
}
