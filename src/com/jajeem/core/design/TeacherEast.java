package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class TeacherEast {

	public static WebPanel createPanel(WebPanel panel2) {

		new Config();

		final WebPanel panel = new WebPanel();

		ImageIcon imgToolTip = new ImageIcon("icons/menubar/tooltip.png");
		TooltipManager.setDefaultDelay(1000);

		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);

		ImageIcon imgIntercom = new ImageIcon("icons/applications/intercom_text.png");
		WebButton intercomButton = new WebButton(imgIntercom);
		TooltipManager.setTooltip(intercomButton, imgToolTip, "Start talking to selected student",
				TooltipWay.left);
		intercomButton.setRound(0);
		intercomButton.setUndecorated(true);
		panel.add(intercomButton);

		ImageIcon imgStopInternet = new ImageIcon(
				"icons/applications/stopInternet_text.png");
		WebButton stopInternetButton = new WebButton(imgStopInternet);
		stopInternetButton.setRound(0);
		TooltipManager.setTooltip(stopInternetButton, imgToolTip, "Stop all browsers",
				TooltipWay.left);
		stopInternetButton.setUndecorated(true);
		panel.add(stopInternetButton);

		ImageIcon imgGroupwork = new ImageIcon(
				"icons/applications/groupwork_text.png");
		WebButton groupworkButton = new WebButton(imgGroupwork);
		groupworkButton.setRound(0);
		TooltipManager.setTooltip(groupworkButton, imgToolTip, "Start a group with selected students",
				TooltipWay.left);
		panel.add(groupworkButton);

		ImageIcon imgBlockApplication = new ImageIcon(
				"icons/applications/blockApplication_text.png");
		WebButton blockApplicationButton = new WebButton(imgBlockApplication);
		blockApplicationButton.setRound(0);
		TooltipManager.setTooltip(blockApplicationButton, imgToolTip, "Restricts students access to specific application",
				TooltipWay.left);
		panel.add(blockApplicationButton);

		ImageIcon imgBlackout = new ImageIcon(
				"icons/applications/blackout_text.png");
		WebButton blackoutButton = new WebButton(imgBlackout);
		blackoutButton.setRound(0);
		TooltipManager.setTooltip(blackoutButton, imgToolTip, "Locks student's mouse and keyboard with blacking his screen",
				TooltipWay.left);
		panel.add(blackoutButton);

		ImageIcon imgQuiz = new ImageIcon("icons/applications/quiz_text.png");
		WebButton quizButton = new WebButton(imgQuiz);
		quizButton.setRound(0);
		TooltipManager.setTooltip(quizButton, imgToolTip, "Start quiz for the class",
				TooltipWay.left);
		panel.add(quizButton);
		
		ImageIcon imgSendWebsite = new ImageIcon("icons/applications/sendWebsite_text.png");
		WebButton sendWebsiteButton = new WebButton(imgSendWebsite);
		sendWebsiteButton.setRound(0);
		TooltipManager.setTooltip(sendWebsiteButton, imgToolTip, "Send website",
				TooltipWay.left);
		panel.add(sendWebsiteButton);
		
		ImageIcon imgMessage = new ImageIcon("icons/applications/message_text.png");
		WebButton messageButton = new WebButton(imgMessage);
		messageButton.setRound(0);
		TooltipManager.setTooltip(messageButton, imgToolTip, "Message",
				TooltipWay.left);
		panel.add(messageButton);

		// blacks student's screen
		// TODO should lock student's mouse & keyboard
		blackoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				if (TeacherCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) TeacherCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					try {
						ServerService ss = new ServerService();
						BlackoutCommand bc = new BlackoutCommand(
								selectedStudent, Integer.parseInt(Config
										.getParam("port")));
						ss.send(bc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		// closes all student's browsers
		stopInternetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
					throws NumberFormatException {
				if (TeacherCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) TeacherCenter.desktopPane
							.getSelectedFrame().getClientProperty("ip");
					try {
						ServerService ss = new ServerService();
						InternetCommand ic = new InternetCommand(
								selectedStudent, Integer.parseInt(Config
										.getParam("port")));
						ss.send(ic);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		// block a student's application by adding it to microsoft's registry
		blockApplicationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
					throws NumberFormatException {
				// Enabling dialog decoration
				boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
				WebLookAndFeel.setDecorateDialogs(true);

				// Opening dialog
				ExampleDialog exampleDialog = new ExampleDialog(panel);
				exampleDialog.pack();
				exampleDialog.setLocationRelativeTo(panel);
				exampleDialog.setVisible(true);

				// Restoring frame decoration option
				WebLookAndFeel.setDecorateDialogs(decorateFrames);
			}
		});

		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.NORTH);

		return panel;
	}

	private static class ExampleDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4762888417428594490L;

		public ExampleDialog(WebPanel owner) {
			super(owner);
			setIconImages(WebLookAndFeel.getImages());
			setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setModal(true);

			TableLayout layout = new TableLayout(new double[][] {
					{ TableLayout.PREFERRED, TableLayout.FILL },
					{ TableLayout.PREFERRED, TableLayout.PREFERRED,
							TableLayout.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(new WebLabel("Application name", WebLabel.TRAILING),
					"0,0");
			final WebTextField text = new WebTextField(15);
			content.add(text, "1,0");

			WebButton block = new WebButton("Block");
			WebButton cancel = new WebButton("Cancel");
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					if (TeacherCenter.desktopPane.getSelectedFrame() != null) {
						String selectedStudent = "";
						selectedStudent = (String) TeacherCenter.desktopPane
								.getSelectedFrame().getClientProperty("ip");
						try {
							ServerService ss = new ServerService();
							WhiteBlackAppCommand ic = new WhiteBlackAppCommand(
									selectedStudent, Integer.parseInt(Config
											.getParam("port")), text.getText(),
									true);
							ss.send(ic);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}
			};

			block.addActionListener(listener);
			cancel.addActionListener(listener);
			content.add(new CenterPanel(new GroupPanel(5, block, cancel)),
					"0,2,1,2");
			SwingUtils.equalizeComponentsWidths(block, cancel);

			add(content);

			HotkeyManager.registerHotkey(this, block, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, block, Hotkey.ENTER);
		}
	}

}