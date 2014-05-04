package com.jajeem.licensing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JTextField textField_Name;
	private JTextField textField_Company;
	private JTextField textField_Phone;
	private JTextField textField_Serial;

	private JTextField textField_Hardware;

	/**
	 * Create the frame.
	 */
	public LicenseFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				LicenseFrame.class.getResource("/icons/noa_en/key.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();

		JPanel panel_2 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_2,
												GroupLayout.DEFAULT_SIZE, 596,
												Short.MAX_VALUE)
										.addComponent(panel_1,
												GroupLayout.DEFAULT_SIZE, 590,
												Short.MAX_VALUE))
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 116,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 201,
								Short.MAX_VALUE).addContainerGap()));

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(LicenseFrame.class
				.getResource("/icons/noa_en/logoteacher3.png")).getImage()
				.getScaledInstance(105, 105, 0)));
		
				JLabel lblName = new JLabel("Name : ");
		
				JLabel lblCompany = new JLabel("Company : ");
		
				JLabel lblPhone = new JLabel("Phone : ");
		
				textField_Phone = new JTextField();
				textField_Phone.setColumns(10);
		
				textField_Company = new JTextField();
				textField_Company.setColumns(10);
		
				textField_Name = new JTextField();
				textField_Name.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblName)
							.addGap(28)
							.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblCompany)
							.addGap(10)
							.addComponent(textField_Company, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblPhone)
							.addGap(25)
							.addComponent(textField_Phone, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(135, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblName))
								.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblCompany))
								.addComponent(textField_Company, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblPhone))
								.addComponent(textField_Phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
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
		
		JButton btnActivateOnline = new JButton("Activate Online");
		btnActivateOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicenseManager.getInstance().ActivateOnline(textField_Name,textField_Company,textField_Phone);
				JOptionPane.showMessageDialog(null, "Request for online activation sent!\nThis might take a while to complete,If you need any questions please contact support!");
			}
		});
		
		JButton btnActivateOffline = new JButton("Activate Offline");
		btnActivateOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicenseManager.getInstance().ActivateOffline(textField_Name,textField_Company,textField_Phone);
				JOptionPane.showMessageDialog(null, "Product Activated!\nEnjoy the program!");
			}
		});
		
		JButton btnContinueTrial = new JButton("Continue Trial");
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
								.addComponent(textField_Serial, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addComponent(btnContinueTrial, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
							.addComponent(btnActivateOffline)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnActivateOnline)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
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
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
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
		textField_Company.setText(string);
	}

	public void setPhone(String string) {
		textField_Phone.setText(string);
	}

	public void setLicensorName(String string) {
		textField_Name.setText(string);
	}
}
