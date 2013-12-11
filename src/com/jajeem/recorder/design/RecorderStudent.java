package com.jajeem.recorder.design;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.jajeem.core.design.student.Student;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;
import com.jajeem.util.i18n;

public class RecorderStudent extends CustomRecorderDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static boolean isRecordingVoice = false;
	private static boolean isRecordingDesktop = false;
	private static boolean isRecordingBoth = false;

	@SuppressWarnings("unused")
	private AudioInputStream audioInputStream;

	Capture capt = new Capture();

	Playback play = new Playback();
	private WebButton wbtnRecord;
	private WebButton wbtnRecordDesktopOnly;
	private WebButton wbtnRecordBoth;
	private RecorderStudent frame;
	final ImageIcon stopIconScaled;
	final ImageIcon recordIconScaled;

	public RecorderStudent(ArrayList<String> selections, boolean isGroup,
			boolean isInstructor) {
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
		} catch (Exception e2) {
			WebOptionPane.showMessageDialog(null, "Look and feel could not be initialized!\nPlease Contact administrator!","Internal Error",WebOptionPane.ERROR_MESSAGE);
		}
		frame = this;
		setAlwaysOnTop(true);
		setModal(false);

		setResizable(false);
		setLocation(211, 295);

		ImageIcon stopIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderstop.png"));
		stopIconScaled = new ImageIcon(stopIcon.getImage().getScaledInstance(
				stopIcon.getIconWidth() - 15, stopIcon.getIconHeight() - 15,
				Image.SCALE_SMOOTH));

		ImageIcon recordIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderrecord.png"));
		recordIconScaled = new ImageIcon(recordIcon.getImage()
				.getScaledInstance(recordIcon.getIconWidth() - 15,
						recordIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));

		CustomRecordButton webButtonRecordVoice = new CustomRecordButton(
				"Record Voice Only");
		wbtnRecord = webButtonRecordVoice;
		webButtonRecordVoice.addActionListener(new ActionListener() {
			private WebDirectoryChooser directoryChooser = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (wbtnRecord.getText().equals("Record Voice Only")) {
					audioInputStream = capt.audioInputStream;
					capt.start();
					isRecordingVoice = true;
					wbtnRecord.setText("Stop");
					wbtnRecord.setIcon(stopIconScaled);
					wbtnRecordBoth.setEnabled(false);
					wbtnRecordDesktopOnly.setEnabled(false);
				} else {
					wbtnRecord.setText("Record Voice Only");
					wbtnRecord.setIcon(recordIconScaled);
					capt.stop();
					isRecordingVoice = false;
					wbtnRecordBoth.setEnabled(true);
					wbtnRecordDesktopOnly.setEnabled(true);
					try {
						Thread.sleep(500);

						if (directoryChooser == null) {
							directoryChooser = new WebDirectoryChooser(frame,
									"Choose directory to save");
						}
						directoryChooser.setVisible(true);

						File filetemp = null;

						if (directoryChooser.getResult() == StyleConstants.OK_OPTION) {
							File file = directoryChooser.getSelectedFolder();
							filetemp = new File(file.getPath());
							if (!filetemp.exists()) {
								filetemp.mkdir();
							}
						} else {
							int resp = 1;

							try {
								resp = JOptionPane.showConfirmDialog(
										frame,
										i18n.getParam("Do you want to save the recording is the default folder (\\Recordings) or discard recording?"),
										i18n.getParam("Confirm"),
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							if (resp == 0) {
								filetemp = new File("Recordings");
							} else {
								return;
							}
						}

						if (!filetemp.exists()) {
							filetemp.mkdir();
						} else if (!filetemp.isDirectory()) {
							filetemp.delete();
							filetemp.mkdir();
						}

						String timeStamp = new SimpleDateFormat(
								"yyyy-MM-dd_HH-mm-ss").format(Calendar
								.getInstance().getTime());

						File output = new File(filetemp.getPath(),
								"recording - " + timeStamp + ".mp3");
						FileOutputStream file = new FileOutputStream(output);
						try {
							AudioSystem.write(capt.audioInputStream,
									AudioFileFormat.Type.WAVE, file);
						} catch (Exception ex) {
							JajeemExcetionHandler.logError(ex, Recorder.class);
						}
						file.flush();
						file.close();
						Desktop.getDesktop().open(output.getParentFile());

					} catch (IOException | InterruptedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		webButtonRecordVoice.setIconTextGap(20);
		webButtonRecordVoice.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon voiceIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderrecord.png"));
		ImageIcon RecordVoiceIconScaled = new ImageIcon(voiceIcon.getImage()
				.getScaledInstance(voiceIcon.getIconWidth() - 15,
						voiceIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		webButtonRecordVoice.setIcon(RecordVoiceIconScaled);
		webButtonRecordVoice.setUndecorated(true);

		CustomRecordButton webButtonRecordDesktop = new CustomRecordButton(
				"Record Desktop Only");
		wbtnRecordDesktopOnly = webButtonRecordDesktop;
		webButtonRecordDesktop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wbtnRecordDesktopOnly.getText().equals(
						"Record Desktop Only")) {
					new CaptureScreenToFile(false);
					CaptureScreenToFile.StartCaputre("temp.mp4");
					isRecordingDesktop = true;
					wbtnRecordDesktopOnly.setText("Stop");
					wbtnRecordDesktopOnly.setIcon(stopIconScaled);
					wbtnRecord.setEnabled(false);
					wbtnRecordBoth.setEnabled(false);
					// progressBarFrame.setVisible(true);
				} else {
					CaptureScreenToFile.StopCapture();
					isRecordingDesktop = false;
					wbtnRecordDesktopOnly.setText("Record Desktop Only");
					wbtnRecordDesktopOnly.setIcon(recordIconScaled);
					wbtnRecord.setEnabled(true);
					wbtnRecordBoth.setEnabled(true);
					// progressBarFrame.setVisible(false);
				}
			}
		});
		ImageIcon desktopIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderrecord.png"));
		ImageIcon RecordDesktopIconScaled = new ImageIcon(desktopIcon
				.getImage().getScaledInstance(desktopIcon.getIconWidth() - 15,
						desktopIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		webButtonRecordDesktop.setIcon(RecordDesktopIconScaled);
		webButtonRecordDesktop.setHorizontalAlignment(SwingConstants.LEFT);
		webButtonRecordDesktop.setIconTextGap(20);
		webButtonRecordDesktop.setUndecorated(true);

		CustomRecordButton webButtonRecordBoth = new CustomRecordButton(
				"Record Both");
		wbtnRecordBoth = webButtonRecordBoth;
		webButtonRecordBoth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wbtnRecordBoth.getText().equals("Record Both")) {
					new CaptureScreenToFile(false);
					CaptureScreenToFile.StartCaputreWithAudio("");
					isRecordingBoth = true;
					wbtnRecordBoth.setText("Stop");
					wbtnRecordBoth.setIcon(stopIconScaled);
					wbtnRecordDesktopOnly.setEnabled(false);
					wbtnRecord.setEnabled(false);
				} else {
					CaptureScreenToFile.StopCapture();
					isRecordingBoth = false;
					wbtnRecordBoth.setText("Record Both");
					wbtnRecordBoth.setIcon(recordIconScaled);
					wbtnRecordDesktopOnly.setEnabled(true);
					wbtnRecord.setEnabled(true);
				}
			}
		});
		ImageIcon bothIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderrecord.png"));
		ImageIcon RecordBothIconScaled = new ImageIcon(bothIcon.getImage()
				.getScaledInstance(bothIcon.getIconWidth() - 15,
						bothIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		webButtonRecordBoth.setIcon(RecordBothIconScaled);
		webButtonRecordBoth.setIconTextGap(20);
		webButtonRecordBoth.setHorizontalAlignment(SwingConstants.LEFT);
		webButtonRecordBoth.setUndecorated(true);
		ImageIcon studentIcon = new ImageIcon(
				Recorder.class.getResource("/icons/noa_en/recorderrecord.png"));
		new ImageIcon(studentIcon.getImage().getScaledInstance(
				studentIcon.getIconWidth() - 15,
				studentIcon.getIconHeight() - 15, Image.SCALE_SMOOTH));
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webButtonRecordVoice,
												GroupLayout.PREFERRED_SIZE,
												194, Short.MAX_VALUE)
										.addComponent(webButtonRecordDesktop,
												GroupLayout.PREFERRED_SIZE,
												194, Short.MAX_VALUE)
										.addComponent(webButtonRecordBoth,
												GroupLayout.PREFERRED_SIZE,
												194, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webButtonRecordVoice,
								GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webButtonRecordDesktop,
								GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webButtonRecordBoth,
								GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(87, Short.MAX_VALUE)));
		getMainContentPane().setLayout(groupLayout);

		if (isRecordingVoice) {
			wbtnRecord.setText("Stop");
			wbtnRecord.setIcon(stopIconScaled);
			wbtnRecordBoth.setEnabled(false);
			wbtnRecordDesktopOnly.setEnabled(false);
		} else if (isRecordingDesktop) {
			wbtnRecordDesktopOnly.setText("Stop");
			wbtnRecordDesktopOnly.setIcon(stopIconScaled);
			wbtnRecord.setEnabled(false);
			wbtnRecordBoth.setEnabled(false);
		} else if (isRecordingBoth) {
			wbtnRecordBoth.setText("Stop");
			wbtnRecordBoth.setIcon(stopIconScaled);
			wbtnRecordDesktopOnly.setEnabled(false);
			wbtnRecord.setEnabled(false);
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Student.getRecordButtonStatic().setEnabled(true);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				Student.getRecordButtonStatic().setEnabled(true);
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Config();
					new i18n();

					RecorderStudent frame = new RecorderStudent(
							new ArrayList<String>(), false, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
