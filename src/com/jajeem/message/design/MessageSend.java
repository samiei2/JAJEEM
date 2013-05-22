package com.jajeem.message.design;

import info.clearthought.layout.TableLayout;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextArea;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.MessageCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class MessageSend extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562220877713666056L;

	final public WebTextArea messageField = new WebTextArea(3, 20);
	private ExampleDialog exampleDialog = new ExampleDialog(this);
	private static String to = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		setTo(args[0]);
		
		@SuppressWarnings("unused")
		MessageSend dialog = new MessageSend();
	}

	/**
	 * Create the dialog.
	 */
	public MessageSend() {

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
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

	private class ExampleDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5926336525198856722L;

		public ExampleDialog(Window owner) {
			super(owner, "Message");
			setIconImages(WebLookAndFeel.getImages());
			setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
			setModal(true);

			TableLayout layout = new TableLayout(new double[][] {
					{ TableLayout.PREFERRED, TableLayout.FILL },
					{ TableLayout.PREFERRED, TableLayout.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(messageField, "0,0");

			WebButton cancel = new WebButton("Send");
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						MessageCommand messageCommand = new MessageCommand(
								InetAddress.getLocalHost().getHostAddress(),
								getTo(), Integer.parseInt(Config.getParam("port")),
								"");

						ServerService serverService = new ServerService();
						serverService.send(messageCommand);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
				}
			};
			cancel.addActionListener(listener);
			content.add(new CenterPanel(new GroupPanel(5, cancel)), "0,1,1,1");
			SwingUtils.equalizeComponentsWidths(cancel);

			add(content);

			HotkeyManager.registerHotkey(this, cancel, Hotkey.ESCAPE);
		}
	}
}
