package com.jajeem.licensing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.crypto.Cipher;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;

public class LicenseDialog extends WebDialog {

	/**
	 * Launch the application.
	 */
	WebButton wbtnTrial;
	WebLabel webLabel_3;
	WebLabel wblblTrialRemaining;
	WebLabel webLabel_2;
	
	public static void main(String[] args) {
		try {
		    System.out.println(MiscUtils.getHDDSN());
			LicenseDialog dialog = new LicenseDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LicenseDialog() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				//GenerateMachineCode();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				if(!LicenseValidator.isLicenseActivated())
					if(!LicenseValidator.isTrialValid()){
						JOptionPane.showMessageDialog(null, "No valid license found!");
						System.exit(0);
					}
			}
		});
		setResizable(false);
		setTitle("License Info");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setType(Type.UTILITY);
		setBounds(100, 100, 450, 229);
		
		WebPanel webPanel = new WebPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 191, Short.MAX_VALUE)
		);
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("Name : ");
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("Serial Number : ");
		
		WebTextField webTextField = new WebTextField();
		
		WebTextField webTextField_1 = new WebTextField();
		
		WebLabel wblblMachineCode = new WebLabel();
		wblblMachineCode.setText("Machine Code : ");
		
		webLabel_2 = new WebLabel();
		
		WebButton wbtnActivate = new WebButton();
		wbtnActivate.setText("Activate");
		
		wbtnTrial = new WebButton();
		wbtnTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		wbtnTrial.setText("Trial");
		
		wblblTrialRemaining = new WebLabel();
		wblblTrialRemaining.setText("Trial Remaining : ");
		
		webLabel_3 = new WebLabel();
		
		WebLabel wblblActivationCode = new WebLabel();
		wblblActivationCode.setVisible(false);
		wblblActivationCode.setText("Activation Code : ");
		
		WebTextField webTextField_2 = new WebTextField();
		webTextField_2.setVisible(false);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblMachineCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(webLabel_2, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_webPanel.createSequentialGroup()
							.addComponent(wblblTrialRemaining, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
							.addComponent(wbtnTrial, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnActivate, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblActivationCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
							.addGap(14)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
								.addComponent(webTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(16)
							.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(14)
							.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblActivationCode, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(wblblMachineCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblTrialRemaining, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbtnTrial, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnActivate, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		webPanel.setLayout(gl_webPanel);
		getContentPane().setLayout(groupLayout);
		
		if(LicenseValidator.isTrialValid()){
			wbtnTrial.setVisible(true);
			webLabel_3.setText(LicenseValidator.getTrialTime() + " Days");
		}
		else{
			wbtnTrial.setVisible(false);
			webLabel_3.setVisible(false);
			wblblTrialRemaining.setVisible(false);
		}
	}

	protected void GenerateMachineCode() {
		String MacAddress = getLocalMacAddress();
		String MotherboardSerialNumber = getMotherboardSN();
		String CpuID = MiscUtils.getCpuSN();
		if(MacAddress.isEmpty() && MotherboardSerialNumber.isEmpty() && CpuID.isEmpty()){
		    webLabel_2.setText("No machine code could be generated.Contact your administrator!");
		    return;
		}
		
		
	}

	private String getMotherboardSN() {
	    return MiscUtils.getMotherboardSN();
	}

	private String getLocalMacAddress() {
		String macAddress = "";
		try {
			byte[] mac1 = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			String mac = "";
			for (int k = 0; k < mac1.length; k++) {
                mac += String.format("%02X%s", mac1[k], (k < mac1.length - 1) ? "-" : "");
            }
			macAddress = mac;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return macAddress;
	}
	
	
}

class MiscUtils {
    private MiscUtils() {
    }

    public static String getMotherboardSN() {
	String result = "";
	try {
	    File file = File.createTempFile("realhowto", ".vbs");
	    file.deleteOnExit();
	    FileWriter fw = new java.io.FileWriter(file);

	    String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		    + "Set colItems = objWMIService.ExecQuery _ \n"
		    + " (\"Select * from Win32_BaseBoard\") \n"
		    + "For Each objItem in colItems \n"
		    + " Wscript.Echo objItem.SerialNumber \n"
		    + " exit for ' do the first cpu only! \n" + "Next \n";

	    fw.write(vbs);
	    fw.close();
	    Process p = Runtime.getRuntime().exec(
		    "cscript //NoLogo " + file.getPath());
	    BufferedReader input = new BufferedReader(new InputStreamReader(
		    p.getInputStream()));
	    String line;
	    while ((line = input.readLine()) != null) {
		result += line;
	    }
	    input.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result.trim();
    }
    
    public static String getCpuSN() {
	String result = "";
	try {
	    File file = File.createTempFile("realhowto", ".vbs");
	    file.deleteOnExit();
	    FileWriter fw = new java.io.FileWriter(file);

	    String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		    + "Set colItems = objWMIService.ExecQuery _ \n"
		    + " (\"Select * from Win32_Processor\") \n"
		    + "For Each objItem in colItems \n"
		    + " Wscript.Echo objItem.ProcessorId \n"
		    + " exit for ' do the first cpu only! \n" + "Next \n";

	    fw.write(vbs);
	    fw.close();
	    Process p = Runtime.getRuntime().exec(
		    "cscript //NoLogo " + file.getPath());
	    BufferedReader input = new BufferedReader(new InputStreamReader(
		    p.getInputStream()));
	    String line;
	    while ((line = input.readLine()) != null) {
		result += line;
	    }
	    input.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result.trim();
    }
    
    public static String getHDDSN() {
	String result = "";
	try {
	    File file = File.createTempFile("realhowto", ".vbs");
	    file.deleteOnExit();
	    FileWriter fw = new java.io.FileWriter(file);

	    String vbs = "Const wbemFlagReturnImmediately = &h10\n" +
	    		"Const wbemFlagForwardOnly = &h20\n" +
	    		"Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		    + "Set colItems = objWMIService.ExecQuery _ \n"
		    + " (\"SELECT * FROM Win32_PhysicalMedia\" , \"WQL\", wbemFlagReturnImmediately + wbemFlagForwardOnly) \n"
		    + "For Each objItem in colItems \n"
		    + " Wscript.Echo objItem.SerialNumber \n"
		    + " exit for ' do the first cpu only! \n" + "Next \n";

	    fw.write(vbs);
	    fw.close();
	    Process p = Runtime.getRuntime().exec(
		    "cscript //NoLogo " + file.getPath());
	    BufferedReader input = new BufferedReader(new InputStreamReader(
		    p.getInputStream()));
	    String line;
	    while ((line = input.readLine()) != null) {
		result += line;
	    }
	    input.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result.trim();
    }
}

