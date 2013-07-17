package com.jajeem.recorder.design;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import com.alee.extended.filechooser.WebFileChooser;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import com.alee.utils.SwingUtils;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.InstructorNoaUtil;
import com.jajeem.util.Config;

public class Recorder extends WebDialog {

	private WebButton wbtnPlay;
	private WebButton wbtnRecord;

	private static boolean isRecording = false;

	private AudioInputStream audioInputStream;
	private static ArrayList<String> recordingsList = new ArrayList<>();

	Capture capt = new Capture();

	Playback play = new Playback();
	private WebButton wbtnRecordDesktopOnly;
	private WebButton wbtnRecordBoth;
	private WebButton wbtnRecordStudent;
	private ArrayList<String> selectedStudent;
	private boolean isGroupSelected;
	private Recorder frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Recorder frame = new Recorder(new ArrayList<String>(),false,true);
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
	public Recorder(ArrayList<String> selections,boolean isGroup,boolean isInstructor) {
		frame = this;
		selectedStudent = selections;
		isGroupSelected = isGroup;
		setAlwaysOnTop(true);
		setModal(false);
//		setRound(0);

		setResizable(false);
		setBounds(100, 100, 211, 295);
		getContentPane().setLayout(null);

		wbtnRecordStudent = new WebButton();
		wbtnRecordStudent.setVisible(true);
		wbtnRecordStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isGroupSelected){
					for (int i = 0; i < recordingsList.size(); i++) {
						if(recordingsList.contains(selectedStudent.get(i))){
							SendStopRecordCommandTo(selectedStudent.get(i));
							recordingsList.remove(selectedStudent.get(i));
							wbtnRecordStudent.setText("Record Student");
							wbtnRecordStudent.setEnabled(true);
						}
						else{
							recordingsList.add(selectedStudent.get(i));
							SendStartRecordCommandTo(selectedStudent.get(i));
							wbtnRecordStudent.setText("Recording Started");
							wbtnRecordStudent.setEnabled(false);
						}
					}
					frame.dispose();
				}
				else{
					if(selectedStudent.size()!=0){
						if(recordingsList.contains(selectedStudent.get(0))){
							SendStopRecordCommandTo(selectedStudent.get(0));
							recordingsList.remove(selectedStudent.get(0));
							wbtnRecordStudent.setText("Record Student");
							wbtnRecordStudent.setEnabled(true);
						}
						else{
							recordingsList.add(selectedStudent.get(0));
							SendStartRecordCommandTo(selectedStudent.get(0));
							wbtnRecordStudent.setText("Recording Started");
							wbtnRecordStudent.setEnabled(false);
							frame.dispose();
						}
					}
				}
			}
		});
		wbtnRecordStudent.setText("Record Student");
		wbtnRecordStudent.setBounds(10, 131, 146, 29);
		if(isInstructor)
			getContentPane().add(wbtnRecordStudent);
		
		wbtnPlay = new WebButton();
		wbtnPlay.setVisible(false);
		wbtnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebFileChooser fileopener = new WebFileChooser(null);
				fileopener.setAlwaysOnTop(true);
				fileopener.setCurrentDirectory("/");
				int command = fileopener.showDialog();
				if(command == 0){
					File file = fileopener.getSelectedFile();
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		wbtnPlay.setText("Play");
		wbtnPlay.setBounds(10, 131, 146, 29);
//		getContentPane().add(wbtnPlay);

		wbtnRecord = new WebButton();
		wbtnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wbtnRecord.getText().equals("Record Voice Only")) {
					wbtnPlay.setEnabled(false);
					audioInputStream = capt.audioInputStream;
					capt.start();
					wbtnRecord.setText("Stop");
					wbtnRecordBoth.setEnabled(false);
					wbtnRecordDesktopOnly.setEnabled(false);
					wbtnPlay.setEnabled(false);
				} else {
					wbtnPlay.setEnabled(true);
					wbtnRecord.setText("Record Voice Only");
					capt.stop();
					wbtnRecordBoth.setEnabled(true);
					wbtnRecordDesktopOnly.setEnabled(true);
					wbtnPlay.setEnabled(true);
					try {
						Thread.sleep(500);
						File filetemp = new File("Recordings");
						if(!filetemp.exists())
							filetemp.mkdir();
						else
							if(!filetemp.isDirectory()){
								filetemp.delete();
								filetemp.mkdir();
							}
						File output = new File("Recordings","recording - "+System.currentTimeMillis()+".mp3");
						FileOutputStream file = new FileOutputStream(output);
						AudioSystem.write(capt.audioInputStream, AudioFileFormat.Type.AIFF, file);
						file.flush();
						file.close();
						
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
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				CaptureScreenToFile capture = null;
				if(wbtnRecordDesktopOnly.getText().equals("Record Desktop Only")){
					capture = new CaptureScreenToFile();
					capture.StartCaputre("temp.mp4");
					wbtnRecordDesktopOnly.setText("Stop");
					wbtnRecord.setEnabled(false);
					wbtnRecordBoth.setEnabled(false);
				}
				else{
					capture.StopCapture();
					wbtnRecordDesktopOnly.setText("Record Desktop Only");
					wbtnRecord.setEnabled(true);
					wbtnRecordBoth.setEnabled(true);
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
				if(wbtnRecordBoth.getText().equals("Record Both")){
					capture = new CaptureScreenToFile();
					capture.StartCaputreWithAudio("");
					wbtnRecordBoth.setText("Stop");
					wbtnRecordDesktopOnly.setEnabled(false);
					wbtnRecord.setEnabled(false);
				}
				else{
					capture.StopCapture();
					wbtnRecordBoth.setText("Record Both");
					wbtnRecordDesktopOnly.setEnabled(true);
					wbtnRecord.setEnabled(true);
				}
			}
		});
		wbtnRecordBoth.setText("Record Both");
		wbtnRecordBoth.setBounds(10, 91, 146, 29);
		getContentPane().add(wbtnRecordBoth);
		// pack();
		// setVisible(true);
	}

	protected void SendStopRecordCommandTo(String ip) {
		try {
			new Config();
			ServerService service;
			if(InstructorNoa.getServerService() == null)
				service = new ServerService();
			else
				service = InstructorNoa.getServerService();
			
			StopStudentRecordCommand cmd = new StopStudentRecordCommand(InetAddress
					.getLocalHost().getHostAddress(),
					ip, Integer.parseInt(Config
							.getParam("port")));

			service.send(cmd);
			
		} catch (Exception e) {
		}
	}

	protected void SendStartRecordCommandTo(String ip) {
		try {
			new Config();
			ServerService service;
			if(InstructorNoa.getServerService() == null)
				service = new ServerService();
			else
				service = InstructorNoa.getServerService();
			
			StartStudentRecordCommand cmd = new StartStudentRecordCommand(InetAddress
					.getLocalHost().getHostAddress(),
					ip, Integer.parseInt(Config
							.getParam("port")));

			
			service.send(cmd);
			
		} catch (Exception e) {
		}
	}
}
