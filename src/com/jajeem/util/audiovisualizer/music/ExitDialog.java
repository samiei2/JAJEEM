/*
 * Music Visualizations: http:/github.com/michaelbrooks/music-visualization
 * Copyright 2012, Michael Brooks. BSD License.
 */

/*
 * ExitDialog.java
 *
 * Created on Feb 10, 2010, 9:28:12 PM
 */

package com.jajeem.util.audiovisualizer.music;

import java.awt.Frame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author michael
 */
public class ExitDialog extends javax.swing.JDialog {

    AudioComponents components;

    /** Creates new form ExitDialog */
    public ExitDialog(AudioComponents components) {
        super((Frame)null, "Control");

        this.components = components;
        
        initComponents();
    }

    public JComboBox getVisualizationBox() {
        return visualizationBox;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        visualizationBox = new javax.swing.JComboBox();
        exitButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        visualizationBox.setModel(new SetComboBoxModel());
        visualizationBox.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(visualizationBox, gridBagConstraints);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(exitButton, gridBagConstraints);

        pauseButton.setText("|| / >");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(pauseButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        components.stop();
        components.destroy();
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        if (components.getGUIManager().paused())
            components.getGUIManager().unpause();
        else {
            components.getGUIManager().pause();
        }
    }//GEN-LAST:event_pauseButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JComboBox visualizationBox;
    // End of variables declaration//GEN-END:variables

}

class SetComboBoxModel extends DefaultComboBoxModel {

    @Override
    public void addElement(Object anObject) {
        if (this.getIndexOf(anObject) == -1) {
            super.addElement(anObject);
        }
    }

    @Override
    public void insertElementAt(Object anObject, int index) {
        if (this.getIndexOf(anObject) == -1) {
            super.insertElementAt(anObject, index);
        }
    }
    
}