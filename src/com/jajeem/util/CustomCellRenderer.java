package com.jajeem.util;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;




public class CustomCellRenderer extends DefaultListCellRenderer  {
         ImageIcon longIcon = new ImageIcon("long.gif");
         ImageIcon shortIcon ;
         
        // This is the only method defined by ListCellRenderer.
        // We just reconfigure the JLabel each time we're called.
         {
        	 try {
        		 shortIcon = new ImageIcon(CustomCellRenderer.class.getResource("/icons/noa_en/groupicon.png"));
			} catch (Exception e) {
			}
         }

        public Component getListCellRendererComponent(
                JList list,              // the list
                Object value,            // value to display
                int index,               // cell index
                boolean isSelected,      // is the cell selected
                boolean cellHasFocus)    // does the cell have focus
        {
            String action = (String)value;
            setText(action);
            setIcon(shortIcon);
            if (isSelected) {
                setBackground(new Color(82,255,0,97));
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }