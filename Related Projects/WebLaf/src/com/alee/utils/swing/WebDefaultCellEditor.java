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

package com.alee.utils.swing;

import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.laf.table.WebTableStyle;
import com.alee.managers.hotkey.Hotkey;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.EventObject;

/**
 * User: mgarin Date: 31.10.12 Time: 16:24
 */

// todo Split into three additional editors
public class WebDefaultCellEditor<C extends JComponent> extends AbstractCellEditor implements TableCellEditor, TreeCellEditor
{
    public static final String COMBOBOX_CELL_EDITOR = "JComboBox.isTableCellEditor";

    protected C editorComponent;
    protected EditorDelegate delegate;
    protected int clickCountToStart = WebTableStyle.clickCountToStartEdit;

    public WebDefaultCellEditor ()
    {
        super ();
    }

    public WebDefaultCellEditor ( final JTextField textField )
    {
        super ();
        editorComponent = ( C ) textField;
        delegate = new EditorDelegate ()
        {
            @Override
            public void setValue ( final Object value )
            {
                textField.setText ( ( value != null ) ? value.toString () : "" );
            }

            @Override
            public Object getCellEditorValue ()
            {
                return textField.getText ();
            }
        };
        textField.addActionListener ( delegate );
    }

    public WebDefaultCellEditor ( final JCheckBox checkBox )
    {
        super ();
        editorComponent = ( C ) checkBox;
        delegate = new EditorDelegate ()
        {
            @Override
            public void setValue ( final Object value )
            {
                boolean selected = false;
                if ( value instanceof Boolean )
                {
                    selected = ( Boolean ) value;
                }
                else if ( value instanceof String )
                {
                    selected = value.equals ( "true" );
                }
                checkBox.setSelected ( selected );
            }

            @Override
            public Object getCellEditorValue ()
            {
                return checkBox.isSelected ();
            }
        };
        checkBox.addActionListener ( delegate );
        checkBox.setRequestFocusEnabled ( false );
    }

    public WebDefaultCellEditor ( final JComboBox comboBox )
    {
        super ();
        editorComponent = ( C ) comboBox;
        if ( comboBox.getUI () instanceof WebComboBoxUI )
        {
            final WebComboBoxUI webComboBoxUI = ( WebComboBoxUI ) comboBox.getUI ();
            webComboBoxUI.setDrawBorder ( false );
        }
        comboBox.putClientProperty ( COMBOBOX_CELL_EDITOR, Boolean.TRUE );
        delegate = new EditorDelegate ()
        {
            @Override
            public void setValue ( final Object value )
            {
                comboBox.setSelectedItem ( value );
            }

            @Override
            public Object getCellEditorValue ()
            {
                return comboBox.getSelectedItem ();
            }

            @Override
            public boolean shouldSelectCell ( final EventObject anEvent )
            {
                if ( anEvent instanceof MouseEvent )
                {
                    final MouseEvent e = ( MouseEvent ) anEvent;
                    return e.getID () != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            @Override
            public boolean stopCellEditing ()
            {
                if ( comboBox.isEditable () )
                {
                    comboBox.actionPerformed ( new ActionEvent ( WebDefaultCellEditor.this, 0, "" ) );
                }
                return super.stopCellEditing ();
            }
        };
        comboBox.addActionListener ( delegate );
    }

    public Component getComponent ()
    {
        return editorComponent;
    }

    public void setClickCountToStart ( final int count )
    {
        clickCountToStart = count;
    }

    public int getClickCountToStart ()
    {
        return clickCountToStart;
    }

    @Override
    public Object getCellEditorValue ()
    {
        return delegate.getCellEditorValue ();
    }

    @Override
    public boolean isCellEditable ( final EventObject anEvent )
    {
        return delegate.isCellEditable ( anEvent );
    }

    @Override
    public boolean shouldSelectCell ( final EventObject anEvent )
    {
        return delegate.shouldSelectCell ( anEvent );
    }

    @Override
    public boolean stopCellEditing ()
    {
        return delegate.stopCellEditing ();
    }

    @Override
    public void cancelCellEditing ()
    {
        delegate.cancelCellEditing ();
    }

    @Override
    public Component getTreeCellEditorComponent ( final JTree tree, final Object value, final boolean isSelected, final boolean expanded,
                                                  final boolean leaf, final int row )
    {
        //String stringValue = tree.convertValueToText ( value, isSelected, expanded, leaf, row, false );
        delegate.setValue ( value );
        return editorComponent;
    }

    @Override
    public Component getTableCellEditorComponent ( final JTable table, final Object value, final boolean isSelected, final int row,
                                                   final int column )
    {
        delegate.setValue ( value );
        return editorComponent;
    }

    protected class EditorDelegate<T> implements ActionListener, ItemListener, Serializable
    {
        protected T value;

        public T getCellEditorValue ()
        {
            return value;
        }

        public void setValue ( final T value )
        {
            this.value = value;
        }

        public boolean isCellEditable ( final EventObject anEvent )
        {
            if ( anEvent == null || anEvent instanceof ActionEvent )
            {
                return true;
            }
            else if ( anEvent instanceof KeyEvent )
            {
                final KeyEvent keyEvent = ( KeyEvent ) anEvent;
                return Hotkey.F2.isTriggered ( keyEvent );
            }
            else if ( anEvent instanceof MouseEvent )
            {
                final MouseEvent mouseEvent = ( MouseEvent ) anEvent;
                return mouseEvent.getClickCount () >= clickCountToStart;
            }
            else
            {
                return false;
            }
        }

        public boolean shouldSelectCell ( final EventObject anEvent )
        {
            return true;
        }

        public boolean stopCellEditing ()
        {
            fireEditingStopped ();
            return true;
        }

        public void cancelCellEditing ()
        {
            fireEditingCanceled ();
        }

        @Override
        public void actionPerformed ( final ActionEvent e )
        {
            WebDefaultCellEditor.this.stopCellEditing ();
        }

        @Override
        public void itemStateChanged ( final ItemEvent e )
        {
            WebDefaultCellEditor.this.stopCellEditing ();
        }
    }
}
