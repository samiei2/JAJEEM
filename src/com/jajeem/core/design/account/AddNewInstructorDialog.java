package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;
import com.jajeem.util.i18n;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class AddNewInstructorDialog extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private WebTextField lastNameTF;
	private WebTextField firstNameTF;
	private WebTextField passwordTF;
	private WebTextField usernameTF;
	private CustomAccountButton okButton;

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public AddNewInstructorDialog(final EventList<Instructor> instructorList)
			throws Exception {
		setTitle(i18n.getParam("Add new instructor"));
		setVisible(true);
		setBounds(400, 200, 457, 260);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(4, 2, 0, 5));
		{
			WebLabel firstnameLabel = new WebLabel(i18n.getParam("First name"));
			contentPanel.add(firstnameLabel);
		}
		{
			firstNameTF = new WebTextField();
			contentPanel.add(firstNameTF);
			firstNameTF.setColumns(10);
		}
		{
			WebLabel lastNameLabel = new WebLabel(i18n.getParam("Last name"));
			contentPanel.add(lastNameLabel);
		}
		{
			lastNameTF = new WebTextField();
			contentPanel.add(lastNameTF);
			lastNameTF.setColumns(10);
		}
		{
			WebLabel usernameLabel = new WebLabel(i18n.getParam("Username"));
			contentPanel.add(usernameLabel);
		}
		{
			usernameTF = new WebTextField();
			contentPanel.add(usernameTF);
			usernameTF.setColumns(10);
		}
		{
			WebLabel passworkLabel = new WebLabel(i18n.getParam("Password"));
			contentPanel.add(passworkLabel);
		}
		{
			passwordTF = new WebTextField();
			contentPanel.add(passwordTF);
			passwordTF.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new CustomAccountButton("/icons/noa_en/accountokbutton.png");
				okButton.setUndecorated(true);
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						Instructor ins = new Instructor();
						ins.setFirstName(firstNameTF.getText());
						ins.setLastName(lastNameTF.getText());
						ins.setUsername(usernameTF.getText());
						ins.setPassword(passwordTF.getText());

						instructorList.add(ins);

						try {
							InstructorService insService = new InstructorService();
							insService.create(ins);
						} catch (SQLException e) {
							e.printStackTrace();
						}

						dispose();
					}
				});

			}
			{
				CustomAccountButton cancelButton = new CustomAccountButton("/icons/noa_en/accountcancelbutton.png");
				cancelButton.setUndecorated(true);
				cancelButton.setActionCommand("Cancel");
				GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
				gl_buttonPane.setHorizontalGroup(
					gl_buttonPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPane.createSequentialGroup()
							.addGap(155)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(151))
				);
				gl_buttonPane.setVerticalGroup(
					gl_buttonPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPane.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
				);
				buttonPane.setLayout(gl_buttonPane);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}

}
