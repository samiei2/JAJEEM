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

package com.alee.extended.date;

import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebWindow;
import com.alee.laf.text.WebFormattedTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.settings.SettingsMethods;
import com.alee.utils.CollectionUtils;
import com.alee.utils.CompareUtils;
import com.alee.utils.SizeUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.laf.ShapeProvider;
import com.alee.utils.swing.SizeMethods;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a custom component that allows date selection.
 *
 * @author Mikle Garin
 * @see WebCalendar
 */

public class WebDateField extends WebFormattedTextField implements ShapeProvider, SettingsMethods, SizeMethods<WebFormattedTextField>
{
    /**
     * Used icons.
     */
    public static final ImageIcon selectDateIcon = new ImageIcon ( WebDateField.class.getResource ( "icons/date.png" ) );

    /**
     * Date selection listeners.
     */
    protected List<DateSelectionListener> dateSelectionListeners = new ArrayList<DateSelectionListener> ( 1 );

    /**
     * Date display format.
     */
    protected SimpleDateFormat dateFormat = new SimpleDateFormat ( "dd.MM.yyyy" );

    /**
     * Currently selected date.
     */
    protected Date date = null;

    /**
     * UI components.
     */
    protected WebButton popupButton;
    protected WebWindow popup;
    protected WebCalendar calendar;
    protected DateSelectionListener dateSelectionListener;

    /**
     * Constructs new date field.
     */
    public WebDateField ()
    {
        this ( null );
    }

    /**
     * @param drawBorder
     */
    public WebDateField ( final boolean drawBorder )
    {
        this ( null, drawBorder );
    }

    /**
     * @param selectedDate
     */
    public WebDateField ( final Date selectedDate )
    {
        this ( selectedDate, WebDateFieldStyle.drawBorder );
    }

