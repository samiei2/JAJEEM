package com.jajeem.recorder.design;

import javax.swing.JFrame;

import com.jajeem.quiz.design.alt.Quiz_Main;
import com.sun.awt.AWTUtilities;
import com.sun.jna.platform.*;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.alee.laf.button.WebButton;
import java.awt.BorderLayout;
import com.alee.laf.panel.WebPanel;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JWindow;
import com.alee.laf.label.WebLabel;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import com.alee.extended.image.WebImage;

public class TransparentRecordingFrame extends JDialog {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblTime;
	private JLabel lblImage;
	long startTime = System.currentTimeMillis();
	public TransparentRecordingFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
				Timer timer = new Timer();
				final NumberFormat format = NumberFormat.getInstance();
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
				        long elapsed = System.currentTimeMillis() - startTime;
				        int minutes = (int) (elapsed / 60000);
						int seconds = (int) ((elapsed % 60000) / 1000);
						lblTime.setText(format.format(minutes) + ":"
								+ format.format(seconds));
						setAlwaysOnTop(true);
					}
				}, 0 ,1000);
			}
		});
	    initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
//	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setIconImage(Toolkit.getDefaultToolkit().getImage(""));
	    setResizable(false);
	    setUndecorated(true);
	    
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setAlwaysOnTop(true);
	    System.setProperty("sun.java2d.noddraw", "true");
	    AWTUtilities.setWindowOpaque(this, false);
//	    WindowUtils.setWindowTransparent(this, true);
//	    WindowUtils.setWindowAlpha(this, 0.6f);
	    
	    ((JComponent)getContentPane()).setBorder(   
	            BorderFactory.createMatteBorder( 3, 3, 3, 3, Color.red ) );  
	    
	    
	    Icon icon = new ImageIcon(TransparentRecordingFrame.class
				.getResource("/com/jajeem/images/recording-icon.gif"));
		lblImage = new JLabel(icon);
	    
	    WebPanel webPanel = new WebPanel();
		
	    GroupLayout groupLayout = new GroupLayout(getContentPane());
	    groupLayout.setHorizontalGroup(
	    	groupLayout.createParallelGroup(Alignment.TRAILING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addContainerGap(570, Short.MAX_VALUE)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    				.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblImage, Alignment.TRAILING))
	    			.addContainerGap())
	    );
	    groupLayout.setVerticalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addContainerGap()
	    			.addComponent(lblImage)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
	    			.addContainerGap(348, Short.MAX_VALUE))
	    );
	    
	    WebLabel wblblRecordingTime = new WebLabel();
	    wblblRecordingTime.setBackground(Color.LIGHT_GRAY);
	    wblblRecordingTime.setText("Elapsed Time : ");
	    lblTime = new JLabel("New label");
	    GroupLayout gl_webPanel = new GroupLayout(webPanel);
	    gl_webPanel.setHorizontalGroup(
	    	gl_webPanel.createParallelGroup(Alignment.LEADING)
	    		.addGroup(gl_webPanel.createSequentialGroup()
	    			.addContainerGap()
	    			.addComponent(wblblRecordingTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(lblTime, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
	    			.addContainerGap())
	    );
	    gl_webPanel.setVerticalGroup(
	    	gl_webPanel.createParallelGroup(Alignment.LEADING)
	    		.addGroup(gl_webPanel.createSequentialGroup()
	    			.addContainerGap()
	    			.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(wblblRecordingTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
	    			.addContainerGap(21, Short.MAX_VALUE))
	    );
	    webPanel.setLayout(gl_webPanel);
	    getContentPane().setLayout(groupLayout);
	    
	   addKeyListener(new KeyListener() {
		   
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	   	Toolkit tk = Toolkit.getDefaultToolkit();  
	    int xSize = ((int) tk.getScreenSize().getWidth());  
	    int ySize = ((int) tk.getScreenSize().getHeight()); 
	    setSize(xSize, ySize);
//	    setVisible(true);
	}
	    
	public static void main(String[] args) {
		TransparentRecordingFrame frm = new TransparentRecordingFrame();
		frm.setVisible(true);
	}
}
