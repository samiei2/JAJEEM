package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.jajeem.util.FileUtil;

public class DatabaseManager extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private CustomAccountButton okButton;

	/**
	 * @wbp.nonvisual location=89,299
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DatabaseManager dialog = new DatabaseManager(null);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param mainFrame
	 */
	public DatabaseManager(final WebFrame mainFrame) {
		WebLookAndFeel.install();
		setTitle("Database Backup/Restore");
		setBounds(100, 100, 548, 263);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.CENTER);

		WebLabel wblblBackupDatabase = new WebLabel();
		wblblBackupDatabase.setText("Backup Database : ");

		WebButton wbtnBackup = new WebButton();
		wbtnBackup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WebDirectoryChooser directoryChooser = new WebDirectoryChooser(
						null, "Choose directory to save");

				directoryChooser.setVisible(true);

				if (directoryChooser.getResult() == StyleConstants.OK_OPTION) {
					File filetmp = directoryChooser.getSelectedFolder();
					try {
						FileUtil.copyFileUsingStream(new File(
								"db/jajeem.db.h2.db"), new File(filetmp,
								"jajeem.db.h2.db.backup"));
						FileUtil.copyFileUsingStream(new File(
								"db/jajeem.db.trace.db"), new File(filetmp,
								"jajeem.db.trace.db.backup"));
						JOptionPane.showMessageDialog(null,
								"Backup Successful!");
					} catch (IOException e1) {
						JOptionPane.showConfirmDialog(null, e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		wbtnBackup.setText("Backup");

		WebButton wbtnRestore = new WebButton();
		wbtnRestore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WebDirectoryChooser directoryChooser = new WebDirectoryChooser(
						null, "Select database backup folder...");

				directoryChooser.setVisible(true);

				if (directoryChooser.getResult() == StyleConstants.OK_OPTION) {
					File filetmp = directoryChooser.getSelectedFolder();
					try {
						FileUtil.copyFileUsingStream(new File(filetmp,
								"jajeem.db.h2.db.backup"), new File(
								"db/jajeem.db.h2.db"));
						FileUtil.copyFileUsingStream(new File(filetmp,
								"jajeem.db.trace.db.backup"), new File(
								"db/jajeem.db.trace.db"));
						JOptionPane.showMessageDialog(null,
								"Restore Successful!\nPlease restart application for the changes to take effect.");
					} catch (IOException e1) {
						JOptionPane.showConfirmDialog(null, e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		wbtnRestore.setText("Restore");

		WebLabel wblblRestoreDatabase = new WebLabel();
		wblblRestoreDatabase.setText("Restore Database : ");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(wblblBackupDatabase,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wblblRestoreDatabase,
												GroupLayout.PREFERRED_SIZE, 99,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_contentPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(wbtnBackup,
												GroupLayout.PREFERRED_SIZE, 72,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnRestore,
												GroupLayout.PREFERRED_SIZE, 72,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(239, Short.MAX_VALUE)));
		gl_contentPanel
				.setVerticalGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblBackupDatabase,
																GroupLayout.PREFERRED_SIZE,
																19,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wbtnBackup,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblRestoreDatabase,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wbtnRestore,
																GroupLayout.PREFERRED_SIZE,
																24,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(45, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new CustomAccountButton("/icons/noa_en/accountokbutton.png");
				okButton.setUndecorated(true);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(439)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}