    /**
     * @param drawBorder
     * @param selectedDate
     */
    public WebDateField ( final Date selectedDate, boolean drawBorder )
    {
        super ();

        this.date = selectedDate;

        // Basic field settings
        setOpaque ( false );
        setWebColored ( WebDateFieldStyle.webColored );
        setDrawBackground ( WebDateFieldStyle.drawBackground );
        setBackground ( WebDateFieldStyle.backgroundColor );
        setWebColored ( WebDateFieldStyle.webColored );
        setDrawFocus ( WebDateFieldStyle.drawFocus );

        // Popup button
        popupButton = WebButton.createIconWebButton ( selectDateIcon, WebDateFieldStyle.round );
        popupButton.setFocusable ( false );
        popupButton.setShadeWidth ( 0 );
        popupButton.setMoveIconOnPress ( false );
        popupButton.setRolloverDecoratedOnly ( true );
        popupButton.setCursor ( Cursor.getDefaultCursor () );
        popupButton.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                showCalendarPopup ();
            }
        } );
        setTrailingComponent ( popupButton );

        // Actions
        addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                setDateFromField ();
            }
        } );
        addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( isEnabled () && SwingUtilities.isRightMouseButton ( e ) )
                {
                    showCalendarPopup ();
                }
            }
        } );
        addFocusListener ( new FocusAdapter ()
        {
            @Override
            public void focusLost ( FocusEvent e )
            {
                if ( !SwingUtils.isEqualOrChild ( popup, e.getOppositeComponent () ) )
                {
                    setDateFromField ();
                }
            }
        } );
        addKeyListener ( new KeyAdapter ()
        {
            @Override
            public void keyReleased ( KeyEvent e )
            {
                if ( isEnabled () )
                {
                    if ( Hotkey.ESCAPE.isTriggered ( e ) )
                    {
                        updateFieldFromDate ();
                    }
                    else if ( Hotkey.DOWN.isTriggered ( e ) )
                    {
                        showCalendarPopup ();
                    }
                }
            }
        } );
        addAncestorListener ( new AncestorListener ()
        {
            @Override
            public void ancestorAdded ( AncestorEvent event )
            {
                hideCalendarPopup ();
            }

            @Override
            public void ancestorRemoved ( AncestorEvent event )
            {
                hideCalendarPopup ();
            }

            @Override
            public void ancestorMoved ( AncestorEvent event )
            {
                hideCalendarPopup ();
            }
        } );
        addComponentListener ( new ComponentAdapter ()
        {
            @Override
            public void componentHidden ( ComponentEvent e )
            {
                hideCalendarPopup ();
            }
        } );

        // Initial field date
        updateFieldFromDate ();

        // Initial styling settings
        setDrawBorder ( drawBorder );
        setRound ( WebDateFieldStyle.round );
        setShadeWidth ( WebDateFieldStyle.shadeWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRound ( final int round )
    {
        super.setRound ( round );
        popupButton.setRound ( round );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDrawBorder ( final boolean drawBorder )
    {
        super.setDrawBorder ( drawBorder );
        updateMargin ();
    }

    /**
     * Updates field margin.
     */
    protected void updateMargin ()
    {
        setMargin ( isDrawBorder () ? WebDateFieldStyle.margin : WebDateFieldStyle.undecoratedMargin );
    }

    /**
     * Displays calendar popup.
     */
    protected void showCalendarPopup ()
    {
        // Checking that component is eligable for focus request
        if ( !requestFocusInWindow () && !isFocusOwner () )
        {
            // Cancel operation if component is not eligable for focus yet
            // This might occur if some other component input verifier holds the focus or in some other rare cases
            return;
        }

        // Updating date from field
        setDateFromField ();

        // Create popup if it doesn't exist
        if ( popup == null || calendar == null )
        {
            Window ancestor = SwingUtils.getWindowAncestor ( this );

            // Calendar
            calendar = new WebCalendar ( date );
            calendar.setDrawFocus ( false );
            calendar.setRound ( StyleConstants.smallRound );
            calendar.setShadeWidth ( 0 );

            // Popup window
            popup = new WebWindow ( ancestor );
            popup.setLayout ( new BorderLayout () );
            popup.setCloseOnFocusLoss ( true );
            popup.setWindowOpaque ( false );
            popup.add ( calendar );
            popup.pack ();

            // Correct popup positioning
            updatePopupLocation ();
            ancestor.addPropertyChangeListener ( WebLookAndFeel.COMPONENT_ORIENTATION_PROPERTY, new PropertyChangeListener ()
            {
                @Override
                public void propertyChange ( PropertyChangeEvent evt )
                {
                    if ( popup.isShowing () )
                    {
                        updatePopupLocation ();
                    }
                }
            } );

            // Selection listener
            dateSelectionListener = new DateSelectionListener ()
            {
                @Override
                public void dateSelected ( Date date )
                {
                    hideCalendarPopup ();
                    setDateFromCalendar ();
                    requestFocusInWindow ();
                }
            };
            calendar.addDateSelectionListener ( dateSelectionListener );
        }
        else
        {
            // Updating window location
            updatePopupLocation ();
        }

        // Applying orientation to popup
        SwingUtils.copyOrientation ( WebDateField.this, popup );

        // Showing popup and changing focus
        popup.setVisible ( true );
        calendar.transferFocus ();
    }

    /**
     * Hides calendar popup.
     */
    protected void hideCalendarPopup ()
    {
        if ( popup != null )
        {
            popup.setVisible ( false );
        }
    }

    /**
     * Updates calendar popup location.
     */
    protected void updatePopupLocation ()
    {
        final Point los = WebDateField.this.getLocationOnScreen ();
        final Rectangle gb = popup.getGraphicsConfiguration ().getBounds ();
        final int shadeWidth = isDrawBorder () ? getShadeWidth () : 0;
        final boolean ltr = WebDateField.this.getComponentOrientation ().isLeftToRight ();
        final int w = WebDateField.this.getWidth ();
        final int h = WebDateField.this.getHeight ();

        final int x;
        if ( ltr )
        {
            if ( los.x + shadeWidth + popup.getWidth () <= gb.x + gb.width )
            {
                x = los.x + shadeWidth;
            }
            else
            {
                x = los.x + w - shadeWidth - popup.getWidth ();
            }
        }
        else
        {
            if ( los.x + w - shadeWidth - popup.getWidth () >= gb.x )
            {
                x = los.x + w - shadeWidth - popup.getWidth ();
            }
            else
            {
                x = los.x + shadeWidth;
            }
        }

        final int y;
        if ( los.y + h + popup.getHeight () <= gb.y + gb.height )
        {
            y = los.y + h + ( isDrawBorder () ? 0 : 1 );
        }
        else
        {
            y = los.y - popup.getHeight () - ( isDrawBorder () ? 0 : 1 );
        }

        popup.setLocation ( x, y );
    }

    /**
     * Returns date specified in text field.
     *
     * @return date specified in text field
     */
    protected Date getDateFromField ()
    {
        try
        {
            String text = getText ();
            if ( text != null && !text.trim ().equals ( "" ) )
            {
                return dateFormat.parse ( text );
            }
            else
            {
                return null;
            }
        }
        catch ( Throwable ex )
        {
            return date;
        }
    }

    /**
     * Returns text date representation according to date format.
     *
     * @return text date representation according to date format
     */
    protected String getTextDate ()
    {
        return date != null ? dateFormat.format ( date ) : "";
    }

    /**
     * Returns currently selected date.
     *
     * @return currently selected date
     */
    public Date getDate ()
    {
        return date;
    }

    /**
     * Sets currently selected date.
     *
     * @param date new selected date
     */
    public void setDate ( final Date date )
    {
        setDateImpl ( date, UpdateSource.other );
    }

    /**
     * Updates date using the value from field.
     */
    protected void setDateFromField ()
    {
        setDateImpl ( getDateFromField (), UpdateSource.field );
    }

    /**
     * Updates date using the value from calendar.
     */
    protected void setDateFromCalendar ()
    {
        setDateImpl ( calendar.getDate (), UpdateSource.calendar );
    }

    /**
     * Sets currently selected date and updates component depending on update source.
     *
     * @param date new selected date
     */
    protected void setDateImpl ( final Date date, final UpdateSource source )
    {
        final boolean changed = !CompareUtils.equals ( this.date, date );
        this.date = date;

        // Updating field text even if there is no changes
        // Text still might change due to formatting pattern
        updateFieldFromDate ();

        if ( changed )
        {
            // Updating calendar date
            if ( source != UpdateSource.calendar && calendar != null )
            {
                updateCalendarFromDate ( date );
            }

            // Informing about date selection changes
            fireDateSelected ( date );
        }
    }

    /**
     * Updates text field with currently selected date.
     */
    protected void updateFieldFromDate ()
    {
        setText ( getTextDate () );
    }

    /**
     * Updates date displayed in calendar.
     *
     * @param date new displayed date
     */
    protected void updateCalendarFromDate ( final Date date )
    {
        calendar.removeDateSelectionListener ( dateSelectionListener );
        calendar.setDate ( date, false );
        calendar.addDateSelectionListener ( dateSelectionListener );
    }

    /**
     * Returns date format.
     *
     * @return date format
     */
    public SimpleDateFormat getDateFormat ()
    {
        return dateFormat;
    }

    /**
     * Sets date format.
     *
     * @param dateFormat date format
     */
    public void setDateFormat ( final SimpleDateFormat dateFormat )
    {
        this.dateFormat = dateFormat;
        updateFieldFromDate ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled ( final boolean enabled )
    {
        super.setEnabled ( enabled );
        popupButton.setEnabled ( enabled );
    }

    /**
     * Adds date selection listener.
     *
     * @param listener date selection listener to add
     */
    public void addDateSelectionListener ( final DateSelectionListener listener )
    {
        dateSelectionListeners.add ( listener );
    }

    /**
     * Removes date selection listener.
     *
     * @param listener date selection listener to remove
     */
    public void removeDateSelectionListener ( final DateSelectionListener listener )
    {
        dateSelectionListeners.remove ( listener );
    }

    /**
     * Notifies about date selection change.
     *
     * @param date new selected date
     */
    public void fireDateSelected ( final Date date )
    {
        for ( DateSelectionListener listener : CollectionUtils.copy ( dateSelectionListeners ) )
        {
            listener.dateSelected ( date );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPreferredWidth ()
    {
        return SizeUtils.getPreferredWidth ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDateField setPreferredWidth ( final int preferredWidth )
    {
        return SizeUtils.setPreferredWidth ( this, preferredWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPreferredHeight ()
    {
        return SizeUtils.getPreferredHeight ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDateField setPreferredHeight ( final int preferredHeight )
    {
        return SizeUtils.setPreferredHeight ( this, preferredHeight );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumWidth ()
    {
        return SizeUtils.getMinimumWidth ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDateField setMinimumWidth ( final int minimumWidth )
    {
        return SizeUtils.setMinimumWidth ( this, minimumWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumHeight ()
    {
        return SizeUtils.getMinimumHeight ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDateField setMinimumHeight ( final int minimumHeight )
    {
        return SizeUtils.setMinimumHeight ( this, minimumHeight );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize ()
    {
        return SizeUtils.getPreferredSize ( this, super.getPreferredSize () );
    }

    /**
     * This enumeration represents the type of source that caused view update.
     */
    protected enum UpdateSource
    {
        /**
         * Text field source.
         */
        field,

        /**
         * Calendar source.
         */
        calendar,

        /**
         * Other source.
         */
        other
    }
}