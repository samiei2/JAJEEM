package com.jajeem.licensing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.jajeem.licensing.exception.LicenseServerErrorException;

public class LicenseFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LicenseFrame frame = new LicenseFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JPanel contentPane;
	private JTextField textField_Activation;
	private JTextField textField_Serial;

	private JTextField textField_Hardware;
	private JTextField textField_name;
	private JTextField textField_company;
	private JTextField textField_phone;
	private JLabel lblTimeleft;
	private JLabel lblVersion;
	private JButton btnContinueTrial;

	public LicenseFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				LicenseFrame.class.getResource("/icons/noa_en/key.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();

		JPanel panel_2 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(LicenseFrame.class
				.getResource("/icons/noa_en/logoteacher3.png")).getImage()
				.getScaledInstance(105, 105, 0)));
		
		JLabel label_1 = new JLabel("");
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(116)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
						.addComponent(lblNewLabel))
					.addContainerGap())
		);
		
		JLabel lblTimeLeft = new JLabel("Time Left : ");
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		
		JLabel label = new JLabel("Name : ");
		
		JLabel label_2 = new JLabel("Company : ");
		
		JLabel label_3 = new JLabel("Phone : ");
		
		textField_company = new JTextField();
		textField_company.setColumns(10);
		
		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		
		lblVersion = new JLabel(" ");
		
		JLabel lblVersion_1 = new JLabel("Version : ");
		
		lblTimeleft = new JLabel(" ");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblTimeLeft)
							.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
							.addComponent(lblTimeleft)
							.addPreferredGap(ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
							.addComponent(lblVersion_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVersion))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
								.addComponent(label, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
								.addComponent(label_3))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_name, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
								.addComponent(textField_phone, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
								.addComponent(textField_company, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTimeLeft)
						.addComponent(lblVersion)
						.addComponent(lblVersion_1)
						.addComponent(lblTimeleft))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(6)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_company, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addGap(9)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3))
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);

		JLabel lblSerialNumber = new JLabel("Serial Number : ");

		JLabel lblHardwareKey = new JLabel("Hardware Key : ");

		JLabel lblActivationKey = new JLabel("Activation Key : ");

		textField_Activation = new JTextField();
		textField_Activation.setColumns(10);

		textField_Serial = new JTextField();
		textField_Serial.setEditable(false);
		textField_Serial.setColumns(10);

		textField_Hardware = new JTextField();
		textField_Hardware.setEditable(false);
		textField_Hardware.setColumns(10);
		
		JButton btnActivateOnline = new JButton("Order Now");
		btnActivateOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					LicenseManager.getInstance().ActivateOnline(textField_name.getText(),textField_company.getText(),textField_phone.getText());
				} catch (GeneralSecurityException e1) {
					JOptionPane.showMessageDialog(null, "Security exception occured.License was not saved!");
					e1.printStackTrace();
					return;
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Write exception occured.License was not saved!");
					e1.printStackTrace();
					return;
				} catch (LicenseServerErrorException e1) {
					System.out.println("Error code: " + e1.getErrorCode());
					JOptionPane.showMessageDialog(null, "Server was not found!\nThere was a problem with your connection.Try again later!");
					e1.printStackTrace();
					return;
				}
				JOptionPane.showMessageDialog(null, "Request for online activation sent!\nThis might take a while to complete,If you need any questions please contact support!");
			}
		});
		
		JButton btnActivateOffline = new JButton("Activate Offline");
		btnActivateOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					LicenseManager.getInstance().ActivateOffline(textField_name.getText(),textField_company.getText(),textField_phone.getText(),textField_Activation.getText());
					JOptionPane.showMessageDialog(null, "Product Activated!\nEnjoy the program!");
					setVisible(false);
				} catch (InvalidActivationKey | GeneralSecurityException
						| IOException | ParseException e1) {
					JOptionPane.showOptionDialog(null, e1.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
				}
			}
		});
		
		btnContinueTrial = new JButton("Continue Trial");
		btnContinueTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(btnContinueTrial)
							.addPreferredGap(ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
							.addComponent(btnActivateOffline)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnActivateOnline))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSerialNumber)
								.addComponent(lblHardwareKey)
								.addComponent(lblActivationKey))
							.addGap(26)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField_Activation, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
								.addComponent(textField_Hardware, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
								.addComponent(textField_Serial, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerialNumber)
						.addComponent(textField_Serial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHardwareKey)
						.addComponent(textField_Hardware, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActivationKey)
						.addComponent(textField_Activation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnActivateOnline)
						.addComponent(btnActivateOffline)
						.addComponent(btnContinueTrial))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
	}

	public void setSerial(String string) {
		textField_Serial.setText(string);
	}

	public void setHardwareKey(String string) {
		textField_Hardware.setText(string);
	}

	public void setActivationKey(String string) {
		textField_Activation.setText(string);
	}

	public void setCompany(String string) {
		textField_company.setText(string);
	}

	public void setPhone(String string) {
		textField_phone.setText(string);
	}

	public void setLicensorName(String string) {
		textField_name.setText(string);
	}

	
	public void setTimeLeft(String string) {
		lblTimeleft.setText(string);
	}

	public void setVersion(String string) {
		lblVersion.setText(string);
	}

	public void setContinueTrialEnabled(boolean b) {
		btnContinueTrial.setEnabled(b);
	}
}
