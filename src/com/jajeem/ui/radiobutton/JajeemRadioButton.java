package com.jajeem.ui.radiobutton;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * User: mgarin Date: 28.06.11 Time: 0:51
 */

public class JajeemRadioButton extends JRadioButton
{
    protected boolean isRollOver;
	protected boolean isPressed;
	private BufferedImage backgroundSelected;

	/**
	 * 
	 */
	{
		try {
			backgroundSelected = ImageIO.read(JajeemRadioButton.class.getResource("/icons/noa_en/radiobuttonbackselected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JajeemRadioButton ()
    {
        super ();
    }

    public JajeemRadioButton ( boolean selected )
    {
        super ( "", selected );
    }

    public JajeemRadioButton ( Icon icon )
    {
        super ( icon );
    }

    public JajeemRadioButton ( Action a )
    {
        super ( a );
    }

    public JajeemRadioButton ( Icon icon, boolean selected )
    {
        super ( icon, selected );
    }

    public JajeemRadioButton ( String text )
    {
        super ( text );
    }

    public JajeemRadioButton ( String text, boolean selected )
    {
        super ( text, selected );
    }

    public JajeemRadioButton ( String text, Icon icon )
    {
        super ( text, icon );
    }

    public JajeemRadioButton ( String text, Icon icon, boolean selected )
    {
        super ( text, icon, selected );
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof JajeemRadioButtonUI ) )
        {
            try
            {
                setUI ( new JajeemRadioButtonUI() );
            }
            catch ( Throwable e )
            {
                e.printStackTrace ();
                setUI ( new JajeemRadioButtonUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	// TODO Auto-generated method stub
    	Graphics2D g2d = (Graphics2D)g.create();
    	if(isSelected()){
    		ImageIcon scaled = new ImageIcon(backgroundSelected.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	            RenderingHints.VALUE_ANTIALIAS_ON);
    		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.9f );
            g2d.setComposite(comp );
    		g2d.drawImage(scaled.getImage(),0,0, this);
    		setForeground(Color.WHITE);
    	}
    	else
    		setForeground(Color.BLACK);

    	g2d.setColor(Color.gray);
    	g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
    	g2d.dispose();
    	super.paintComponent(g);   	
    }
}