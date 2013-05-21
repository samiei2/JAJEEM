package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.UIManager;

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.core.service.InstructorService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;

public class InstructorLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5121321476236877112L;

	private static LoginDialog loginDialog;

	final static WebTextField username = new WebTextField(15);
	final static WebPasswordField password = new WebPasswordField(15);
	final static WebComboBox courseCombo = new WebComboBox();

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			@SuppressWarnings("unused")
			InstructorLogin dialog = new InstructorLogin();
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
	@SuppressWarnings("unchecked")
	public InstructorLogin() throws NumberFormatException, Exception {

		new Config();
		
		// Enabling dialog decoration
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);
		
		RoomService rs = new RoomService();
		ArrayList<Course> courseList = rs.getCourseDAO().list();
		
		for (Course courseItem : courseList) {
			courseCombo.addItem(courseItem.getName());
		}

		// Opening dialog
		loginDialog = new LoginDialog(this);
		loginDialog.pack();
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);

		// Restoring frame decoration option
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
	}

	public static class LoginDialog extends WebDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4106820035425545163L;
		public static String name = "";

		public LoginDialog(Window owner) throws SQLException {
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
							TableLayout.PREFERRED, TableLayout.PREFERRED } });
			layout.setHGap(5);
			layout.setVGap(5);
			WebPanel content = new WebPanel(layout);
			content.setMargin(15, 30, 15, 30);
			content.setOpaque(false);

			content.add(new WebLabel("Name", WebLabel.TRAILING), "0,0");
			content.add(username, "1,0");

			content.add(new WebLabel("Password", WebLabel.TRAILING), "0,1");
			content.add(password, "1,1");

			
			content.add(courseCombo, "1,2");

			WebButton login = new WebButton("Login");
			login.setRound(0);
			WebButton cancel = new WebButton("Cancel");
			cancel.setRound(0);
			ActionListener listener = new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent e) {
					try {
						boolean grant = false;
						InstructorService instructorService = new InstructorService();
						grant = instructorService.authenticate(username.getText(), password.getPassword());
						if(grant) {
							setVisible(false);
							Instructor instructor = new Instructor();
							instructor.main(new String[] {username.getText(), (String) courseCombo.getSelectedItem()});
							dispose();
						} else {
							username.setBackground(Color.decode("#FAD9D9"));
							password.setBackground(Color.decode("#FAD9D9"));
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			};
			login.addActionListener(listener);
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
			content.add(new CenterPanel(new GroupPanel(5, login, cancel)),
					"0,3,1,3");
			SwingUtils.equalizeComponentsWidths(login, cancel);

			add(content);

			HotkeyManager.registerHotkey(this, login, Hotkey.ESCAPE);
			HotkeyManager.registerHotkey(this, login, Hotkey.ENTER);
		}
	}
}
