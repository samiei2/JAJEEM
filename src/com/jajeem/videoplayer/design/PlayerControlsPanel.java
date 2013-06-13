/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013 Caprica Software Limited.
 */

package com.jajeem.videoplayer.design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.jajeem.command.model.StartVideoCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;
import com.jajeem.whiteboard.client.Client.design.MyFileFilter;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class PlayerControlsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int SKIP_TIME_MS = 10 * 1000;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final EmbeddedMediaPlayer mediaPlayer;

    private JLabel timeLabel;
//    private JProgressBar positionProgressBar;
    private JSlider positionSlider;
    private JLabel chapterLabel;

    private JButton previousChapterButton;
    private JButton rewindButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton fastForwardButton;
    private JButton nextChapterButton;

    private JButton toggleMuteButton;
    private JSlider volumeSlider;

    private JButton captureButton;

    private JButton ejectButton;
    private JButton connectButton;

    private JButton fullScreenButton;

    private JButton subTitlesButton;

    private JFileChooser fileChooser;

    private boolean mousePressedPlaying = false;
    private JButton btnBroadcast;

	private VideoPlayer player;
	private JButton btnRecord;
	
	private String stream = "";

    public PlayerControlsPanel(EmbeddedMediaPlayer mediaPlayer,VideoPlayer player) {
        this.mediaPlayer = mediaPlayer;
        this.player = player;
        createUI();

        executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer), 0L, 1L, TimeUnit.SECONDS);
    }

    private void createUI() {
        createControls();
        layoutControls();
        registerListeners();
    }

    private void createControls() {
        timeLabel = new JLabel("hh:mm:ss");

        // positionProgressBar = new JProgressBar();
        // positionProgressBar.setMinimum(0);
        // positionProgressBar.setMaximum(1000);
        // positionProgressBar.setValue(0);
        // positionProgressBar.setToolTipText("Time");

        positionSlider = new JSlider();
        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);
        positionSlider.setToolTipText("Position");

        chapterLabel = new JLabel("00/00");

        previousChapterButton = new JButton();
        previousChapterButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/STS-icon.png")));
        //previousChapterButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_start_blue.png")));
        previousChapterButton.setToolTipText("Go to previous chapter");

        rewindButton = new JButton();
        rewindButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Rewind-icon.png")));
        rewindButton.setToolTipText("Skip back");

        stopButton = new JButton();
        stopButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Stop-icon.png")));
        stopButton.setToolTipText("Stop");

        pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Pause-icon.png")));
        pauseButton.setToolTipText("Play/pause");

        playButton = new JButton();
        playButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Play-icon.png")));
        playButton.setToolTipText("Play");

        fastForwardButton = new JButton();
        fastForwardButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/FF-icon.png")));
        fastForwardButton.setToolTipText("Skip forward");

        nextChapterButton = new JButton();
        nextChapterButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/End-icon.png")));
        nextChapterButton.setToolTipText("Go to next chapter");
        
        btnRecord = new JButton();
        btnRecord.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/rec-icon.png")));
        btnRecord.setToolTipText("Record");

        toggleMuteButton = new JButton();
        toggleMuteButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Mute-icon.png")));
        toggleMuteButton.setToolTipText("Toggle Mute");

        volumeSlider = new JSlider();
        volumeSlider.setOrientation(JSlider.HORIZONTAL);
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
        volumeSlider.setPreferredSize(new Dimension(100, 40));
        volumeSlider.setToolTipText("Change volume");

        ejectButton = new JButton();
        ejectButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Eject-icon.png")));
//        ejectButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/control_eject_blue.png")));
        ejectButton.setToolTipText("Load/eject media");

        connectButton = new JButton();
        connectButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Connect-icon.png")));
//        connectButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/connect.png")));
        connectButton.setToolTipText("Connect to media");

        fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Play");
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter());
        FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
        fileChooser.addChoosableFileFilter(defaultFilter);
        fileChooser.setFileFilter(defaultFilter);

        fullScreenButton = new JButton();
        fullScreenButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/FS-icon.png")));
//        fullScreenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/image.png")));
        fullScreenButton.setToolTipText("Toggle full-screen");
        
        btnBroadcast = new JButton();
        btnBroadcast.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/broadcast.png")));
        btnBroadcast.setToolTipText("Broadcast current media to all students");

        subTitlesButton = new JButton();
