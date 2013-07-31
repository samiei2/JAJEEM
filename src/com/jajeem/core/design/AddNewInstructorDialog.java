package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import ca.odell.glazedlists.EventList;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;

public class AddNewInstructorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private WebTextField lastNameTF;
	private WebTextField firstNameTF;
	private WebTextField passwordTF;
	private WebTextField usernameTF;

	/**
	 * Create the dialog.
	 */
	public AddNewInstructorDialog(final EventList<Instructor> instructorList) {
		setTitle("Add new instructor");
		setVisible(true);
		setBounds(400, 200, 392, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(4, 2, 0, 5));
		{
			WebLabel firstnameLabel = new WebLabel("First name");
			contentPanel.add(firstnameLabel);
		}
		{
			firstNameTF = new WebTextField();
			contentPanel.add(firstNameTF);
			firstNameTF.setColumns(10);
		}
		{
			WebLabel lastNameLabel = new WebLabel("Last name");
			contentPanel.add(lastNameLabel);
		}
		{
			lastNameTF = new WebTextField();
			contentPanel.add(lastNameTF);
			lastNameTF.setColumns(10);
		}
		{
			WebLabel usernameLabel = new WebLabel("Username");
			contentPanel.add(usernameLabel);
		}
		{
			usernameTF = new WebTextField();
			contentPanel.add(usernameTF);
			usernameTF.setColumns(10);
		}
		{
			WebLabel passworkLabel = new WebLabel("Password");
			contentPanel.add(passworkLabel);
		}
		{
			passwordTF = new WebTextField();
			contentPanel.add(passwordTF);
			passwordTF.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				WebButton okButton = new WebButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
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
				WebButton cancelButton = new WebButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
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
