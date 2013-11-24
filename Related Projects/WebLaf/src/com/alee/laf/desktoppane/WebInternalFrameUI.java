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

package com.alee.laf.desktoppane;

import com.alee.laf.StyleConstants;
import com.alee.managers.focus.DefaultFocusTracker;
import com.alee.managers.focus.FocusManager;
import com.alee.managers.focus.FocusTracker;
import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom UI for JInternalFrame component.
 *
 * @author Mikle Garin
 */

public class WebInternalFrameUI extends BasicInternalFrameUI
{
    /**
     * Style settings.
     */
    protected int sideSpacing = 1;

    /**
     * Panel focus tracker.
     */
    protected FocusTracker focusTracker;

    /**
     * Whether internal frame is focused or owns focused component or not.
     */
    protected boolean focused = false;

    /**
     * Constructs new internal frame UI.
     *
     * @param b internal frame to which this UI will be applied
     */
    public WebInternalFrameUI ( JInternalFrame b )
    {
        super ( b );
    }

    /**
     * Returns an instance of the WebInternalFrameUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebInternalFrameUI
     */
    public static ComponentUI createUI ( JComponent c )
    {
        return new WebInternalFrameUI ( ( JInternalFrame ) c );
    }

    /**
     * Installs UI in the specified component.
     *
     * @param c component for this UI
     */
    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        SwingUtils.setOrientation ( c );
        c.setOpaque ( false );
        c.setBackground ( new Color ( 90, 90, 90, 220 ) );
        c.setBorder ( BorderFactory.createEmptyBorder () );

        // Focus tracker for the panel content
        focusTracker = new DefaultFocusTracker ()
        {
            @Override
            public void focusChanged ( boolean focused )
            {
                WebInternalFrameUI.this.focused = focused;
                frame.repaint ();
            }
        };
        FocusManager.addFocusTracker ( frame, focusTracker );
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( JComponent c )
    {
        FocusManager.removeFocusTracker ( focusTracker );

        super.uninstallUI ( c );
    }

    /**
     * Creates and returns internal pane north panel.
     *
     * @param w internal pane to process
     * @return north panel for specified internal frame
     */
    @Override
    protected JComponent createNorthPane ( JInternalFrame w )
    {
        titlePane = new WebInternalFrameTitlePane ( w );
        return titlePane;
    }

    /**
     * Creates and returns internal pane west panel.
     *
     * @param w internal pane to process
     * @return west panel for specified internal frame
     */
    @Override
    protected JComponent createWestPane ( JInternalFrame w )
    {
        // todo Proper internal frame resize
        return new JComponent ()
        {
            {
                setOpaque ( false );
            }

            @Override
            public Dimension getPreferredSize ()
            {
                return new Dimension ( 4 + sideSpacing, 0 );
            }
        };
    }

    /**
     * Creates and returns internal pane east panel.
     *
     * @param w internal pane to process
     * @return east panel for specified internal frame
     */
    @Override
    protected JComponent createEastPane ( JInternalFrame w )
    {
        // todo Proper internal frame resize
        return new JComponent ()
        {
            {
                setOpaque ( false );
            }

            @Override
            public Dimension getPreferredSize ()
            {
                return new Dimension ( 4 + sideSpacing, 0 );
            }
        };
    }

    /**
     * Creates and returns internal pane south panel.
     *
     * @param w internal pane to process
     * @return south panel for specified internal frame
     */
    @Override
    protected JComponent createSouthPane ( JInternalFrame w )
    {
        // todo Proper internal frame resize
        return new JComponent ()
        {
            {
                setOpaque ( false );
            }

            @Override
            public Dimension getPreferredSize ()
            {
                return new Dimension ( 0, 4 + sideSpacing );
            }
        };
    }

    /**
     * Paints internal frame.
     *
     * @param g graphics
     * @param c component
     */
    @Override
    public void paint ( Graphics g, JComponent c )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        Object aa = LafUtils.setupAntialias ( g2d );

        // Shape border = LafUtils.getWebBorderShape ( c, StyleConstants.shadeWidth, StyleConstants.round );

        Insets insets = c.getInsets ();
        RoundRectangle2D innerBorder = new RoundRectangle2D.Double ( insets.left + 3 + sideSpacing, insets.top + titlePane.getHeight () - 1,
                c.getWidth () - 1 - insets.left - 3 - sideSpacing - insets.right - 3 - sideSpacing,
                c.getHeight () - 1 - insets.top - titlePane.getHeight () + 1 - insets.bottom - 3 -
                        sideSpacing, ( StyleConstants.bigRound - 1 ) * 2, ( StyleConstants.bigRound - 1 ) * 2 );

        // Border and background
        LafUtils.drawWebStyle ( g2d, c, c.isEnabled () && focused ? StyleConstants.fieldFocusColor : StyleConstants.shadeColor,
                StyleConstants.shadeWidth, StyleConstants.bigRound, true, false );

        // Inner border
        g2d.setPaint ( Color.GRAY );
        g2d.draw ( innerBorder );

        LafUtils.restoreAntialias ( g2d, aa );
    }
}
