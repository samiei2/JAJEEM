package com.jajeem.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.sun.awt.AWTUtilities;
public class LoadingDialog extends JDialog {

	ImageIcon myImage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoadingDialog dialog = new LoadingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoadingDialog() {
		setResizable(false);
	    setUndecorated(true);
	    
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    setAlwaysOnTop(true);
	    System.setProperty("sun.java2d.noddraw", "true");
	    AWTUtilities.setWindowOpaque(this, false);
	    
//		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadingDialog.class.getResource("/com/jajeem/whiteboard/Images/logo.png")));
		
		setBounds(100, 100, 696, 451);
		setBackground(new Color(0.9f, 0.9f, 0.9f,0.3f).darker().darker().darker().darker().darker());
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
	    int xSize = ((int) tk.getScreenSize().getWidth());  
	    int ySize = ((int) tk.getScreenSize().getHeight()); 
	    setLocationRelativeTo(null);
	    setBounds(0,0,xSize,ySize);
	    getContentPane().setLayout(new BorderLayout(0, 0));
	    
	    WebPanel webPanel = new WebPanel();
	    getContentPane().add(webPanel, BorderLayout.WEST);
	    
	    WebPanel webPanel_1 = new WebPanel();
	    getContentPane().add(webPanel_1, BorderLayout.EAST);
	    
	    WebPanel webPanel_2 = new WebPanel();
	    getContentPane().add(webPanel_2, BorderLayout.NORTH);
	    
	    WebPanel webPanel_3 = new WebPanel();
	    getContentPane().add(webPanel_3, BorderLayout.SOUTH);
	    
	    WebLabel webLabel = new WebLabel();
	    webLabel.setIcon(new ImageIcon(LoadingDialog.class.getResource("/com/jajeem/whiteboard/Image/logo.png")));
	    webLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    getContentPane().add(webLabel, BorderLayout.CENTER);
	}
}
