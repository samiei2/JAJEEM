package jrdesktop.viewer;

import java.awt.GraphicsDevice;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

import jrdesktop.Commons;
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

	private void jBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnCloseActionPerformed
		if (JOptionPane.showConfirmDialog(null, "Exit Viewer ?",
				"Confirm Dialog", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			recorder.terminate();
			dispose();
			main.activeConnection--;
			main.displayTab(0);
		}
	}// GEN-LAST:event_jBtnCloseActionPerformed

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
	private javax.swing.JPopupMenu jPopupMenuFileTranfer;
	private javax.swing.JPopupMenu jPopupMenuHelp;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToggleButton jToggleBtnPauseResume;

}
