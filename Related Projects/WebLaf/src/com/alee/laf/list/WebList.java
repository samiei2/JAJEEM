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

package com.alee.laf.list;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.list.editor.DefaultListCellEditor;
import com.alee.laf.list.editor.ListCellEditor;
import com.alee.laf.list.editor.ListEditListener;
import com.alee.utils.CollectionUtils;
import com.alee.utils.ReflectUtils;
import com.alee.utils.SizeUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.swing.FontMethods;
import com.alee.utils.swing.SizeMethods;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * This JList extension class provides a direct access to WebListUI methods.
 * There is also a set of additional methods to simplify some operations with list.
 * <p/>
 * This component should never be used with a non-Web UIs as it might cause an unexpected behavior.
 * You could stil use that component even if WebLaF is not your application L&F as this component will use Web-UI in any case.
 *
 * @author Mikle Garin
 */

public class WebList extends JList implements FontMethods<WebList>, SizeMethods<WebList>
{
    /**
     * List edit lsiteners.
     */
    protected List<ListEditListener> editListeners = new ArrayList<ListEditListener> ( 1 );

    /**
     * Whether this list is editable or not.
     */
    protected boolean editable = false;

    /**
     * List cell editor.
     */
    protected ListCellEditor listCellEditor = null;

    /**
     * Currently edited cell index or -1 if none edited at the moment.
     */
    protected int editedCell = -1;

    /**
     * Whether list allows an empty selection or not.
     * This setting doesn't force initial selection though.
     */
    protected boolean unselectable = true;

    /**
     * Constructs empty list.
     */
    public WebList ()
    {
        super ();
    }

    /**
     * Constructs list with the specified data.
     *
     * @param listData list data
     */
    public WebList ( List listData )
    {
        super ( listData.toArray () );
    }

    /**
     * Constructs list with the specified data.
     *
     * @param listData list data
     */
    public WebList ( Vector listData )
    {
        super ( listData );
    }

    /**
     * Constructs list with the specified data.
     *
     * @param listData list data
     */
    public WebList ( Object[] listData )
    {
        super ( listData );
    }

    /**
     * Constructs list with the specified list model.
     *
     * @param dataModel list model
     */
    public WebList ( ListModel dataModel )
    {
        super ( dataModel );
    }

    /**
     * Returns specific web list cell renderer or null if a custom non-web renderer is installed.
     *
     * @return specific web list cell renderer or null if a custom non-web renderer is installed
     */
    public WebListCellRenderer getWebListCellRenderer ()
    {
        final ListCellRenderer renderer = getCellRenderer ();
        return renderer instanceof WebListCellRenderer ? ( WebListCellRenderer ) renderer : null;
    }

    /**
     * Sets selected value and scrolls view to its cell.
     *
     * @param element element to select
     */
    public void setSelectedValue ( Object element )
    {
        setSelectedValue ( element, true );
    }

    /**
     * Selects all specified values.
     * Values which are not in the list are simply ignored.
     * This method might be slow in case model cannot provide all separate values quickly.
     *
     * @param values values to select
     */
    public void setSelectedValues ( Object[] values )
    {
        setSelectedValues ( CollectionUtils.toList ( values ) );
    }

    /**
     * Selects all specified values.
     * Values which are not in the list are simply ignored.
     * This method might be slow in case model cannot provide all separate values quickly.
     *
     * @param values values to select
     */
    public void setSelectedValues ( Collection values )
    {
        setSelectedValues ( new ArrayList ( values ) );
    }

    /**
     * Selects all specified values.
     * Values which are not in the list are simply ignored.
     * This method might be slow in case model cannot provide all separate values quickly.
     *
     * @param values values to select
     */
    public void setSelectedValues ( List values )
    {
        List<Integer> indices = new ArrayList<Integer> ();
        ListModel model = getModel ();
        for ( int i = 0; i < model.getSize (); i++ )
        {
            if ( values.contains ( model.getElementAt ( i ) ) )
            {
                indices.add ( i );
            }
        }
        setSelectedIndices ( CollectionUtils.toArray ( indices ) );
    }

    /**
     * Returns whether this list is editable or not.
     *
     * @return true if this list is editable, false otherwise
     */
    public boolean isEditable ()
    {
        return editable;
    }

