package com.jajeem.message.design;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.Config;

public class MessageSend extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562220877713666056L;

	final public WebTextArea messageField = new WebTextArea(5, 30);
	private ExampleDialog exampleDialog = new ExampleDialog(this);
	private static String to = "";
	private static int port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// setTo(args[0]);
		// setPort(Integer.parseInt(args[1]));
		//
		@SuppressWarnings("unused")
		MessageSend dialog = new MessageSend();
	}

	/**
	 * Create the dialog.
	 */
	public MessageSend() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MessageSend.class.getResource("/icons/menubar/message.png")));

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}

		setBounds(100, 100, 450, 300);

		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		// Opening dialog

		exampleDialog.pack();
		exampleDialog.setLocationRelativeTo(this);
		exampleDialog.setVisible(true);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	public void setMessageDialogVisible(boolean vis) {
		exampleDialog.setVisible(vis);
	}

	public String getTo() {
		return to;
	}

	public static void setTo(String to) {
		MessageSend.to = to;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		MessageSend.port = port;
	}

	private class ExampleDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5926336525198856722L;

		public ExampleDialog(Window owner) {
			super(owner, "Message");
			setResizable(false);
			setIconImages(WebLookAndFeel.getImages());
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			WebPanel content = new WebPanel();
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			WebButton cancel = new WebButton("Send");
			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MessageCommand messageCommand = new MessageCommand(
								InetAddress.getLocalHost().getHostAddress(),
								StudentLogin.getServerIp(),
								Integer.parseInt(Config.getParam("serverPort")),
								messageField.getText());

						if(StudentLogin.getServerService()!=null)
							StudentLogin.getServerService().send(messageCommand);
						else
							WebOptionPane.showMessageDialog(null, "You are not logged in!\nPlease login first!","Error",JOptionPane.ERROR_MESSAGE);
					} catch (Exception e1) {
						JajeemExceptionHandler.logError(e1);
						e1.printStackTrace();
					}
					setVisible(false);
				}
			};
			cancel.addActionListener(listener);
			content.add(new GroupPanel(5, false,
					new WebScrollPane(messageField), cancel));
			SwingUtils.equalizeComponentsWidths(cancel);

			add(content);

			HotkeyManager.registerHotkey(this, cancel, Hotkey.ESCAPE);
		}
	}
}
