package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JDialog;
import javax.swing.UIManager;

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
import com.jajeem.util.Config;

public class StudentLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5121321476236877112L;

	private static String serverIp;
	private static LoginDialog loginDialog;

	public static void setLoginDialogVisible(boolean flag) {
		loginDialog.setVisible(flag);
	}

	public static void setFields() {
		// change fields if they are wrong
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

		ClientService clientServiceTimer = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("startUpPort")));
		clientServiceTimer.start();

		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		// Opening dialog
		LoginDialog loginDialog = new LoginDialog(this);
		loginDialog.pack();
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	private class LoginDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4106820035425545163L;

		public LoginDialog(Window owner) {
			super(owner, "Login to iCalabo");
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					Student.class.getResource("/menubar/jajeem.jpg")));
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
			content.add(new WebTextField(15), "1,0");

			final WebLabel username = new WebLabel("Password",
					WebLabel.TRAILING);
			content.add(username, "0,1");
			final WebPasswordField password = new WebPasswordField(15);
			content.add(password, "1,1");

			WebButton login = new WebButton("Login");
			login.setRound(0);
			WebButton cancel = new WebButton("Cancel");
			cancel.setRound(0);
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						AuthenticateCommand authenticateCommand = new AuthenticateCommand(
								InetAddress.getLocalHost().getHostAddress(),
								serverIp, Integer.parseInt(Config
										.getParam("port")), username.getText(),
								password.getPassword().toString());
						ServerService serverService = new ServerService();
						serverService.send(authenticateCommand);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					// setVisible(false);
				}
			};
			login.addActionListener(listener);
			cancel.addActionListener(listener);
			content.add(new CenterPanel(new GroupPanel(5, login, cancel)),
					"0,2,1,2");
			SwingUtils.equalizeComponentsWidths(login, cancel);

			add(content);

			HotkeyManager.registerHotkey(this, login, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, login, Hotkey.ENTER);
		}
	}
}