    /**
     * Sets whether this list is editable or not.
     *
     * @param editable whether this list is editable or not
     */
    public void setEditable ( boolean editable )
    {
        this.editable = editable;

        // Installing default cell editor if none added yet
        if ( editable && listCellEditor == null )
        {
            setCellEditor ( createDefaultCellEditor () );
        }
    }

    /**
     * Returns whether rollover selection is enabled for this list or not.
     *
     * @return true if rollover selection is enabled for this list, false otherwise
     */
    public boolean isRolloverSelectionEnabled ()
    {
        return ListRolloverSelectionAdapter.isInstalled ( this );
    }

    /**
     * Sets whether rollover selection is enabled for this list or not.
     *
     * @param enabled whether rollover selection is enabled for this list or not
     */
    public void setRolloverSelectionEnabled ( boolean enabled )
    {
        if ( enabled )
        {
            if ( !isRolloverSelectionEnabled () )
            {
                setHighlightRolloverCell ( false );
                ListRolloverSelectionAdapter.install ( this );
            }
        }
        else
        {
            if ( isRolloverSelectionEnabled () )
            {
                ListRolloverSelectionAdapter.uninstall ( this );
            }
        }
    }

    /**
     * Installs cell editor for this list.
     *
     * @param listCellEditor new cell editor
     */
    public void setCellEditor ( ListCellEditor listCellEditor )
    {
        removeCellEditor ();
        this.listCellEditor = listCellEditor;
        listCellEditor.install ( this );
    }

    /**
     * Returns default cell editor for this list.
     *
     * @return default cell editor for this list
     */
    protected ListCellEditor createDefaultCellEditor ()
    {
        return new DefaultListCellEditor ();
    }

    /**
     * Returns cell editor for this list.
     *
     * @return cell editor for this list
     */
    public ListCellEditor getCellEditor ()
    {
        return listCellEditor;
    }

    /**
     * Uninstalls cell editor from this list.
     */
    public void removeCellEditor ()
    {
        if ( listCellEditor != null )
        {
            listCellEditor.uninstall ( this );
            listCellEditor = null;
        }
    }

    /**
     * Forces selected cell into editing mode.
     */
    public void editSelectedCell ()
    {
        editCell ( getSelectedIndex () );
    }

    /**
     * Forces the cell under specified index into editing mode.
     *
     * @param index index for the cell to edit
     */
    public void editCell ( int index )
    {
        final ListCellEditor cellEditor = getCellEditor ();
        if ( index != -1 && cellEditor != null )
        {
            setSelectedIndex ( index );
            cellEditor.startEdit ( this, index );
        }
    }

    /**
     * Stops cell editing if possible.
     */
    public boolean stopCellEditing ()
    {
        final ListCellEditor cellEditor = getCellEditor ();
        return cellEditor != null && cellEditor.isEditing () && cellEditor.stopEdit ( WebList.this );

    }

    /**
     * Returns whether some list cell is being edited at the moment or not.
     *
     * @return true if some list cell is being edited at the moment, false otherwise
     */
    public boolean isEditing ()
    {
        final ListCellEditor cellEditor = getCellEditor ();
        return cellEditor != null && cellEditor.isEditing ();
    }

    /**
     * Returns whether list allows an empty selection or not.
     *
     * @return true if list allows an empty selection, false otherwise
     */
    public boolean isUnselectable ()
    {
        return unselectable;
    }

    /**
     * Sets whether list allows an empty selection or not.
     *
     * @param unselectable whether list allows an empty selection or not
     */
    public void setUnselectable ( boolean unselectable )
    {
        this.unselectable = unselectable;

        // Updating selection model
        int lead = getLeadSelectionIndex ();
        int[] selected = getSelectedIndices ();
        setSelectionModel ( unselectable ? new DefaultListSelectionModel () : new UnselectableListModel () );
        setSelectedIndices ( selected );
        getSelectionModel ().setLeadSelectionIndex ( lead );
    }

    /**
     * Returns list model size.
     *
     * @return list model size
     */
    public int getModelSize ()
    {
        return getModel ().getSize ();
    }

    /**
     * Returns model value at the specified cell index.
     *
     * @param index cell index
     * @param <T>   value type
     * @return model value at the specified cell index
     */
    public <T> T getValueAt ( int index )
    {
        return ( T ) getModel ().getElementAt ( index );
    }

