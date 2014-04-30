package com.jajeem.licensing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class LicenseFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField txtTesadsa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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

	/**
	 * Create the frame.
	 */
	public LicenseFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LicenseFrame.class.getResource("/icons/noa_en/key.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 393);
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
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(LicenseFrame.class.getResource("/icons/noa_en/logoteacher3.png")).getImage().getScaledInstance(105, 105,0)));
		
		JLabel lblLicenseTo = new JLabel("License To : ");
		
		JLabel lblname = new JLabel("[Name]");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addGap(64)
					.addComponent(lblLicenseTo)
					.addGap(6)
					.addComponent(lblname)
					.addContainerGap(314, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLicenseTo)
								.addComponent(lblname))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblSerialNumber = new JLabel("Serial Number : ");
		
		JLabel lblHardwareKey = new JLabel("Hardware Key : ");
		
		JLabel lblActivationKey = new JLabel("Activation Key : ");
		
		JLabel lblName = new JLabel("Name : ");
		
		JLabel lblCompany = new JLabel("Company : ");
		
		JLabel lblPhone = new JLabel("Phone : ");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		txtTesadsa = new JTextField();
		txtTesadsa.setEditable(false);
		txtTesadsa.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName)
								.addComponent(lblCompany)
								.addComponent(lblPhone))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textField_3, Alignment.LEADING)
								.addComponent(textField_2, Alignment.LEADING)
								.addComponent(textField_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSerialNumber)
								.addComponent(lblHardwareKey)
								.addComponent(lblActivationKey))
							.addGap(26)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
								.addComponent(txtTesadsa, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
								.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap(27, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompany)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPhone)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerialNumber)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHardwareKey)
						.addComponent(txtTesadsa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActivationKey)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
	}
}
