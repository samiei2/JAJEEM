package com.jajeem.util.audiovisualizer.music;

import com.jajeem.util.audiovisualizer.graphics.MultiLineGraphPanel;
import com.jajeem.util.audiovisualizer.music.data.DataFrame;
import com.jajeem.util.audiovisualizer.music.data.calc.NoteConverter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewGUI extends JPanel {

	MultiLineGraphPanel pitchLoudnessBrightness;
	/**
	 * Create the panel.
	 */
	public NewGUI() {
		String fname = null;
        //fname = "samples/combo.wav";
        
        //Create all the components
        Recorder recorder = new QueueRecorder();
        Engine engine = new BasicEngine();
        AudioInput audioSource;
        if (fname != null) {
            audioSource = new BasicAudioFileInput(fname);
        } else {
            audioSource = new BasicAudioInput();
        }
        AudioOutput audioOut = new AudioFileWriter();
        InterviewGUIManager gui = new InterviewGUIManager();
        NoiseFilter noise = new SimpleNoiseFilter();
        
        //Configure the components
        AudioFormat audioInputFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100.0F, 16, 1, 2, 44100.0F, false);
        AudioFormat audioOutputFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100.0F, 16, 1, 2, 44100.0F, false);

        AudioComponents components = new AudioComponents();
        components.setAudioChunkSize(1024);
        components.setAudioInputFormat(audioInputFormat);
        components.setAudioOutputFormat(audioOutputFormat);
        components.setRecorder(recorder);
        components.setAudioInput(audioSource);
        components.setEngine(engine);
        components.setGUIManager(gui);
        components.setAudioOutput(audioOut);
        components.setNoiseFilter(noise);

        noise.setEnabled(false);
        
        //Run the components

        components.initialize();

        components.start();
        
        setSize(400, 100);
        add(gui.getPitchLoudnessBrightness().getPanel());
        
        setVisible(true);
	}
	
	public static void main(String[] args){
		new NewGUI();
	}
}
