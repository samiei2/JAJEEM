package jrdesktop.viewer.FileMng;

import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import jrdesktop.Commons;
import jrdesktop.utilities.FileUtility;
import jrdesktop.viewer.Recorder;

import org.jdesktop.swingworker.SwingWorker;


/**
 * FileTransGui.java
 * @author  benbac
 */

public class FileTransGUI extends javax.swing.JFrame {
    private Recorder recorder;
        
    private boolean canceled = false;
    private boolean done = false;
    
    private File[] files;
    private long index = 0;    
    private long count = 0;
    private long size = 0;
    private long startedAt = 0;
    private long duration = 0;
    private long speed = 0;    
    private float leftEstim = 0.0f;
    private float totalEstim = 0.0f;   
    
    /** Creates new form FileTransGui */
    public FileTransGUI(Recorder recorder) {
        this.recorder = recorder;
        initComponents();
        setVisible(true);
        startedAt = System.currentTimeMillis();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabelCurrentFile = new javax.swing.JLabel();
        jLabelFileCount = new javax.swing.JLabel();
        jLabelTotalSize = new javax.swing.JLabel();
        jLabelSpeed = new javax.swing.JLabel();
        jLabelDuration = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("File Transmission");
        setIconImage(new ImageIcon(Commons.IDLE_ICON).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jProgressBar1.setMaximumSize(new java.awt.Dimension(150, 20));
        jProgressBar1.setMinimumSize(new java.awt.Dimension(150, 20));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(150, 20));
        jProgressBar1.setStringPainted(true);

        jLabel1.setText("Current file:");

        jLabelFileCount.setText("0 file(s)");

        jLabelTotalSize.setText("0 KB");

        jLabelSpeed.setText("0 KB/S");

        jLabelDuration.setText("0 Seconds");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelDuration)
                    .add(jLabel1))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabelCurrentFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 314, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabelSpeed)
                        .add(105, 105, 105)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 65, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelFileCount)
                    .add(jLabelTotalSize))
                .addContainerGap())
            .add(jPanel1Layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 471, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelCurrentFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE))
                    .add(jLabelFileCount, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelDuration)
                    .add(jLabelSpeed)
                    .add(jLabelTotalSize))
                .addContainerGap())
        );

        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jrdesktop/images/no.png"))); // NOI18N
        jButtonClose.setText("Cancel");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jButtonClose)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 521, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 7, Short.MAX_VALUE)
                .add(jButtonClose)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-608)/2, (screenSize.height-162)/2, 608, 162);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        if (done) dispose();
        else if (JOptionPane.showConfirmDialog(this, 
               "Stop file transmission ?", "Confirm Dialog", 
               JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
               == JOptionPane.OK_OPTION) {       
                canceled = true;      
                dispose();
            }
}//GEN-LAST:event_jButtonCloseActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (evt.getID() == WindowEvent.WINDOW_CLOSING)
            jButtonCloseActionPerformed(null);        
        else
            super.processWindowEvent(evt);
}//GEN-LAST:event_formWindowClosing
          
    private SwingWorker sendingWorker = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {              
            recorder.fileManager.SendFiles(FileTransGUI.this, files);                 
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void done() {
            Done();
        }      
    };

        
    private SwingWorker receivingWorker = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {        
            recorder.fileManager.ReceiveFiles(FileTransGUI.this, files); 
            throw new UnsupportedOperationException("Not supported yet.");
        }
            
        @Override
        protected void done() {
            Done();
        }             
   };
       
   public boolean isCanceled () {
        return canceled;
   }   
   
   public void Done() {
        done = true;
        jButtonClose.setText("Close");
        jButtonClose.setIcon(new ImageIcon(getClass().
                getResource("/jrdesktop/images/exit.png")));
        setTitle("File Transmission [Done !! ]");       
   }
   
   public void ReceiveFiles (ArrayList arrayList) {
        setTitle("File Transmission [Receiving ...]");       
        recorder.fileManager.setDownloadingFolder(
                arrayList.get(0).toString());
        files = (File[]) arrayList.get(1);
        count = files.length;        
        
        receivingWorker.execute(); 
   }   
   
   public void SendFiles (File[] files) {    
       setTitle("File Transmission [Sending ...]");               
       recorder.fileManager.setUploadingFolder(
               files[0].getParent());        
       this.files = FileUtility.getAllFiles(files); 
       count = this.files.length; 
       sendingWorker.execute(); 
   }
   
   public void setCurrentFile(String filename) {
        jLabelCurrentFile.setText(filename);    
   }   

   public void updateData(long size) {
       this.size = size;
       index ++;
       duration = System.currentTimeMillis() - startedAt;
       speed = size / (duration + 1);
       leftEstim = (float) duration / (float) index;
       totalEstim = leftEstim * (float) count;
       
        jLabelFileCount.setText(String.valueOf(index) + "/" 
               + String.valueOf(count) + " file(s)");         
       jLabelTotalSize.setText(String.valueOf(size / 1000) + " KB");       
       jLabelDuration.setText(String.valueOf(duration / 1000) + " Seconds");
       jLabelSpeed.setText(String.valueOf(speed) + " KB/S");
       jProgressBar1.setMaximum((int) totalEstim);
       jProgressBar1.setValue((int) (leftEstim * index));
   }   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCurrentFile;
    private javax.swing.JLabel jLabelDuration;
    private javax.swing.JLabel jLabelFileCount;
    private javax.swing.JLabel jLabelSpeed;
    private javax.swing.JLabel jLabelTotalSize;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    
}
