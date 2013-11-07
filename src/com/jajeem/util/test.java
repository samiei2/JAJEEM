package com.jajeem.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class test {

    public static void main(String[] args) {
        new test();
    }

    public test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JList list;
        private BufferedImage background;

        public TestPane() {
            setLayout(new BorderLayout());
            try {
            	URL inp = CustomScrollPane.class.getResource("/icons/noa_en/jscrollpanebackground.png");
                background = ImageIO.read(inp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            int count = 50;
            String[] values = new String[count];
            for (int index = 0; index < count; index++) {
                values[index] = "Testing " + (index + 1);
            }

            list = new JList(values);
            list.setOpaque(false);
            list.setBackground(new Color(0, 0, 0, 0));
            list.setForeground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            add(scrollPane);

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = getWidth() - background.getWidth();
                int y = getHeight() - background.getHeight();
                g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }
        }
    }
}
