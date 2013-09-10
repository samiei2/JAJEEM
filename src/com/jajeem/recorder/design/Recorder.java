package com.jajeem.recorder.design;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.extended.filechooser.WebFileChooser;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;
import com.jajeem.util.Session;
import com.jajeem.util.i18n;

public class Recorder extends WebDialog {

	private WebButton wbtnPlay;
	private WebButton wbtnRecord;

	private static boolean isRecordingVoice = false;
	private static boolean isRecordingDesktop = false;
	private static boolean isRecordingBoth = false;

	private AudioInputStream audioInputStream;
	private static ArrayList<String> recordingsList = Session
			.getRecordingList();

	Capture capt = new Capture();

	Playback play = new Playback();
	private WebButton wbtnRecordDesktopOnly;
	private WebButton wbtnRecordBoth;
	private WebButton wbtnRecordStudent;
	private ArrayList<String> selectedStudent;
	private boolean isGroupSelected;
	private Recorder frame;
//	public WebFrame progressBarFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(WebLookAndFeel.class
							.getCanonicalName());

					new Config();
					new i18n();

					Recorder frame = new Recorder(new ArrayList<String>(),
							false, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Recorder(ArrayList<String> selections, boolean isGroup,
			boolean isInstructor) {
		frame = this;
		selectedStudent = selections;
		isGroupSelected = isGroup;
		setAlwaysOnTop(true);
		setModal(false);

//		WebProgressBar progressBar = new WebProgressBar();
//		progressBar.setIndeterminate(true);
//		progressBar.setStringPainted(true);
//		progressBar.setString("Recording...");
//		progressBar.setOpaque(false);
//
//		progressBarFrame = new WebFrame();
//		progressBarFrame.add(progressBar);
//		progressBarFrame.setSize(200, 35);
//		progressBarFrame.setLocationRelativeTo(null);
//		progressBarFrame.setUndecorated(true);
//		progressBarFrame.setAlwaysOnTop(true);

		setResizable(false);
		setBounds(100, 100, 211, 295);
		getContentPane().setLayout(null);
		
		
		
		wbtnRecordStudent = new WebButton();
		wbtnRecordStudent.setVisible(true);
		wbtnRecordStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RecordStudent();
			}
		});
		wbtnRecordStudent.setText("Record Student");
		wbtnRecordStudent.setBounds(10, 131, 146, 29);
		if (isInstructor)
			getContentPane().add(wbtnRecordStudent);

		wbtnPlay = new WebButton();
		wbtnPlay.setVisible(false);
		wbtnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebFileChooser fileopener = new WebFileChooser(null);
				fileopener.setAlwaysOnTop(true);
				fileopener.setCurrentDirectory("/");
				int command = fileopener.showDialog();
				if (command == 0) {
					File file = fileopener.getSelectedFile();
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		wbtnPlay.setText("Play");
		wbtnPlay.setBounds(10, 131, 146, 29);
		// getContentPane().add(wbtnPlay);

		wbtnRecord = new WebButton();
		wbtnRecord.addActionListener(new ActionListener() {
			private WebDirectoryChooser directoryChooser = null;

			public void actionPerformed(ActionEvent e) {
				if (wbtnRecord.getText().equals("Record Voice Only")) {
					wbtnPlay.setEnabled(false);
					audioInputStream = capt.audioInputStream;
					capt.start();
					isRecordingVoice = true;
					wbtnRecord.setText("Stop");
					wbtnRecordBoth.setEnabled(false);
					wbtnRecordDesktopOnly.setEnabled(false);
					wbtnPlay.setEnabled(false);
//					progressBarFrame.setVisible(true);
				} else {
					wbtnPlay.setEnabled(true);
					wbtnRecord.setText("Record Voice Only");
					capt.stop();
					isRecordingVoice = false;
					wbtnRecordBoth.setEnabled(true);
					wbtnRecordDesktopOnly.setEnabled(true);
					wbtnPlay.setEnabled(true);
//					progressBarFrame.setVisible(false);
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
							if (!filetemp.exists())
								filetemp.mkdir();
						} else {
							int resp = 1;

							try {
								resp = WebOptionPane.showConfirmDialog(
										frame,
										i18n.getParam("Do you want to save the recording is the default folder (\\Recordings) or discard recording?"),
										i18n.getParam("Confirm"),
										WebOptionPane.YES_NO_OPTION,
										WebOptionPane.QUESTION_MESSAGE);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							if (resp == 0) {
								filetemp = new File("Recordings");
							} else {
								return;
							}
						}

						if (!filetemp.exists())
							filetemp.mkdir();
						else if (!filetemp.isDirectory()) {
							filetemp.delete();
							filetemp.mkdir();
						}

						String timeStamp = new SimpleDateFormat(
								"yyyy-MM-dd_HH-mm-ss").format(Calendar
								.getInstance().getTime());

						File output = new File(filetemp.getPath(),
								"recording - " + timeStamp + ".mp3");
						FileOutputStream file = new FileOutputStream(output);
						try{
							AudioSystem.write(capt.audioInputStream,
									AudioFileFormat.Type.WAVE, file);
						}
						catch(Exception ex){
							JajeemExcetionHandler.logError(ex,Recorder.class);
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
		wbtnRecord.setText("Record Voice Only");
		wbtnRecord.setBounds(10, 11, 146, 29);
		getContentPane().add(wbtnRecord);
		setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
		SwingUtils.equalizeComponentsWidths(wbtnPlay, wbtnRecord);

		wbtnRecordDesktopOnly = new WebButton();
		wbtnRecordDesktopOnly.addActionListener(new ActionListener() {

			private WebDirectoryChooser directoryChooser = null;

			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				CaptureScreenToFile capture = null;
				if (wbtnRecordDesktopOnly.getText().equals(
						"Record Desktop Only")) {
					capture = new CaptureScreenToFile();
					capture.StartCaputre("temp.mp4");
					isRecordingDesktop = true;
					wbtnRecordDesktopOnly.setText("Stop");
					wbtnRecord.setEnabled(false);
					wbtnRecordBoth.setEnabled(false);
//					progressBarFrame.setVisible(true);
				} else {
					capture.StopCapture();
					isRecordingDesktop = false;
					wbtnRecordDesktopOnly.setText("Record Desktop Only");
					wbtnRecord.setEnabled(true);
					wbtnRecordBoth.setEnabled(true);
//					progressBarFrame.setVisible(false);
				}
			}
		});
		wbtnRecordDesktopOnly.setText("Record Desktop Only");
		wbtnRecordDesktopOnly.setBounds(10, 51, 146, 29);
		getContentPane().add(wbtnRecordDesktopOnly);

		wbtnRecordBoth = new WebButton();
		wbtnRecordBoth.addActionListener(new ActionListener() {
			@SuppressWarnings({ "static-access", "null" })
			public void actionPerformed(ActionEvent e) {
				CaptureScreenToFile capture = null;
				if (wbtnRecordBoth.getText().equals("Record Both")) {
					capture = new CaptureScreenToFile();
					capture.StartCaputreWithAudio("");
					isRecordingBoth = true;
					wbtnRecordBoth.setText("Stop");
					wbtnRecordDesktopOnly.setEnabled(false);
					wbtnRecord.setEnabled(false);
//					progressBarFrame.setVisible(true);
				} else {
					capture.StopCapture();
					isRecordingBoth = false;
					wbtnRecordBoth.setText("Record Both");
					wbtnRecordDesktopOnly.setEnabled(true);
					wbtnRecord.setEnabled(true);
//					progressBarFrame.setVisible(false);
				}
			}
		});
		wbtnRecordBoth.setText("Record Both");
		wbtnRecordBoth.setBounds(10, 91, 146, 29);
		getContentPane().add(wbtnRecordBoth);
		
		if(isRecordingVoice){
			wbtnRecord.setText("Stop");
			wbtnRecordBoth.setEnabled(false);
			wbtnRecordDesktopOnly.setEnabled(false);
			wbtnPlay.setEnabled(false);
		}
		else if(isRecordingDesktop){
			wbtnRecordDesktopOnly.setText("Stop");
			wbtnRecord.setEnabled(false);
			wbtnRecordBoth.setEnabled(false);
		}
		else if(isRecordingBoth){
			wbtnRecordBoth.setText("Stop");
			wbtnRecordDesktopOnly.setEnabled(false);
			wbtnRecord.setEnabled(false);
		}
		// pack();
		// setVisible(true);
	}

	public void RecordStudent() {
		if (isGroupSelected) { 
			for (int i = 0; i < selectedStudent.size(); i++) {
				if (recordingsList.contains(selectedStudent.get(i))) {
					SendStopRecordCommandTo(selectedStudent.get(i));
					recordingsList.remove(selectedStudent.get(i));
					wbtnRecordStudent.setText("Record Student");
					wbtnRecordStudent.setEnabled(true);
//					progressBarFrame.setVisible(false);
				} else {
					recordingsList.add(selectedStudent.get(i));
					SendStartRecordCommandTo(selectedStudent.get(i));
					wbtnRecordStudent.setText("Recording Started");
					wbtnRecordStudent.setEnabled(false);
//					progressBarFrame.setVisible(true);
				}
			}
			frame.dispose();
		} else {
			if (selectedStudent.size() != 0) {
				if (recordingsList.contains(selectedStudent.get(0))) {
					SendStopRecordCommandTo(selectedStudent.get(0));
					recordingsList.remove(selectedStudent.get(0));
					wbtnRecordStudent.setText("Record Student");
					wbtnRecordStudent.setEnabled(true);
//					progressBarFrame.setVisible(false);
				} else {
					recordingsList.add(selectedStudent.get(0));
					SendStartRecordCommandTo(selectedStudent.get(0));
					wbtnRecordStudent.setText("Recording Started");
					wbtnRecordStudent.setEnabled(false);
//					progressBarFrame.setVisible(true);
					frame.dispose();
				}
			}
		}
	}

	protected void SendStopRecordCommandTo(String ip) {
		try {
			new Config();
			ServerService service;
			if (InstructorNoa.getServerService() == null)
				service = new ServerService();
			else
				service = InstructorNoa.getServerService();

			StopStudentRecordCommand cmd = new StopStudentRecordCommand(
					InetAddress.getLocalHost().getHostAddress(), ip,
					Integer.parseInt(Config.getParam("port")));

			service.send(cmd);

		} catch (Exception e) {
		}
	}

	protected void SendStartRecordCommandTo(String ip) {
		try {
			new Config();
			ServerService service;
			if (InstructorNoa.getServerService() == null)
				service = new ServerService();
			else
				service = InstructorNoa.getServerService();

			StartStudentRecordCommand cmd = new StartStudentRecordCommand(
					InetAddress.getLocalHost().getHostAddress(), ip,
					Integer.parseInt(Config.getParam("port")));

			service.send(cmd);

		} catch (Exception e) {
		}
	}
}
