package com.jajeem.ui.checkbox;

import com.alee.extended.checkbox.CheckState;
import com.alee.laf.checkbox.CheckIcon;
import com.alee.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Check icon for simple checkbox.
 *
 * @author Mikle Garin
 */

public class SimpleCheckIcon extends CheckIcon
{
    /**
     * Check icons for all states.
     */
    public static List<ImageIcon> CHECK_STATES = new ArrayList<ImageIcon> ();
    public static List<ImageIcon> DISABLED_CHECK_STATES = new ArrayList<ImageIcon> ();

    /**
     * Check icons initialization.
     */
    static
    {
        for ( int i = 1; i <= 4; i++ )
        {
            final ImageIcon icon = new ImageIcon ( JajeemCheckBoxUI.class.getResource ( "/icons/noa_en/checkboxc" + i + ".png" ) );
            CHECK_STATES.add ( icon );
            DISABLED_CHECK_STATES.add ( ImageUtils.getDisabledCopy ( "JajeemCheckBox.disabled.check." + i, icon ) );
        }
    }

    /**
     * Current step.
     */
    protected int step = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doStep ()
    {
        step = nextState == CheckState.checked ? step + 1 : step - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetStep ()
    {
        step = state == CheckState.checked ? 3 : -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTransitionCompleted ()
    {
        return nextState == CheckState.unchecked && step == -1 || nextState == CheckState.checked && step == 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishTransition ()
    {
        this.state = nextState;
        this.nextState = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon ( final Component c, final Graphics2D g2d, final int x, final int y, final int w, final int h )
    {
        if ( step > -1 )
        {
            final ImageIcon icon = enabled ? CHECK_STATES.get ( step ) : DISABLED_CHECK_STATES.get ( step );
            g2d.drawImage ( icon.getImage (), x + w / 2 - getIconWidth () / 2, y + h / 2 - getIconHeight () / 2, null );
        }
    }
}