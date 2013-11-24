package com.jajeem.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CustomLoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	int posX=0,posY=0;
	JFrame mainFrame;
	JPanel contentPanel;
	public CustomLoginFrame() {
		mainFrame = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(619,303);
		
		CustomLoginPanel panel = new CustomLoginPanel("/icons/noa_en/new/loginpanel.png");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
		);
		
		CustomLogoLabel lblNewLabel = new CustomLogoLabel("/icons/noa_en/new/loginlogo.png");
		
		CustomLogoLabel customLogoLabel_1 = new CustomLogoLabel("/icons/noa_en/new/welcomto.png");
		
		CustomLogoLabel customLogoLabel_2 = new CustomLogoLabel("/icons/noa_en/new/loginclassmate.png");
		
		contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 539, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(customLogoLabel_1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 192, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(customLogoLabel_2, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
									.addGap(46)))
							.addGap(43))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(customLogoLabel_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(customLogoLabel_2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addGap(22))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
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
		
		this.addKeyListener(new KeyAdapter() {
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
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				mainFrame.toFront();
			}
		});
	}
	
	public Container getMainContentPane() {
		return contentPanel;
	}
	
	public static void main(String[] args){
		CustomLoginFrame frm = new CustomLoginFrame();
		frm.setVisible(true);
	}
}
 
class CustomLoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private BufferedImage originalImage;

    CustomLoginPanel(String imageURI) {
		try {
			originalImage = ImageIO.read(
					test.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
	}
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics g2 = g.create();
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }
    
    @Override
    public int getWidth() {
    	int w = super.getWidth();
    	return w;
    }
    
    @Override
    public int getHeight() {
    	int h = super.getHeight();
    	return h;
    }
}

class CustomLoginButton extends WebButton{
	private static final long serialVersionUID = 1L;
    private BufferedImage originalImage;
    int width,height = 20;
    int x = 10;
    int y = 5;
    BufferedImage background;
    BufferedImage rollover;
    BufferedImage selected;
    
    boolean isRollOver,isPressed;
    
    CustomLoginButton(String imageURI) {
		try {
			originalImage = ImageIO.read(
					test.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setUndecorated(false);
	}
    
    CustomLoginButton(String imageURI,int w,int h) {
    {
        	try {
    			URL inp = CustomButton.class.getResource(imageURI+".png");
    			background = ImageIO.read(inp);
    			inp = CustomButton.class.getResource(imageURI+"Hover.png");
    			rollover = ImageIO.read(inp);
    			inp = CustomButton.class.getResource(imageURI+"Pressed.png");
    			selected = ImageIO.read(inp);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		getModel().addChangeListener(new ChangeListener() {
    	        @Override
    	        public void stateChanged(ChangeEvent e) {
    	            ButtonModel model = (ButtonModel) e.getSource();
    	            if (model.isRollover()) {
    	                isRollOver = true;
    	            } else {
    	            	isRollOver = false;
    	            }
    	            if(model.isPressed()){
    	            	isPressed = true;
    	            }
    	            else{
    	            	isPressed = false;
    	            }
    	         }
    		});
        }
    	
		
		setOpaque(false);
		width = w;
		height = h;
	}
    
    CustomLoginButton(String imageURI,int w,int h,int x,int y) {
    	{
        	try {
    			URL inp = CustomButton.class.getResource(imageURI+".png");
    			background = ImageIO.read(inp);
//    			inp = CustomButton.class.getResource(imageURI+"Hover.png");
//    			rollover = ImageIO.read(inp);
//    			inp = CustomButton.class.getResource(imageURI+"Pressed.png");
//    			selected = ImageIO.read(inp);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		getModel().addChangeListener(new ChangeListener() {
    	        @Override
    	        public void stateChanged(ChangeEvent e) {
    	            ButtonModel model = (ButtonModel) e.getSource();
    	            if (model.isRollover()) {
    	                isRollOver = true;
    	            } else {
    	            	isRollOver = false;
    	            }
    	            if(model.isPressed()){
    	            	isPressed = true;
    	            }
    	            else{
    	            	isPressed = false;
    	            }
    	         }
    		});
        }
		try {
			originalImage = ImageIO.read(
					test.class.getResource(imageURI+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
		width = w;
		height = h;
		this.x = x;
		this.y = y;
	}
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics g2 = g.create();
        if(background != null){
        	g2.drawImage(background, x, y, width, height, null);
	    }
        g2.dispose();
    }
}