    /**
     * Adds a listener to the list that's notified each time a change to the data model occurs.
     *
     * @param listener the ListDataListener to be added
     */
    public void addListDataListener ( ListDataListener listener )
    {
        getModel ().addListDataListener ( listener );
    }

    /**
     * Removes a listener from the list that's notified each time a change to the data model occurs.
     *
     * @param listener the ListDataListener to be removed
     */
    public void removeListDataListener ( ListDataListener listener )
    {
        getModel ().removeListDataListener ( listener );
    }

    /**
     * Scrolls list to specified cell.
     *
     * @param index cell index
     */
    public void scrollToCell ( int index )
    {
        if ( index != -1 )
        {
            Rectangle cellBounds = getCellBounds ( index, index );
            if ( cellBounds != null )
            {
                scrollRectToVisible ( cellBounds );
            }
        }
    }

    /**
     * Returns whether should decorate selected and rollover cells or not.
     *
     * @return true if should decorate selected and rollover cells, false otherwise
     */
    public boolean isDecorateSelection ()
    {
        return getWebUI ().isDecorateSelection ();
    }

    /**
     * Sets whether should decorate selected and rollover cells or not.
     *
     * @param decorateSelection whether should decorate selected and rollover cells or not
     */
    public void setDecorateSelection ( boolean decorateSelection )
    {
        getWebUI ().setDecorateSelection ( decorateSelection );
    }

    /**
     * Returns whether should highlight rollover cell or not.
     *
     * @return true if rollover cell is being highlighted, false otherwise
     */
    public boolean isHighlightRolloverCell ()
    {
        return getWebUI ().isHighlightRolloverCell ();
    }

    /**
     * Sets whether should highlight rollover cell or not.
     *
     * @param highlightRolloverCell whether should highlight rollover cell or not
     */
    public void setHighlightRolloverCell ( boolean highlightRolloverCell )
    {
        getWebUI ().setHighlightRolloverCell ( highlightRolloverCell );
    }

    /**
     * Returns cells selection rounding.
     *
     * @return cells selection rounding
     */
    public int getSelectionRound ()
    {
        return getWebUI ().getSelectionRound ();
    }

    /**
     * Sets cells selection rounding.
     *
     * @param selectionRound new cells selection rounding
     */
    public void setSelectionRound ( int selectionRound )
    {
        getWebUI ().setSelectionRound ( selectionRound );
    }

    /**
     * Returns cells selection shade width.
     *
     * @return cells selection shade width
     */
    public int getSelectionShadeWidth ()
    {
        return getWebUI ().getSelectionShadeWidth ();
    }

    /**
     * Sets cells selection shade width.
     *
     * @param selectionShadeWidth new cells selection shade width
     */
    public void setSelectionShadeWidth ( int selectionShadeWidth )
    {
        getWebUI ().setSelectionShadeWidth ( selectionShadeWidth );
    }

    /**
     * Returns whether to scroll list down to selection automatically or not.
     *
     * @return true if list is being automatically scrolled to selection, false otherwise
     */
    public boolean isAutoScrollToSelection ()
    {
        return getWebUI ().isAutoScrollToSelection ();
    }

    /**
     * Sets whether to scroll list down to selection automatically or not.
     *
     * @param autoScrollToSelection whether to scroll list down to selection automatically or not
     */
    public void setAutoScrollToSelection ( boolean autoScrollToSelection )
    {
        getWebUI ().setAutoScrollToSelection ( autoScrollToSelection );
    }

    /**
     * Returns Web-UI applied to this class.
     *
     * @return Web-UI applied to this class
     */
    public WebListUI getWebUI ()
    {
        return ( WebListUI ) getUI ();
    }

