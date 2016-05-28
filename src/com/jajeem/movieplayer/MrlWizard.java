package com.jajeem.movieplayer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.jajeem.command.model.StartMoviePlayerCommand;
import com.jajeem.command.model.StopMoviePlayerCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class MrlWizard extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private MrlWizard frame;
	private JCheckBox chckbxNewCheckBox;
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public MrlWizard() {
		frame = this;
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MrlWizard.class.getResource("/icons/noa_en/New/logo.png")));
		setMinimumSize(new Dimension(600, 310));
		setTitle("MRL Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
		);
		
		JLabel lblMrl = new JLabel("Mrl: ");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblType = new JLabel("Type:");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"http", "rtp", "rtsp"}));
		
		JLabel lblPort = new JLabel("Port:");
		
		textField_1 = new JTextField();
		textField_1.setText("8080");
		textField_1.setColumns(10);
		
		JLabel lblPath = new JLabel("Path: ");
		
		textField_2 = new JTextField();
		textField_2.setText("/");
		textField_2.setColumns(10);
		
		final JButton btnStartStreamingFor = new JButton("Start streaming ...");
		btnStartStreamingFor.putClientProperty("Status", "Start");
		btnStartStreamingFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setAlwaysOnTop(false);
				if(btnStartStreamingFor.getClientProperty("Status").equals("Start")){
					int choice = JOptionPane.showConfirmDialog(null, "Are you sure the streaming link is correct?",
			                   "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(choice==JOptionPane.OK_OPTION){
						sendStreamingCommand();
						btnStartStreamingFor.putClientProperty("Status", "Stop");
						btnStartStreamingFor.setText("Stop streaming ...");
					}
					else{
						frame.setAlwaysOnTop(true);
						return;
					}
				}
				else{
					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop?",
			                   "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(choice==JOptionPane.OK_OPTION){
						sendStopStreamingCommand();
						btnStartStreamingFor.putClientProperty("Status", "Start");
						btnStartStreamingFor.setText("Start streaming ...");
					}
					else{
						frame.setAlwaysOnTop(true);
						return;
					}
				}
			}
		});
		
		JLabel lblWarningDontClick = new JLabel("Warning: Dont click \"Start streaming ...\" button unless you have started streaming first.");
		
		chckbxNewCheckBox = new JCheckBox("Manual");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				textField.setEnabled(chckbxNewCheckBox.isSelected());
				textField_1.setEnabled(!chckbxNewCheckBox.isSelected());
				textField_2.setEnabled(!chckbxNewCheckBox.isSelected());
				comboBox.setEnabled(!chckbxNewCheckBox.isSelected());
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblMrl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblType)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
							.addComponent(chckbxNewCheckBox))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblPort)
							.addGap(18)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblPath)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnStartStreamingFor, Alignment.TRAILING)
						.addComponent(lblWarningDontClick))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMrl)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWarningDontClick)
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxNewCheckBox))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPort)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPath)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
					.addComponent(btnStartStreamingFor)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		String localIp;
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			localIp = "127.0.0.1";
		}
		textField.setText("http://"+ localIp+":8080/");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setSelected(false);
		
		pack();
	}
	
	private void sendStreamingCommand() {
		try {
			String mrl = "";
			String localIp;
			try {
				localIp = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				localIp = "127.0.0.1";
			}
			if(chckbxNewCheckBox.isSelected())
				mrl = textField.getText();
			else{
				if(comboBox.getSelectedItem().equals("http")){
					mrl+="http://";
					mrl+=localIp;
					mrl+=":"+textField_1.getText();
					mrl+=textField_2.getText();
				}
				else if(comboBox.getSelectedItem().equals("rtp")){
					mrl+="rtp://"+localIp+":";
					mrl+=textField_1.getText();
					mrl+=textField_2.getText();
				}
				else{
					mrl+="rtsp://:";
					mrl+=textField_1.getText();
					mrl+=textField_2.getText();
				}
			}
			StartMoviePlayerCommand cmd = new StartMoviePlayerCommand(InetAddress
					.getLocalHost().getHostAddress(), Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
			ServerService service = new ServerService();
			cmd.setMrl(mrl);
			service.send(cmd);
		} catch (Exception e) {
		}
	}
	
	private void sendStopStreamingCommand() {
		StopMoviePlayerCommand cmd;
		try {
			cmd = new StopMoviePlayerCommand(InetAddress
					.getLocalHost().getHostAddress(), Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
			ServerService service = new ServerService();
			service.send(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
