package com.jajeem.videoplayer.design;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import Vlcj;

/**
 * This simple test player shows how to get direct access to the video frame
 * data.
 * <p>
 * This implementation uses the new (1.1.1) libvlc video call-backs function.
 * <p>
 * Since the video frame data is made available, the Java call-back may modify
 * the contents of the frame if required.
 * <p>
 * The frame data may also be rendered into components such as an OpenGL 
 * texture.
 */
public class DirectTestPlayer extends Vlcj {

  // The size does NOT need to match the mediaPlayer size - it's the size that
  // the media will be scaled to
  // Matching the native size will be faster of course
  private final int width = 720;
  private final int height = 480;

// private final int width = 1280;
// private final int height = 720;

  /**
   * Image to render the video frame data.
   */
  private final BufferedImage image;

  private final MediaPlayerFactory factory;

  private final DirectMediaPlayer mediaPlayer;

  private ImagePane imagePane;

  public DirectTestPlayer(String media, String[] args) throws InterruptedException, InvocationTargetException {
    image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
    image.setAccelerationPriority(1.0f);

    SwingUtilities.invokeAndWait(new Runnable() {

      @Override
      public void run() {
        JFrame frame = new JFrame("VLCJ Direct Video Test");
        frame.setIconImage(new ImageIcon(getClass().getResource("/icons/vlcj-logo.png")).getImage());
        imagePane = new ImagePane(image);
        imagePane.setSize(width, height);
        imagePane.setMinimumSize(new Dimension(width, height));
        imagePane.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(imagePane, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent evt) {
            mediaPlayer.release();
            factory.release();
            System.exit(0);
          }
        });
      }

    });

    factory = new MediaPlayerFactory(args);
    mediaPlayer = factory.newDirectMediaPlayer(width, height, new TestRenderCallback());
    mediaPlayer.playMedia(media);

    // Just to show regular media player functions still work...
    Thread.sleep(5000);
    mediaPlayer.nextChapter();
  }

  public static void main(String[] args) throws InterruptedException, InvocationTargetException {
    if(args.length < 1) {
      System.out.println("Specify a single media URL");
      System.exit(1);
    }

    String[] vlcArgs = (args.length == 1) ? new String[] {} : Arrays.copyOfRange(args, 1, args.length);

    new DirectTestPlayer(args[0], vlcArgs);

    // Application will not exit since the UI thread is running
  }

  @SuppressWarnings("serial")
  private final class ImagePane extends JPanel {

    private final BufferedImage image;

    private final Font font = new Font("Sansserif", Font.BOLD, 36);

    public ImagePane(BufferedImage image) {
      this.image = image;
    }

    @Override
    public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.drawImage(image, null, 0, 0);
      // You could draw on top of the image here...
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
      g2.setColor(Color.red);
      g2.setComposite(AlphaComposite.SrcOver.derive(0.3f));
      g2.fillRoundRect(100, 100, 100, 80, 32, 32);
      g2.setComposite(AlphaComposite.SrcOver);
      g2.setColor(Color.white);
      g2.setFont(font);
      g2.drawString("vlcj direct media player", 130, 150);
    }
  }

  private final class TestRenderCallback extends RenderCallbackAdapter {

    public TestRenderCallback() {
      super(new int[width * height]);
    }

    @Override
    public void onDisplay(int[] data) {
      // The image data could be manipulated here...
      image.setRGB(0, 0, width, height, data, 0, width);
      imagePane.repaint();
    }
  }
}}