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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.alee.laf.optionpane.WebOptionPane;

public class CustomFrame extends JFrame{
	int posX=0,posY=0;
	public CustomFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		setContentPane(new CustomPanel("/icons/noa_en/background.png"));
		
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
	
}

class CustomPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float xScaleFactor = (float) 0.1;
	private float yScaleFactor = (float) 0.1;
    private BufferedImage originalImage;

    CustomPanel(String imageURI) {
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
		Graphics2D g2 = (Graphics2D)g;
        int newW = (int)(originalImage.getWidth() * xScaleFactor);
        int newH = (int)(originalImage.getHeight() * yScaleFactor);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //g2.drawImage(originalImage, 0, 0, newW, newH, null);
        super.paint(g);
	}
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics g2 = g.create();
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }
    
    @Override
    @Transient
    public Dimension getPreferredSize() {
    	return new Dimension(800,600);
    }
}