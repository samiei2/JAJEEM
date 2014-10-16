package com.jajeem.message.design;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.exception.JajeemExceptionHandler;

public class MessageReceive extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562220877713666056L;

	private WebLabel messageLabel = new WebLabel("", WebLabel.TRAILING);
	private ExampleDialog exampleDialog = new ExampleDialog(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String text = "";
		@SuppressWarnings("unused")
		MessageReceive dialog = new MessageReceive(text, "");
	}

	/**
	 * Create the dialog.
	 */
	public MessageReceive(String text, String name) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MessageReceive.class.getResource("/icons/menubar/message.png")));

		setMessageLabel(text);
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}

		setBounds(100, 100, 450, 300);

		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);

		// Opening dialog

		exampleDialog.setTitle(name);
		exampleDialog.pack();
		exampleDialog.setLocationRelativeTo(this);
		exampleDialog.setVisible(true);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	public void setMessageDialogVisible(boolean vis) {
		exampleDialog.setVisible(vis);
	}

	public WebLabel getMessageLabel() {
		return messageLabel;
	}

	public void setMessageLabel(String messageLabel) {
		this.messageLabel.setText(messageLabel);
	}

	private class ExampleDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5926336525198856722L;

		public ExampleDialog(Window owner) {
			setIconImages(WebLookAndFeel.getImages());
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			// setModal(true);

			TableLayout layout = new TableLayout(
					new double[][] {
							{ TableLayoutConstants.PREFERRED,
									TableLayoutConstants.FILL },
							{ TableLayoutConstants.PREFERRED,
									TableLayoutConstants.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(getMessageLabel(), "0,0");

			WebButton okButton = new WebButton("Ok");
			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			};
			okButton.addActionListener(listener);
			content.add(new CenterPanel(new GroupPanel(5, okButton)), "0,1,1,1");
			SwingUtils.equalizeComponentsWidths(okButton);

			add(content);

			HotkeyManager.registerHotkey(this, okButton, Hotkey.ESCAPE);
		}
	}
}
