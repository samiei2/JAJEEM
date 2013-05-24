package com.jajeem.message.design;

import info.clearthought.layout.TableLayout;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
import java.awt.Toolkit;

public class MessageReceive extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562220877713666056L;

	private WebLabel messageLabel = new WebLabel("",
			WebLabel.TRAILING);
	private ExampleDialog exampleDialog = new ExampleDialog(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String text = "";
		@SuppressWarnings("unused")
		MessageReceive dialog = new MessageReceive(text);
	}

	/**
	 * Create the dialog.
	 */
	public MessageReceive(String text) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MessageReceive.class.getResource("/icons/menubar/message.png")));
		
		setMessageLabel(text);
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

			content.add(getMessageLabel(), "0,0");

			WebButton cancel = new WebButton("Ok");
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
