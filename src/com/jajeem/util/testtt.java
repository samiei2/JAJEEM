package com.jajeem.util;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alee.laf.combobox.WebComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jajeem.ui.combobox.JajeemComboBox;

public class testtt {

    public static void main(String[] args) {
        new testtt();
    }

    public testtt() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel = new JPanel();
                frame.getContentPane().add(panel);
                
                JajeemComboBox jajeemComboBox = new JajeemComboBox();
                GroupLayout gl_panel = new GroupLayout(panel);
                gl_panel.setHorizontalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGap(96)
                			.addComponent(jajeemComboBox, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
                			.addContainerGap(136, Short.MAX_VALUE))
                );
                gl_panel.setVerticalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGap(84)
                			.addComponent(jajeemComboBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                			.addContainerGap(128, Short.MAX_VALUE))
                );
                panel.setLayout(gl_panel);
//                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}