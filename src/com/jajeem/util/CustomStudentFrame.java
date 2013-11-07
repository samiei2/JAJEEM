package com.jajeem.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.alee.laf.optionpane.WebOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.button.WebButton;

public class CustomStudentFrame extends JFrame{
	int posX=0,posY=0;
	public CustomStudentFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		CustomStudentPanel customStudentPanel = new CustomStudentPanel("/icons/noa_en/studentback.png");
		setContentPane(customStudentPanel);
		
		WebButton webButton = new WebButton();
		
		WebButton webButton_1 = new WebButton();
		
		WebButton webButton_2 = new WebButton();
		GroupLayout gl_customStudentPanel = new GroupLayout(customStudentPanel);
		gl_customStudentPanel.setHorizontalGroup(
			gl_customStudentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customStudentPanel.createSequentialGroup()
					.addGap(120)
					.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_customStudentPanel.setVerticalGroup(
			gl_customStudentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customStudentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_customStudentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(552, Short.MAX_VALUE))
		);
		customStudentPanel.setLayout(gl_customStudentPanel);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = d.width-410;
		int y = d.height-600;
		setBounds(x, y, 410, 600);
		this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e)
		   {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		
		this.addMouseMotionListener(new MouseAdapter()
		{
		     public void mouseDragged(MouseEvent evt)
		     {
				//sets frame position when mouse dragged			
				setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
							
		     }
		});
		
		this.addKeyListener(new KeyListener() {
			
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					int i = WebOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to close this window?");
					if(i==0)
						dispose();
					else if(i==1)
						return;
					else
						return;
				}
			}
		});
	}
	
	public static void main(String[] args){
		CustomStudentFrame frm = new CustomStudentFrame();
		frm.setVisible(true);
	}
}

class CustomStudentPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float xScaleFactor = (float) 0.1;
	private float yScaleFactor = (float) 0.1;
    private BufferedImage originalImage;

    CustomStudentPanel(String imageURI) {
		try {
			originalImage = ImageIO.read(
					test.class.getResource(imageURI));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpaque(false);
	}

    @Override
	public void paint(Graphics g) {
    	
    	Graphics g2 = g.create();
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
        super.paint(g);
	}
    
    @Override
    protected void paintComponent(Graphics g){
//        Graphics g2 = g.create();
//        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
//        g2.dispose();
    }
}