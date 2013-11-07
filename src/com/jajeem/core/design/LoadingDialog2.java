package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.jajeem.util.CustomTopPanel;
import com.sun.awt.AWTUtilities;

public class LoadingDialog2 extends JDialog{

	ImageIcon myImage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoadingDialog2 dialog = new LoadingDialog2();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoadingDialog2() {
		setResizable(false);
	    setUndecorated(true);
	    
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setAlwaysOnTop(true);
	    System.setProperty("sun.java2d.noddraw", "true");
	    AWTUtilities.setWindowOpaque(this, false);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0,0,screen.width,screen.height);
	    
	    WebPanel webpanel = new WebPanel();
	    webpanel.setOpaque(false);
	    CustomLoadingPanel panel=new CustomLoadingPanel();
	    setContentPane(webpanel);
	    GroupLayout gl_webpanel = new GroupLayout(webpanel);
	    gl_webpanel.setHorizontalGroup(
	    	gl_webpanel.createParallelGroup(Alignment.LEADING)
	    		.addGroup(Alignment.TRAILING, gl_webpanel.createSequentialGroup()
	    			.addContainerGap(538, Short.MAX_VALUE)
	    			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 573, GroupLayout.PREFERRED_SIZE)
	    			.addGap(489))
	    );
	    gl_webpanel.setVerticalGroup(
	    	gl_webpanel.createParallelGroup(Alignment.LEADING)
	    		.addGroup(gl_webpanel.createSequentialGroup()
	    			.addGap(68)
	    			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 747, GroupLayout.PREFERRED_SIZE)
	    			.addContainerGap(85, Short.MAX_VALUE))
	    );
	    webpanel.setLayout(gl_webpanel);
	}
}

class CustomLoadingPanel extends WebPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image myImage;
	BufferedImage originalImage;
	{
		try {
			myImage = Toolkit.getDefaultToolkit().getImage(LoadingDialog2.class.getResource("/icons/noa_en/loadingfinal.gif"));
			originalImage = ImageIO.read(
					CustomTopPanel.class.getResource("/icons/noa_en/loadingfinal.gif"));
		} catch (Exception e) {
		}
		setBackground(new Color(0,0,0,0));
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics g2 = g.create();
        g2.drawImage(myImage, 0, 0 , originalImage.getWidth(),originalImage.getHeight(), this);
        g2.dispose();
	}
}
