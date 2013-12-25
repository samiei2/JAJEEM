package com.jajeem.core.design.teacher;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;
import com.jajeem.util.CustomButton;


class LoginRoundedPanel extends JPanel {
	public LoginRoundedPanel() {
		setOpaque(false);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Stroke size. it is recommended to set it to 1 for better view */
	protected int strokeSize = 6;
	/** Color of shadow */
	protected Color shadowColor = Color.black;
	/** Sets if it drops shadow */
	protected boolean shady = false;
	/** Sets if it has an High Quality view */
	protected boolean highQuality = true;
	/** Double values for Horizontal and Vertical radius of corner arcs */
	protected Dimension arcs = new Dimension(30, 30);
	/** Distance between shadow border and opaque panel border */
	protected int shadowGap = 5;
	/** The offset of shadow. */
	protected int shadowOffset = 4;
	/** The transparency value of shadow. ( 0 - 255) */
	protected int shadowAlpha = 90;

	// FOLLOWING CODES GOES HERE

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		int shadowGap = this.shadowGap;
		Graphics2D graphics = (Graphics2D) g;

		// Sets antialiasing if HQ.
		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Draws the rounded opaque panel with borders.
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.setStroke(new BasicStroke(strokeSize));
		graphics.drawRoundRect(2, 2, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);

		// Sets strokes to default, is better.
		graphics.setStroke(new BasicStroke());
	}
}

class LoginRoundedTextBox extends JTextField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private String watermarkText;
    public LoginRoundedTextBox() {
        super();
        setOpaque(false); // As suggested by @AVD in comment.
        setMargin(new Insets(0, 10, 0, 0));
        addFocusListener(new FocusAdapter(){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if(getText().equals(watermarkText))
        			setText("");
        		super.focusGained(e);
        	}
        	
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		if(getText().equals(""))
        			setText(watermarkText);
        		super.focusLost(arg0);
        	}
        });
    }
    public void setWaterMarkText(String string) {
    	watermarkText = string;
		setText(string);
	}
    
    protected void paintComponent(Graphics g) {
//    	Graphics2D g2 = (Graphics2D)g;
//    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//				RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(getBackground());
//        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}

class LoginRoundedPasswordBox extends JPasswordField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private String watermarkText;
	private String realText;
    public LoginRoundedPasswordBox() {
        super();
        setOpaque(false); // As suggested by @AVD in comment.
        setMargin(new Insets(0, 10, 0, 0));
        setEchoChar((char) 0);
        addFocusListener(new FocusAdapter(){
        	@SuppressWarnings("deprecation")
			@Override
        	public void focusGained(FocusEvent e) {
        		setEchoChar('\u2022');
        		if(getText().equals(watermarkText))
        			setText("");
        		super.focusGained(e);
        	}
        	
        	@SuppressWarnings("deprecation")
			@Override
        	public void focusLost(FocusEvent arg0) {
        		if(getText().equals("")){
        			setText(watermarkText);
        			setEchoChar((char) 0);
        		}
        		super.focusLost(arg0); 
        	}
        });       
    }
    public void setWaterMarkText(String string) {
    	watermarkText = string;
		setText(string);
	}
    
    protected void paintComponent(Graphics g) {
//    	Graphics2D g2 = (Graphics2D)g;
//    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//				RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(getBackground());
//        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
	public String getRealText() {
		return realText;
	}
	public void setRealText(String realText) {
		this.realText = realText;
	}
}

class CustomLoginButton extends WebButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;
	protected boolean isRollOver;
	protected boolean isPressed;

	public CustomLoginButton() {
		init();
	}

	public CustomLoginButton(ImageIcon img) {
		super(img);
		init();
	}

	public CustomLoginButton(String label, ImageIcon img) {
		super(label, img);
		init();
	}

	public CustomLoginButton(String label) {
		super(label);
		init();
	}

	public void init() {
		try {
			URL inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
			background = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
			rollover = ImageIO.read(inp);
			inp = CustomButton.class
					.getResource("/icons/noa_en/teacherloginbutton.png");
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
				if (model.isPressed()) {
					isPressed = true;
				} else {
					isPressed = false;
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			if (isPressed) {
				g2.drawImage(selected, 0, 0, getWidth(), getHeight(), this);
			} else {
				if (isRollOver) {
					g2.drawImage(rollover, 0, 0, getWidth(), getHeight(), this);
				} else {
					g2.drawImage(background, 0, 0, getWidth(), getHeight(),
							this);
				}
			}
		}
		g2.dispose();
		super.paintComponent(g);
	}
}