//        subTitlesButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/comment.png")));
        subTitlesButton.setToolTipText("Cycle sub-titles");
    }

    private void layoutControls() {
        setBorder(new EmptyBorder(4, 4, 4, 4));

        setLayout(new BorderLayout());

        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new GridLayout(1, 1));
        // positionPanel.add(positionProgressBar);
        positionPanel.add(positionSlider);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(8, 0));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(positionPanel, BorderLayout.CENTER);
        topPanel.add(chapterLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(previousChapterButton);
        bottomPanel.add(rewindButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(playButton);
        bottomPanel.add(fastForwardButton);
        bottomPanel.add(nextChapterButton);
        bottomPanel.add(btnRecord);

        bottomPanel.add(volumeSlider);
        bottomPanel.add(toggleMuteButton);

        if(!player.isClient())
        	bottomPanel.add(ejectButton);
        
        captureButton = new JButton();
        captureButton.setIcon(new ImageIcon(PlayerControlsPanel.class.getResource("/com/jajeem/images/Camera-icon.png")));
        //        captureButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/camera.png")));
        captureButton.setToolTipText("Take picture");
        bottomPanel.add(captureButton);
                      
        if(!player.isClient())
        	bottomPanel.add(connectButton);

        bottomPanel.add(fullScreenButton);
        if(!player.isClient())
        	bottomPanel.add(btnBroadcast);
        //bottomPanel.add(subTitlesButton);

        add(bottomPanel, BorderLayout.SOUTH);
        
        
        
    }

    /**
     * Broken out position setting, handles updating mediaPlayer
     */
    private void setSliderBasedPosition() {
        if(!mediaPlayer.isSeekable()) {
            return;
        }
        float positionValue = positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        mediaPlayer.setPosition(positionValue);
    }

    private void updateUIState() {
        if(!mediaPlayer.isPlaying()) {
            // Resume play or play a few frames then pause to show current position in video
            mediaPlayer.play();
            if(!mousePressedPlaying) {
                try {
                    // Half a second probably gets an iframe
                    Thread.sleep(500);
                }
                catch(InterruptedException e) {
                    // Don't care if unblocked early
                }
                mediaPlayer.pause();
            }
        }
        long time = mediaPlayer.getTime();
        int position = (int)(mediaPlayer.getPosition() * 1000.0f);
        int chapter = mediaPlayer.getChapter();
        int chapterCount = mediaPlayer.getChapterCount();
        updateTime(time);
        updatePosition(position);
        updateChapter(chapter, chapterCount);
    }

    private void skip(int skipTime) {
        // Only skip time if can handle time setting
        if(mediaPlayer.getLength() > 0) {
            mediaPlayer.skip(skipTime);
            updateUIState();
        }
    }

    private void registerListeners() {
        mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                updateVolume(mediaPlayer.getVolume());
            }
        });

        positionSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mediaPlayer.isPlaying()) {
                    mousePressedPlaying = true;
                    mediaPlayer.pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });

        previousChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.previousChapter();
            }
        });

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(-SKIP_TIME_MS);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.stop();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.pause();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.play();
            }
        });

        fastForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(SKIP_TIME_MS);
            }
        });

        nextChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.nextChapter();
            }
        });
        

        btnRecord.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		int flag;
                fileChooser.setDialogTitle("Save File");
                // set the file filter 
