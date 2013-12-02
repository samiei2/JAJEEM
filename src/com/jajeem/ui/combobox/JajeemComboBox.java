package com.jajeem.ui.combobox;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.utils.ReflectUtils;

@SuppressWarnings("unchecked")
public class JajeemComboBox extends JComboBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JajeemComboBox ()
    {
        super ();
    }

    public JajeemComboBox ( Vector<?> items )
    {
        super ( items );
    }

    public JajeemComboBox ( Vector<?> items, int selected )
    {
        super ( items );
        setSelectedIndex ( selected );
    }

    public JajeemComboBox ( Vector<?> items, Object selected )
    {
        super ( items );
        setSelectedItem ( selected );
    }

    public JajeemComboBox ( Object[] items )
    {
        super ( items );
    }

    public JajeemComboBox ( Object[] items, int selected )
    {
        super ( items );
        setSelectedIndex ( selected );
    }

	public JajeemComboBox ( Object[] items, Object selected )
    {
        super ( items );
        setSelectedItem ( selected );
    }

    public JajeemComboBox ( ComboBoxModel aModel )
    {
        super ( aModel );
    }

    public JajeemComboBox ( ComboBoxModel aModel, int selected )
    {
        super ( aModel );
        setSelectedIndex ( selected );
    }

    public JajeemComboBox ( ComboBoxModel aModel, Object selected )
    {
        super ( aModel );
        setSelectedItem ( selected );
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof JajeemComboBoxUI ) )
        {
            try
            {
                setUI ( new JajeemComboBoxUI() );
            }
            catch ( Throwable e )
            {
                e.printStackTrace ();
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }

}
