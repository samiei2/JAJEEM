package jrdesktop.viewer;

import java.awt.GraphicsDevice;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

import jrdesktop.Commons;
import jrdesktop.HostProperties;
import jrdesktop.main;

/**
 * ViewerGUI.java
 * 
 * @author benbac
 */

public class ViewerGUIThumbs extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean fullScreenMode = false;

	private Recorder recorder;

	/** Creates new form MainFrame */
	public ViewerGUIThumbs(Recorder recorder) {
		this.recorder = recorder;
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setViewportView(recorder.screenPlayer);
		jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		main.activeConnection++;
	}

	public void Start(JInternalFrame panel) {
		recorder.Start();
		panel.add(jScrollPane1);
		// jBtnStartStopActionPerformed(null);
	}

	private void jBtnStartStopActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnStartStopActionPerformed
		if (recorder.isRecording()) {
			recorder.Stop();
		} else {
			recorder.Start();
		}

		if (recorder.isRecording()) {
			setIconImage(new ImageIcon(Commons.ALIVE_ICON).getImage());
			jBtnStartStop.setIcon(new ImageIcon(Commons.STOP_ICON));
			setTitle("Classmate Viewer [" + recorder.config.server_address
					+ "]");
		} else {
			setTitle("Classmate Viewer");
			setIconImage(new ImageIcon(Commons.WAIT_ICON).getImage());
			jBtnStartStop.setIcon(new ImageIcon(Commons.START_ICON));
			jToggleBtnPauseResume.setSelected(false);
			jBtnViewCtrl.setIcon(new ImageIcon(Commons.LOCKED_INPUTS_ICON));
			jBtnFullNormal.setIcon(new ImageIcon(Commons.FULL_SCREEN_ICON));
			jBtnPartialComplete.setIcon(new ImageIcon(
					Commons.CUSTOM_SCREEN_ICON));
		}
	}// GEN-LAST:event_jBtnStartStopActionPerformed

	private void jBtnViewCtrlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnViewCtrlActionPerformed
		recorder.setViewOnly(!recorder.isViewOnly());
		if (recorder.isViewOnly()) {
			jBtnViewCtrl.setIcon(new ImageIcon(Commons.INPUTS_ICON));
		} else {
			jBtnViewCtrl.setIcon(new ImageIcon(Commons.LOCKED_INPUTS_ICON));
		}
	}// GEN-LAST:event_jBtnViewCtrlActionPerformed

	private void jBtnFullNormalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnFullNormalActionPerformed
		changeFullScreenMode();
	}// GEN-LAST:event_jBtnFullNormalActionPerformed

	private void jBtnPartialCompleteActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnPartialCompleteActionPerformed
		if (recorder.isRecording()) {
			if (recorder.screenPlayer.isPartialScreenMode()) {
				jBtnPartialComplete.setIcon(new ImageIcon(
						Commons.CUSTOM_SCREEN_ICON));
				recorder.screenPlayer.stopSelectingMode();
			} else {
				recorder.screenPlayer.startSelectingMode();
			}
		}
	}// GEN-LAST:event_jBtnPartialCompleteActionPerformed

	private void jCheckBoxImageQualityActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBoxImageQualityActionPerformed
		jComboBoxImageQuality.setEnabled(jCheckBoxImageQuality.isSelected());
		if (!jCheckBoxImageQuality.isSelected()) {
			recorder.viewerOptions.setImageQuality(-1);
		} else {
			recorder.viewerOptions
					.setImageQuality(Float.valueOf(jComboBoxImageQuality
							.getSelectedItem().toString()) / 100.0f);
		}
		if (recorder.isRecording()) {
			if (recorder.config.reverseConnection) {
				recorder.viewerOptions.setChanged(true);
			} else {
				recorder.viewer.setOption(Commons.IMAGE_OPTION);
			}
		}
	}// GEN-LAST:event_jCheckBoxImageQualityActionPerformed

	private void jComboBoxColorQualityItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jComboBoxColorQualityItemStateChanged
		setColorQuality(jComboBoxColorQuality.getSelectedIndex());
	}// GEN-LAST:event_jComboBoxColorQualityItemStateChanged

	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
			jBtnCloseActionPerformed(null);
		} else {
			super.processWindowEvent(evt);
		}
	}// GEN-LAST:event_formWindowClosing

	private void jCheckBoxClipTransActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBoxClipTransActionPerformed
		setClipboardTransfer(jCheckBoxClipTrans.isSelected());
	}// GEN-LAST:event_jCheckBoxClipTransActionPerformed

	private void jBtnFileTransferActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnFileTransferActionPerformed
		jPopupMenuFileTranfer.show(jBtnFileTransfer, 15, 15);
	}// GEN-LAST:event_jBtnFileTransferActionPerformed

	private void jMenuItemSendFilesFromClipbrdActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemSendFilesFromClipbrdActionPerformed
		if (!recorder.isRecording()) {
			return;
		}
		File[] files = recorder.clipbrdUtility.getFiles();
		if (files.length == 0) { // no files in clipboard, return
			JOptionPane.showMessageDialog(ViewerGUIThumbs.this,
					"No file in clipboard !!", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		recorder.fileManager.setFiles(files);
		recorder.viewer.SendFiles();
	}// GEN-LAST:event_jMenuItemSendFilesFromClipbrdActionPerformed

	private void jMenuItemReceiveFilesFromClipbrdActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemReceiveFilesFromClipbrdActionPerformed
		if (!recorder.isRecording()) {
			return;
		}
		recorder.viewer.ReceiveFiles();
	}// GEN-LAST:event_jMenuItemReceiveFilesFromClipbrdActionPerformed

	private void jToggleBtnPauseResumeActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleBtnPauseResumeActionPerformed
		if (recorder.isRecording()) {
			recorder.setPause(!recorder.isPaused());
			if (recorder.isPaused()) {
				jToggleBtnPauseResume.setSelected(true);
			} else {
				jToggleBtnPauseResume.setSelected(false);
			}
		}
	}// GEN-LAST:event_jToggleBtnPauseResumeActionPerformed

	private void jBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnCloseActionPerformed
		if (JOptionPane.showConfirmDialog(null, "Exit Viewer ?",
				"Confirm Dialog", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			recorder.terminate();
			dispose();
			main.activeConnection--;
			main.displayTab(0);
		}
	}// GEN-LAST:event_jBtnCloseActionPerformed

	private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
		if (recorder.viewerOptions.getClipboardTransfer()) {
			recorder.clipbrdUtility.addFlavorListener();
		}
		if (recorder.isRecording() && !recorder.isPaused()
				&& !recorder.isViewOnly()) {
			recorder.eventsListener.addAdapters();
		}
	}// GEN-LAST:event_formWindowActivated

	private void formWindowDeactivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowDeactivated
		if (recorder.viewerOptions.getClipboardTransfer()) {
			recorder.clipbrdUtility.removeFlavorListener();
		}
		recorder.eventsListener.removeAdapters();
	}// GEN-LAST:event_formWindowDeactivated

	private void formWindowIconified(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowIconified
		if (recorder.isRecording()) {
			recorder.setHold(true);
		}
	}// GEN-LAST:event_formWindowIconified

	private void formWindowDeiconified(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowDeiconified
		if (recorder.isRecording()) {
			recorder.setHold(false);
		}
		recorder.Notify();
	}// GEN-LAST:event_formWindowDeiconified

	private void jCheckBoxScreenCompressActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBoxScreenCompressActionPerformed
		recorder.viewerOptions.setScreenCompression(jCheckBoxScreenCompress
				.isSelected());
	}// GEN-LAST:event_jCheckBoxScreenCompressActionPerformed

	private void jComboBoxRefreshRateItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jComboBoxRefreshRateItemStateChanged
		recorder.viewerOptions.setRefreshRate(Integer
				.valueOf(jComboBoxRefreshRate.getSelectedItem().toString()));
	}// GEN-LAST:event_jComboBoxRefreshRateItemStateChanged

	private void jComboBoxImageQualityItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jComboBoxImageQualityItemStateChanged
		recorder.viewerOptions
				.setImageQuality(Float.valueOf(jComboBoxImageQuality
						.getSelectedItem().toString()) / 100.0f);
		if (recorder.isRecording()) {
			if (recorder.config.reverseConnection) {
				recorder.viewerOptions.setChanged(true);
			} else {
				recorder.viewer.setOption(Commons.IMAGE_OPTION);
			}
		}
	}// GEN-LAST:event_jComboBoxImageQualityItemStateChanged

	private void jComboBoxScreenZoomItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jComboBoxScreenZoomItemStateChanged
		recorder.viewerOptions.setScreenScale(Float.valueOf(jComboBoxScreenZoom
				.getSelectedItem().toString()) / 100.0f);
	}// GEN-LAST:event_jComboBoxScreenZoomItemStateChanged

	private void jBtnHelpActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnHelpActionPerformed
		jPopupMenuHelp.show(jBtnHelp, 15, 15);
	}// GEN-LAST:event_jBtnHelpActionPerformed

	private void jMenuItemConnectInfoActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemConnectInfoActionPerformed
		if (!recorder.isRecording()) {
			return;
		}
		if (recorder.config.reverseConnection) {
			recorder.viewerOptions.connectionInfos.display();
		} else {
			recorder.viewer.getConnectionInfos();
		}
	}// GEN-LAST:event_jMenuItemConnectInfoActionPerformed

	private void jMenuItemHostPropsActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemHostPropsActionPerformed
		if (!recorder.isRecording()) {
			return;
		}
		if (recorder.config.reverseConnection) {
			HostProperties.display(recorder.viewerOptions.getProperties());
		} else {
			recorder.viewer.getHostProperties();
		}
	}// GEN-LAST:event_jMenuItemHostPropsActionPerformed

	private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItemAboutActionPerformed
		main.displayTab(4);
	}// GEN-LAST:event_jMenuItemAboutActionPerformed

	public boolean isFullScreenMode() {
		return fullScreenMode;
	}

	public void changeFullScreenMode() {
		GraphicsDevice device = getGraphicsConfiguration().getDevice();
		if (!device.isFullScreenSupported()) {
			return;
		}

		fullScreenMode = !fullScreenMode;

		dispose();
		if (fullScreenMode) { // Full screen mode
			setUndecorated(true);
			device.setFullScreenWindow(this);
			jBtnFullNormal.setIcon(new ImageIcon(Commons.NORMAL_SCREEN_ICON));
		} else { // Normal screen mode
			setUndecorated(false);
			device.setFullScreenWindow(null);
			jBtnFullNormal.setIcon(new ImageIcon(Commons.FULL_SCREEN_ICON));
		}
		setVisible(true);
	}

	public void setColorQuality(int index) {
		byte colorQuality = Commons.defaultColorQuality;
		switch (index) {
		case 0:
			colorQuality = Commons.cqFull;
			break;
		case 1:
			colorQuality = Commons.cq16Bit;
			break;
		case 2:
			colorQuality = Commons.cq256;
			break;
		case 3:
			colorQuality = Commons.cqGray;
			break;
		}
		recorder.viewerOptions.setColorQuality(colorQuality);
		if (recorder.isRecording()) {
			if (recorder.config.reverseConnection) {
				recorder.viewerOptions.setChanged(true);
			} else {
				recorder.viewer.setOption(Commons.COLOR_OPTION);
			}
		}
	}

	public void setClipboardTransfer(boolean bool) {
		recorder.viewerOptions.setClipboardTransfer(bool);
		if (bool) {
			recorder.clipbrdUtility.addFlavorListener();
		} else {
			recorder.clipbrdUtility.removeFlavorListener();
		}
		if (recorder.isRecording()) {
			if (recorder.config.reverseConnection) {
				recorder.viewerOptions.setChanged(true);
			} else {
				recorder.viewer.setOption(Commons.CLIPBOARD_OPTION);
			}
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jBtnClose;
	private javax.swing.JButton jBtnFileTransfer;
	private javax.swing.JButton jBtnFullNormal;
	private javax.swing.JButton jBtnHelp;
	public javax.swing.JButton jBtnPartialComplete;
	private javax.swing.JButton jBtnStartStop;
	private javax.swing.JButton jBtnViewCtrl;
	private javax.swing.JCheckBox jCheckBoxClipTrans;
	private javax.swing.JCheckBox jCheckBoxImageQuality;
	private javax.swing.JCheckBox jCheckBoxScreenCompress;
	private javax.swing.JComboBox jComboBoxColorQuality;
	private javax.swing.JComboBox jComboBoxImageQuality;
	private javax.swing.JComboBox jComboBoxRefreshRate;
	private javax.swing.JComboBox jComboBoxScreenZoom;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JMenuItem jMenuItemAbout;
	private javax.swing.JMenuItem jMenuItemConnectInfo;
	private javax.swing.JMenuItem jMenuItemHostProps;
	private javax.swing.JMenuItem jMenuItemReceiveFilesFromClipbrd;
	private javax.swing.JMenuItem jMenuItemSendFilesFromClipbrd;
	private javax.swing.JPopupMenu jPopupMenuFileTranfer;
	private javax.swing.JPopupMenu jPopupMenuHelp;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JToolBar.Separator jSeparator10;
	private javax.swing.JToolBar.Separator jSeparator11;
	private javax.swing.JToolBar.Separator jSeparator12;
	private javax.swing.JToolBar.Separator jSeparator13;
	private javax.swing.JToolBar.Separator jSeparator14;
	private javax.swing.JToolBar.Separator jSeparator15;
	private javax.swing.JToolBar.Separator jSeparator4;
	private javax.swing.JToolBar.Separator jSeparator5;
	private javax.swing.JToolBar.Separator jSeparator6;
	private javax.swing.JToolBar.Separator jSeparator9;
	private javax.swing.JToggleButton jToggleBtnPauseResume;
	private javax.swing.JToolBar jToolBar1;
	// End of variables declaration//GEN-END:variables

}
