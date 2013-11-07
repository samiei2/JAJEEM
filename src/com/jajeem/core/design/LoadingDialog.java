package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.sun.awt.AWTUtilities;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window.Type;
import java.awt.Dialog.ModalityType;
import java.awt.Cursor;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.button.WebButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadingDialog extends JDialog{

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
		});
		setResizable(false);
	    setUndecorated(true);
	    
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setAlwaysOnTop(true);
	    System.setProperty("sun.java2d.noddraw", "true");
	    AWTUtilities.setWindowOpaque(this, false);
	    
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadingDialog.class.getResource("/icons/noa_en/1.jpg")));
		
		setBounds(100, 100, 696, 451);
		int scaledWidth, scaledHeight;
		{
			
			myImage = new ImageIcon(LoadingDialog.class.getResource("/icons/noa_en/1.jpg"));
			
			int originalWidth = myImage.getIconWidth();
			int originalHeight = myImage.getIconHeight();

			// Then calculate the ratio
			float imageRatio = (float) ((float)originalWidth / (float)originalHeight);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			float screenRatio = (float) ((float)screenSize.width / (float)screenSize.height);

			if(imageRatio <= screenRatio) {
			// The scaled size is based on the height
			scaledHeight = screenSize.height;
			scaledWidth = (int) (scaledHeight * imageRatio);
			} else {
			// The scaled size is based on the width
			scaledWidth = screenSize.width;
			scaledHeight = (int) (scaledWidth / imageRatio);
			}

			myImage = new ImageIcon(myImage.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST));
		}
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
	    int xSize = ((int) tk.getScreenSize().getWidth());  
	    int ySize = ((int) tk.getScreenSize().getHeight()); 
	    setSize(scaledWidth, scaledHeight);
	    setLocationRelativeTo(null);
	    
	    WebPanel webPanel = new WebPanel();
	    webPanel.setOpaque(false);
	    getContentPane().add(webPanel, BorderLayout.CENTER);
	    
	    WebLabel wblblStatus = new WebLabel();
	    wblblStatus.setText("Initializing...");
	    
	    WebProgressBar webProgressBar = new WebProgressBar();
	    webProgressBar.setOpaque(true);
	    webProgressBar.setIndeterminate(true);
	    GroupLayout gl_webPanel = new GroupLayout(webPanel);
	    gl_webPanel.setHorizontalGroup(
	    	gl_webPanel.createParallelGroup(Alignment.TRAILING)
	    		.addGroup(gl_webPanel.createSequentialGroup()
	    			.addContainerGap()
	    			.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
	    				.addComponent(webProgressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
	    				.addComponent(wblblStatus, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
	    			.addContainerGap())
	    );
	    gl_webPanel.setVerticalGroup(
	    	gl_webPanel.createParallelGroup(Alignment.TRAILING)
	    		.addGroup(gl_webPanel.createSequentialGroup()
	    			.addContainerGap(370, Short.MAX_VALUE)
	    			.addComponent(wblblStatus, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
	    			.addPreferredGap(ComponentPlacement.UNRELATED)
	    			.addComponent(webProgressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
	    			.addContainerGap())
	    );
	    webPanel.setLayout(gl_webPanel);
	}
	
	@Override
	public void paint(Graphics g){
		super.paintComponents(g);
        g.drawImage(myImage.getImage(), 0, 0, null);
	}
}