//                MyFileFilter projFileFilter = new MyFileFilter(".avi","*");
//                fileChooser.setFileFilter(projFileFilter);

                // display the dialog to choose file name saved
                
                if(getStream().equals("")){
                	flag = fileChooser.showSaveDialog(null);
	                if( flag == JFileChooser.APPROVE_OPTION) {
	                    // create a media reader
	                    if(!mediaPlayer.mrl().startsWith("file:")){
	                    	new Thread(new Runnable() {
								public void run() {
									String saveFilePath = "";
				                    saveFilePath = fileChooser.getSelectedFile().getPath();
				                    IMediaReader reader = ToolFactory.makeReader(mediaPlayer.mrl());
				                    IMediaWriter writer = ToolFactory.makeWriter(saveFilePath+" - output.mp4", reader);
				                    
				                    // add a debug listener to the writer to see media writer events
				                    writer.addListener(ToolFactory.makeDebugListener());
				                    reader.addListener(writer);
				                   
				                    // read and decode packets from the source file and
				                    // and dispatch decoded audio and video to the writer
				                    while (true) {
				                        if (reader.readPacket() != null) {
				                            break;
				                        }
				                    }
				                    writer.flush();
				                    writer.close();
								}
							});
	                    }
	                }
                }
                else{
                	flag = fileChooser.showSaveDialog(null);
                	if( flag == JFileChooser.APPROVE_OPTION) {
	                    new Thread(new Runnable() {
							public void run() {
								String saveFilePath = "";
			                    saveFilePath = fileChooser.getSelectedFile().getPath();
								IMediaReader reader = ToolFactory.makeReader(getStream());
			                    IMediaWriter writer = ToolFactory.makeWriter(saveFilePath+" - output.mp4", reader);
			                    
			                    // add a debug listener to the writer to see media writer events
			                    writer.addListener(ToolFactory.makeDebugListener());
			                    reader.addListener(writer);
			                   
			                    // read and decode packets from the source file and
			                    // and dispatch decoded audio and video to the writer
								while (true) {
			                        if (reader.readPacket() != null) {
			                            break;
			                        }
			                    }
								writer.flush();
			                    writer.close();
							}
						});
                	}
                }
        	}
        });

        toggleMuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.mute();
            }
        });

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.setVolume(source.getValue());
                // }
            }
        });

        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.saveSnapshot();
            }
        });

        ejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.enableOverlay(false);
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(PlayerControlsPanel.this)) {
                    mediaPlayer.playMedia(fileChooser.getSelectedFile().getAbsolutePath());
                }
                mediaPlayer.enableOverlay(true);
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.enableOverlay(false);
                String mediaUrl = JOptionPane.showInputDialog(PlayerControlsPanel.this, "Enter a media URL", "Connect to media", JOptionPane.QUESTION_MESSAGE);
                if(mediaUrl != null && mediaUrl.length() > 0) {
                    mediaPlayer.playMedia(mediaUrl);
                }
                mediaPlayer.enableOverlay(true);
            }
        });

        fullScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.toggleFullScreen();
            }
        });
        
        btnBroadcast.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
        			System.out.println(mediaPlayer.mrl());
        			new Config();
					ServerService server = new ServerService();
					StartVideoCommand videoCommand = new StartVideoCommand(InetAddress.getLocalHost().getHostAddress(), Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
					if(mediaPlayer.mrl().equals("") || mediaPlayer.mrl().equals("null")){
						videoCommand.setStreamAddress("");
					}
					else{
						if(mediaPlayer.mrl().startsWith("file:")){
							Thread t = new Thread(new Runnable() {
								
								@Override
								public void run() {
									try {
										StreamRtp stream = new StreamRtp(mediaPlayer.mrl(), "");
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
							t.start();
							//StreamRtp stream = new StreamRtp(mediaPlayer.mrl(), "");"
							videoCommand.setStreamAddress("rtp://@"+"230.1.1.1"+":5555");
						}
						else{
							videoCommand.setStreamAddress(mediaPlayer.mrl());
						}
					}
					videoCommand.setClient(true);
					server.send(videoCommand);
        		} catch (Exception e) {
					System.out.println(e.getMessage());
				}
        	}
        });

        subTitlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int spu = mediaPlayer.getSpu();
                if(spu > -1) {
                    spu ++ ;
                    if(spu > mediaPlayer.getSpuCount()) {
                        spu = -1;
                    }
                }
                else {
                    spu = 0;
                }
                mediaPlayer.setSpu(spu);
            }
        });
    }

    private final class UpdateRunnable implements Runnable {

        private final MediaPlayer mediaPlayer;

        private UpdateRunnable(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        @Override
        public void run() {
            final long time = mediaPlayer.getTime();
            final int position = (int)(mediaPlayer.getPosition() * 1000.0f);
            final int chapter = mediaPlayer.getChapter();
            final int chapterCount = mediaPlayer.getChapterCount();

            // Updates to user interface components must be executed on the Event
            // Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer.isPlaying()) {
                        updateTime(time);
                        updatePosition(position);
                        updateChapter(chapter, chapterCount);
                    }
                }
            });
        }
    }

    private void updateTime(long millis) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timeLabel.setText(s);
    }

    private void updatePosition(int value) {
        // positionProgressBar.setValue(value);
        positionSlider.setValue(value);
    }

    private void updateChapter(int chapter, int chapterCount) {
        String s = chapterCount != -1 ? (chapter + 1) + "/" + chapterCount : "-";
        chapterLabel.setText(s);
        chapterLabel.invalidate();
        validate();
    }

    private void updateVolume(int value) {
        volumeSlider.setValue(value);
    }

	public void setStream(String stream) {
		this.stream = stream;
	}
	
	public String getStream() {
		return this.stream;
	}
	
}
