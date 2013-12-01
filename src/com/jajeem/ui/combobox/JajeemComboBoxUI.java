package com.jajeem.ui.combobox;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxStyle;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.utils.SwingUtils;

public class JajeemComboBoxUI extends BasicComboBoxUI{
	private ImageIcon expandIcon = WebComboBoxStyle.expandIcon;
    private ImageIcon collapseIcon = WebComboBoxStyle.collapseIcon;
    private int iconSpacing = WebComboBoxStyle.iconSpacing;
    private boolean drawBorder = WebComboBoxStyle.drawBorder;
    private int round = WebComboBoxStyle.round;
    private int shadeWidth = WebComboBoxStyle.shadeWidth;
    private boolean drawFocus = WebComboBoxStyle.drawFocus;
    private boolean mouseWheelScrollingEnabled = WebComboBoxStyle.mouseWheelScrollingEnabled;

    private MouseWheelListener mouseWheelListener = null;
    private WebButton arrow = null;
    
    
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new WebComboBoxUI ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        final JComboBox comboBox = ( JComboBox ) c;

        // Default settings
        SwingUtils.setOrientation ( comboBox );
        comboBox.setFocusable ( true );
        comboBox.setOpaque ( false );

        // Updating border
        updateBorder ();

        // Drfault renderer
        if ( !( comboBox.getRenderer () instanceof WebComboBoxCellRenderer ) )
        {
            comboBox.setRenderer ( new WebComboBoxCellRenderer ( comboBox ) );
        }

        // Rollover scrolling listener
        mouseWheelListener = new MouseWheelListener ()
        {
            @Override
            public void mouseWheelMoved ( MouseWheelEvent e )
            {
                if ( mouseWheelScrollingEnabled && comboBox.isEnabled () )
                {
                    // Changing selection in case index actually changed
                    final int index = comboBox.getSelectedIndex ();
                    final int newIndex = Math.min ( Math.max ( 0, index + e.getWheelRotation () ), comboBox.getModel ().getSize () - 1 );
                    if ( newIndex != index )
                    {
                        comboBox.setSelectedIndex ( newIndex );
                    }
                }
            }
        };
        comboBox.addMouseWheelListener ( mouseWheelListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstallUI ( JComponent c )
    {
        c.removeMouseWheelListener ( mouseWheelListener );
        mouseWheelListener = null;
        arrow = null;

        super.uninstallUI ( c );
    }
    
    @Override
    protected JButton createArrowButton ()
    {
        arrow = new WebButton ();
        arrow.setUndecorated ( true );
        arrow.setDrawFocus ( false );
        arrow.setMoveIconOnPress ( false );
        arrow.setName ( "ComboBox.arrowButton" );
        arrow.setIcon ( expandIcon );
        arrow.setLeftRightSpacing ( iconSpacing );
        return arrow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureArrowButton ()
    {
        super.configureArrowButton ();
        if ( arrowButton != null )
        {
            arrowButton.setFocusable ( false );
        }
    }
    
    private void updateBorder ()
    {
        if ( drawBorder )
        {
            comboBox.setBorder ( BorderFactory.createEmptyBorder ( shadeWidth + 1, shadeWidth + 1, shadeWidth + 1, shadeWidth + 1 ) );
        }
        else
        {
            comboBox.setBorder ( null );
        }
    }
}
