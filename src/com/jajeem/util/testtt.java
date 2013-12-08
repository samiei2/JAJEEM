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
import com.jajeem.ui.radiobutton.JajeemRadioButton;
import com.alee.laf.radiobutton.WebRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import com.jajeem.ui.checkbox.JajeemCheckBox;

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
                jajeemComboBox.setModel(new DefaultComboBoxModel(new String[] {"test 1", "test 2", "test 3"}));
                
                JajeemRadioButton jmrdbtnSdfasfasdfa = new JajeemRadioButton();
                jmrdbtnSdfasfasdfa.setText("sdfasfasdfa");
                
                WebRadioButton wbrdbtnAdsfasdf = new WebRadioButton();
                wbrdbtnAdsfasdf.setText("adsfasdf");
                
                JajeemRadioButton jmrdbtnSadfasdfasdfas = new JajeemRadioButton();
                jmrdbtnSadfasdfasdfas.setFont(new Font("Tahoma", Font.BOLD, 13));
                jmrdbtnSadfasdfasdfas.setIconTextGap(12);
                jmrdbtnSadfasdfasdfas.setText("sadfasdfasdfas");
                
                JajeemCheckBox jajeemCheckBox = new JajeemCheckBox();
                jajeemCheckBox.setText("");
                GroupLayout gl_panel = new GroupLayout(panel);
                gl_panel.setHorizontalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_panel.createSequentialGroup()
                					.addContainerGap()
                					.addComponent(jajeemComboBox, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
                				.addGroup(gl_panel.createSequentialGroup()
                					.addGap(28)
                					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                						.addComponent(jmrdbtnSadfasdfasdfas, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                						.addGroup(gl_panel.createSequentialGroup()
                							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                								.addComponent(wbrdbtnAdsfasdf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                								.addComponent(jmrdbtnSdfasfasdfa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                							.addGap(90)
                							.addComponent(jajeemCheckBox, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)))))
                			.addContainerGap(98, Short.MAX_VALUE))
                );
                gl_panel.setVerticalGroup(
                	gl_panel.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_panel.createSequentialGroup()
                			.addContainerGap()
                			.addComponent(jajeemComboBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_panel.createSequentialGroup()
                					.addGap(64)
                					.addComponent(jmrdbtnSdfasfasdfa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                					.addPreferredGap(ComponentPlacement.RELATED)
                					.addComponent(wbrdbtnAdsfasdf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
                					.addPreferredGap(ComponentPlacement.RELATED)
                					.addComponent(jajeemCheckBox, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
                			.addGap(18)
                			.addComponent(jmrdbtnSadfasdfasdfas, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                			.addContainerGap(37, Short.MAX_VALUE))
                );
                panel.setLayout(gl_panel);
//                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}