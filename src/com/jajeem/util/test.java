package com.jajeem.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.jajeem.core.design.InstructorNoa;

public class test extends JFrame {
	test() {
	}

	public static void main(String[] args) {
		CustomFrame jframe = new CustomFrame();
		jframe.setVisible(true);
	}
}