    /**
     * Installs a Web-UI into this component.
     */
    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebListUI ) )
        {
            try
            {
                setUI ( ( WebListUI ) ReflectUtils.createInstance ( WebLookAndFeel.listUI ) );
            }
            catch ( Throwable e )
            {
                e.printStackTrace ();
                setUI ( new WebListUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }

    /**
     * Repaints list cell for the specified value.
     *
     * @param value cell value
     */
    public void repaint ( Object value )
    {
        ListModel model = getModel ();
        if ( model instanceof WebListModel )
        {
            repaint ( ( ( WebListModel ) model ).indexOf ( value ) );
        }
        else
        {
            for ( int i = 0; i < model.getSize (); i++ )
            {
                if ( model.getElementAt ( i ) == value )
                {
                    repaint ( i );
                    break;
                }
            }
        }
    }

    /**
     * Repaints list cell under the specified index.
     *
     * @param index cell index
     */
    public void repaint ( int index )
    {
        repaint ( index, index );
    }

    /**
     * Repaints all list cells between the specified indices.
     *
     * @param from first cell index
     * @param to   last cell index
     */
    public void repaint ( int from, int to )
    {
        final Rectangle cellBounds = getCellBounds ( from, to );
        if ( cellBounds != null )
        {
            repaint ( cellBounds );
        }
    }

    /**
     * Returns currently edited cell index or -1 if none edited at the moment.
     *
     * @return currently edited cell index or -1 if none edited at the moment
     */
    public int getEditedCell ()
    {
        return editedCell;
    }

    /**
     * Adds list edit listener into this list.
     *
     * @param listener list edit listener to add
     */
    public void addListEditListener ( ListEditListener listener )
    {
        editListeners.add ( listener );
    }

    /**
     * Removes list edit lsitener from this list.
     *
     * @param listener list edit listener to remove
     */
    public void removeListEditListener ( ListEditListener listener )
    {
        editListeners.remove ( listener );
    }

    /**
     * Informs all listener that editing was started.
     *
     * @param index edited cell index
     */
    public void fireEditStarted ( int index )
    {
        editedCell = index;
        for ( ListEditListener listener : CollectionUtils.copy ( editListeners ) )
        {
            listener.editStarted ( index );
        }
    }

    /**
     * Informs all listener that editing was finished.
     *
     * @param index    edited cell index
     * @param oldValue old cell value
     * @param newValue new cell value
     */
    public void fireEditFinished ( int index, Object oldValue, Object newValue )
    {
        editedCell = -1;
        for ( ListEditListener listener : CollectionUtils.copy ( editListeners ) )
        {
            listener.editFinished ( index, oldValue, newValue );
        }
    }

    /**
     * Informs all listener that editing was cancelled.
     *
     * @param index edited cell index
     */
    public void fireEditCancelled ( int index )
    {
        editedCell = -1;
        for ( ListEditListener listener : CollectionUtils.copy ( editListeners ) )
        {
            listener.editCancelled ( index );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setPlainFont ()
    {
        return SwingUtils.setPlainFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setPlainFont ( boolean apply )
    {
        return SwingUtils.setPlainFont ( this, apply );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlainFont ()
    {
        return SwingUtils.isPlainFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setBoldFont ()
    {
        return SwingUtils.setBoldFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setBoldFont ( boolean apply )
    {
        return SwingUtils.setBoldFont ( this, apply );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBoldFont ()
    {
        return SwingUtils.isBoldFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setItalicFont ()
    {
        return SwingUtils.setItalicFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setItalicFont ( boolean apply )
    {
        return SwingUtils.setItalicFont ( this, apply );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItalicFont ()
    {
        return SwingUtils.isItalicFont ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontStyle ( boolean bold, boolean italic )
    {
        return SwingUtils.setFontStyle ( this, bold, italic );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontStyle ( int style )
    {
        return SwingUtils.setFontStyle ( this, style );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontSize ( int fontSize )
    {
        return SwingUtils.setFontSize ( this, fontSize );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList changeFontSize ( int change )
    {
        return SwingUtils.changeFontSize ( this, change );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFontSize ()
    {
        return SwingUtils.getFontSize ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontSizeAndStyle ( int fontSize, boolean bold, boolean italic )
    {
        return SwingUtils.setFontSizeAndStyle ( this, fontSize, bold, italic );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontSizeAndStyle ( int fontSize, int style )
    {
        return SwingUtils.setFontSizeAndStyle ( this, fontSize, style );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebList setFontName ( String fontName )
    {
        return SwingUtils.setFontName ( this, fontName );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontName ()
    {
        return SwingUtils.getFontName ( this );
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
    public WebList setPreferredWidth ( int preferredWidth )
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
    public WebList setPreferredHeight ( int preferredHeight )
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
    public WebList setMinimumWidth ( int minimumWidth )
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
    public WebList setMinimumHeight ( int minimumHeight )
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
}