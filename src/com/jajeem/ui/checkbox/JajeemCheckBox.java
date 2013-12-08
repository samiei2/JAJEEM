/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jajeem.ui.checkbox;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.hotkey.HotkeyData;
import com.alee.managers.hotkey.HotkeyInfo;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.language.LanguageMethods;
import com.alee.managers.language.updaters.LanguageUpdater;
import com.alee.managers.settings.DefaultValue;
import com.alee.managers.settings.SettingsManager;
import com.alee.managers.settings.SettingsMethods;
import com.alee.managers.settings.SettingsProcessor;
import com.alee.managers.tooltip.TooltipWay;
import com.alee.utils.ReflectUtils;
import com.alee.utils.SizeUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.laf.ShapeProvider;
import com.alee.utils.swing.FontMethods;
import com.alee.utils.swing.SizeMethods;
import com.jajeem.ui.radiobutton.JajeemRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * User: mgarin Date: 28.06.11 Time: 0:50
 */

public class JajeemCheckBox extends JCheckBox
{
	private BufferedImage backgroundSelected;
	protected boolean isRollOver;
	protected boolean isPressed;
	
	{
		try {
			backgroundSelected = ImageIO.read(JajeemRadioButton.class.getResource("/icons/noa_en/radiobuttonbackselected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public JajeemCheckBox ()
    {
        super ();
        setMargin(0, 15, 0, 0);
    }

    public JajeemCheckBox ( boolean selected )
    {
        super ( "", selected );
    }

    public JajeemCheckBox ( Icon icon )
    {
        super ( icon );
    }

    public JajeemCheckBox ( Icon icon, boolean selected )
    {
        super ( icon, selected );
    }

    public JajeemCheckBox ( String text )
    {
        super ( text );
    }

    public JajeemCheckBox ( Action a )
    {
        super ( a );
    }

    public JajeemCheckBox ( String text, boolean selected )
    {
        super ( text, selected );
    }

    public JajeemCheckBox ( String text, Icon icon )
    {
        super ( text, icon );
    }

    public JajeemCheckBox ( String text, Icon icon, boolean selected )
    {
        super ( text, icon, selected );
    }

    /**
     * Proxified kotkey manager methods
     */

    public HotkeyInfo addHotkey ( Integer keyCode )
    {
        return addHotkey ( new HotkeyData ( keyCode ) );
    }

    public HotkeyInfo addHotkey ( boolean isCtrl, boolean isAlt, boolean isShift, Integer keyCode )
    {
        return addHotkey ( new HotkeyData ( isCtrl, isAlt, isShift, keyCode ) );
    }

    public HotkeyInfo addHotkey ( HotkeyData hotkeyData )
    {
        return HotkeyManager.registerHotkey ( this, hotkeyData );
    }

    public HotkeyInfo addHotkey ( HotkeyData hotkeyData, boolean hidden )
    {
        return HotkeyManager.registerHotkey ( this, hotkeyData, hidden );
    }

    public HotkeyInfo addHotkey ( HotkeyData hotkeyData, TooltipWay tooltipWay )
    {
        return HotkeyManager.registerHotkey ( this, hotkeyData, tooltipWay );
    }

    public HotkeyInfo addHotkey ( Component topComponent, HotkeyData hotkeyData )
    {
        return HotkeyManager.registerHotkey ( topComponent, this, hotkeyData );
    }

    public HotkeyInfo addHotkey ( Component topComponent, HotkeyData hotkeyData, boolean hidden )
    {
        return HotkeyManager.registerHotkey ( topComponent, this, hotkeyData, hidden );
    }

    public HotkeyInfo addHotkey ( Component topComponent, HotkeyData hotkeyData, TooltipWay tooltipWay )
    {
        return HotkeyManager.registerHotkey ( topComponent, this, hotkeyData, tooltipWay );
    }

    public List<HotkeyInfo> getHotkeys ()
    {
        return HotkeyManager.getComponentHotkeys ( this );
    }

    public void removeHotkey ( HotkeyInfo hotkeyInfo )
    {
        HotkeyManager.unregisterHotkey ( hotkeyInfo );
    }

    public void removeHotkeys ()
    {
        HotkeyManager.unregisterHotkeys ( this );
    }

    /**
     * UI methods
     */

    @Override
    public Insets getMargin ()
    {
        return getWebUI ().getMargin ();
    }

    @Override
    public void setMargin ( Insets margin )
    {
        getWebUI ().setMargin ( margin );
    }

    public void setMargin ( int top, int left, int bottom, int right )
    {
        setMargin ( new Insets ( top, left, bottom, right ) );
    }

    public void setMargin ( int spacing )
    {
        setMargin ( spacing, spacing, spacing, spacing );
    }

    public boolean isAnimated ()
    {
        return getWebUI ().isAnimated ();
    }

    public void setAnimated ( boolean animated )
    {
        getWebUI ().setAnimated ( animated );
    }

    public boolean isRolloverDarkBorderOnly ()
    {
        return getWebUI ().isRolloverDarkBorderOnly ();
    }

    public void setRolloverDarkBorderOnly ( boolean rolloverDarkBorderOnly )
    {
        getWebUI ().setRolloverDarkBorderOnly ( rolloverDarkBorderOnly );
    }

    public Color getBorderColor ()
    {
        return getWebUI ().getBorderColor ();
    }

    public void setBorderColor ( Color borderColor )
    {
        getWebUI ().setBorderColor ( borderColor );
    }

    public Color getDarkBorderColor ()
    {
        return getWebUI ().getDarkBorderColor ();
    }

    public void setDarkBorderColor ( Color darkBorderColor )
    {
        getWebUI ().setDarkBorderColor ( darkBorderColor );
    }

    public Color getDisabledBorderColor ()
    {
        return getWebUI ().getDisabledBorderColor ();
    }

    public void setDisabledBorderColor ( Color disabledBorderColor )
    {
        getWebUI ().setDisabledBorderColor ( disabledBorderColor );
    }

    public Color getTopBgColor ()
    {
        return getWebUI ().getTopBgColor ();
    }

    public void setTopBgColor ( Color topBgColor )
    {
        getWebUI ().setTopBgColor ( topBgColor );
    }

    public Color getBottomBgColor ()
    {
        return getWebUI ().getBottomBgColor ();
    }

    public void setBottomBgColor ( Color bottomBgColor )
    {
        getWebUI ().setBottomBgColor ( bottomBgColor );
    }

    public Color getTopSelectedBgColor ()
    {
        return getWebUI ().getTopSelectedBgColor ();
    }

    public void setTopSelectedBgColor ( Color topSelectedBgColor )
    {
        getWebUI ().setTopSelectedBgColor ( topSelectedBgColor );
    }

    public Color getBottomSelectedBgColor ()
    {
        return getWebUI ().getBottomSelectedBgColor ();
    }

    public void setBottomSelectedBgColor ( Color bottomSelectedBgColor )
    {
        getWebUI ().setBottomSelectedBgColor ( bottomSelectedBgColor );
    }

    public int getRound ()
    {
        return getWebUI ().getRound ();
    }

    public void setRound ( int round )
    {
        getWebUI ().setRound ( round );
    }

    public int getShadeWidth ()
    {
        return getWebUI ().getShadeWidth ();
    }

    public void setShadeWidth ( int shadeWidth )
    {
        getWebUI ().setShadeWidth ( shadeWidth );
    }

    public int getIconWidth ()
    {
        return getWebUI ().getIconWidth ();
    }

    public void setIconWidth ( int iconWidth )
    {
        getWebUI ().setIconWidth ( iconWidth );
    }

    public int getIconHeight ()
    {
        return getWebUI ().getIconHeight ();
    }

    public void setIconHeight ( int iconHeight )
    {
        getWebUI ().setIconHeight ( iconHeight );
    }

    @Override
    public void setSelected ( boolean b )
    {
        setSelected ( b, isShowing () );
    }

    public void setSelected ( boolean b, boolean withAnimation )
    {
        boolean animated = isAnimated ();
        if ( !withAnimation && animated )
        {
            setAnimated ( false );
        }
        super.setSelected ( b );
        if ( !withAnimation )
        {
            setAnimated ( animated );
        }
    }

    public JajeemCheckBoxUI getWebUI ()
    {
        return ( JajeemCheckBoxUI ) getUI ();
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof JajeemCheckBoxUI ) )
        {
            try
            {
            	setUI ( new JajeemCheckBoxUI () );
            }
            catch ( Throwable e )
            {
                e.printStackTrace ();
                setUI ( new JajeemCheckBoxUI () );
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